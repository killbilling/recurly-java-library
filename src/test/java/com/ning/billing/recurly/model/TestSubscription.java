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

import java.io.IOException;

import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestSubscription extends TestModelBase {

    @Test(groups = "fast")
    public void testDeserialization() throws Exception {
        // See https://dev.recurly.com/docs/list-subscriptions
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
                                        "  <starts_at>2010-07-28T07:00:00Z</starts_at>\n" +
                                        "  <a name=\"cancel\" href=\"https://api.recurly.com/v2/subscriptions/44f83d7cba354d5b84812419f923ea96/cancel\" method=\"put\"/>\n" +
                                        "  <a name=\"terminate\" href=\"https://api.recurly.com/v2/subscriptions/44f83d7cba354d5b84812419f923ea96/terminate\" method=\"put\"/>\n" +
                                        "  <a name=\"postpone\" href=\"https://api.recurly.com/v2/subscriptions/44f83d7cba354d5b84812419f923ea96/postpone\" method=\"put\"/>\n" +
                                        "  <collection_method>manual</collection_method>\n" +
                                        "  <net_terms type=\"integer\">10</net_terms>\n" +
                                        "  <po_number>PO19384</po_number>\n" +
                                        "  <first_renewal_date type=\"datetime\">2011-07-01T07:00:00Z</first_renewal_date>\n" +
                                        "  <subscription_add_ons type=\"array\">\n" +
                                        "  </subscription_add_ons>\n" +
                                        "  <pending_subscription type=\"subscription\">\n" +
                                        "    <plan href=\"https://api.recurly.com/v2/plans/silver\">\n" +
                                        "      <plan_code>silver</plan_code>\n" +
                                        "      <name>Silver plan</name>\n" +
                                        "    </plan>\n" +
                                        "    <unit_amount_in_cents type=\"integer\">400</unit_amount_in_cents>\n" +
                                        "    <quantity type=\"integer\">1</quantity>\n" +
                                        "    <subscription_add_ons type=\"array\">\n" +
                                        "    </subscription_add_ons>\n" +
                                        "  </pending_subscription>\n" +
                                        "</subscription>";

        final Subscription subscription = verifySubscription(subscriptionData);
        verifyPaginationData(subscription);
        verifyPendingSubscription(subscription);
        Assert.assertEquals(subscription.getAddOns().size(), 0);
    }

    @Test(groups = "fast")
    public void testDeserializationWithAddons() throws Exception {
        // See https://dev.recurly.com/docs/subscription-add-ons
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
                                        "  <starts_at>2010-07-28T07:00:00Z</starts_at>\n" +
                                        "  <a name=\"cancel\" href=\"https://api.recurly.com/v2/subscriptions/44f83d7cba354d5b84812419f923ea96/cancel\" method=\"put\"/>\n" +
                                        "  <a name=\"terminate\" href=\"https://api.recurly.com/v2/subscriptions/44f83d7cba354d5b84812419f923ea96/terminate\" method=\"put\"/>\n" +
                                        "  <a name=\"postpone\" href=\"https://api.recurly.com/v2/subscriptions/44f83d7cba354d5b84812419f923ea96/postpone\" method=\"put\"/>\n" +
                                        "  <collection_method>manual</collection_method>\n" +
                                        "  <net_terms type=\"integer\">10</net_terms>\n" +
                                        "  <po_number>PO19384</po_number>\n" +
                                        "  <first_renewal_date type=\"datetime\">2011-07-01T07:00:00Z</first_renewal_date>\n" +
                                        "  <subscription_add_ons type=\"array\">\n" +
                                        "    <subscription_add_on>\n" +
                                        "      <add_on_code>extra_users</add_on_code>\n" +
                                        "      <quantity>2</quantity>\n" +
                                        "      <unit_amount_in_cents>1000</unit_amount_in_cents>\n" +
                                        "    </subscription_add_on>\n" +
                                        "    <subscription_add_on>\n" +
                                        "      <add_on_code>extra_ip</add_on_code>\n" +
                                        "      <quantity>3</quantity>\n" +
                                        "      <unit_amount_in_cents>200</unit_amount_in_cents>\n" +
                                        "    </subscription_add_on>\n" +
                                        "   </subscription_add_ons>" +
                                        "</subscription>";

        final Subscription subscription = verifySubscription(subscriptionData);
        verifySubscriptionAddons(subscription);
        verifyPaginationData(subscription);

        // Verify we can serialize them properly
        final String subscriptionDataSerialized = xmlMapper.writeValueAsString(subscription);
        final Subscription subscription2 = verifySubscription(subscriptionDataSerialized);
        verifySubscriptionAddons(subscription2);
    }

    private void verifySubscriptionAddons(final Subscription subscription) {
        Assert.assertEquals(subscription.getAddOns().size(), 2);
        Assert.assertEquals(subscription.getAddOns().get(0).getAddOnCode(), "extra_users");
        Assert.assertEquals(subscription.getAddOns().get(0).getQuantity(), (Integer) 2);
        Assert.assertEquals(subscription.getAddOns().get(0).getUnitAmountInCents(), (Integer) 1000);
        Assert.assertEquals(subscription.getAddOns().get(1).getAddOnCode(), "extra_ip");
        Assert.assertEquals(subscription.getAddOns().get(1).getQuantity(), (Integer) 3);
        Assert.assertEquals(subscription.getAddOns().get(1).getUnitAmountInCents(), (Integer) 200);
    }

    private Subscription verifySubscription(final String subscriptionData) throws IOException {
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
        Assert.assertEquals(subscription.getStartsAt(), new DateTime("2010-07-28T07:00:00Z"));
        Assert.assertEquals(subscription.getCollectionMethod(), "manual");
        Assert.assertEquals(subscription.getNetTerms(), (Integer) 10);
        Assert.assertEquals(subscription.getPoNumber(), "PO19384");
        Assert.assertEquals(subscription.getFirstRenewalDate(), new DateTime("2011-07-01T07:00:00Z"));

        return subscription;
    }

    private void verifyPaginationData(final Subscription subscription) {
        // Verify nested attributes
        Assert.assertEquals(subscription.getAccount().getHref(), "https://api.recurly.com/v2/accounts/1");
        Assert.assertEquals(subscription.getAccount().getAccountCode(), "1");
    }

    private void verifyPendingSubscription(final Subscription subscription) {
        Subscription pending = subscription.getPendingSubscription();
        Assert.assertEquals(pending.getPlan().getPlanCode(), "silver");
        Assert.assertEquals(pending.getPlan().getName(), "Silver plan");
        Assert.assertEquals(pending.getUnitAmountInCents(), (Integer) 400);
        Assert.assertEquals(pending.getQuantity(), (Integer) 1);
        Assert.assertEquals(pending.getAddOns().size(), 0);
    }
}
