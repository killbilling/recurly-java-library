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
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import java.math.BigDecimal;

public class TestAddOnsPercentageTiered extends TestModelBase {

    @Test(groups = "fast")
    public void testDeserialization() throws Exception {
        // See https://dev.recurly.com/docs/list-add-ons-for-a-plan
        final String addOnsData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                  "<add_ons type=\"array\">\n" +
                                  "  <add_on href=\"https://your-subdomain.recurly.com/v2/plans/gold/add_ons/add_on_code\">\n" +
                                  "    <plan href=\"https://your-subdomain.recurly.com/v2/plans/gold\"/>\n" +
                                  "    <measured_unit href=\"https://your-subdomain.recurly.com/v2/measured_units/12345678\"/>\n "+
                                  "    <add_on_code>add_on_code</add_on_code>\n" +
                                  "    <name>Tiered Add-On</name>\n" +
                                  "    <default_quantity type=\"integer\">1</default_quantity>\n" +
                                  "    <display_quantity_on_hosted_page type=\"boolean\">false</display_quantity_on_hosted_page>\n" +
                                  "    <percentage_tiers>\n" +
                                  "      <percentage_tier>\n" +
                                  "        <currency>USD</currency>\n" +
                                  "        <tiers>\n" +
                                  "         <tier>\n" +
                                  "          <ending_amount_in_cents type=\"integer\">100</ending_amount_in_cents>\n" +
                                  "          <usage_percentage>10.0</usage_percentage>\n" +
                                  "         </tier>\n" +
                                  "         <tier>\n" +
                                  "          <ending_amount_in_cents nil=\"nil\"></ending_amount_in_cents>\n" +
                                  "          <usage_percentage>20.0</usage_percentage>\n" +
                                  "         </tier>\n" +
                                  "       </tiers>\n" +
                                  "      </percentage_tier>\n" +
                                  "    </percentage_tiers>\n" +
                                  "    <accounting_code nil=\"nil\"></accounting_code>\n" +
                                  "    <add_on_type>usage</add_on_type>\n" +
                                  "    <optional type=\"boolean\">true</optional>\n" +
                                  "    <revenue_schedule_type>evenly</revenue_schedule_type>\n" +
                                  "    <tier_type>tiered</tier_type>\n" +
                                  "    <created_at type=\"datetime\">2020-03-30T21:44:03Z</created_at>\n" +
                                  "    <updated_at type=\"datetime\">2020-03-30T21:44:03Z</updated_at>\n" +
                                  "  </add_on>\n" +
                                  "</add_ons>";

        final AddOns addOns = xmlMapper.readValue(addOnsData, AddOns.class);
        verifyAddOns(addOns);

        // Verify serialization
        final String addOnsSerialized = xmlMapper.writeValueAsString(addOns);
        final AddOns addOns2 = xmlMapper.readValue(addOnsSerialized, AddOns.class);
        verifyAddOns(addOns2);
    }

    @Test(groups = "fast")
    public void testHashCodeAndEquality() throws Exception {
        // create AddOns of the same value but difference references
        AddOn addOn = TestUtils.createRandomAddOnPercentageTiered(0);
        AddOn otherAddOn = TestUtils.createRandomAddOnPercentageTiered(0);

        assertNotEquals(System.identityHashCode(addOn), System.identityHashCode(otherAddOn));
        assertEquals(addOn.hashCode(), otherAddOn.hashCode());
        assertEquals(addOn, otherAddOn);
    }

    private void verifyAddOns(final AddOns addOns) {
        Assert.assertEquals(addOns.size(), 1);

        final AddOn addOn = addOns.get(0);
        Assert.assertEquals(addOn.getPercentageTiers().get(0).getCurrency(), "USD");
        Assert.assertEquals((int) addOn.getPercentageTiers().get(0).getTiers().get(0).getEndingAmountInCents(), 100);
        Assert.assertEquals(addOn.getPercentageTiers().get(0).getTiers().get(0).getUsagePercentage(), new BigDecimal("10.0"));
        Assert.assertEquals(addOn.getPercentageTiers().get(0).getTiers().get(1).getEndingAmountInCents(), null);
        Assert.assertEquals(addOn.getPercentageTiers().get(0).getTiers().get(1).getUsagePercentage(), new BigDecimal("20.0"));
    }
}
