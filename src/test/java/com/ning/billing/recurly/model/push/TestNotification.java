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

package com.ning.billing.recurly.model.push;

import com.ning.billing.recurly.model.push.invoice.*;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ning.billing.recurly.model.Account;
import com.ning.billing.recurly.model.Plan;
import com.ning.billing.recurly.model.TestModelBase;
import com.ning.billing.recurly.model.push.account.AccountNotification;
import com.ning.billing.recurly.model.push.account.BillingInfoUpdatedNotification;
import com.ning.billing.recurly.model.push.account.CanceledAccountNotification;
import com.ning.billing.recurly.model.push.account.NewAccountNotification;
import com.ning.billing.recurly.model.push.payment.FailedPaymentNotification;
import com.ning.billing.recurly.model.push.payment.PaymentNotification;
import com.ning.billing.recurly.model.push.payment.PushTransaction;
import com.ning.billing.recurly.model.push.payment.SuccessfulPaymentNotification;
import com.ning.billing.recurly.model.push.payment.SuccessfulRefundNotification;
import com.ning.billing.recurly.model.push.payment.VoidPaymentNotification;
import com.ning.billing.recurly.model.push.subscription.CanceledSubscriptionNotification;
import com.ning.billing.recurly.model.push.subscription.ExpiredSubscriptionNotification;
import com.ning.billing.recurly.model.push.subscription.NewSubscriptionNotification;
import com.ning.billing.recurly.model.push.subscription.PushSubscription;
import com.ning.billing.recurly.model.push.subscription.ReactivatedAccountNotification;
import com.ning.billing.recurly.model.push.subscription.RenewedSubscriptionNotification;
import com.ning.billing.recurly.model.push.subscription.SubscriptionNotification;
import com.ning.billing.recurly.model.push.subscription.UpdatedSubscriptionNotification;

import com.google.common.base.CaseFormat;

// See https://recurly.readme.io/v2.0/page/webhooks
public class TestNotification extends TestModelBase {

    private static final Logger log = LoggerFactory.getLogger(TestNotification.class);

    private static final String ACCOUNTDATA = "<account>\n" +
                                              "  <account_code>1</account_code>\n" +
                                              "  <username nil=\"true\"></username>\n" +
                                              "  <email>verena@example.com</email>\n" +
                                              "  <first_name>Verena</first_name>\n" +
                                              "  <last_name>Example</last_name>\n" +
                                              "  <company_name nil=\"true\"></company_name>\n" +
                                              "</account>";

    private static final String SUBSCRIPTIONDATA = "<subscription>\n" +
                                                   "  <plan>\n" +
                                                   "    <plan_code>1dpt</plan_code>\n" +
                                                   "     <name>Subscription One</name>\n" +
                                                   "  </plan>\n" +
                                                   "  <uuid>292332928954ca62fa48048be5ac98ec</uuid>\n" +
                                                   "  <state>active</state>\n" +
                                                   "  <quantity type=\"integer\">1</quantity>\n" +
                                                   "  <total_amount_in_cents type=\"integer\">200</total_amount_in_cents>\n" +
                                                   "  <activated_at type=\"datetime\">2010-09-23T22:12:39Z</activated_at>\n" +
                                                   "  <canceled_at nil=\"true\"></canceled_at>\n" +
                                                   "  <expires_at nil=\"true\"></expires_at>\n" +
                                                   "  <current_period_started_at type=\"datetime\">2010-09-23T22:03:30Z</current_period_started_at>\n" +
                                                   "  <current_period_ends_at type=\"datetime\">2010-09-24T22:03:30Z</current_period_ends_at>\n" +
                                                   "  <trial_started_at nil=\"true\" type=\"datetime\"></trial_started_at>\n" +
                                                   "  <trial_ends_at nil=\"true\" type=\"datetime\"></trial_ends_at>\n" +
                                                   "  <starts_at type=\"datetime\">2010-09-23T07:00:00Z</starts_at>\n" +
                                                   "</subscription>";

