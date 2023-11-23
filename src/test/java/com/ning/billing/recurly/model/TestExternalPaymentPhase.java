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

 import java.math.BigDecimal;

 import org.joda.time.DateTime;
 import org.testng.Assert;
 import org.testng.annotations.Test;
 
 public class TestExternalPaymentPhase extends TestModelBase {
 
     @Test(groups = "fast")
     public void testDeserialization() throws Exception {
         final String externalPaymentPhaseData = 
           "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
           "<external_payment_phase href=\"https://your-subdomain.recurly.com/v2/external_invoices/twqswp627ri3\">" +
           "  <id>twqswp627ri3</id>" +
           "  <started_at type=\"datetime\">2023-11-15T00:00:00Z</started_at>" +
           "  <ends_at type=\"datetime\">2023-11-17T21:27:10Z</ends_at>" +
           "  <starting_billing_period_index>1</starting_billing_period_index>" +
           "  <ending_billing_period_index>2</ending_billing_period_index>" +
           "  <offer_type>FREE_TRIAL</offer_type>" +
           "  <offer_name>introductory</offer_name>" +
           "  <period_count>2</period_count>" +
           "  <period_length>TWO WEEKS</period_length>" +
           "  <amount>1.99</amount>" +
           "  <currency>USD</currency>" +
           "  <created_at type=\"datetime\">2022-09-12T18:40:51Z</created_at>" +
           "  <updated_at type=\"datetime\">2022-09-12T18:40:51Z</updated_at>" +
           "</external_payment_phase>";
 
         final ExternalPaymentPhase externalPaymentPhase = xmlMapper.readValue(externalPaymentPhaseData, ExternalPaymentPhase.class);
 
         Assert.assertEquals(externalPaymentPhase.getHref(), "https://your-subdomain.recurly.com/v2/external_invoices/twqswp627ri3");
         Assert.assertEquals(externalPaymentPhase.getStartedAt(), new DateTime("2023-11-15T00:00:00Z"));
         Assert.assertEquals(externalPaymentPhase.getEndsAt(), new DateTime("2023-11-17T21:27:10Z"));
         Assert.assertEquals(externalPaymentPhase.getStartingBillingPeriodIndex(), 1);
         Assert.assertEquals(externalPaymentPhase.getEndingBillingPeriodIndex(), 2);
         Assert.assertEquals(externalPaymentPhase.getOfferType(), "FREE_TRIAL");
         Assert.assertEquals(externalPaymentPhase.getOfferName(), "introductory");
         Assert.assertEquals(externalPaymentPhase.getPeriodCount(), 2);
         Assert.assertEquals(externalPaymentPhase.getPeriodLength(), "TWO WEEKS");
         Assert.assertEquals(externalPaymentPhase.getAmount(), new BigDecimal("1.99"));
         Assert.assertEquals(externalPaymentPhase.getCurrency(), "USD");
         Assert.assertEquals(externalPaymentPhase.getCreatedAt(), new DateTime("2022-09-12T18:40:51Z"));
         Assert.assertEquals(externalPaymentPhase.getUpdatedAt(), new DateTime("2022-09-12T18:40:51Z"));
     }
 }
 