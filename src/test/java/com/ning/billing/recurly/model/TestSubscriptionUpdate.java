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

public class TestSubscriptionUpdate extends TestModelBase {

    @Test(groups = "fast")
    public void testDeserialization() throws Exception {
        // See https://dev.recurly.com/docs/list-subscriptions
        final String subscriptionData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                        "<subscription>\n" +
                                        "  <timeframe>term_end</timeframe>\n" +
                                        "  <plan_code>gold</plan_code>\n" +
                                        "  <unit_amount_in_cents type=\"integer\">800</unit_amount_in_cents>\n" +
                                        "  <quantity type=\"integer\">1</quantity>\n" +
                                        "  <net_terms type=\"integer\">10</net_terms>\n" +
                                        "  <net_terms_type>net</net_terms_type>\n" +
                                        "  <subscription_add_ons type=\"array\">\n" +
                                        "  </subscription_add_ons>\n" +
                                        "</subscription>";

        final SubscriptionUpdate subscription = xmlMapper.readValue(subscriptionData, SubscriptionUpdate.class);
        Assert.assertEquals(subscription.getTimeframe(), SubscriptionUpdate.Timeframe.term_end);
        Assert.assertEquals(subscription.getPlanCode(), "gold");
        Assert.assertEquals(subscription.getUnitAmountInCents(), (Integer) 800);
        Assert.assertEquals(subscription.getQuantity(), (Integer) 1);
        Assert.assertEquals(subscription.getNetTerms(), (Integer) 10);
        Assert.assertEquals(subscription.getNetTermsType(), "net");
        Assert.assertEquals(subscription.getAddOns().size(), 0);
    }

    @Test(groups = "fast")
    public void testSerializationWithTerms() throws Exception{
        final SubscriptionUpdate subscription = new SubscriptionUpdate();
        subscription.setPlanCode("gold");
        subscription.setTimeframe(SubscriptionUpdate.Timeframe.now);
        subscription.setUnitAmountInCents(800);
        subscription.setQuantity(1);
        subscription.setNetTerms(10);
        subscription.setNetTermsType("eom");

        final String xml = xmlMapper.writeValueAsString(subscription);
        Assert.assertEquals(xml, "<subscription xmlns=\"\">" +
                                 "<timeframe>now</timeframe>" +
                                 "<unit_amount_in_cents>800</unit_amount_in_cents>" +
                                 "<quantity>1</quantity>" +
                                 "<plan_code>gold</plan_code>" +
                                 "<net_terms>10</net_terms>" +
                                 "<net_terms_type>eom</net_terms_type>" +
                                 "</subscription>");
    }

    @Test(groups = "fast")
    public void testSerializationWithoutAddOns() throws Exception {
        final SubscriptionUpdate subscription = new SubscriptionUpdate();
        subscription.setPlanCode("gold");
        subscription.setTimeframe(SubscriptionUpdate.Timeframe.now);
        subscription.setUnitAmountInCents(800);
        subscription.setQuantity(1);
        subscription.setBillingInfoUuid("uniqueUuid");

        final String xml = xmlMapper.writeValueAsString(subscription);
        Assert.assertEquals(xml, "<subscription xmlns=\"\">" +
                                 "<timeframe>now</timeframe>" +
                                 "<unit_amount_in_cents>800</unit_amount_in_cents>" +
                                 "<quantity>1</quantity>" +
                                 "<plan_code>gold</plan_code>" +
                                 "<billing_info_uuid>uniqueUuid</billing_info_uuid>" +
                                 "</subscription>");
    }

    @Test(groups = "fast")
    public void testSerializationWithProrationFlexibility() throws Exception {
        final ProrationSettings prorationSettings = new ProrationSettings();
        prorationSettings.setCredit("full_amount");
        prorationSettings.setCharge("none");

        final SubscriptionUpdate subscription = new SubscriptionUpdate();
        subscription.setPlanCode("gold");
        subscription.setTimeframe(SubscriptionUpdate.Timeframe.now);
        subscription.setUnitAmountInCents(800);
        subscription.setQuantity(1);
        subscription.setBillingInfoUuid("uniqueUuid");
        subscription.setProrationSettings(prorationSettings);

        final String xml = xmlMapper.writeValueAsString(subscription);
        Assert.assertEquals(xml, "<subscription xmlns=\"\">" +
                                 "<timeframe>now</timeframe>" +
                                 "<unit_amount_in_cents>800</unit_amount_in_cents>" +
                                 "<quantity>1</quantity>" +
                                 "<plan_code>gold</plan_code>" +
                                 "<proration_settings>" +
                                 "<credit>full_amount</credit>" +
                                 "<charge>none</charge>" +
                                 "</proration_settings>" +
                                 "<billing_info_uuid>uniqueUuid</billing_info_uuid>" +
                                 "</subscription>");

        Assert.assertEquals(subscription.getProrationSettings().toString(),
            "ProrationSettings{ charge='none', credit='full_amount' }");
    }