    private static final String TRANSACTIONDATA = "<transaction>\n" +
                                                  "  <id>a5143c1d3a6f4a8287d0e2cc1d4c0427</id>\n" +
                                                  "  <invoice_id>1974a09kj90s0789dsf099798326881c</invoice_id>\n" +
                                                  "  <invoice_number type=\"integer\">2059</invoice_number>\n" +
                                                  "  <subscription_id>1974a098jhlkjasdfljkha898326881c</subscription_id>\n" +
                                                  "  <action>purchase</action>\n" +
                                                  "  <date type=\"datetime\">2009-11-22T13:10:38Z</date>\n" +
                                                  "  <amount_in_cents type=\"integer\">1000</amount_in_cents>\n" +
                                                  "  <status>Success</status>\n" +
                                                  "  <message>Bogus Gateway: Forced success</message>\n" +
                                                  "  <gateway_error_codes>00</gateway_error_codes>\n" +
                                                  "  <failure_type>Declined by the gateway</failure_type>\n" +
                                                  "  <reference></reference>\n" +
                                                  "  <cvv_result code=\"\"></cvv_result>\n" +
                                                  "  <avs_result code=\"D\">Street address and postal code match.</avs_result>\n" +
                                                  "  <avs_result_street>123 Main St.</avs_result_street>\n" +
                                                  "  <avs_result_postal>20121</avs_result_postal>\n" +
                                                  "  <source>subscription</source>\n" +
                                                  "  <test type=\"boolean\">true</test>\n" +
                                                  "  <voidable type=\"boolean\">true</voidable>\n" +
                                                  "  <refundable type=\"boolean\">true</refundable>\n" +
                                                  "</transaction>";

    private static final String INVOICEDATA = "<invoice>\n" +
                                              "  <uuid>ffc64d71d4b5404e93f13aac9c63b007</uuid>\n" +
                                              "  <subscription_id nil=\"true\"></subscription_id>\n" +
                                              "  <state>collected</state>\n" +
                                              "  <invoice_number_prefix></invoice_number_prefix>\n" +
                                              "  <invoice_number type=\"integer\">1000</invoice_number>\n" +
                                              "  <po_number>PO-12345</po_number>\n" +
                                              "  <vat_number></vat_number>\n" +
                                              "  <total_in_cents type=\"integer\">1100</total_in_cents>\n" +
                                              "  <currency>USD</currency>\n" +
                                              "  <date type=\"datetime\">2014-01-01T20:20:29Z</date>\n" +
                                              "  <closed_at type=\"datetime\">2014-01-01T20:24:02Z</closed_at>\n" +
                                              "  <net_terms type=\"integer\">0</net_terms>\n" +
                                              "  <collection_method>automatic</collection_method>\n" +
                                              "</invoice>";

    private <T extends Notification> void deserialize(final Class<T> clazz) {

        final String xmlElement = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, clazz.getSimpleName());
        final StringBuilder notificationDataBuilder = new StringBuilder()
                .append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
                .append("<").append(xmlElement).append(">\n");

        boolean isAccount = false, isSubscription = false, isPayment = false, isInvoice = false;

        if (AccountNotification.class.isAssignableFrom(clazz)) {
            notificationDataBuilder.append(ACCOUNTDATA).append("\n");
            isAccount = true;
        }

        if (SubscriptionNotification.class.isAssignableFrom(clazz)) {
            notificationDataBuilder.append(SUBSCRIPTIONDATA).append("\n");
            isSubscription = true;
        }

        if (PaymentNotification.class.isAssignableFrom(clazz)) {
            notificationDataBuilder.append(TRANSACTIONDATA).append("\n");
            isPayment = true;
        }

        if(InvoiceNotification.class.isAssignableFrom(clazz)) {
            notificationDataBuilder.append(INVOICEDATA).append("\n");
            isInvoice = true;
        }

        notificationDataBuilder.append("</").append(xmlElement).append(">");
        final String notificationData = notificationDataBuilder.toString();
        log.debug("Test deserialization of \n{}", notificationData);

        final Notification.Type detected = Notification.detect(notificationData);
        Assert.assertEquals(detected.getJavaType(), clazz);

        final T notification = Notification.read(notificationData, clazz);
        Assert.assertNotNull(notification);

