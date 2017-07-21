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

package com.ning.billing.recurly.model;

import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestRedemptions extends TestModelBase {

    @Test(groups = "fast")
    public void testDeserialization() throws Exception {
        // See https://dev.recurly.com/docs/lookup-a-coupon-redemption-on-an-account
        final String redemptionsData = "<redemptions type=\"array\">\n" +
                "  <redemption href=\"https://your-subdomain.recurly.com/v2/accounts/1/redemptions/316a4213e8fa9e97390aff4995bda9e6\">\n" +
                "    <coupon href=\"https://your-subdomain.recurly.com/v2/coupons/special\"/>\n" +
                "    <account href=\"https://your-subdomain.recurly.com/v2/accounts/1\"/>\n" +
                "    <subscription href=\"https://your-subdomain.recurly.com/v2/subscriptions/315fbd7a25b04f1333ea9f4418994fb5\"/>\n" +
                "    <uuid>316a4213e8fa9e97390aff4995bda9e6</uuid>\n" +
                "    <account_code>1</account_code>" +
                "    <single_use type=\"boolean\">false</single_use>\n" +
                "    <total_discounted_in_cents type=\"integer\">0</total_discounted_in_cents>\n" +
                "    <currency>USD</currency>\n" +
                "    <state>active</state>\n" +
                "    <created_at type=\"dateTime\">2015-09-23T17:13:30Z</created_at>\n" +
                "    <updated_at type=\"dateTime\">2015-09-23T17:13:30Z</updated_at>\n" +
                "  </redemption>\n" +
                "  <redemption href=\"https://your-subdomain.recurly.com/v2/accounts/1/redemptions/3169fd6127ff82ccbfa08a442188d575\">\n" +
                "    <coupon href=\"https://your-subdomain.recurly.com/v2/coupons/special\"/>\n" +
                "    <account href=\"https://your-subdomain.recurly.com/v2/accounts/1\"/>\n" +
                "    <uuid>3169fd6127ff82ccbfa08a442188d575</uuid>\n" +
                "    <single_use type=\"boolean\">false</single_use>\n" +
                "    <total_discounted_in_cents type=\"integer\">1500</total_discounted_in_cents>\n" +
                "    <currency>USD</currency>\n" +
                "    <state>active</state>\n" +
                "    <created_at type=\"dateTime\">2011-06-27T12:34:56Z</created_at>\n" +
                "    <updated_at type=\"dateTime\">2011-06-27T12:34:56Z</updated_at>\n" +
                "  </redemption>\n" +
                "</redemptions>";


        final Redemptions redemptions = xmlMapper.readValue(redemptionsData, Redemptions.class);
        Assert.assertEquals(redemptions.size(), 2);

        final Redemption redemption = redemptions.get(0);
        Assert.assertEquals(redemption.getHref(), "https://your-subdomain.recurly.com/v2/accounts/1/redemptions/316a4213e8fa9e97390aff4995bda9e6");
        Assert.assertEquals(redemption.getAccountCode(), "1");
        Assert.assertEquals(redemption.getState(), "active");
        Assert.assertEquals(redemption.getCurrency(), "USD");
        Assert.assertEquals(redemption.getCreatedAt(), new DateTime("2015-09-23T17:13:30Z"));
        Assert.assertEquals(redemption.getUpdatedAt(), new DateTime("2015-09-23T17:13:30Z"));
    }
}
