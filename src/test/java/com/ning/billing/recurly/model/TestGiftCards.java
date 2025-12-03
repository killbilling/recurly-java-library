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

 import java.util.List;

 import org.joda.time.DateTime;
 import org.testng.Assert;
 import org.testng.annotations.Test;

 public class TestGiftCards extends TestModelBase {

     @Test(groups = "fast")
     public void testDeserialization() throws Exception {
        final String giftCardsData =
               "<gift_cards type=\"array\">" +
               "    <gift_card href=\"https://api.recurly.com/v2/gift_cards/1988596967980562362\">\n" +
               "        <gifter_account href=\"https://api.recurly.com/v2/accounts/1\" />\n" +
               "        <invoice href=\"https://api.recurly.com/v2/invoices/1001\" />\n" +
               "        <recipient_account href=\"https://api.recurly.com/v2/accounts/1\" />\n" +
               "        <unit_amount_in_cents type=\"integer\">2000</unit_amount_in_cents>\n" +
               "        <currency>USD</currency>   \n" +
               "        <redemption_code>AI4VOVO1RC74H9E2</redemption_code>\n" +
               "        <id type=\"integer\">1988596967980562362</id>\n" +
               "        <created_at type=\"dateTime\">2016-04-15T07:00:00Z</created_at>\n" +
               "        <updated_at type=\"dateTime\">2016-12-27T07:00:00Z</updated_at>\n" +
               "        <delivered_at type=\"dateTime\">2016-12-25T07:00:05Z</delivered_at>\n" +
               "        <redeemed_at type=\"dateTime\">2016-12-27T07:00:00Z</redeemed_at>\n" +
               "        <canceled_at nil=\"nil\"></canceled_at>\n" +
               "        <product_code>gift_card</product_code>\n" +
               "        <balance_in_cents type=\"integer\">2000</balance_in_cents>\n" +
               "        <delivery>\n" +
               "            <deliver_at nil=\"nil\"></deliver_at>\n" +
               "            <gifter_name>Sally</gifter_name>\n" +
               "            <personal_message>Hi John, Happy Birthday! I hope you have a great day! Love, Sally</personal_message>\n" +
               "            <method>email</method>\n" +
               "            <email_address>john@example.com</email_address>\n" +
               "            <first_name>John</first_name>\n" +
               "            <last_name>Smith</last_name>\n" +
               "            <address>\n" +
               "               <address1>400 Alabama St</address1>\n" +
               "               <address2></address2>\n" +
               "               <city>San Francisco</city>\n" +
               "               <state>CA</state>\n" +
               "               <zip>94110</zip>\n" +
               "               <country>US</country>\n" +
               "               <phone>555-555-5555</phone>\n" +
               "           </address>\n" +
               "        </delivery>\n" +
               "        <liability_gl_account_id>123</liability_gl_account_id>\n" +
               "        <revenue_gl_account_id>456</revenue_gl_account_id>\n" +
               "        <performance_obligation_id>789</performance_obligation_id>\n" +
               "        <tax_service_opt_out type=\"boolean\">true</tax_service_opt_out>\n" +
               "   </gift_card>" +
               "</gift_cards>";

        final GiftCards giftCards = xmlMapper.readValue(giftCardsData, GiftCards.class);
        Assert.assertEquals(giftCards.size(), 1);

        final GiftCard giftCard = giftCards.get(0);

        Assert.assertEquals(giftCard.getCurrency(), "USD");
        Assert.assertEquals(giftCard.getRedemptionCode(), "AI4VOVO1RC74H9E2");
        Assert.assertEquals(giftCard.getProductCode(), "gift_card");
        Assert.assertEquals(giftCard.getBalanceInCents(), new Integer(2000));
        Assert.assertEquals(giftCard.getUnitAmountInCents(), new Integer(2000));
        Assert.assertEquals(giftCard.getId(), new Long(1988596967980562362L));
        Assert.assertEquals(giftCard.getCreatedAt(), new DateTime("2016-04-15T07:00:00Z"));
        Assert.assertEquals(giftCard.getUpdatedAt(), new DateTime("2016-12-27T07:00:00Z"));
        Assert.assertEquals(giftCard.getRedeemedAt(), new DateTime("2016-12-27T07:00:00Z"));
        Assert.assertEquals(giftCard.getDeliveredAt(), new DateTime("2016-12-25T07:00:05Z"));
        Assert.assertEquals(giftCard.getLiabilityGlAccountId(), "123");
        Assert.assertEquals(giftCard.getRevenueGlAccountId(), "456");
        Assert.assertEquals(giftCard.getPerformanceObligationId(), "789");
        Assert.assertEquals(giftCard.getTaxServiceOptOut(), Boolean.TRUE);
        Assert.assertNull(giftCard.getCanceledAt());

        final Delivery delivery = giftCard.getDelivery();

        Assert.assertEquals(delivery.getMethod(), "email");
        Assert.assertEquals(delivery.getEmailAddress(), "john@example.com");
        Assert.assertEquals(delivery.getFirstName(), "John");
        Assert.assertEquals(delivery.getLastName(), "Smith");
        Assert.assertEquals(delivery.getGifterName(), "Sally");
        Assert.assertEquals(delivery.getPersonalMessage(), "Hi John, Happy Birthday! I hope you have a great day! Love, Sally");

        final Address address = delivery.getAddress();

        Assert.assertEquals(address.getAddress1(), "400 Alabama St");
        Assert.assertNull(address.getAddress2());
        Assert.assertEquals(address.getCity(), "San Francisco");
        Assert.assertEquals(address.getState(), "CA");
        Assert.assertEquals(address.getCountry(), "US");
        Assert.assertEquals(address.getZip(), "94110");
        Assert.assertEquals(address.getPhone(), "555-555-5555");
     }

     @Test(groups = "fast")
     public void testSerializationWithTaxServiceOptOut() throws Exception {
        final GiftCard giftCard = new GiftCard();
        giftCard.setProductCode("gift_card_100");
        giftCard.setCurrency("USD");
        giftCard.setUnitAmountInCents(10000);
        giftCard.setTaxServiceOptOut(true);

        final String xml = xmlMapper.writeValueAsString(giftCard);

        Assert.assertTrue(xml.contains("<product_code>gift_card_100</product_code>"));
        Assert.assertTrue(xml.contains("<currency>USD</currency>"));
        Assert.assertTrue(xml.contains("<unit_amount_in_cents>10000</unit_amount_in_cents>"));
        Assert.assertTrue(xml.contains("<tax_service_opt_out>true</tax_service_opt_out>"));
     }

     @Test(groups = "fast")
     public void testDeserializationWithTaxServiceOptOutFalse() throws Exception {
        final String giftCardData =
               "<gift_card>" +
               "    <product_code>gift_card</product_code>\n" +
               "    <currency>USD</currency>   \n" +
               "    <unit_amount_in_cents type=\"integer\">2000</unit_amount_in_cents>\n" +
               "    <tax_service_opt_out type=\"boolean\">false</tax_service_opt_out>\n" +
               "</gift_card>";

        final GiftCard giftCard = xmlMapper.readValue(giftCardData, GiftCard.class);

        Assert.assertEquals(giftCard.getProductCode(), "gift_card");
        Assert.assertEquals(giftCard.getCurrency(), "USD");
        Assert.assertEquals(giftCard.getUnitAmountInCents(), new Integer(2000));
        Assert.assertEquals(giftCard.getTaxServiceOptOut(), Boolean.FALSE);
     }

     @Test(groups = "fast")
     public void testDeserializationWithoutTaxServiceOptOut() throws Exception {
        final String giftCardData =
               "<gift_card>" +
               "    <product_code>gift_card</product_code>\n" +
               "    <currency>USD</currency>   \n" +
               "    <unit_amount_in_cents type=\"integer\">2000</unit_amount_in_cents>\n" +
               "</gift_card>";

        final GiftCard giftCard = xmlMapper.readValue(giftCardData, GiftCard.class);

        Assert.assertEquals(giftCard.getProductCode(), "gift_card");
        Assert.assertEquals(giftCard.getCurrency(), "USD");
        Assert.assertEquals(giftCard.getUnitAmountInCents(), new Integer(2000));
        Assert.assertNull(giftCard.getTaxServiceOptOut());
     }
 }
