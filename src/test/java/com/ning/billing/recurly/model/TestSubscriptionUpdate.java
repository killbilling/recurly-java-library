/*
 * Copyright 2010-2013 Ning, Inc.
 *
 * Ning licenses this file to you under the Apache License, version 2.0
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

public class TestSubscriptionUpdate extends TestModelBase {

    @Test(groups = "fast")
    public void testDeserialization() throws Exception {
        // See http://docs.recurly.com/api/subscriptions
        final String subscriptionData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                        "<subscription>\n" +
                                        "  <timeframe>now</timeframe>\n" +
                                        "  <plan_code>gold</plan_code>\n" +
                                        "  <unit_amount_in_cents type=\"integer\">800</unit_amount_in_cents>\n" +
                                        "  <quantity type=\"integer\">1</quantity>\n" +
                                        "  <subscription_add_ons type=\"array\">\n" +
                                        "  </subscription_add_ons>\n" +
                                        "</subscription>";

        final SubscriptionUpdate subscription = xmlMapper.readValue(subscriptionData, SubscriptionUpdate.class);
        Assert.assertEquals(subscription.getTimeframe(), SubscriptionUpdate.Timeframe.now);
        Assert.assertEquals(subscription.getPlanCode(), "gold");
        Assert.assertEquals(subscription.getUnitAmountInCents(), (Integer) 800);
        Assert.assertEquals(subscription.getQuantity(), (Integer) 1);
        Assert.assertNull(subscription.getAddOns());
    }
}
