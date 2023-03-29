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

 import org.joda.time.DateTime;
 import org.testng.Assert;
 import org.testng.annotations.Test;
 
 public class TestExternalInvoices extends TestModelBase {
 
     @Test(groups = "fast")
     public void testDeserialization() throws Exception {
         final String externalInvoicesData = 
           "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
           "<external_invoices type=\"array\">" +
           "  <external_invoice href=\"https://your-subdomain.recurly.com/v2/external_invoices/scaig66ovogw\">" +
           "    <account href=\"https://your-subdomain.recurly.com/v2/accounts/scaig66ovoga\"/>" +
           "    <external_subscription href=\"https://your-subdomain.recurly.com/v2/external_subscriptions/scaig66ovoge\"/>" +
           "    <external_id>external_id</external_id>" +
           "    <state>paid</state>" +
           "    <currency>USD</currency>" +
           "    <total type=\"integer\">123</total>" +
           "    <line_items type=\"array\">" +
           "      <external_charge>" +
           "        <account href=\"https://your-subdomain.recurly.com/v2/accounts/scaig66ovoga\"/>" +
           "        <external_invoice href=\"https://your-subdomain.recurly.com/v2/external_invoices/scaig66ovogw\"/>" +
           "        <description>description</description>" +
           "        <unit_amount type=\"integer\">123</unit_amount>" +
           "        <currency>USD</currency>" +
           "        <quantity>123</quantity>" +
           "        <created_at type=\"datetime\">2022-09-12T18:40:51Z</created_at>" +
           "        <updated_at type=\"datetime\">2022-09-12T18:40:51Z</updated_at>" +
           "      </external_charge>" +
           "    </line_items>" +
           "    <purchased_at type=\"datetime\">2022-09-12T18:40:51Z</purchased_at>" +
           "    <created_at type=\"datetime\">2022-09-12T18:40:51Z</created_at>" +
           "    <updated_at type=\"datetime\">2022-09-12T18:40:51Z</updated_at>" +
           "  </external_invoice>" +
           "</external_invoices>";
 
         final ExternalInvoices accountExternalInvoices = xmlMapper.readValue(externalInvoicesData, ExternalInvoices.class);
         Assert.assertEquals(accountExternalInvoices.size(), 1);
 
         final ExternalInvoice externalInvoice = accountExternalInvoices.get(0);
         Assert.assertEquals(externalInvoice.getHref(), "https://your-subdomain.recurly.com/v2/external_invoices/scaig66ovogw");
         Assert.assertEquals(externalInvoice.getAccount().getHref(), "https://your-subdomain.recurly.com/v2/accounts/scaig66ovoga");
         Assert.assertEquals(externalInvoice.getExternalSubscription().getHref(), "https://your-subdomain.recurly.com/v2/external_subscriptions/scaig66ovoge");
         Assert.assertEquals(externalInvoice.getExternalId(), "external_id");
         Assert.assertEquals(externalInvoice.getState(), "paid");
         Assert.assertEquals(externalInvoice.getCurrency(), "USD");
         Assert.assertEquals(externalInvoice.getTotalInCents(), new Integer(123));
         Assert.assertEquals(externalInvoice.getPurchasedAt(), new DateTime("2022-09-12T18:40:51Z"));
         Assert.assertEquals(externalInvoice.getCreatedAt(), new DateTime("2022-09-12T18:40:51Z"));
         Assert.assertEquals(externalInvoice.getUpdatedAt(), new DateTime("2022-09-12T18:40:51Z"));

         final LineItems lineItems = externalInvoice.getLineItems();
         Assert.assertEquals(lineItems.size(), 1);

         final ExternalCharge externalCharge = lineItems.get(0);
         Assert.assertEquals(externalCharge.getAccount().getHref(), "https://your-subdomain.recurly.com/v2/accounts/scaig66ovoga");
         Assert.assertEquals(externalCharge.getExternalInvoice().getHref(), "https://your-subdomain.recurly.com/v2/external_invoices/scaig66ovogw");
         Assert.assertEquals(externalCharge.getDescription(), "description");
         Assert.assertEquals(externalCharge.getUnitAmount(), new Integer(123));
         Assert.assertEquals(externalCharge.getCurrency(), "USD");
         Assert.assertEquals(externalCharge.getQuantity(), new Integer(123));
         Assert.assertEquals(externalCharge.getCreatedAt(), new DateTime("2022-09-12T18:40:51Z"));
         Assert.assertEquals(externalCharge.getUpdatedAt(), new DateTime("2022-09-12T18:40:51Z"));
     }
 }
 