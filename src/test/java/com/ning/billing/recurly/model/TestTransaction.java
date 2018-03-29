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

import com.ning.billing.recurly.TestUtils;
import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class TestTransaction extends TestModelBase {

    @Test(groups = "fast")
    public void testDeserialization() throws Exception {
        // See http://docs.recurly.com/api/invoices
        final String transactionData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                       "<transaction href=\"https://your-subdomain.recurly.com/v2/transactions/a13acd8fe4294916b79aec87b7ea441f\">\n" +
                                       "  <account href=\"https://your-subdomain.recurly.com/v2/accounts/1\"/>\n" +
                                       "  <invoice href=\"https://your-subdomain.recurly.com/v2/invoices/1108\"/>\n" +
                                       "  <uuid>a13acd8fe4294916b79aec87b7ea441f</uuid>\n" +
                                       "  <action>purchase</action>\n" +
                                       "  <amount_in_cents type=\"integer\">1000</amount_in_cents>\n" +
                                       "  <tax_in_cents type=\"integer\">0</tax_in_cents>\n" +
                                       "  <currency>USD</currency>\n" +
                                       "  <status>success</status>\n" +
                                       "  <payment_method>check</payment_method>\n" +
                                       "  <reference nil=\"nil\"/>\n" +
                                       "  <source>transaction</source>\n" +
                                       "  <recurring type=\"boolean\">false</recurring>\n" +
                                       "  <test type=\"boolean\">true</test>\n" +
                                       "  <voidable type=\"boolean\">true</voidable>\n" +
                                       "  <refundable type=\"boolean\">true</refundable>\n" +
                                       "  <ip_address nil=\"nil\"/>\n" +
                                       "  <cvv_result code=\"\" nil=\"nil\"></cvv_result>" +
                                       "  <avs_result code=\"\" nil=\"nil\"></avs_result>" +
                                       "  <avs_result_street nil=\"nil\"></avs_result_street>" +
                                       "  <avs_result_postal nil=\"nil\"></avs_result_postal>" +
                                       "  <gateway_type>test</gateway_type>\n" +
                                       "  <origin>api</origin>\n" +
                                       "  <message>Successful test transaction</message>\n" +
                                       "  <approval_code>P1234577Q</approval_code>\n" +
                                       "  <failure_type>Declined by the gateway</failure_type>\n" +
                                       "  <created_at type=\"dateTime\">2015-06-19T03:01:33Z</created_at>\n" +
                                       "  <updated_at type=\"dateTime\">2015-06-19T03:01:33Z</updated_at>\n" +
                                       "  <details>\n" +
                                       "    <account>\n" +
                                       "      <account_code>1</account_code>\n" +
                                       "      <first_name nil=\"nil\"/>\n" +
                                       "      <last_name nil=\"nil\"/>\n" +
                                       "      <company nil=\"nil\"/>\n" +
                                       "      <email nil=\"nil\"/>\n" +
                                       "      <billing_info>\n" +
                                       "        <first_name>Verena</first_name>\n" +
                                       "        <last_name>Example</last_name>\n" +
                                       "        <address1>123 Main St.</address1>\n" +
                                       "        <address2 nil=\"nil\"/>\n" +
                                       "        <city>San Francisco</city>\n" +
                                       "        <state>CA</state>\n" +
                                       "        <zip>94105</zip>\n" +
                                       "        <country>US</country>\n" +
                                       "        <phone nil=\"nil\"/>\n" +
                                       "        <vat_number nil=\"nil\"/>\n" +
                                       "      </billing_info>\n" +
                                       "    </account>\n" +
                                       "  </details>\n" +
                                       "  <a name=\"refund\" href=\"https://your-subdomain.recurly.com/v2/transactions/a13acd8fe4294916b79aec87b7ea441f\" method=\"delete\"/>\n" +
                                       "</transaction>";

        final Transaction transaction = xmlMapper.readValue(transactionData, Transaction.class);
        Assert.assertEquals(transaction.getHref(), "https://your-subdomain.recurly.com/v2/transactions/a13acd8fe4294916b79aec87b7ea441f");
        Assert.assertEquals(transaction.getAccount().getHref(), "https://your-subdomain.recurly.com/v2/accounts/1");
        Assert.assertEquals(transaction.getInvoice().getHref(), "https://your-subdomain.recurly.com/v2/invoices/1108");
        Assert.assertEquals(transaction.getUuid(), "a13acd8fe4294916b79aec87b7ea441f");
        Assert.assertEquals(transaction.getSource(), "transaction");
        Assert.assertEquals(transaction.getRecurring(), new Boolean(false));
        Assert.assertEquals(transaction.getTest(), new Boolean(true));
        Assert.assertEquals(transaction.getVoidable(), new Boolean(true));
        Assert.assertEquals(transaction.getRefundable(), new Boolean(true));
        Assert.assertNull(transaction.getIpAddress());
        Assert.assertNull(transaction.getAvsResult());
        Assert.assertNull(transaction.getAvsResultPostal());
        Assert.assertNull(transaction.getAvsResultStreet());
        Assert.assertNull(transaction.getCvvResult());
        Assert.assertEquals(transaction.getCreatedAt(), new DateTime("2015-06-19T03:01:33Z"));
        Assert.assertEquals(transaction.getUpdatedAt(), new DateTime("2015-06-19T03:01:33Z"));
        Assert.assertEquals(transaction.getFailureType(), "Declined by the gateway");
        Assert.assertEquals(transaction.getGatewayType(), "test");
        Assert.assertEquals(transaction.getOrigin(), "api");
        Assert.assertEquals(transaction.getApprovalCode(), "P1234577Q");

        final Account account = transaction.getDetails().getAccount();
        Assert.assertEquals(account.getAccountCode(), "1");
        Assert.assertNull(account.getFirstName());
        Assert.assertNull(account.getLastName());
        Assert.assertNull(account.getCompanyName());
        Assert.assertNull(account.getEmail());

        final BillingInfo billingInfo = account.getBillingInfo();
        Assert.assertEquals(billingInfo.getFirstName(), "Verena");
        Assert.assertEquals(billingInfo.getLastName(), "Example");
        Assert.assertEquals(billingInfo.getAddress1(), "123 Main St.");
        Assert.assertNull(billingInfo.getAddress2());
        Assert.assertEquals(billingInfo.getCity(), "San Francisco");
        Assert.assertEquals(billingInfo.getZip(), "94105");
        Assert.assertEquals(billingInfo.getCountry(), "US");
        Assert.assertNull(billingInfo.getPhone());
        Assert.assertNull(billingInfo.getVatNumber());
    }

    @Test(groups = "fast")
    public void testTransactionErrorDeserialization() throws Exception {
        // See http://docs.recurly.com/api/invoices
        final String transactionData = "<transaction href=\"https://your-subdomain.recurly.com/v2/transactions/41de32a87e061a97bb27724194856e4e\" type=\"credit_card\">\n" +
                "        <account href=\"https://your-subdomain.recurly.com/v2/accounts/70e5ff0e-df50-11e7-9485-0aabf6bafcb0\"/>\n" +
                "        <invoice href=\"https://your-subdomain.recurly.com/v2/invoices/55959127\"/>\n" +
                "        <subscription href=\"https://your-subdomain.recurly.com/v2/subscriptions/41ba25f2e4bbd6f41ab1734237aca931\"/>\n" +
                "        <uuid>41de32a87e061a97bb27724194856e4e</uuid>\n" +
                "        <action>purchase</action>\n" +
                "        <amount_in_cents type=\"integer\">8898</amount_in_cents>\n" +
                "        <tax_in_cents type=\"integer\">0</tax_in_cents>\n" +
                "        <currency>USD</currency>\n" +
                "        <status>declined</status>\n" +
                "        <payment_method>credit_card</payment_method>\n" +
                "        <reference>2485247626;</reference>\n" +
                "        <source>subscription</source>\n" +
                "        <recurring type=\"boolean\">true</recurring>\n" +
                "        <test type=\"boolean\">false</test>\n" +
                "        <voidable type=\"boolean\">false</voidable>\n" +
                "        <refundable type=\"boolean\">false</refundable>\n" +
                "        <ip_address nil=\"nil\"></ip_address>\n" +
                "        <transaction_error>\n" +
                "            <error_code>insufficient_funds</error_code>\n" +
                "            <error_category>soft</error_category>\n" +
                "            <merchant_message>The card has insufficient funds to cover the cost of the transaction.</merchant_message>\n" +
                "            <customer_message>Your transaction was declined due to insufficient funds in your account. Please use a different card or contact your bank.</customer_message>\n" +
                "            <gateway_error_code>302</gateway_error_code>\n" +
                "        </transaction_error>\n" +
                "        <cvv_result code=\"\" nil=\"nil\"></cvv_result>\n" +
                "        <avs_result code=\"A\">Street address matches, but 5-digit and 9-digit postal code do not match.</avs_result>\n" +
                "        <avs_result_street nil=\"nil\"></avs_result_street>\n" +
                "        <avs_result_postal nil=\"nil\"></avs_result_postal>\n" +
                "        <created_at type=\"datetime\">2017-12-19T15:32:16Z</created_at>\n" +
                "        <details>\n" +
                "            <account>\n" +
                "                <account_code>70e5ff0e-df50-11e7-9485-0aabf6bafcb0</account_code>\n" +
                "                <first_name>jaqueace</first_name>\n" +
                "                <last_name>parker</last_name>\n" +
                "                <company nil=\"nil\"></company>\n" +
                "                <email>mookiebrienbrook@gmail.com</email>\n" +
                "                <billing_info type=\"credit_card\">\n" +
                "                    <first_name>jaqueace</first_name>\n" +
                "                    <last_name>parker</last_name>\n" +
                "                    <address1 nil=\"nil\"></address1>\n" +
                "                    <address2 nil=\"nil\"></address2>\n" +
                "                    <city nil=\"nil\"></city>\n" +
                "                    <state nil=\"nil\"></state>\n" +
                "                    <zip>46545</zip>\n" +
                "                    <country>US</country>\n" +
                "                    <phone nil=\"nil\"></phone>\n" +
                "                    <vat_number nil=\"nil\"></vat_number>\n" +
                "                    <card_type>MasterCard</card_type>\n" +
                "                    <year type=\"integer\">2021</year>\n" +
                "                    <month type=\"integer\">1</month>\n" +
                "                    <first_six>533248</first_six>\n" +
                "                    <last_four>9687</last_four>\n" +
                "                </billing_info>\n" +
                "            </account>\n" +
                "        </details>\n" +
                "    </transaction>";

        final Transaction transaction = xmlMapper.readValue(transactionData, Transaction.class);
        final TransactionError transactionError = transaction.getTransactionError();

        Assert.assertEquals(transactionError.getErrorCode(), "insufficient_funds");
        Assert.assertEquals(transactionError.getErrorCategory(), "soft");
        Assert.assertEquals(transactionError.getCustomerMessage(), "Your transaction was declined due to insufficient funds in your account. Please use a different card or contact your bank.");
        Assert.assertEquals(transactionError.getMerchantMessage(), "The card has insufficient funds to cover the cost of the transaction.");
        Assert.assertEquals(transactionError.getGatewayErrorCode(), "302");
    }

    @Test(groups = "fast")
    public void testHashCodeAndEquality() throws Exception {
        // create transactions of the same value but difference references
        Transaction transaction = TestUtils.createRandomTransaction(0);
        Transaction otherTransaction = TestUtils.createRandomTransaction(0);

        assertNotEquals(System.identityHashCode(transaction), System.identityHashCode(otherTransaction));
        assertEquals(transaction.hashCode(), otherTransaction.hashCode());
        assertEquals(transaction, otherTransaction);
    }
}
