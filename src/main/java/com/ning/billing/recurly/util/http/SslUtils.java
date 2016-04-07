/*
 * Copyright 2014 Groupon, Inc
 * Copyright 2014 The Billing Project, LLC
 *
 * The Billing Project licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.ning.billing.recurly.util.http;


import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class SslUtils {

    static class LooseTrustManager implements X509TrustManager {

        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[0];
        }

        public void checkClientTrusted(final java.security.cert.X509Certificate[] certs, final String authType) {
        }

        public void checkServerTrusted(final java.security.cert.X509Certificate[] certs, final String authType) {
        }
    }

    private SSLContext looseTrustManagerSSLContext = looseTrustManagerSSLContext();

    private SSLContext looseTrustManagerSSLContext() {
        try {
            final SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, new TrustManager[]{new LooseTrustManager()}, new SecureRandom());
            return sslContext;
        } catch (final NoSuchAlgorithmException e) {
            throw new ExceptionInInitializerError(e);
        } catch (final KeyManagementException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private static class SingletonHolder {
        public static final SslUtils instance = new SslUtils();
    }

    public static SslUtils getInstance() {
        return SingletonHolder.instance;
    }

    public SSLContext getSSLContext(final boolean acceptAnyCertificate) throws GeneralSecurityException {
        return acceptAnyCertificate ? looseTrustManagerSSLContext : SSLContext.getDefault();
    }
}