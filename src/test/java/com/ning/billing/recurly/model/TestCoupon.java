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

import com.ning.billing.recurly.TestUtils;
import com.ning.billing.recurly.model.Coupon.DiscountType;
import com.ning.billing.recurly.model.Coupon.Duration;
import com.ning.billing.recurly.model.Coupon.RedemptionResource;
import com.ning.billing.recurly.model.Coupon.TemporalUnit;
import com.ning.billing.recurly.model.Coupon.Type;
import org.joda.time.DateTime;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertNotEquals;

public class TestCoupon extends TestModelBase {


    @Test(groups = "fast")
    public void testDeserializationPercent() throws Exception {
        final String couponData = 
                "<coupon href=\"https://api.recurly.com/v2/coupons/f8028\">" +
                    "<redemptions href=\"https://api.recurly.com/v2/coupons/f8028/redemptions\"/>" +
                    "<id type=\"integer\">1234567890</id>" +
                    "<coupon_code>f8028</coupon_code>" +
                    "<name>t</name>" +
                    "<state>redeemable</state>" +
                    "<description>test description.</description>" +
                    "<discount_type>percent</discount_type>" +
                    "<discount_percent type=\"integer\">100</discount_percent>" +
                    "<invoice_description>invoice description</invoice_description>" +
                    "<redeem_by_date type=\"datetime\">2017-12-31T00:00:00Z</redeem_by_date>" +
                    "<single_use type=\"boolean\">true</single_use>" +
                    "<applies_for_months nil=\"nil\"/>" +
                    "<max_redemptions type=\"integer\">200</max_redemptions>" +
                    "<applies_to_all_plans type=\"boolean\">false</applies_to_all_plans>" +
                    "<created_at type=\"datetime\">2016-07-11T18:50:17Z</created_at>" +
                    "<updated_at type=\"datetime\">2016-07-11T18:50:17Z</updated_at>" +
                    "<deleted_at nil=\"nil\"/>" +
                    "<duration>single_use</duration>" +
                    "<temporal_unit nil=\"nil\"/>" +
                    "<temporal_amount nil=\"nil\"/>" +
                    "<applies_to_non_plan_charges type=\"boolean\">false</applies_to_non_plan_charges>" +
                    "<redemption_resource>account</redemption_resource>" +
                    "<max_redemptions_per_account type=\"integer\">1</max_redemptions_per_account>" +
                    "<coupon_type>single_code</coupon_type>" +
                    "<plan_codes type=\"array\">" +
                        "<plan_code>gold</plan_code>" +
                        "<plan_code>platinum</plan_code>" +
                    "</plan_codes>" +
                    "<a name=\"redeem\" href=\"https://api.recurly.com/v2/coupons/special/redeem\" method=\"post\"/>" +
                "</coupon>";

        final Coupon coupon = xmlMapper.readValue(couponData, Coupon.class);

        assertEquals(coupon.getHref(), "https://api.recurly.com/v2/coupons/f8028");
        assertEquals(coupon.getCouponCode(), "f8028");
        assertEquals(coupon.getName(), "t");
        assertEquals(coupon.getState(), "redeemable");
        assertEquals(coupon.getDescription(), "test description.");
        assertEquals(coupon.getDiscountType(), DiscountType.percent);
        assertEquals(coupon.getDiscountPercent(), Integer.valueOf(100));
        assertEquals(coupon.getRedeemByDate(), new DateTime("2017-12-31T00:00:00Z"));
        assertEquals(coupon.getSingleUse(), Boolean.TRUE);
        assertNull(coupon.getAppliesForMonths());
        assertEquals(coupon.getMaxRedemptions().intValue(), 200);
        assertEquals(coupon.getAppliesToAllPlans(), Boolean.FALSE);
        assertEquals(coupon.getCreatedAt(), new DateTime("2016-07-11T18:50:17Z"));
        assertEquals(coupon.getUpdatedAt(), new DateTime("2016-07-11T18:50:17Z"));
        assertEquals(coupon.getDuration(), Duration.single_use);
        assertNull(coupon.getTemporalUnit());
        assertNull(coupon.getTemporalAmount());
        assertEquals(coupon.getAppliesToNonPlanCharges(), Boolean.FALSE);
        assertEquals(coupon.getRedemptionResource(), RedemptionResource.account);
        assertEquals(coupon.getMaxRedemptionsPerAccount().intValue(), 1);
        assertEquals(coupon.getType(), Type.single_code);
        assertEquals(coupon.getId(), Long.valueOf(1234567890));
    }

