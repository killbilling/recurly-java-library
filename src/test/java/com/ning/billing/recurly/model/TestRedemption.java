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

public class TestRedemption extends TestModelBase {

    @Test(groups = "fast")
    public void testDeserialization() throws Exception {
        // See https://dev.recurly.com/docs/lookup-a-coupon-redemption-on-an-account
        final String redemptionData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<redemption href=\"https://your-subdomain.recurly.com/v2/accounts/1/redemption\">\n" +
                "  <coupon href=\"https://your-subdomain.recurly.com/v2/coupons/special\"/>\n" +
                "  <account href=\"https://your-subdomain.recurly.com/v2/accounts/1\"/>\n" +
                "  <single_use type=\"boolean\">false</single_use>\n" +
                "  <total_discounted_in_cents type=\"integer\">100</total_discounted_in_cents>\n" +
                "  <currency>USD</currency>\n" +
                "  <state>active</state>\n" +
                "  <created_at type=\"datetime\">2011-06-27T12:34:56Z</created_at>\n" +
                "</redemption>";

        final Redemption redemption = xmlMapper.readValue(redemptionData, Redemption.class);
        Assert.assertFalse(redemption.getSingleUse());
        Assert.assertEquals(redemption.getTotalDiscountedInCents(), (Integer) 100);
        Assert.assertEquals(redemption.getCurrency(), "USD");
        Assert.assertEquals(redemption.getState(), "active");
        Assert.assertEquals(redemption.getCreatedAt(), new DateTime("2011-06-27T12:34:56Z"));
    }

    @Test(groups = "fast")
    public void testSerialization() throws Exception {
        final Redemption redemption = new Redemption();
        redemption.setAccountCode("1");
        redemption.setCurrency("USD");

        final String xml = xmlMapper.writeValueAsString(redemption);
        Assert.assertEquals(xml, "<redemption xmlns=\"\">" +
                "<account_code>1</account_code>" +
                "<currency>USD</currency>" +
                "</redemption>");
    }
}
