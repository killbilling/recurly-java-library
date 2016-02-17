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
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class TestCoupon extends TestModelBase {

    @Test(groups = "fast")
    public void testDeserializationPercent() throws Exception {
        // See https://dev.recurly.com/docs/list-active-coupons
        final String couponData =
                "<coupon href=\"https://api.recurly.com/v2/coupons/f8028\">\n" +
                "  <redemptions href=\"https://api.recurly.com/v2/coupons/f8028/redemptions\"/>\n" +
                "  <coupon_code>f8028</coupon_code>\n" +
                "  <name>t</name>\n" +
                "  <state>redeemable</state>\n" +
                "  <description nil=\"nil\"></description>\n" +
                "  <discount_type>percent</discount_type>\n" +
                "  <discount_percent type=\"integer\">100</discount_percent>\n" +
                "  <redeem_by_date type=\"datetime\">2013-10-10T00:00:00Z</redeem_by_date>\n" +
                "  <single_use type=\"boolean\">true</single_use>\n" +
                "  <applies_for_months type=\"integer\">1</applies_for_months>\n" +
                "  <max_redemptions nil=\"nil\"></max_redemptions>\n" +
                "  <applies_to_all_plans type=\"boolean\">true</applies_to_all_plans>\n" +
                "  <created_at type=\"datetime\">2013-05-08T10:21:52Z</created_at>\n" +
                "  <plan_codes type=\"array\">\n" +
                "  </plan_codes>\n" +
                "  <a name=\"redeem\" href=\"https://api.recurly.com/v2/coupons/f8028/redeem\" method=\"post\"/>\n" +
                "</coupon>\n";

        final Coupon coupon = xmlMapper.readValue(couponData, Coupon.class);

        assertEquals(coupon.getHref(), "https://api.recurly.com/v2/coupons/f8028");
        assertEquals(coupon.getCouponCode(), "f8028");
        assertEquals(coupon.getName(), "t");
        assertEquals(coupon.getState(), "redeemable");
        assertEquals(coupon.getDiscountType(), "percent");
        assertEquals(coupon.getDiscountPercent(), new Integer(100));
        assertEquals(coupon.getRedeemByDate(), new DateTime("2013-10-10T00:00:00Z"));
        assertEquals(coupon.getSingleUse(), Boolean.TRUE);
        assertEquals(coupon.getAppliesForMonths(), new Integer(1));
        assertEquals(coupon.getAppliesToAllPlans(), Boolean.TRUE);
        assertEquals(coupon.getMaxRedemptions(), null);
    }

    @Test(groups = "fast", description = "https://github.com/killbilling/recurly-java-library/issues/57")
    public void testDeserializationDollars() throws Exception {
        // See https://dev.recurly.com/docs/list-active-coupons
        final String couponData =
                "<coupon href=\"https://api.recurly.com/v2/coupons/f8028\">\n" +
                "  <redemptions href=\"https://api.recurly.com/v2/coupons/f8028/redemptions\"/>\n" +
                "  <coupon_code>f8028</coupon_code>\n" +
                "  <name>t</name>\n" +
                "  <state>redeemable</state>\n" +
                "  <description nil=\"nil\"></description>\n" +
                "  <discount_type>dollars</discount_type>\n" +
                "  <discount_in_cents>\n" +
                "    <USD type=\"integer\">199</USD>\n" +
                "  </discount_in_cents>" +
                "  <redeem_by_date type=\"datetime\">2013-10-10T00:00:00Z</redeem_by_date>\n" +
                "  <single_use type=\"boolean\">true</single_use>\n" +
                "  <applies_for_months type=\"integer\">1</applies_for_months>\n" +
                "  <max_redemptions nil=\"nil\"></max_redemptions>\n" +
                "  <applies_to_all_plans type=\"boolean\">true</applies_to_all_plans>\n" +
                "  <created_at type=\"datetime\">2013-05-08T10:21:52Z</created_at>\n" +
                "  <plan_codes type=\"array\">\n" +
                "  </plan_codes>\n" +
                "  <a name=\"redeem\" href=\"https://api.recurly.com/v2/coupons/f8028/redeem\" method=\"post\"/>\n" +
                "</coupon>\n";

        final Coupon coupon = xmlMapper.readValue(couponData, Coupon.class);

        assertEquals(coupon.getHref(), "https://api.recurly.com/v2/coupons/f8028");
        assertEquals(coupon.getCouponCode(), "f8028");
        assertEquals(coupon.getName(), "t");
        assertEquals(coupon.getState(), "redeemable");
        assertEquals(coupon.getDiscountType(), "dollars");
        assertEquals(coupon.getDiscountInCents().getUnitAmountUSD(), new Integer(199));
        assertEquals(coupon.getRedeemByDate(), new DateTime("2013-10-10T00:00:00Z"));
        assertEquals(coupon.getSingleUse(), Boolean.TRUE);
        assertEquals(coupon.getAppliesForMonths(), new Integer(1));
        assertEquals(coupon.getAppliesToAllPlans(), Boolean.TRUE);
        assertEquals(coupon.getMaxRedemptions(), null);
    }
}