    @Test(groups = "fast", description = "https://github.com/killbilling/recurly-java-library/issues/162")
    public void testDeserializationTemporalDuration() throws Exception {
        final String couponData = 
                "<coupon href=\"https://api.recurly.com/v2/coupons/f8028\">" +
                    "<redemptions href=\"https://api.recurly.com/v2/coupons/f8028/redemptions\"/>" +
                    "<coupon_code>f8028</coupon_code>" +
                    "<name>t</name>" +
                    "<state>redeemable</state>" +
                    "<description>test description.</description>" +
                    "<discount_type>percent</discount_type>" +
                    "<discount_percent type=\"integer\">100</discount_percent>" +
                    "<invoice_description>invoice description</invoice_description>" +
                    "<redeem_by_date type=\"datetime\">2017-12-31T00:00:00Z</redeem_by_date>" +
                    "<single_use type=\"boolean\">false</single_use>" +
                    "<applies_for_months nil=\"nil\"/>" +
                    "<max_redemptions type=\"integer\">200</max_redemptions>" +
                    "<applies_to_all_plans type=\"boolean\">false</applies_to_all_plans>" +
                    "<created_at type=\"datetime\">2016-07-11T18:50:17Z</created_at>" +
                    "<updated_at type=\"datetime\">2016-07-11T18:50:17Z</updated_at>" +
                    "<deleted_at nil=\"nil\"/>" +
                    "<duration>temporal</duration>" +
                    "<temporal_unit>day</temporal_unit>" +
                    "<temporal_amount type=\"integer\">45</temporal_amount>" +
                    "<applies_to_non_plan_charges type=\"boolean\">false</applies_to_non_plan_charges>" +
                    "<redemption_resource>account</redemption_resource>" +
                    "<max_redemptions_per_account type=\"integer\">1</max_redemptions_per_account>" +
                    "<coupon_type>single_code</coupon_type>" +
                    "<plan_codes type=\"array\">" +
                        "<plan_code>gold</plan_code>" +
                        "<plan_code>platinum</plan_code>" +
                    "</plan_codes>" +
                    "<a name=\"redeem\" href=\"https://api.recurly.com/v2/coupons/special/redeem\" method=\"post\"/>" +
                "</coupon>";

        final Coupon coupon = xmlMapper.readValue(couponData, Coupon.class);

        assertEquals(coupon.getHref(), "https://api.recurly.com/v2/coupons/f8028");
        assertEquals(coupon.getCouponCode(), "f8028");
        assertEquals(coupon.getName(), "t");
        assertEquals(coupon.getState(), "redeemable");
        assertEquals(coupon.getDescription(), "test description.");
        assertEquals(coupon.getDiscountType(), DiscountType.percent);
        assertEquals(coupon.getDiscountPercent(), Integer.valueOf(100));
        assertEquals(coupon.getRedeemByDate(), new DateTime("2017-12-31T00:00:00Z"));
        assertEquals(coupon.getSingleUse(), Boolean.FALSE);
        assertNull(coupon.getAppliesForMonths());
        assertEquals(coupon.getMaxRedemptions().intValue(), 200);
        assertEquals(coupon.getAppliesToAllPlans(), Boolean.FALSE);
        assertEquals(coupon.getCreatedAt(), new DateTime("2016-07-11T18:50:17Z"));
        assertEquals(coupon.getUpdatedAt(), new DateTime("2016-07-11T18:50:17Z"));
        assertEquals(coupon.getDuration(), Duration.temporal);
        assertEquals(coupon.getTemporalUnit(), TemporalUnit.day);
        assertEquals(coupon.getTemporalAmount().intValue(), 45);
        assertEquals(coupon.getAppliesToNonPlanCharges(), Boolean.FALSE);
        assertEquals(coupon.getRedemptionResource(), RedemptionResource.account);
        assertEquals(coupon.getMaxRedemptionsPerAccount().intValue(), 1);
        assertEquals(coupon.getType(), Type.single_code);
    }

