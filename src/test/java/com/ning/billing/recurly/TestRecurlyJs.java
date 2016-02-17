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

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class TestRecurlyJs {

    @Test(groups = "fast")
    public void testGetRecurlySignature() throws Exception {
        final String jsPrivateKey = "0123456789abcdef0123456789abcdef";
        final Long mockUnixTime = 1329942896L;
        final String mockNonce = "unique";
        final String expected = "0da7006b2093fd3d2d24a1f1f414bd9a5810a689|timestamp=1329942896&nonce=unique";

        String actual = RecurlyJs.getRecurlySignature(jsPrivateKey, mockUnixTime, mockNonce, null);
        Assert.assertEquals(actual, expected);
    }

    @Test(groups = "fast")
    public void testGetRecurlySignatureCustomParams() throws Exception {
        final String PARAMETER_FORMAT = "%s=%s";
        final String SUBSCRIPTION_PARAMETER = "subscription%5Bplan_code%5D";
        final String ACCOUNT_CODE_PARAMETER = "account%5Baccount_code%5D";

        final String jsPrivateKey = "0123456789abcdef0123456789abcdef";
        final Long mockUnixTime = 1329942896L;
        final String mockNonce = "unique";
        final String expected = "aa2743b6e686bf50940881733f2da37b551804f5|subscription%5Bplan_code%5D=testsub&account%5Baccount_code%5D=johndoe123&timestamp=1329942896&nonce=unique";

        ArrayList<String> customParams = new ArrayList<String>();
        customParams.add(String.format(PARAMETER_FORMAT, SUBSCRIPTION_PARAMETER, "testsub"));
        customParams.add(String.format(PARAMETER_FORMAT, ACCOUNT_CODE_PARAMETER, "johndoe123"));

        String actual = RecurlyJs.getRecurlySignature(jsPrivateKey, mockUnixTime, mockNonce, customParams);
        Assert.assertEquals(actual, expected);
    }
}
