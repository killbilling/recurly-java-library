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

public class TestErrors extends TestModelBase {

    @Test(groups = "fast")
    public void testSerializationErrors() throws Exception {
        final String errorsData = "<errors>\n" +
                                  "  <error field=\"billing_info.address1\" symbol=\"empty\">can't be empty</error>\n" +
                                  "  <error field=\"billing_info.year\" symbol=\"less_than\">must be less than 2050</error>\n" +
                                  "</errors>";

        final Errors errors = xmlMapper.readValue(errorsData, Errors.class);
        Assert.assertEquals(errors.getRecurlyErrors().get(0).getField(), "billing_info.address1");
        Assert.assertEquals(errors.getRecurlyErrors().get(0).getSymbol(), "empty");
        Assert.assertEquals(errors.getRecurlyErrors().get(0).getMessage(), "can't be empty");
        Assert.assertEquals(errors.getRecurlyErrors().get(1).getField(), "billing_info.year");
        Assert.assertEquals(errors.getRecurlyErrors().get(1).getSymbol(), "less_than");
        Assert.assertEquals(errors.getRecurlyErrors().get(1).getMessage(), "must be less than 2050");
    }

    @Test(groups = "fast")
    public void testSerializationTransactionErrors() throws Exception {
        final String errorsData = "<errors>\n" +
                                  "  <transaction_error>\n" +
                                  "    <error_code>fraud_ip_address</error_code>\n" +
                                  "    <error_category>fraud</error_category>\n" +
                                  "    <merchant_message>The payment gateway declined the transaction because it originated from an IP address known for fraudulent transactions.</merchant_message>\n" +
                                  "    <customer_message>The transaction was declined. Please contact support.</customer_message>\n" +
                                  "  </transaction_error>\n" +
                                  "  <error field=\"billing_info.base\" symbol=\"fraud_ip_address\">The transaction was declined. Please contact support.</error>\n" +
                                  "  <transaction href=\"https://your-subdomain.recurly.com/v2/transactions/3d1c6aa86e3d447eb0f3b4a6e3e074d9\" type=\"credit_card\">\n" +
                                  "    <account href=\"https://your-subdomain.recurly.com/v2/accounts/c9cd3b70-1559-11e3-8ffd-0800200c9a66\"/>\n" +
                                  "    <uuid>12578bb566572144deb5364d4ebd32ce</uuid>\n" +
                                  "    <action>verify</action>\n" +
                                  "    <amount_in_cents type=\"integer\">0</amount_in_cents>\n" +
                                  "    <tax_in_cents type=\"integer\">0</tax_in_cents>\n" +
                                  "    <currency>USD</currency>\n" +
                                  "    <status>declined</status>\n" +
                                  "    <reference>8433694</reference>\n" +
                                  "    <source>billing_info</source>\n" +
                                  "    <recurring type=\"boolean\">false</recurring>\n" +
                                  "    <test type=\"boolean\">true</test>\n" +
                                  "    <voidable type=\"boolean\">false</voidable>\n" +
                                  "    <refundable type=\"boolean\">false</refundable>\n" +
                                  "    <transaction_error>\n" +
                                  "      <error_code>fraud_ip_address</error_code>\n" +
                                  "      <error_category>fraud</error_category>\n" +
                                  "      <merchant_message>The payment gateway declined the transaction because it originated from an IP address known for fraudulent transactions.</merchant_message>\n" +
                                  "      <customer_message>The transaction was declined. Please contact support.</customer_message>\n" +
                                  "    </transaction_error>\n" +
                                  "    <cvv_result code=\"\" nil=\"nil\"></cvv_result>\n" +
                                  "    <avs_result code=\"\" nil=\"nil\"></avs_result>\n" +
                                  "    <avs_result_street nil=\"nil\"></avs_result_street>\n" +
                                  "    <avs_result_postal nil=\"nil\"></avs_result_postal>\n" +
                                  "    <created_at type=\"datetime\">2013-09-04T11:34:21Z</created_at>\n" +
                                  "    <details>\n" +
                                  "      <account>\n" +
                                  "        <account_code>4503f4df-acab-4097-9245-f4c130b5f9ac</account_code>\n" +
                                  "        <first_name>248e5</first_name>\n" +
                                  "        <last_name>f1a42f</last_name>\n" +
                                  "        <company>2a08b4344e</company>\n" +
                                  "        <email>0a39@test.com</email>\n" +
                                  "        <billing_info type=\"credit_card\">\n" +
                                  "          <first_name>0dec5</first_name>\n" +
                                  "          <last_name>91faa8</last_name>\n" +
                                  "          <address1>680a5d6012</address1>\n" +
                                  "          <address2>cf66c41dea</address2>\n" +
                                  "          <city>b77f65bff7</city>\n" +
                                  "          <state>c3274dd270</state>\n" +
                                  "          <zip>b12f7</zip>\n" +
                                  "          <country>f0ba2</country>\n" +
                                  "          <phone>6</phone>\n" +
                                  "          <vat_number>7</vat_number>\n" +
                                  "          <card_type>Visa</card_type>\n" +
                                  "          <year type=\"integer\">2015</year>\n" +
                                  "          <month type=\"integer\">11</month>\n" +
                                  "          <first_six>400000</first_six>\n" +
                                  "          <last_four>0093</last_four>\n" +
                                  "        </billing_info>\n" +
                                  "      </account>\n" +
                                  "    </details>\n" +
                                  "  </transaction>\n" +
                                  "</errors>";

        final Errors errors = xmlMapper.readValue(errorsData, Errors.class);
        Assert.assertEquals(errors.getTransactionError().getErrorCode(), "fraud_ip_address");
        Assert.assertEquals(errors.getTransactionError().getMerchantMessage(), "The payment gateway declined the transaction because it originated from an IP address known for fraudulent transactions.");
        Assert.assertEquals(errors.getTransactionError().getCustomerMessage(), "The transaction was declined. Please contact support.");

        Assert.assertEquals(errors.getTransaction().getUuid(), "12578bb566572144deb5364d4ebd32ce");
        Assert.assertEquals(errors.getTransaction().getAction(), "verify");
        Assert.assertEquals(errors.getTransaction().getAmountInCents(), (Integer) 0);
        Assert.assertEquals(errors.getTransaction().getTaxInCents(), (Integer) 0);
        Assert.assertEquals(errors.getTransaction().getCurrency(), "USD");
        Assert.assertEquals(errors.getTransaction().getStatus(), "declined");
        Assert.assertEquals(errors.getTransaction().getReference(), "8433694");
        Assert.assertEquals(errors.getTransaction().getSource(), "billing_info");
        Assert.assertFalse(errors.getTransaction().getRecurring());
        Assert.assertTrue(errors.getTransaction().getTest());
        Assert.assertFalse(errors.getTransaction().getVoidable());
        Assert.assertFalse(errors.getTransaction().getRefundable());
        Assert.assertEquals(errors.getTransaction().getTransactionError().getErrorCode(), "fraud_ip_address");
        Assert.assertEquals(errors.getTransaction().getTransactionError().getMerchantMessage(), "The payment gateway declined the transaction because it originated from an IP address known for fraudulent transactions.");
        Assert.assertEquals(errors.getTransaction().getTransactionError().getCustomerMessage(), "The transaction was declined. Please contact support.");

        Assert.assertEquals(errors.getTransaction().getDetails().getAccount().getAccountCode(), "4503f4df-acab-4097-9245-f4c130b5f9ac");
        Assert.assertEquals(errors.getTransaction().getDetails().getAccount().getFirstName(), "248e5");
        Assert.assertEquals(errors.getTransaction().getDetails().getAccount().getLastName(), "f1a42f");
        // TODO Element not consistent with Account API
        //Assert.assertEquals(errors.getTransaction().getDetails().getAccount().getCompanyName(), "2a08b4344e");
        Assert.assertEquals(errors.getTransaction().getDetails().getAccount().getEmail(), "0a39@test.com");
        Assert.assertEquals(errors.getTransaction().getDetails().getAccount().getBillingInfo().getFirstName(), "0dec5");
        Assert.assertEquals(errors.getTransaction().getDetails().getAccount().getBillingInfo().getLastName(), "91faa8");
        Assert.assertEquals(errors.getTransaction().getDetails().getAccount().getBillingInfo().getAddress1(), "680a5d6012");
        Assert.assertEquals(errors.getTransaction().getDetails().getAccount().getBillingInfo().getAddress2(), "cf66c41dea");
        Assert.assertEquals(errors.getTransaction().getDetails().getAccount().getBillingInfo().getCity(), "b77f65bff7");
        Assert.assertEquals(errors.getTransaction().getDetails().getAccount().getBillingInfo().getState(), "c3274dd270");
        Assert.assertEquals(errors.getTransaction().getDetails().getAccount().getBillingInfo().getZip(), "b12f7");
        Assert.assertEquals(errors.getTransaction().getDetails().getAccount().getBillingInfo().getCountry(), "f0ba2");
        Assert.assertEquals(errors.getTransaction().getDetails().getAccount().getBillingInfo().getPhone(), "6");
        Assert.assertEquals(errors.getTransaction().getDetails().getAccount().getBillingInfo().getVatNumber(), "7");
        Assert.assertEquals(errors.getTransaction().getDetails().getAccount().getBillingInfo().getCardType(), "Visa");
        Assert.assertEquals(errors.getTransaction().getDetails().getAccount().getBillingInfo().getYear(), (Integer) 2015);
        Assert.assertEquals(errors.getTransaction().getDetails().getAccount().getBillingInfo().getMonth(), (Integer) 11);
        Assert.assertEquals(errors.getTransaction().getDetails().getAccount().getBillingInfo().getFirstSix(), "400000");
        Assert.assertEquals(errors.getTransaction().getDetails().getAccount().getBillingInfo().getLastFour(), "0093");
    }
}