    @Test(groups = "fast", description = "https://github.com/killbilling/recurly-java-library/issues/57")
    public void testDeserializationDollars() throws Exception {
        // See https://dev.recurly.com/docs/list-active-coupons
        final String couponData =
                "<coupon href=\"https://api.recurly.com/v2/coupons/f8028\">" +
                    "<redemptions href=\"https://api.recurly.com/v2/coupons/f8028/redemptions\"/>" +
                    "<coupon_code>f8028</coupon_code>" +
                    "<name>t</name>" +
                    "<state>redeemable</state>" +
                    "<description>test description.</description>" +
                    "<discount_type>dollars</discount_type>\n" +
                    "<discount_in_cents>\n" +
                    "    <USD type=\"integer\">199</USD>\n" +
                    "</discount_in_cents>" +
                    "<invoice_description>invoice description</invoice_description>" +
                    "<redeem_by_date type=\"datetime\">2017-12-31T00:00:00Z</redeem_by_date>" +
                    "<single_use type=\"boolean\">true</single_use>" +
                    "<applies_for_months nil=\"nil\"/>" +
                    "<max_redemptions type=\"integer\">200</max_redemptions>" +
                    "<applies_to_all_plans type=\"boolean\">false</applies_to_all_plans>" +
                    "<created_at type=\"datetime\">2016-07-11T18:50:17Z</created_at>" +
                    "<updated_at type=\"datetime\">2016-07-11T18:50:17Z</updated_at>" +
                    "<deleted_at nil=\"nil\"/>" +
                    "<duration>single_use</duration>" +
                    "<temporal_unit nil=\"nil\"/>" +
                    "<temporal_amount nil=\"nil\"/>" +
                    "<applies_to_non_plan_charges type=\"boolean\">false</applies_to_non_plan_charges>" +
                    "<redemption_resource>account</redemption_resource>" +
                    "<max_redemptions_per_account type=\"integer\">1</max_redemptions_per_account>" +
                    "<coupon_type>single_code</coupon_type>" +
                    "<plan_codes type=\"array\">" +
                        "<plan_code>gold</plan_code>" +
                        "<plan_code>platinum</plan_code>" +
                    "</plan_codes>" +
                    "<a name=\"redeem\" href=\"https://api.recurly.com/v2/coupons/special/redeem\" method=\"post\"/>" +
                "</coupon>";

        final Coupon coupon = xmlMapper.readValue(couponData, Coupon.class);

        assertEquals(coupon.getHref(), "https://api.recurly.com/v2/coupons/f8028");
        assertEquals(coupon.getCouponCode(), "f8028");
        assertEquals(coupon.getName(), "t");
        assertEquals(coupon.getState(), "redeemable");
        assertEquals(coupon.getDescription(), "test description.");
        assertEquals(coupon.getDiscountType(), DiscountType.dollars);
        assertEquals(coupon.getDiscountInCents().getUnitAmountUSD().intValue(), 199);
        assertEquals(coupon.getRedeemByDate(), new DateTime("2017-12-31T00:00:00Z"));
        assertEquals(coupon.getSingleUse(), Boolean.TRUE);
        assertNull(coupon.getAppliesForMonths());
        assertEquals(coupon.getMaxRedemptions().intValue(), 200);
        assertEquals(coupon.getAppliesToAllPlans(), Boolean.FALSE);
        assertEquals(coupon.getCreatedAt(), new DateTime("2016-07-11T18:50:17Z"));
        assertEquals(coupon.getUpdatedAt(), new DateTime("2016-07-11T18:50:17Z"));
        assertEquals(coupon.getDuration(), Duration.single_use);
        assertNull(coupon.getTemporalUnit());
        assertNull(coupon.getTemporalAmount());
        assertEquals(coupon.getAppliesToNonPlanCharges(), Boolean.FALSE);
        assertEquals(coupon.getRedemptionResource(), RedemptionResource.account);
        assertEquals(coupon.getMaxRedemptionsPerAccount().intValue(), 1);
        assertEquals(coupon.getType(), Type.single_code);
    }


