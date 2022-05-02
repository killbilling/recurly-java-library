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

public class TestSubscriptionAddOnWithPercentageTiers extends TestModelBase {

    @Test(groups = "fast")
    public void testDeserialization() throws Exception {
        // See https://dev.recurly.com/docs/list-subscriptions
        final String subscriptionAddOnsData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                         "    <subscription_add_ons type=\"array\">\n" +
                                         "      <subscription_add_on>\n" +
                                         "        <usage_timeframe>billing_period</usage_timeframe>\n" +
                                         "        <percentage_tiers>\n" +
                                         "          <percentage_tier>\n" +
                                         "            <ending_amount_in_cents>1500</ending_amount_in_cents>\n" +
                                         "            <usage_percentage>15.0</usage_percentage>\n" +
                                         "          </percentage_tier>\n" +
                                         "          <percentage_tier>\n" +
                                         "            <ending_amount_in_cents nil=\"nil\"></ending_amount_in_cents>\n" +
                                         "            <usage_percentage>30.0</usage_percentage>\n" +
                                         "          </percentage_tier>\n" +
                                         "        </percentage_tiers>\n" +
                                         "      </subscription_add_on>\n" +
                                         "    </subscription_add_ons>";

        final SubscriptionAddOns subscriptionAddOns = xmlMapper.readValue(subscriptionAddOnsData, SubscriptionAddOns.class);
        verifySubscriptionAddOns(subscriptionAddOns);

        // Verify serialization
        final String subscriptionAddOnsSerialized = xmlMapper.writeValueAsString(subscriptionAddOns);
        final SubscriptionAddOns subscriptionAddOns2 = xmlMapper.readValue(subscriptionAddOnsSerialized, SubscriptionAddOns.class);
        verifySubscriptionAddOns(subscriptionAddOns2);
    }

    @Test(groups = "fast")
    public void testHashCodeAndEquality() throws Exception {
        // create AddOns of the same value but difference references
        SubscriptionAddOn subscriptionAddOn = TestUtils.createRandomSubscriptionAddOnPercentageTiered();
        SubscriptionAddOn otherSubscriptionAddOn = TestUtils.createRandomSubscriptionAddOnPercentageTiered();

        assertNotEquals(System.identityHashCode(subscriptionAddOn), System.identityHashCode(otherSubscriptionAddOn));
        assertEquals(subscriptionAddOn.hashCode(), otherSubscriptionAddOn.hashCode());
        assertEquals(subscriptionAddOn, otherSubscriptionAddOn);
    }

    private void verifySubscriptionAddOns(final SubscriptionAddOns subscriptionAddOns) {
        Assert.assertEquals(subscriptionAddOns.size(), 1);

        final SubscriptionAddOn subscriptionAddOn = subscriptionAddOns.get(0);
        Assert.assertEquals(subscriptionAddOn.getUsageTimeframe(), "billing_period");
        Assert.assertEquals((int) subscriptionAddOn.getPercentageTiers().get(0).getEndingAmountInCents(), 1500);
        Assert.assertEquals(subscriptionAddOn.getPercentageTiers().get(0).getUsagePercentage(), "15.0");
        Assert.assertEquals(subscriptionAddOn.getPercentageTiers().get(1).getEndingAmountInCents(), null);
        Assert.assertEquals(subscriptionAddOn.getPercentageTiers().get(1).getUsagePercentage(), "30.0");
    }
}