        if (isAccount) {
            testAccountNotification((AccountNotification) notification);
        }
        if (isSubscription) {
            testSubscriptionNotification((SubscriptionNotification) notification);
        }
        if (isPayment) {
            testPaymentNotification((PaymentNotification) notification);
        }
        if (isInvoice) {
            testInvoiceNotification((InvoiceNotification) notification);
        }
        log.info("{} deserialized", clazz.getSimpleName());
    }

    private void testAccountNotification(final AccountNotification accountNotification) {
        final Account account = accountNotification.getAccount();
        Assert.assertNotNull(account);
        Assert.assertEquals(account.getAccountCode(), "1");
        Assert.assertNull(account.getUsername());
        Assert.assertEquals(account.getEmail(), "verena@example.com");
        Assert.assertEquals(account.getFirstName(), "Verena");
        Assert.assertEquals(account.getLastName(), "Example");
        Assert.assertNull(account.getCompanyName());
    }

    private void testSubscriptionNotification(final SubscriptionNotification subscriptionNotification) {
        final PushSubscription subscription = subscriptionNotification.getSubscription();
        Assert.assertNotNull(subscription);
        final Plan plan = subscription.getPlan();
        Assert.assertNotNull(plan);
        Assert.assertEquals(plan.getPlanCode(), "1dpt");
        Assert.assertEquals(plan.getName(), "Subscription One");
        Assert.assertEquals(subscription.getUuid(), "292332928954ca62fa48048be5ac98ec");
        Assert.assertEquals(subscription.getState(), "active");
        Assert.assertEquals(subscription.getQuantity(), Integer.valueOf(1));
        Assert.assertNull(subscription.getUnitAmountInCents());
        Assert.assertEquals(subscription.getTotalAmountInCents(), Integer.valueOf(200));
        Assert.assertEquals(subscription.getActivatedAt(), new DateTime("2010-09-23T22:12:39Z"));
        Assert.assertNull(subscription.getCanceledAt());
        Assert.assertNull(subscription.getExpiresAt());
        Assert.assertEquals(subscription.getCurrentPeriodStartedAt(), new DateTime("2010-09-23T22:03:30Z"));
        Assert.assertEquals(subscription.getCurrentPeriodEndsAt(), new DateTime("2010-09-24T22:03:30Z"));
        Assert.assertEquals(subscription.getStartsAt(), new DateTime("2010-09-23T07:00:00Z"));
        Assert.assertNull(subscription.getTrialStartedAt());
        Assert.assertNull(subscription.getTrialEndsAt());
    }

    private void testPaymentNotification(final PaymentNotification paymentNotification) {
        final PushTransaction transaction = paymentNotification.getTransaction();
        Assert.assertNotNull(transaction);
        Assert.assertEquals(transaction.getId(), "a5143c1d3a6f4a8287d0e2cc1d4c0427");
        Assert.assertEquals(transaction.getInvoiceId(), "1974a09kj90s0789dsf099798326881c");
        Assert.assertEquals(transaction.getInvoiceNumber(),Integer.valueOf(2059));
        Assert.assertEquals(transaction.getSubscriptionId(), "1974a098jhlkjasdfljkha898326881c");
        Assert.assertEquals(transaction.getDate(), new DateTime("2009-11-22T13:10:38Z"));
        Assert.assertEquals(transaction.getAction(), "purchase");
        Assert.assertEquals(transaction.getAmountInCents(), Integer.valueOf(1000));
        Assert.assertEquals(transaction.getStatus(), "Success");
        Assert.assertEquals(transaction.getMessage(), "Bogus Gateway: Forced success");
        Assert.assertEquals(transaction.getFailureType(), "Declined by the gateway");
        Assert.assertEquals(transaction.getGatewayErrorCodes(), "00");
        Assert.assertNull(transaction.getReference());
        Assert.assertTrue(transaction.getTest());
        Assert.assertTrue(transaction.getRefundable());
        Assert.assertTrue(transaction.getVoidable());
        Assert.assertEquals(transaction.getAvsResultStreet(), "123 Main St.");
        Assert.assertEquals(transaction.getAvsResultPostal(), "20121");
        Assert.assertEquals(transaction.getSource(), "subscription");

        final PushTransaction.VerificationResult cvv = transaction.getCvvResult();
        Assert.assertNotNull(cvv);
        Assert.assertEquals(cvv.getCode(), "");
        Assert.assertNull(cvv.getMessage());

        final PushTransaction.VerificationResult avs = transaction.getAvsResult();
        Assert.assertNotNull(avs);
        Assert.assertEquals(avs.getCode(), "D");
        Assert.assertEquals(avs.getMessage(), "Street address and postal code match.");
    }

    private void testInvoiceNotification(final InvoiceNotification invoiceNotification) {
        PushInvoice invoice = invoiceNotification.getInvoice();
        Assert.assertNotNull(invoice);
        Assert.assertEquals(invoice.getUuid(), "ffc64d71d4b5404e93f13aac9c63b007");
        Assert.assertNull(invoice.getSubscriptionId());
        Assert.assertEquals(invoice.getState(), "collected");
        Assert.assertNull(invoice.getInvoiceNumberPrefix());
        Assert.assertEquals(invoice.getInvoiceNumber(), Integer.valueOf(1000));
        Assert.assertEquals(invoice.getPoNumber(), "PO-12345");
        Assert.assertNull(invoice.getVatNumber());
        Assert.assertEquals(invoice.getTotalInCents(), Integer.valueOf(1100));
        Assert.assertEquals(invoice.getCurrency(), "USD");
        Assert.assertEquals(invoice.getDate(), new DateTime("2014-01-01T20:20:29Z"));
        Assert.assertEquals(invoice.getClosedAt(), new DateTime("2014-01-01T20:24:02Z"));
        Assert.assertEquals(invoice.getNetTerms(), Integer.valueOf(0));
        Assert.assertEquals(invoice.getCollectionMethod(), "automatic");
    }

    @Test(groups = "fast")
    public void testNewAccountNotification() {
        deserialize(NewAccountNotification.class);
    }

    @Test(groups = "fast")
    public void testCanceledAccountNotification() {
        deserialize(CanceledAccountNotification.class);
    }

    @Test(groups = "fast")
    public void testBillingInfoUpdatedNotification() {
        deserialize(BillingInfoUpdatedNotification.class);
    }

    @Test(groups = "fast")
    public void testNewSubscriptionNotification() {
        deserialize(NewSubscriptionNotification.class);
    }

    @Test(groups = "fast")
    public void testUpdatedSubscriptionNotification() {
        deserialize(UpdatedSubscriptionNotification.class);
    }

    @Test(groups = "fast")
    public void testCanceledSubscriptionNotification() {
        deserialize(CanceledSubscriptionNotification.class);
    }

    @Test(groups = "fast")
    public void testRenewedSubscriptionNotification() {
        deserialize(RenewedSubscriptionNotification.class);
    }

    @Test(groups = "fast")
    public void testReactivatedAccountNotification() {
        deserialize(ReactivatedAccountNotification.class);
    }

    @Test(groups = "fast")
    public void testExpiredSubscriptionNotification() {
        deserialize(ExpiredSubscriptionNotification.class);
    }

    @Test(groups = "fast")
    public void testSuccessfulPaymentNotification() {
        deserialize(SuccessfulPaymentNotification.class);
    }

    @Test(groups = "fast")
    public void testFailedPaymentNotification() {
        deserialize(FailedPaymentNotification.class);
    }

    @Test(groups = "fast")
    public void testVoidPaymentNotification() {
        deserialize(VoidPaymentNotification.class);
    }

    @Test(groups = "fast")
    public void testSuccessfulRefundNotification() {
        deserialize(SuccessfulRefundNotification.class);
    }

    @Test(groups = "fast")
    public void testClosedInvoiceNotification() {
        deserialize(ClosedInvoiceNotification.class);
    }

    @Test(groups = "fast")
    public void testNewInvoiceNotification() {
        deserialize(NewInvoiceNotification.class);
    }

    @Test(groups = "fast")
    public void testPastDueInvoiceNotification() {
        deserialize(PastDueInvoiceNotification.class);
    }

    @Test(groups = "fast")
    public void testProcessingInvoiceNotification() {
        deserialize(ProcessingInvoiceNotification.class);
    }
}
