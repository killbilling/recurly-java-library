/*
 * Copyright 2010-2013 Ning, Inc.
 *  *
 * Ning licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *  *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.ning.billing.recurly.model.push;

import com.google.common.base.CaseFormat;
import com.ning.billing.recurly.model.RecurlyObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Notification extends RecurlyObject {

    private static Logger log = LoggerFactory.getLogger(Notification.class);
    private static Pattern ROOT_NAME = Pattern.compile("<(.*_notification)>");

    public static enum Type {
        BillingInfoUpdatedNotification(com.ning.billing.recurly.model.push.account.BillingInfoUpdatedNotification.class),
        CanceledAccountNotification(com.ning.billing.recurly.model.push.account.CanceledAccountNotification.class),
        NewAccountNotification(com.ning.billing.recurly.model.push.account.NewAccountNotification.class),
        FailedPaymentNotification(com.ning.billing.recurly.model.push.payment.FailedPaymentNotification.class),
        SuccessfulPaymentNotification(com.ning.billing.recurly.model.push.payment.SuccessfulPaymentNotification.class),
        SuccessfulRefundNotification(com.ning.billing.recurly.model.push.payment.SuccessfulRefundNotification.class),
        VoidedPaymentNotification(com.ning.billing.recurly.model.push.payment.VoidedPaymentNotification.class),
        CanceledSubscriptionNotification(com.ning.billing.recurly.model.push.subscription.CanceledSubscriptionNotification.class),
        ExpiredSubscriptionNotification(com.ning.billing.recurly.model.push.subscription.ExpiredSubscriptionNotification.class),
        NewSubscriptionNotification(com.ning.billing.recurly.model.push.subscription.NewSubscriptionNotification.class),
        ReactivatedAccountNotification(com.ning.billing.recurly.model.push.subscription.ReactivatedAccountNotification.class),
        RenewedSubscriptionNotification(com.ning.billing.recurly.model.push.subscription.RenewedSubscriptionNotification.class),
        UpdatedSubscriptionNotification(com.ning.billing.recurly.model.push.subscription.UpdatedSubscriptionNotification.class),
        ;

        private Class<? extends Notification> javaType;

        private Type(Class<? extends Notification> javaType) {
            this.javaType = javaType;
        }

        public Class<? extends Notification> getJavaType() {
            return javaType;
        }

        public void setJavaType(Class<? extends Notification> javaType) {
            this.javaType = javaType;
        }
    }

    public static <T> T read(String payload, Class<T> clazz) {
        try {
            return RecurlyObject.newXmlMapper().readValue(payload, clazz);
        } catch (IOException e) {
            log.warn("Enable to read notification, de-serialization failed : {}", e.getMessage());
            return null;
        }
    }

    /**
     * Detect notification type based on the xml root name.
     * @param payload
     * @return notification type or null if root name is not found or if there
     * is no type corresponding to the root name
     */
    public static Type detect(String payload) {
        Matcher m = ROOT_NAME.matcher(payload);
        if(m.find() && m.groupCount() >= 1) {
            String root = m.group(1);
            try{
                return Type.valueOf(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, root));
            }catch (IllegalArgumentException e) {
                log.warn("Enable to detect notification type, no type for {}", root);
                return null;
            }
        }
        log.warn("Enable to detect notification type");
        return null;
    }
}
