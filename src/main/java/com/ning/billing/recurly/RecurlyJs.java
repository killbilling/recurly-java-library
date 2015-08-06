/*
 * Copyright 2010-2014 Ning, Inc.
 * Copyright 2014-2015 The Billing Project, LLC
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

package com.ning.billing.recurly;

import com.google.common.base.Joiner;
import com.google.common.io.BaseEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RecurlyJs {

    private static final Logger log = LoggerFactory.getLogger(RecurlyJs.class);

    // Specific to signature generation
    public static final String PARAMETER_FORMAT = "%s=%s";
    public static final String PARAMETER_SEPARATOR = "&";
    public static final String NONCE_PARAMETER = "nonce";
    public static final String TIMESTAMP_PARAMETER = "timestamp";

    /**
     * Get Recurly.js Signature
     * See spec here: https://docs.recurly.com/deprecated-api-docs/recurlyjs/signatures
     * <p/>
     * Returns a signature key for use with recurly.js BuildSubscriptionForm.
     *
     * @param privateJsKey recurly.js private key
     * @return signature string on success, null otherwise
     */
    public static String getRecurlySignature(String privateJsKey) {
        return getRecurlySignature(privateJsKey, new ArrayList<String>());
    }

    /**
     * Get Recurly.js Signature
     * See spec here: https://docs.recurly.com/deprecated-api-docs/recurlyjs/signatures
     * <p/>
     * Returns a signature key for use with recurly.js BuildSubscriptionForm.
     *
     * @param privateJsKey recurly.js private key
     * @param extraParams extra parameters to include in the signature
     * @return signature string on success, null otherwise
     */
    public static String getRecurlySignature(String privateJsKey, List<String> extraParams) {
        final long unixTime = System.currentTimeMillis() / 1000L;
        final String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return getRecurlySignature(privateJsKey, unixTime, uuid, extraParams);
    }

    /**
     * Get Recurly.js Signature with extra parameter strings in the format "[param]=[value]"
     * See spec here: https://docs.recurly.com/deprecated-api-docs/recurlyjs/signatures
     * <p/>
     * Returns a signature key for use with recurly.js BuildSubscriptionForm.
     *
     * @param privateJsKey recurly.js private key
     * @param unixTime Unix timestamp, i.e. elapsed seconds since Midnight, Jan 1st 1970, UTC
     * @param nonce A randomly generated string (number used only once)
     * @param extraParams extra parameters to include in the signature
     * @return signature string on success, null otherwise
     */
    public static String getRecurlySignature(String privateJsKey, Long unixTime, String nonce, List<String> extraParams) {
        // Mandatory parameters shared by all signatures (as per spec)
        extraParams = (extraParams == null) ? new ArrayList<String>() : extraParams;
        extraParams.add(String.format(PARAMETER_FORMAT, TIMESTAMP_PARAMETER, unixTime));
        extraParams.add(String.format(PARAMETER_FORMAT, NONCE_PARAMETER, nonce));
        String protectedParams = Joiner.on(PARAMETER_SEPARATOR).join(extraParams);

        return generateRecurlyHMAC(privateJsKey, protectedParams) + "|" + protectedParams;
    }

    /**
     * HMAC-SHA1 Hash Generator - Helper method
     * <p/>
     * Returns a signature key for use with recurly.js BuildSubscriptionForm.
     *
     * @param privateJsKey recurly.js private key
     * @param protectedParams protected parameter string in the format: &lt;secure_hash&gt;|&lt;protected_string&gt;
     * @return subscription object on success, null otherwise
     */
    private static String generateRecurlyHMAC(String privateJsKey, String protectedParams) {
        try {
            SecretKey sk = new SecretKeySpec(privateJsKey.getBytes(), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(sk);
            byte[] result = mac.doFinal(protectedParams.getBytes("UTF-8"));
            return BaseEncoding.base16().encode(result).toLowerCase();
        } catch (Exception e) {
            log.error("Error while trying to generate Recurly HMAC signature", e);
            return null;
        }
    }

}