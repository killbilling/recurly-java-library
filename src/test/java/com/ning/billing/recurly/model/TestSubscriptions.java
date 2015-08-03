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

public class TestSubscriptions extends TestModelBase {

    @Test(groups = "fast")
    public void testDeserialization() throws Exception {
        // See https://dev.recurly.com/docs/list-subscriptions
        final String subscriptionsData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                         "<subscriptions type=\"array\">\n" +
                                         "  <subscription href=\"https://your-subdomain.recurly.com/v2/subscriptions/44f83d7cba354d5b84812419f923ea96\">\n" +
                                         "    <account href=\"https://your-subdomain.recurly.com/v2/accounts/1\"/>\n" +
                                         "    <plan href=\"https://your-subdomain.recurly.com/v2/plans/gold\">\n" +
                                         "      <plan_code>gold</plan_code>\n" +
                                         "      <name>Gold plan</name>\n" +
                                         "    </plan>\n" +
                                         "    <uuid>44f83d7cba354d5b84812419f923ea96</uuid>\n" +
                                         "    <state>active</state>\n" +
                                         "    <unit_amount_in_cents type=\"integer\">800</unit_amount_in_cents>\n" +
                                         "    <currency>EUR</currency>\n" +
                                         "    <quantity type=\"integer\">1</quantity>\n" +
                                         "    <activated_at type=\"datetime\">2011-05-27T07:00:00Z</activated_at>\n" +
                                         "    <canceled_at nil=\"nil\"></canceled_at>\n" +
                                         "    <expires_at nil=\"nil\"></expires_at>\n" +
                                         "    <current_period_started_at type=\"datetime\">2011-06-27T07:00:00Z</current_period_started_at>\n" +
                                         "    <current_period_ends_at type=\"datetime\">2010-07-27T07:00:00Z</current_period_ends_at>\n" +
                                         "    <trial_started_at nil=\"nil\"></trial_started_at>\n" +
                                         "    <trial_ends_at nil=\"nil\"></trial_ends_at>\n" +
                                         "    <subscription_add_ons type=\"array\">\n" +
                                         "    </subscription_add_ons>\n" +
                                         "    <a name=\"cancel\" href=\"https://your-subdomain.recurly.com/v2/subscriptions/44f83d7cba354d5b84812419f923ea96/cancel\" method=\"put\"/>\n" +
                                         "    <a name=\"terminate\" href=\"https://your-subdomain.recurly.com/v2/subscriptions/44f83d7cba354d5b84812419f923ea96/terminate\" method=\"put\"/>\n" +
                                         "    <a name=\"postpone\" href=\"https://your-subdomain.recurly.com/v2/subscriptions/44f83d7cba354d5b84812419f923ea96/postpone\" method=\"put\"/>\n" +
                                         "  </subscription>\n" +
                                         "  <!-- Continued... -->\n" +
                                         "</subscriptions>";

        final Subscriptions subscriptions = xmlMapper.readValue(subscriptionsData, Subscriptions.class);
        verifySubscriptions(subscriptions);


        // Verify serialization
        final String subscriptionsSerialized = xmlMapper.writeValueAsString(subscriptions);
        final Subscriptions subscriptions2 = xmlMapper.readValue(subscriptionsSerialized, Subscriptions.class);
        verifySubscriptions(subscriptions2);
    }

    private void verifySubscriptions(final Subscriptions subscriptions) {
        Assert.assertEquals(subscriptions.size(), 1);

        final Subscription subscription = subscriptions.get(0);
        Assert.assertEquals(subscription.getUuid(), "44f83d7cba354d5b84812419f923ea96");
        Assert.assertEquals(subscription.getCurrency(), "EUR");
    }
}
