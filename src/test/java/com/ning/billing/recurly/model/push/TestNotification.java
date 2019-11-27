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

import com.ning.billing.recurly.model.push.account.*;
import com.ning.billing.recurly.model.push.invoice.*;
import com.ning.billing.recurly.model.push.subscription.*;
import com.ning.billing.recurly.model.push.payment.*;
import com.ning.billing.recurly.model.push.usage.*;
import com.ning.billing.recurly.model.push.giftcard.*;
import com.ning.billing.recurly.model.push.creditpayment.*;
import com.ning.billing.recurly.model.push.item.*;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.math.BigDecimal;

import com.ning.billing.recurly.model.Account;
import com.ning.billing.recurly.model.CreditPayment;
import com.ning.billing.recurly.model.Usage;
import com.ning.billing.recurly.model.GiftCard;
import com.ning.billing.recurly.model.Plan;
import com.ning.billing.recurly.model.Item;
import com.ning.billing.recurly.model.TestModelBase;


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
                                                   "  <subscription_add_ons type=\"array\"/>\n" +
                                                   "  <activated_at type=\"dateTime\">2010-09-23T22:12:39Z</activated_at>\n" +
                                                   "  <canceled_at nil=\"true\"></canceled_at>\n" +
                                                   "  <expires_at nil=\"true\"></expires_at>\n" +
                                                   "  <current_period_started_at type=\"dateTime\">2010-09-23T22:03:30Z</current_period_started_at>\n" +
                                                   "  <current_period_ends_at type=\"dateTime\">2010-09-24T22:03:30Z</current_period_ends_at>\n" +
                                                   "  <paused_at type=\"datetime\">2018-03-10T22:12:08Z</paused_at>\n" +
                                                   "  <resume_at type=\"datetime\">2018-03-20T22:12:08Z</resume_at>\n" +
                                                   "  <remaining_pause_cycles type=\"integer\">9</remaining_pause_cycles>\n" +
                                                   "  <trial_started_at nil=\"true\" type=\"dateTime\"></trial_started_at>\n" +
                                                   "  <trial_ends_at nil=\"true\" type=\"dateTime\"></trial_ends_at>\n" +
                                                   "  <starts_at type=\"dateTime\">2010-09-23T07:00:00Z</starts_at>\n" +
                                                   "</subscription>";

    private static final String TRANSACTIONDATA = "<transaction>\n" +
                                                  "  <id>a5143c1d3a6f4a8287d0e2cc1d4c0427</id>\n" +
                                                  "  <invoice_id>1974a09kj90s0789dsf099798326881c</invoice_id>\n" +
                                                  "  <invoice_number type=\"integer\">2059</invoice_number>\n" +
                                                  "  <subscription_id>1974a098jhlkjasdfljkha898326881c</subscription_id>\n" +
                                                  "  <action>purchase</action>\n" +
                                                  "  <date type=\"dateTime\">2009-11-22T13:10:38Z</date>\n" +
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
                                              "  <subscription_ids type=\"array\">\n" +
                                              "    <subscription_id>40b8f5e99df03b8684b99d4993b6e088</subscription_id>\n" +
                                              "    <subscription_id>40b8f5e99df03b8684b99d4993b6e089</subscription_id>\n" +
                                              "  </subscription_ids>\n" +
                                              "  <total_in_cents type=\"integer\">1100</total_in_cents>\n" +
                                              "  <currency>USD</currency>\n" +
                                              "  <date type=\"dateTime\">2014-01-01T20:20:29Z</date>\n" +
                                              "  <closed_at type=\"dateTime\">2014-01-01T20:24:02Z</closed_at>\n" +
                                              "  <net_terms type=\"integer\">0</net_terms>\n" +
                                              "  <collection_method>automatic</collection_method>\n" +
                                              "  <dunning_events_count type=\"integer\">2</dunning_events_count>\n" +
                                              "  <final_dunning_event type=\"boolean\">true</final_dunning_event>\n" +
                                              "</invoice>";

    private static final String ITEMDATA =    "<item>\n" +
                                              "  <item_code>gray_socks</item_code>\n" +
                                              "  <name>Gray Socks</name>\n" +
                                              "  <description>Gray Socks</description>\n" +
                                              "  <external_sku>socks-12345</external_sku>\n" +
                                              "  <accounting_code>acc-12345</accounting_code>\n" +
                                              "  <revenue_schedule_type>evenly</revenue_schedule_type>\n" +
                                              "  <tax_exempt type=\"boolean\">true</tax_exempt>\n" +
                                              "  <tax_code nil=\"nil\"/>\n" +
                                              "  <pricing_type>fixed</pricing_type>\n" +
                                              "  <custom_fields type=\"array\">\n" +
                                              "    <custom_field>\n" +
                                              "      <name>color</name>\n" +
                                              "      <value>gray</value>\n" +
                                              "    </custom_field>\n" +
                                              "  </custom_fields>\n" +
                                              "  <unit_amount_in_cents>\n" +
                                              "    <CAD type=\"integer\">6000</CAD>\n" +
                                              "    <USD type=\"integer\">1000</USD>\n" +
                                              "  </unit_amount_in_cents>\n" +
                                              "  <created_at type=\"datetime\">2019-07-15T18:48:01Z</created_at>\n" +
                                              "  <updated_at type=\"datetime\">2019-07-15T18:48:01Z</updated_at>\n" +
                                              "  <deleted_at nil=\"nil\"/>\n" +
                                              "</item>";

    private static final String USAGEDATA = "<usage>\n" +
                                              "  <id type=\"integer\">394729929104688227</id>\n" +
                                              "  <subscription_id>35cda8d4ae0a214f69779e4ddbbc2ebd</subscription_id>\n" +
                                              "  <add_on_code>video_storage</add_on_code>\n" +
                                              "  <measured_unit_id type=\"integer\">394681920153192422</measured_unit_id>\n" +
                                              "  <amount type=\"integer\">-40</amount>\n" +
                                              "  <merchant_tag nil=\"true\"></merchant_tag>\n" +
                                              "  <recording_timestamp type=\"datetime\">2016-04-28T21:57:53+00:00</recording_timestamp>\n" +
                                              "  <usage_timestamp type=\"datetime\">2016-04-28T21:57:53+00:00</usage_timestamp>\n" +
                                              "  <created_at type=\"datetime\">2016-04-28T21:57:54+00:00</created_at>\n" +
                                              "  <modified_at type=\"datetime\" nil=\"true\"></modified_at>\n" +
                                              "  <billed_at type=\"datetime\">2016-04-28T21:57:54+00:00</billed_at>\n" +
                                              "  <usage_type>PRICE</usage_type>\n" +
                                              "  <unit_amount_in_cents>50</unit_amount_in_cents>\n" +
                                              "  <usage_percentage type=\"float\">0.1</usage_percentage>\n" +
                                              "</usage>";

    private static final String GIFTCARDDATA = "<gift_card>\n" +
                                              "  <redemption_code>1A5069E266AED435</redemption_code>\n" +
                                              "  <id type=\"integer\">2008976331180115114</id>\n" +
                                              "  <product_code>gift_card</product_code>\n" +
                                              "  <unit_amount_in_cents type=\"integer\">1000</unit_amount_in_cents>\n" +
                                              "  <currency>USD</currency>\n" +
                                              "  <gifter_account_code>84395</gifter_account_code>\n" +
                                              "  <recipient_account_code nil=\"true\"></recipient_account_code>\n" +
                                              "  <invoice_number type=\"integer\">1105</invoice_number>\n" +
                                              "  <delivery>\n" +
                                              "    <method>email</method>\n" +
                                              "    <email_address>john@example.com</email_address>\n" +
                                              "    <deliver_at nil=\"true\"></deliver_at>\n" +
                                              "    <first_name>John</first_name>\n" +
                                              "    <last_name>Smith</last_name>\n" +
                                              "    <address>\n" +
                                              "      <address1 nil=\"true\"></address1>\n" +
                                              "      <address2 nil=\"true\"></address2>\n" +
                                              "      <city nil=\"true\"></city>\n" +
                                              "      <state nil=\"true\"></state>\n" +
                                              "      <zip nil=\"true\"></zip>\n" +
                                              "      <country nil=\"true\"></country>\n" +
                                              "      <phone nil=\"true\"></phone>\n" +
                                              "    </address>\n" +
                                              "    <gifter_name>Sally</gifter_name>\n" +
                                              "    <personal_message>Hi John, Happy Birthday! I hope you have a great day! Love, Sally</personal_message>\n" +
                                              "  </delivery>\n" +
                                              "  <created_at type=\"datetime\">2016-08-03T20:37:21Z</created_at>\n" +
                                              "  <updated_at type=\"datetime\">2016-08-03T20:37:21Z</updated_at>\n" +
                                              "  <delivered_at type=\"datetime\" nil=\"true\"></delivered_at>\n" +
                                              "  <redeemed_at type=\"datetime\" nil=\"true\"></redeemed_at>\n" +
                                              "  <canceled_at type=\"datetime\" nil=\"true\"></canceled_at>\n" +
                                              "</gift_card>";

    private static final String CREDITPAYMENTDATA = "<credit_payment>\n" +
                                                    "  <uuid>42fa2a56dfeca2ace39b0e4a9198f835</uuid>\n" +
                                                    "  <action type=\"symbol\">payment</action>\n" +
                                                    "  <currency>USD</currency>\n" +
                                                    "  <amount_in_cents type=\"integer\">3579</amount_in_cents>\n" +
                                                    "  <original_invoice_number type=\"integer\">2389</original_invoice_number>\n" +
                                                    "  <applied_to_invoice_number type=\"integer\">2390</applied_to_invoice_number>\n" +
                                                    "  <original_credit_payment_uuid nil=\"true\"></original_credit_payment_uuid>\n" +
                                                    "  <refund_transaction_uuid nil=\"true\"></refund_transaction_uuid>\n" +
                                                    "  <created_at type=\"datetime\">2018-02-12T18:55:20Z</created_at>\n" +
                                                    "  <updated_at type=\"datetime\">2018-02-12T18:55:20Z</updated_at>\n" +
                                                    "  <closed_at type=\"dateTime\">2014-01-01T20:24:02Z</closed_at>\n" +
                                                    "  <voided_at type=\"datetime\" nil=\"true\"></voided_at>\n" +
                                                    "</credit_payment>";


    private <T extends Notification> void deserialize(final Class<T> clazz) {

        final String xmlElement = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, clazz.getSimpleName());
        final StringBuilder notificationDataBuilder = new StringBuilder()
                .append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
                .append("<").append(xmlElement).append(">\n");

        boolean isAccount = false, isSubscription = false, isPayment = false,
                isInvoice = false, isItem = false, isUsage = false, isGiftCard = false, isCreditPayment = false;

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

        if(ItemNotification.class.isAssignableFrom(clazz)) {
            notificationDataBuilder.append(ITEMDATA).append("\n");
            isItem = true;
        }

        if(UsageNotification.class.isAssignableFrom(clazz)) {
            notificationDataBuilder.append(USAGEDATA).append("\n");
            isUsage = true;
        }

        if(GiftCardNotification.class.isAssignableFrom(clazz)) {
            notificationDataBuilder.append(GIFTCARDDATA).append("\n");
            isGiftCard = true;
        }

        if(CreditPaymentNotification.class.isAssignableFrom(clazz)) {
            notificationDataBuilder.append(CREDITPAYMENTDATA).append("\n");
            isCreditPayment = true;
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
        if (isItem) {
            testItemNotification((ItemNotification) notification);
        }
        if (isUsage) {
            testUsageNotification((UsageNotification) notification);
        }
        if (isGiftCard) {
            testGiftCardNotification((GiftCardNotification) notification);
        }
        if (isCreditPayment) {
            testCreditPaymentNotification((CreditPaymentNotification) notification);
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
        Assert.assertEquals(subscription.getQuantity(), new Integer(1));
        Assert.assertNull(subscription.getUnitAmountInCents());
        Assert.assertEquals(subscription.getTotalAmountInCents(), new Integer(200));
        Assert.assertEquals(subscription.getActivatedAt(), new DateTime("2010-09-23T22:12:39Z"));
        Assert.assertNull(subscription.getCanceledAt());
        Assert.assertNull(subscription.getExpiresAt());
        Assert.assertEquals(subscription.getResumeAt(), new DateTime("2018-03-20T22:12:08Z"));
        Assert.assertEquals(subscription.getPausedAt(), new DateTime("2018-03-10T22:12:08Z"));
        Assert.assertEquals(subscription.getRemainingPauseCycles(), new Integer(9));
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
        Assert.assertEquals(transaction.getInvoiceNumber(), new Integer(2059));
        Assert.assertEquals(transaction.getSubscriptionId(), "1974a098jhlkjasdfljkha898326881c");
        Assert.assertEquals(transaction.getDate(), new DateTime("2009-11-22T13:10:38Z"));
        Assert.assertEquals(transaction.getAction(), "purchase");
        Assert.assertEquals(transaction.getAmountInCents(), new Integer(1000));
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
        Assert.assertEquals(invoice.getSubscriptionIds().get(0), "40b8f5e99df03b8684b99d4993b6e088");
        Assert.assertEquals(invoice.getState(), "collected");
        Assert.assertNull(invoice.getInvoiceNumberPrefix());
        Assert.assertEquals(invoice.getInvoiceNumber(), new Integer(1000));
        Assert.assertEquals(invoice.getPoNumber(), "PO-12345");
        Assert.assertNull(invoice.getVatNumber());
        Assert.assertEquals(invoice.getTotalInCents(), new Integer(1100));
        Assert.assertEquals(invoice.getCurrency(), "USD");
        Assert.assertEquals(invoice.getDate(), new DateTime("2014-01-01T20:20:29Z"));
        Assert.assertEquals(invoice.getClosedAt(), new DateTime("2014-01-01T20:24:02Z"));
        Assert.assertEquals(invoice.getNetTerms(), new Integer(0));
        Assert.assertEquals(invoice.getCollectionMethod(), "automatic");
        Assert.assertEquals(invoice.getDunningEventsCount(), new Integer(2));
        Assert.assertEquals(invoice.isFinalDunningEvent(), Boolean.TRUE);
    }

    private void testItemNotification(final ItemNotification itemNotification) {
        Item item = itemNotification.getItem();
        Assert.assertNotNull(item);
        Assert.assertEquals(item.getItemCode(),"gray_socks");
        Assert.assertEquals(item.getName(), "Gray Socks");
        Assert.assertEquals(item.getDescription(), "Gray Socks");
        Assert.assertEquals(item.getExternalSku(), "socks-12345");
        Assert.assertEquals(item.getAccountingCode(), "acc-12345");
        Assert.assertEquals(item.getRevenueScheduleType(), "evenly");
        Assert.assertEquals(item.getCustomFields().get(0).getName(), "color");
        Assert.assertEquals(item.getCustomFields().get(0).getValue(), "gray");
    }

    private void testUsageNotification(final UsageNotification usageNotification) {
        Usage usage = usageNotification.getUsage();
        Assert.assertNotNull(usage);
        Assert.assertEquals(usage.getAmount(), new Integer(-40));
        Assert.assertNull(usage.getMerchantTag());
        Assert.assertEquals(usage.getRecordingAt(), new DateTime("2016-04-28T21:57:53Z"));
        Assert.assertEquals(usage.getUsageAt(), new DateTime("2016-04-28T21:57:53Z"));
        Assert.assertEquals(usage.getCreatedAt(), new DateTime("2016-04-28T21:57:54Z"));
        Assert.assertNull(usage.getUpdatedAt());
        Assert.assertEquals(usage.getBilledAt(), new DateTime("2016-04-28T21:57:54Z"));
        Assert.assertEquals(usage.getUsageType(), "PRICE");
        Assert.assertEquals(usage.getUnitAmountInCents(), new Integer(50));
        Assert.assertEquals(usage.getUsagePercentage(), new BigDecimal("0.1"));
    }

    private void testGiftCardNotification(final GiftCardNotification giftCardNotification) {
        GiftCard giftCard = giftCardNotification.getGiftCard();
        Assert.assertNotNull(giftCard);
        Assert.assertEquals(giftCard.getRedemptionCode(), "1A5069E266AED435");
        Assert.assertEquals(giftCard.getId(), new Long(2008976331180115114L));
        Assert.assertEquals(giftCard.getProductCode(), "gift_card");
        Assert.assertEquals(giftCard.getUnitAmountInCents(), new Integer(1000));
        Assert.assertEquals(giftCard.getCurrency(), "USD");
        Assert.assertNotNull(giftCard.getDelivery());
        Assert.assertEquals(giftCard.getDelivery().getMethod(), "email");
        Assert.assertEquals(giftCard.getDelivery().getEmailAddress(), "john@example.com");
        Assert.assertEquals(giftCard.getDelivery().getFirstName(), "John");
        Assert.assertEquals(giftCard.getDelivery().getLastName(), "Smith");
        Assert.assertNull(giftCard.getDelivery().getAddress().getAddress1());
        Assert.assertNull(giftCard.getDelivery().getAddress().getAddress2());
        Assert.assertNull(giftCard.getDelivery().getAddress().getCity());
        Assert.assertNull(giftCard.getDelivery().getAddress().getState());
        Assert.assertNull(giftCard.getDelivery().getAddress().getZip());
        Assert.assertNull(giftCard.getDelivery().getAddress().getCountry());
        Assert.assertNull(giftCard.getDelivery().getAddress().getPhone());
        Assert.assertEquals(giftCard.getDelivery().getGifterName(), "Sally");
        Assert.assertEquals(giftCard.getDelivery().getPersonalMessage(), "Hi John, Happy Birthday! I hope you have a great day! Love, Sally");
        Assert.assertEquals(giftCard.getCreatedAt(), new DateTime("2016-08-03T20:37:21Z"));
        Assert.assertEquals(giftCard.getUpdatedAt(), new DateTime("2016-08-03T20:37:21Z"));
        Assert.assertNull(giftCard.getDeliveredAt());
        Assert.assertNull(giftCard.getRedeemedAt());
        Assert.assertNull(giftCard.getCanceledAt());
    }

    private void testCreditPaymentNotification(final CreditPaymentNotification creditPaymentNotification) {
        final CreditPayment creditPayment = creditPaymentNotification.getCreditPayment();
        Assert.assertNotNull(creditPayment);
        Assert.assertEquals(creditPayment.getUuid(), "42fa2a56dfeca2ace39b0e4a9198f835");
        Assert.assertEquals(creditPayment.getCurrency(), "USD");
        Assert.assertEquals(creditPayment.getAmountInCents(), new Integer(3579));
        Assert.assertEquals(creditPayment.getCreatedAt(), new DateTime("2018-02-12T18:55:20Z"));
        Assert.assertEquals(creditPayment.getUpdatedAt(), new DateTime("2018-02-12T18:55:20Z"));
        Assert.assertNull(creditPayment.getVoidedAt());
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
    public void testUpdatedAccountNotification() { deserialize(UpdatedAccountNotification.class); }

    @Test(groups = "fast")
    public void testBillingInfoUpdatedNotification() {
        deserialize(BillingInfoUpdatedNotification.class);
    }

    @Test(groups = "fast")
    public void testBillingInfoUpdateFailedNotification() {
        deserialize(BillingInfoUpdateFailedNotification.class);
    }

    @Test(groups = "fast")
    public void testNewShippingAddressNotification() {
        deserialize(NewShippingAddressNotification.class);
    }

    @Test(groups = "fast")
    public void testUpdatedShippingAddressNotification() {
        deserialize(UpdatedShippingAddressNotification.class);
    }

    @Test(groups = "fast")
    public void testDeletedShippingAddressNotification() {
        deserialize(DeletedShippingAddressNotification.class);
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
    public void testSubscriptionPausedNotification() {
        deserialize(SubscriptionPausedNotification.class);
    }

    @Test(groups = "fast")
    public void testSubscriptionResumedNotification() {
        deserialize(SubscriptionResumedNotification.class);
    }

    @Test(groups = "fast")
    public void testScheduledSubscriptionPauseNotification() {
        deserialize(ScheduledSubscriptionPauseNotification.class);
    }

    @Test(groups = "fast")
    public void testSubscriptionPausedModifiedNotification() {
        deserialize(SubscriptionPausedModifiedNotification.class);
    }

    @Test(groups = "fast")
    public void testPausedSubscriptionRenewalNotification() {
        deserialize(PausedSubscriptionRenewalNotification.class);
    }

    @Test(groups = "fast")
    public void testSubscriptionPausedCanceledNotification() {
        deserialize(SubscriptionPausedCanceledNotification.class);
    }

    @Test(groups = "fast")
    public void testScheduledPaymentNotification() {
        deserialize(ScheduledPaymentNotification.class);
    }

    @Test(groups = "fast")
    public void testProcessingPaymentNotification() {
        deserialize(ProcessingPaymentNotification.class);
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
    public void testFraudInfoUpdatedNotification() {
        deserialize(FraudInfoUpdatedNotification.class);
    }

    @Test(groups = "fast")
    public void testTransactionStatusUpdatedNotification() {
        deserialize(TransactionStatusUpdatedNotification.class);
    }

    @Test(groups = "fast")
    public void testTransactionAuthorizedNotification() {
        deserialize(TransactionAuthorizedNotification.class);
    }

    @Test(groups = "fast")
    public void testNewCreditPaymentNotification() {
        deserialize(NewCreditPaymentNotification.class);
    }

    @Test(groups = "fast")
    public void testVoidedCreditPaymentNotification() {
        deserialize(VoidedCreditPaymentNotification.class);
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

    @Test(groups = "fast")
    public void testNewDunningEventNotification() {
        deserialize(NewDunningEventNotification.class);
    }

    @Test(groups = "fast")
    public void testNewChargeInvoiceNotification() {
        deserialize(NewChargeInvoiceNotification.class);
    }

    @Test(groups = "fast")
    public void testProcessingChargeInvoiceNotification() {
        deserialize(ProcessingChargeInvoiceNotification.class);
    }

    @Test(groups = "fast")
    public void testPastDueChargeInvoiceNotification() {
        deserialize(PastDueChargeInvoiceNotification.class);
    }

    @Test(groups = "fast")
    public void testPaidChargeInvoiceNotification() {
        deserialize(PaidChargeInvoiceNotification.class);
    }

    @Test(groups = "fast")
    public void testFailedChargeInvoiceNotification() {
        deserialize(FailedChargeInvoiceNotification.class);
    }

    @Test(groups = "fast")
    public void testReopenedChargeInvoiceNotification() {
        deserialize(ReopenedChargeInvoiceNotification.class);
    }

    @Test(groups = "fast")
    public void testUpdatedChargeInvoiceNotification() {
        deserialize(UpdatedChargeInvoiceNotification.class);
    }

    @Test(groups = "fast")
    public void testNewCreditInvoiceNotification() {
        deserialize(NewCreditInvoiceNotification.class);
    }

    @Test(groups = "fast")
    public void testProcessingCreditInvoiceNotification() {
        deserialize(ProcessingCreditInvoiceNotification.class);
    }

    @Test(groups = "fast")
    public void testClosedCreditInvoiceNotification() {
        deserialize(ClosedCreditInvoiceNotification.class);
    }

    @Test(groups = "fast")
    public void testVoidedCreditInvoiceNotification() {
        deserialize(VoidedCreditInvoiceNotification.class);
    }

    @Test(groups = "fast")
    public void testReopenedCreditInvoiceNotification() {
        deserialize(ReopenedCreditInvoiceNotification.class);
    }

    @Test(groups = "fast")
    public void testOpenCreditInvoiceNotification() {
        deserialize(OpenCreditInvoiceNotification.class);
    }

    @Test(groups = "fast")
    public void testUpdatedCreditInvoiceNotification() {
        deserialize(UpdatedCreditInvoiceNotification.class);
    }

    @Test(groups = "fast")
    public void testUpdatedInvoiceNotification() {
        deserialize(UpdatedInvoiceNotification.class);
    }

    @Test(groups = "fast")
    public void testNewUsageNotification() {
        deserialize(NewUsageNotification.class);
    }

    @Test(groups = "fast")
    public void testPurchasedGiftCardNotification() {
        deserialize(PurchasedGiftCardNotification.class);
    }

    @Test(groups = "fast")
    public void testCanceledGiftCardNotification() {
        deserialize(CanceledGiftCardNotification.class);
    }

    @Test(groups = "fast")
    public void testUpdatedGiftCardNotification() {
        deserialize(UpdatedGiftCardNotification.class);
    }

    @Test(groups = "fast")
    public void testRegeneratedGiftCardNotification() {
        deserialize(RegeneratedGiftCardNotification.class);
    }

    @Test(groups = "fast")
    public void testRedeemedGiftCardNotification() {
        deserialize(RedeemedGiftCardNotification.class);
    }

    @Test(groups = "fast")
    public void testUpdatedBalanceGiftCardNotification() {
        deserialize(UpdatedBalanceGiftCardNotification.class);
    }

    @Test(groups = "fast")
    public void testLowBalanceGiftCardNotification() {
        deserialize(LowBalanceGiftCardNotification.class);
    }

    @Test(groups = "fast")
    public void testNewItemNotification() {
        deserialize(NewItemNotification.class);
    }

    @Test(groups = "fast")
    public void testUpdatedItemNotification() {
        deserialize(UpdatedItemNotification.class);
    }

    @Test(groups = "fast")
    public void testDeactivatedItemNotification() {
        deserialize(DeactivatedItemNotification.class);
    }

    @Test(groups = "fast")
    public void testReactivatedItemNotification() {
        deserialize(ReactivatedItemNotification.class);
    }

}