    @Test(groups = "fast")
    public void testSerializationWithEmptyAddOns() throws Exception {
        final SubscriptionUpdate subscription = new SubscriptionUpdate();
        subscription.setPlanCode("gold");
        subscription.setTimeframe(SubscriptionUpdate.Timeframe.now);
        subscription.setUnitAmountInCents(800);
        subscription.setQuantity(1);
        subscription.setAddOns(new SubscriptionAddOns());

        final String xml = xmlMapper.writeValueAsString(subscription);
        Assert.assertEquals(xml, "<subscription xmlns=\"\">" +
                                 "<timeframe>now</timeframe>" +
                                 "<unit_amount_in_cents>800</unit_amount_in_cents>" +
                                 "<quantity>1</quantity>" +
                                 "<subscription_add_ons></subscription_add_ons>" +
                                 "<plan_code>gold</plan_code>" +
                                 "</subscription>");
    }

    @Test(groups = "fast")
    public void testSerializationWithAddOns() throws Exception {
        final SubscriptionUpdate subscription = new SubscriptionUpdate();
        subscription.setPlanCode("gold");
        subscription.setTimeframe(SubscriptionUpdate.Timeframe.now);
        subscription.setUnitAmountInCents(800);
        subscription.setQuantity(1);
        final SubscriptionAddOns addOns = new SubscriptionAddOns();
        final SubscriptionAddOn addOn = new SubscriptionAddOn();
        addOn.setAddOnCode("extra_users");
        addOn.setQuantity(2);
        addOn.setUnitAmountInCents(1000);
        addOns.add(addOn);
        subscription.setAddOns(addOns);

        final String xml = xmlMapper.writeValueAsString(subscription);
        Assert.assertEquals(xml, "<subscription xmlns=\"\">" +
                                 "<timeframe>now</timeframe>" +
                                 "<unit_amount_in_cents>800</unit_amount_in_cents>" +
                                 "<quantity>1</quantity>" +
                                 "<subscription_add_ons>" +
                                 "<subscription_add_on>" +
                                 "<add_on_code>extra_users</add_on_code>" +
                                 "<unit_amount_in_cents>1000</unit_amount_in_cents>" +
                                 "<quantity>2</quantity>" +
                                 "</subscription_add_on>" +
                                 "</subscription_add_ons>" +
                                 "<plan_code>gold</plan_code>" +
                                 "</subscription>");
    }


    @Test(groups = "fast")
    public void testSerializationWithCustomFields() throws Exception {
        final SubscriptionUpdate subscription = new SubscriptionUpdate();
        subscription.setPlanCode("gold");
        subscription.setTimeframe(SubscriptionUpdate.Timeframe.now);
        subscription.setUnitAmountInCents(800);
        subscription.setQuantity(1);
        final CustomFields fields = new CustomFields();
        final CustomField customField= new CustomField();
        customField.setName("name1");
        customField.setValue("value1");
        fields.add(customField);
        subscription.setCustomFields(fields);

        final String xml = xmlMapper.writeValueAsString(subscription);
        Assert.assertEquals(xml, "<subscription xmlns=\"\">" +
                "<timeframe>now</timeframe>" +
                "<unit_amount_in_cents>800</unit_amount_in_cents>" +
                "<quantity>1</quantity>" +
                "<custom_fields>" +
                "<custom_field>" +
                "<name>name1</name>" +
                "<value>value1</value>" +
                "</custom_field>" +
                "</custom_fields>" +
                "<plan_code>gold</plan_code>" +
                "</subscription>");
    }

    @Test(groups = "fast")
    public void testSerializationWithCoupons() throws Exception {
        final SubscriptionUpdate subscription = new SubscriptionUpdate();
        subscription.setPlanCode("gold");
        subscription.setTimeframe(SubscriptionUpdate.Timeframe.now);
        subscription.setUnitAmountInCents(800);
        subscription.setQuantity(1);
        subscription.setCouponCode("my_coupon");

        final String xml = xmlMapper.writeValueAsString(subscription);
        Assert.assertEquals(xml, "<subscription xmlns=\"\">" +
                                 "<timeframe>now</timeframe>" +
                                 "<unit_amount_in_cents>800</unit_amount_in_cents>" +
                                 "<quantity>1</quantity>" +
                                 "<plan_code>gold</plan_code>" +
                                 "<coupon_code>my_coupon</coupon_code>" +
                                 "</subscription>");
    }
}