    @Test(groups = "fast")
    public void testPlanCodes() throws Exception {
        // See https://dev.recurly.com/docs/list-active-coupons
        final String couponData =
                "<coupon href=\"https://api.recurly.com/v2/coupons/f8028\">" +
                        "<redemptions href=\"https://api.recurly.com/v2/coupons/f8028/redemptions\"/>" +
                        "<coupon_code>f8028</coupon_code>" +
                        "<name>t</name>" +
                        "<state>redeemable</state>" +
                        "<description>test description.</description>" +
                        "<discount_type>percent</discount_type>" +
                        "<discount_percent type=\"integer\">100</discount_percent>" +
                        "<invoice_description>invoice description</invoice_description>" +
                        "<redeem_by_date type=\"datetime\">2017-12-31T00:00:00Z</redeem_by_date>" +
                        "<single_use type=\"boolean\">true</single_use>" +
                        "<applies_for_months nil=\"nil\"/>" +
                        "<max_redemptions type=\"integer\">200</max_redemptions>" +
                        "<applies_to_all_plans type=\"boolean\">false</applies_to_all_plans>" +
                        "<created_at type=\"datetime\">2016-07-11T18:50:17Z</created_at>" +
                        "<updated_at type=\"datetime\">2016-07-11T18:50:17Z</updated_at>" +
                        "<deleted_at nil=\"nil\"/>" +
                        "<duration>single_use</duration>" +
                        "<temporal_unit nil=\"nil\"/>" +
                        "<temporal_amount nil=\"nil\"/>" +
                        "<applies_to_non_plan_charges type=\"boolean\">false</applies_to_non_plan_charges>" +
                        "<redemption_resource>account</redemption_resource>" +
                        "<max_redemptions_per_account type=\"integer\">1</max_redemptions_per_account>" +
                        "<coupon_type>single_code</coupon_type>" +
                        "<plan_codes type=\"array\">" +
                            "<plan_code>gold</plan_code>" +
                            "<plan_code>platinum</plan_code>" +
                        "</plan_codes>" +
                        "<a name=\"redeem\" href=\"https://api.recurly.com/v2/coupons/special/redeem\" method=\"post\"/>" +
                    "</coupon>";

        final Coupon coupon = xmlMapper.readValue(couponData, Coupon.class);

        assertEquals(coupon.getHref(), "https://api.recurly.com/v2/coupons/f8028");
        assertEquals(coupon.getCouponCode(), "f8028");
        assertEquals(coupon.getName(), "t");
        assertEquals(coupon.getState(), "redeemable");
        assertEquals(coupon.getDescription(), "test description.");
        assertEquals(coupon.getDiscountType(), DiscountType.percent);
        assertEquals(coupon.getDiscountPercent(), Integer.valueOf(100));
        assertEquals(coupon.getRedeemByDate(), new DateTime("2017-12-31T00:00:00Z"));
        assertEquals(coupon.getSingleUse(), Boolean.TRUE);
        assertNull(coupon.getAppliesForMonths());
        assertEquals(coupon.getMaxRedemptions().intValue(), 200);
        assertEquals(coupon.getAppliesToAllPlans(), Boolean.FALSE);
        assertEquals(coupon.getCreatedAt(), new DateTime("2016-07-11T18:50:17Z"));
        assertEquals(coupon.getUpdatedAt(), new DateTime("2016-07-11T18:50:17Z"));
        assertEquals(coupon.getDuration(), Duration.single_use);
        assertNull(coupon.getTemporalUnit());
        assertNull(coupon.getTemporalAmount());
        assertEquals(coupon.getAppliesToNonPlanCharges(), Boolean.FALSE);
        assertEquals(coupon.getRedemptionResource(), RedemptionResource.account);
        assertEquals(coupon.getMaxRedemptionsPerAccount().intValue(), 1);
        assertEquals(coupon.getType(), Type.single_code);
        assertEquals(coupon.getPlanCodes().size(), 2);
        assertTrue(coupon.getPlanCodes().contains("platinum"));
        assertTrue(coupon.getPlanCodes().contains("gold"));
    }

    @Test(groups = "fast")
    public void testHashCodeAndEquality() throws Exception {
        // create coupons of the same value but difference references
        Coupon coupon = TestUtils.createRandomCoupon(0);
        Coupon otherCoupon = TestUtils.createRandomCoupon(0);

        assertNotEquals(System.identityHashCode(coupon), System.identityHashCode(otherCoupon));
        assertEquals(coupon.hashCode(), otherCoupon.hashCode());
        assertEquals(coupon, otherCoupon);
    }
}
