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

import com.google.common.base.CaseFormat;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ning.billing.recurly.model.RecurlyObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Notification extends RecurlyObject {

    private static final Logger log = LoggerFactory.getLogger(Notification.class);
    private static final Pattern ROOT_NAME = Pattern.compile("<(\\w+_notification)>");

    public enum Type {
        BillingInfoUpdatedNotification(com.ning.billing.recurly.model.push.account.BillingInfoUpdatedNotification.class),
        BillingInfoUpdateFailedNotification(com.ning.billing.recurly.model.push.account.BillingInfoUpdateFailedNotification.class),
        NewShippingAddressNotification(com.ning.billing.recurly.model.push.account.NewShippingAddressNotification.class),
        UpdatedShippingAddressNotification(com.ning.billing.recurly.model.push.account.UpdatedShippingAddressNotification.class),
        DeletedShippingAddressNotification(com.ning.billing.recurly.model.push.account.DeletedShippingAddressNotification.class),
        CanceledAccountNotification(com.ning.billing.recurly.model.push.account.CanceledAccountNotification.class),
        NewAccountNotification(com.ning.billing.recurly.model.push.account.NewAccountNotification.class),
        FailedPaymentNotification(com.ning.billing.recurly.model.push.payment.FailedPaymentNotification.class),
        ScheduledPaymentNotification(com.ning.billing.recurly.model.push.payment.ScheduledPaymentNotification.class),
        ProcessingPaymentNotification(com.ning.billing.recurly.model.push.payment.ProcessingPaymentNotification.class),
        SuccessfulPaymentNotification(com.ning.billing.recurly.model.push.payment.SuccessfulPaymentNotification.class),
        SuccessfulRefundNotification(com.ning.billing.recurly.model.push.payment.SuccessfulRefundNotification.class),
        VoidPaymentNotification(com.ning.billing.recurly.model.push.payment.VoidPaymentNotification.class),
        FraudInfoUpdatedNotification(com.ning.billing.recurly.model.push.payment.FraudInfoUpdatedNotification.class),
        TransactionStatusUpdatedNotification(com.ning.billing.recurly.model.push.payment.TransactionStatusUpdatedNotification.class),
        TransactionAuthorizedNotification(com.ning.billing.recurly.model.push.payment.TransactionAuthorizedNotification.class),
        NewCreditPaymentNotification(com.ning.billing.recurly.model.push.creditpayment.NewCreditPaymentNotification.class),
        VoidedCreditPaymentNotification(com.ning.billing.recurly.model.push.creditpayment.VoidedCreditPaymentNotification.class),
        CanceledSubscriptionNotification(com.ning.billing.recurly.model.push.subscription.CanceledSubscriptionNotification.class),
        ExpiredSubscriptionNotification(com.ning.billing.recurly.model.push.subscription.ExpiredSubscriptionNotification.class),
        SubscriptionPausedNotification(com.ning.billing.recurly.model.push.subscription.SubscriptionPausedNotification.class),
        SubscriptionResumedNotification(com.ning.billing.recurly.model.push.subscription.SubscriptionResumedNotification.class),
        ScheduledSubscriptionPauseNotification(com.ning.billing.recurly.model.push.subscription.ScheduledSubscriptionPauseNotification.class),
        SubscriptionPausedModifiedNotification(com.ning.billing.recurly.model.push.subscription.SubscriptionPausedModifiedNotification.class),
        PausedSubscriptionRenewalNotification(com.ning.billing.recurly.model.push.subscription.PausedSubscriptionRenewalNotification.class),
        SubscriptionPausedCanceledNotification(com.ning.billing.recurly.model.push.subscription.SubscriptionPausedCanceledNotification.class),
        NewSubscriptionNotification(com.ning.billing.recurly.model.push.subscription.NewSubscriptionNotification.class),
        ReactivatedAccountNotification(com.ning.billing.recurly.model.push.subscription.ReactivatedAccountNotification.class),
        RenewedSubscriptionNotification(com.ning.billing.recurly.model.push.subscription.RenewedSubscriptionNotification.class),
        UpdatedSubscriptionNotification(com.ning.billing.recurly.model.push.subscription.UpdatedSubscriptionNotification.class),
        ClosedInvoiceNotification(com.ning.billing.recurly.model.push.invoice.ClosedInvoiceNotification.class),
        NewInvoiceNotification(com.ning.billing.recurly.model.push.invoice.NewInvoiceNotification.class),
        PastDueInvoiceNotification(com.ning.billing.recurly.model.push.invoice.PastDueInvoiceNotification.class),
        ProcessingInvoiceNotification(com.ning.billing.recurly.model.push.invoice.ProcessingInvoiceNotification.class),
        UpdatedAccountNotification(com.ning.billing.recurly.model.push.account.UpdatedAccountNotification.class),
        NewDunningEventNotification(com.ning.billing.recurly.model.push.invoice.NewDunningEventNotification.class),
        NewChargeInvoiceNotification(com.ning.billing.recurly.model.push.invoice.NewChargeInvoiceNotification.class),
        ProcessingChargeInvoiceNotification(com.ning.billing.recurly.model.push.invoice.ProcessingChargeInvoiceNotification.class),
        PastDueChargeInvoiceNotification(com.ning.billing.recurly.model.push.invoice.PastDueChargeInvoiceNotification.class),
        PaidChargeInvoiceNotification(com.ning.billing.recurly.model.push.invoice.PaidChargeInvoiceNotification.class),
        FailedChargeInvoiceNotification(com.ning.billing.recurly.model.push.invoice.FailedChargeInvoiceNotification.class),
        ReopenedChargeInvoiceNotification(com.ning.billing.recurly.model.push.invoice.ReopenedChargeInvoiceNotification.class),
        UpdatedChargeInvoiceNotification(com.ning.billing.recurly.model.push.invoice.UpdatedChargeInvoiceNotification.class),
        NewCreditInvoiceNotification(com.ning.billing.recurly.model.push.invoice.NewCreditInvoiceNotification.class),
        ProcessingCreditInvoiceNotification(com.ning.billing.recurly.model.push.invoice.ProcessingCreditInvoiceNotification.class),
        ClosedCreditInvoiceNotification(com.ning.billing.recurly.model.push.invoice.ClosedCreditInvoiceNotification.class),
        VoidedCreditInvoiceNotification(com.ning.billing.recurly.model.push.invoice.VoidedCreditInvoiceNotification.class),
        ReopenedCreditInvoiceNotification(com.ning.billing.recurly.model.push.invoice.ReopenedCreditInvoiceNotification.class),
        OpenCreditInvoiceNotification(com.ning.billing.recurly.model.push.invoice.OpenCreditInvoiceNotification.class),
        UpdatedCreditInvoiceNotification(com.ning.billing.recurly.model.push.invoice.UpdatedCreditInvoiceNotification.class),
        UpdatedInvoiceNotification(com.ning.billing.recurly.model.push.invoice.UpdatedInvoiceNotification.class),
        NewUsageNotification(com.ning.billing.recurly.model.push.usage.NewUsageNotification.class),
        PurchasedGiftCardNotification(com.ning.billing.recurly.model.push.giftcard.PurchasedGiftCardNotification.class),
        CanceledGiftCardNotification(com.ning.billing.recurly.model.push.giftcard.CanceledGiftCardNotification.class),
        UpdatedGiftCardNotification(com.ning.billing.recurly.model.push.giftcard.UpdatedGiftCardNotification.class),
        RegeneratedGiftCardNotification(com.ning.billing.recurly.model.push.giftcard.RegeneratedGiftCardNotification.class),
        RedeemedGiftCardNotification(com.ning.billing.recurly.model.push.giftcard.RedeemedGiftCardNotification.class),
        UpdatedBalanceGiftCardNotification(com.ning.billing.recurly.model.push.giftcard.UpdatedBalanceGiftCardNotification.class),
        LowBalanceGiftCardNotification(com.ning.billing.recurly.model.push.subscription.LowBalanceGiftCardNotification.class),
        NewItemNotification(com.ning.billing.recurly.model.push.item.NewItemNotification.class),
        UpdatedItemNotification(com.ning.billing.recurly.model.push.item.UpdatedItemNotification.class),
        DeactivatedItemNotification(com.ning.billing.recurly.model.push.item.DeactivatedItemNotification.class),
        ReactivatedItemNotification(com.ning.billing.recurly.model.push.item.ReactivatedItemNotification.class);

        private Class<? extends Notification> javaType;

        Type(final Class<? extends Notification> javaType) {
            this.javaType = javaType;
        }

        public Class<? extends Notification> getJavaType() {
            return javaType;
        }

        public void setJavaType(final Class<? extends Notification> javaType) {
            this.javaType = javaType;
        }
    }

    public static <T> T read(final String payload, final Class<T> clazz) {
        try {
            return RecurlyObject.sharedXmlMapper().readValue(payload, clazz);
        } catch (IOException e) {
            log.warn("Enable to read notification, de-serialization failed : {}", e.getMessage());
            return null;
        }
    }

    /**
     * Detect notification type based on the xml root name.
     *
     * @param payload
     * @return notification type or null if root name is not found or if there
     *         is no type corresponding to the root name
     */
    public static Type detect(final String payload) {
        final Matcher m = ROOT_NAME.matcher(payload);
        if (m.find() && m.groupCount() >= 1) {
            final String root = m.group(1);
            try {
                return Type.valueOf(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, root));
            } catch (IllegalArgumentException e) {
                log.warn("Enable to detect notification type, no type for {}", root);
                return null;
            }
        }
        log.warn("Enable to detect notification type");
        return null;
    }
}
