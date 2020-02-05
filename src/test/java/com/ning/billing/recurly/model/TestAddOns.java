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
import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class TestAddOns extends TestModelBase {

    @Test(groups = "fast")
    public void testDeserialization() throws Exception {
        // See https://dev.recurly.com/docs/list-add-ons-for-a-plan
        final String addOnsData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                  "<add_ons type=\"array\">\n" +
                                  "  <add_on href=\"https://your-subdomain.recurly.com/v2/plans/gold/add_ons/ipaddresses\">\n" +
                                  "    <plan href=\"https://your-subdomain.recurly.com/v2/plans/gold\"/>\n" +
                                  "    <measured_unit href=\"https://your-subdomain.recurly.com/v2/measured_units/12345678\"/>\n "+
                                  "    <add_on_code>ipaddresses</add_on_code>\n" +
                                  "    <name>IP Addresses</name>\n" +
                                  "    <display_quantity_on_hosted_page type=\"boolean\">false</display_quantity_on_hosted_page>\n" +
                                  "    <default_quantity type=\"integer\">1</default_quantity>\n" +
                                  "    <unit_amount_in_cents>\n" +
                                  "      <USD>200</USD>\n" +
                                  "    </unit_amount_in_cents>\n" +
                                  "    <add_on_type>usage</add_on_type>\n" +
                                  "    <usage_type>price</usage_type>\n" +
                                  "    <revenue_schedule_type>evenly</revenue_schedule_type>\n" +
                                  "    <created_at type=\"datetime\">2011-06-28T12:34:56Z</created_at>\n" +
                                  "    <updated_at type=\"datetime\">2011-06-28T12:34:56Z</updated_at>\n" +
                                  "  </add_on>\n" +
                                  "  <!-- Continued... -->\n" +
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
        AddOn addOn = TestUtils.createRandomAddOn(0);
        AddOn otherAddOn = TestUtils.createRandomAddOn(0);

        assertNotEquals(System.identityHashCode(addOn), System.identityHashCode(otherAddOn));
        assertEquals(addOn.hashCode(), otherAddOn.hashCode());
        assertEquals(addOn, otherAddOn);
    }

    private void verifyAddOns(final AddOns addOns) {
        Assert.assertEquals(addOns.size(), 1);

        final AddOn addOn = addOns.get(0);
        Assert.assertEquals(addOn.getAddOnCode(), "ipaddresses");
        Assert.assertEquals(addOn.getName(), "IP Addresses");
        Assert.assertEquals(addOn.getMeasuredUnit().getId(), Long.valueOf(12345678));
        Assert.assertEquals(addOn.getAddOnType(), "usage");
        Assert.assertEquals(addOn.getUsageType(), "price");
        Assert.assertEquals((boolean) addOn.getDisplayQuantityOnHostedPage(), false);
        Assert.assertEquals((int) addOn.getDefaultQuantity(), 1);
        Assert.assertEquals((int) addOn.getUnitAmountInCents().getUnitAmountUSD(), 200);
        Assert.assertEquals(addOn.getRevenueScheduleType(), RevenueScheduleType.EVENLY);
        Assert.assertEquals(addOn.getCreatedAt(), new DateTime("2011-06-28T12:34:56Z"));
        Assert.assertEquals(addOn.getUpdatedAt(), new DateTime("2011-06-28T12:34:56Z"));
    }
}
