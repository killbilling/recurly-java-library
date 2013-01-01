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

import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestSubscription extends TestModelBase {

    @Test(groups = "fast")
    public void testDeserialization() throws Exception {
        // See http://docs.recurly.com/api/subscriptions
        final String subscriptionData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                        "<subscription href=\"https://api.recurly.com/v2/subscriptions/44f83d7cba354d5b84812419f923ea96\">\n" +
                                        "  <account href=\"https://api.recurly.com/v2/accounts/1\"/>\n" +
                                        "  <plan href=\"https://api.recurly.com/v2/plans/gold\">\n" +
                                        "    <plan_code>gold</plan_code>\n" +
                                        "    <name>Gold plan</name>\n" +
                                        "  </plan>\n" +
                                        "  <uuid>44f83d7cba354d5b84812419f923ea96</uuid>\n" +
                                        "  <state>active</state>\n" +
                                        "  <unit_amount_in_cents type=\"integer\">800</unit_amount_in_cents>\n" +
                                        "  <currency>EUR</currency>\n" +
                                        "  <quantity type=\"integer\">1</quantity>\n" +
                                        "  <activated_at type=\"datetime\">2011-05-27T07:00:00Z</activated_at>\n" +
                                        "  <canceled_at nil=\"nil\"></canceled_at>\n" +
                                        "  <expires_at nil=\"nil\"></expires_at>\n" +
                                        "  <current_period_started_at type=\"datetime\">2011-06-27T07:00:00Z</current_period_started_at>\n" +
                                        "  <current_period_ends_at type=\"datetime\">2010-07-27T07:00:00Z</current_period_ends_at>\n" +
                                        "  <trial_started_at nil=\"nil\"></trial_started_at>\n" +
                                        "  <trial_ends_at nil=\"nil\"></trial_ends_at>\n" +
                                        "  <subscription_add_ons type=\"array\">\n" +
                                        "  </subscription_add_ons>\n" +
                                        "  <a name=\"cancel\" href=\"https://api.recurly.com/v2/subscriptions/44f83d7cba354d5b84812419f923ea96/cancel\" method=\"put\"/>\n" +
                                        "  <a name=\"terminate\" href=\"https://api.recurly.com/v2/subscriptions/44f83d7cba354d5b84812419f923ea96/terminate\" method=\"put\"/>\n" +
                                        "  <a name=\"postpone\" href=\"https://api.recurly.com/v2/subscriptions/44f83d7cba354d5b84812419f923ea96/postpone\" method=\"put\"/>\n" +
                                        "</subscription>";

        final Subscription subscription = xmlMapper.readValue(subscriptionData, Subscription.class);
        Assert.assertEquals(subscription.getUuid(), "44f83d7cba354d5b84812419f923ea96");
        Assert.assertEquals(subscription.getState(), "active");
        Assert.assertEquals(subscription.getUnitAmountInCents(), (Integer) 800);
        Assert.assertEquals(subscription.getCurrency(), "EUR");
        Assert.assertEquals(subscription.getQuantity(), (Integer) 1);
        Assert.assertEquals(subscription.getActivatedAt(), new DateTime("2011-05-27T07:00:00Z"));
        Assert.assertNull(subscription.getCanceledAt(), "");
        Assert.assertNull(subscription.getExpiresAt(), "");
        Assert.assertEquals(subscription.getCurrentPeriodStartedAt(), new DateTime("2011-06-27T07:00:00Z"));
        Assert.assertEquals(subscription.getCurrentPeriodEndsAt(), new DateTime("2010-07-27T07:00:00Z"));
        Assert.assertNull(subscription.getTrialStartedAt(), "");
        Assert.assertNull(subscription.getTrialEndsAt(), "");
        Assert.assertNull(subscription.getAddOns());

        // Verify nested attributes
        Assert.assertEquals(subscription.getAccount().getHref(), "https://api.recurly.com/v2/accounts/1");
        Assert.assertEquals(subscription.getAccount().getAccountCode(), "1");
    }
}
