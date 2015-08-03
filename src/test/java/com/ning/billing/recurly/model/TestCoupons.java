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

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestCoupons extends TestModelBase {

    @Test(groups = "fast")
    public void testDeserialization() throws Exception {
        // See https://dev.recurly.com/docs/list-accounts
        final String accountsData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                    "<coupons type=\"array\">\n" +
                                    "  <coupon href=\"https://api.recurly.com/v2/coupons/cdeb2\">\n" +
                                    "    <redemptions href=\"https://api.recurly.com/v2/coupons/cdeb2/redemptions\"/>\n" +
                                    "    <coupon_code>cdeb2</coupon_code>\n" +
                                    "    <name>test5</name>\n" +
                                    "    <state>redeemable</state>\n" +
                                    "    <description nil=\"nil\"></description>\n" +
                                    "    <discount_type>percent</discount_type>\n" +
                                    "    <discount_percent type=\"integer\">100</discount_percent>\n" +
                                    "    <redeem_by_date type=\"datetime\">2013-09-30T21:00:00Z</redeem_by_date>\n" +
                                    "    <single_use type=\"boolean\">true</single_use>\n" +
                                    "    <applies_for_months type=\"integer\">3</applies_for_months>\n" +
                                    "    <max_redemptions nil=\"nil\"></max_redemptions>\n" +
                                    "    <applies_to_all_plans type=\"boolean\">true</applies_to_all_plans>\n" +
                                    "    <created_at type=\"datetime\">2013-05-08T11:25:05Z</created_at>\n" +
                                    "    <plan_codes type=\"array\">\n" +
                                    "    </plan_codes>\n" +
                                    "    <a name=\"redeem\" href=\"https://api.recurly.com/v2/coupons/cdeb2/redeem\" method=\"post\"/>\n" +
                                    "  </coupon>\n" +
                                    "  <coupon href=\"https://api.recurly.com/v2/coupons/4c236\">\n" +
                                    "    <redemptions href=\"https://api.recurly.com/v2/coupons/4c236/redemptions\"/>\n" +
                                    "    <coupon_code>4c236</coupon_code>\n" +
                                    "    <name>test5</name>\n" +
                                    "    <state>redeemable</state>\n" +
                                    "    <description nil=\"nil\"></description>\n" +
                                    "    <discount_type>percent</discount_type>\n" +
                                    "    <discount_percent type=\"integer\">100</discount_percent>\n" +
                                    "    <redeem_by_date type=\"datetime\">2013-09-30T21:00:00Z</redeem_by_date>\n" +
                                    "    <single_use type=\"boolean\">true</single_use>\n" +
                                    "    <applies_for_months type=\"integer\">3</applies_for_months>\n" +
                                    "    <max_redemptions nil=\"nil\"></max_redemptions>\n" +
                                    "    <applies_to_all_plans type=\"boolean\">true</applies_to_all_plans>\n" +
                                    "    <created_at type=\"datetime\">2013-05-08T11:25:04Z</created_at>\n" +
                                    "    <plan_codes type=\"array\">\n" +
                                    "    </plan_codes>\n" +
                                    "    <a name=\"redeem\" href=\"https://api.recurly.com/v2/coupons/4c236/redeem\" method=\"post\"/>\n" +
                                    "  </coupon>\n" +
                                    "  <coupon href=\"https://api.recurly.com/v2/coupons/558ae\">\n" +
                                    "    <redemptions href=\"https://api.recurly.com/v2/coupons/558ae/redemptions\"/>\n" +
                                    "    <coupon_code>558ae</coupon_code>\n" +
                                    "    <name>test5</name>\n" +
                                    "    <state>redeemable</state>\n" +
                                    "    <description nil=\"nil\"></description>\n" +
                                    "    <discount_type>percent</discount_type>\n" +
                                    "    <discount_percent type=\"integer\">100</discount_percent>\n" +
                                    "    <redeem_by_date type=\"datetime\">2013-09-30T21:00:00Z</redeem_by_date>\n" +
                                    "    <single_use type=\"boolean\">true</single_use>\n" +
                                    "    <applies_for_months type=\"integer\">3</applies_for_months>\n" +
                                    "    <max_redemptions nil=\"nil\"></max_redemptions>\n" +
                                    "    <applies_to_all_plans type=\"boolean\">true</applies_to_all_plans>\n" +
                                    "    <created_at type=\"datetime\">2013-05-08T11:25:03Z</created_at>\n" +
                                    "    <plan_codes type=\"array\">\n" +
                                    "    </plan_codes>\n" +
                                    "    <a name=\"redeem\" href=\"https://api.recurly.com/v2/coupons/558ae/redeem\" method=\"post\"/>\n" +
                                    "  </coupon>\n" +
                                    "  <coupon href=\"https://api.recurly.com/v2/coupons/9442c\">\n" +
                                    "    <redemptions href=\"https://api.recurly.com/v2/coupons/9442c/redemptions\"/>\n" +
                                    "    <coupon_code>9442c</coupon_code>\n" +
                                    "    <name>test5</name>\n" +
                                    "    <state>redeemable</state>\n" +
                                    "    <description nil=\"nil\"></description>\n" +
                                    "    <discount_type>percent</discount_type>\n" +
                                    "    <discount_percent type=\"integer\">100</discount_percent>\n" +
                                    "    <redeem_by_date type=\"datetime\">2013-09-30T21:00:00Z</redeem_by_date>\n" +
                                    "    <single_use type=\"boolean\">true</single_use>\n" +
                                    "    <applies_for_months type=\"integer\">3</applies_for_months>\n" +
                                    "    <max_redemptions nil=\"nil\"></max_redemptions>\n" +
                                    "    <applies_to_all_plans type=\"boolean\">true</applies_to_all_plans>\n" +
                                    "    <created_at type=\"datetime\">2013-05-08T11:25:02Z</created_at>\n" +
                                    "    <plan_codes type=\"array\">\n" +
                                    "    </plan_codes>\n" +
                                    "    <a name=\"redeem\" href=\"https://api.recurly.com/v2/coupons/9442c/redeem\" method=\"post\"/>\n" +
                                    "  </coupon>\n" +
                                    "  <coupon href=\"https://api.recurly.com/v2/coupons/f15a9\">\n" +
                                    "    <redemptions href=\"https://api.recurly.com/v2/coupons/f15a9/redemptions\"/>\n" +
                                    "    <coupon_code>f15a9</coupon_code>\n" +
                                    "    <name>test5</name>\n" +
                                    "    <state>redeemable</state>\n" +
                                    "    <description nil=\"nil\"></description>\n" +
                                    "    <discount_type>percent</discount_type>\n" +
                                    "    <discount_percent type=\"integer\">100</discount_percent>\n" +
                                    "    <redeem_by_date type=\"datetime\">2013-09-30T21:00:00Z</redeem_by_date>\n" +
                                    "    <single_use type=\"boolean\">true</single_use>\n" +
                                    "    <applies_for_months type=\"integer\">3</applies_for_months>\n" +
                                    "    <max_redemptions nil=\"nil\"></max_redemptions>\n" +
                                    "    <applies_to_all_plans type=\"boolean\">true</applies_to_all_plans>\n" +
                                    "    <created_at type=\"datetime\">2013-05-08T11:24:31Z</created_at>\n" +
                                    "    <plan_codes type=\"array\">\n" +
                                    "    </plan_codes>\n" +
                                    "    <a name=\"redeem\" href=\"https://api.recurly.com/v2/coupons/f15a9/redeem\" method=\"post\"/>\n" +
                                    "  </coupon>\n" +
                                    "</coupons>\n";

        final Coupons coupons = xmlMapper.readValue(accountsData, Coupons.class);
        Assert.assertEquals(coupons.size(), 5);
    }
}
