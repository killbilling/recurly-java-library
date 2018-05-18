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


import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;

import javax.net.ssl.SSLException;

public class SslUtils {

    private static final String TLS_PROTOCOL_KEY = "killbill.payment.recurly.tlsProtocol";
    private static final String TLS_PROTOCOL_DEFAULT = "TLSv1.2";
    private SslContext context;

    private static class SingletonHolder {
        public static final SslUtils instance = new SslUtils();
    }

    public static SslUtils getInstance() {
        return SingletonHolder.instance;
    }

    public SslContext getSslContext() throws SSLException {
        if (context != null) return this.context;

        final String protocol = System.getProperty(TLS_PROTOCOL_KEY, TLS_PROTOCOL_DEFAULT);

        context = SslContextBuilder.forClient().protocols(protocol).build();

        return context;
    }
}