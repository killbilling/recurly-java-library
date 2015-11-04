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

package com.ning.billing.recurly.model.push.payment;

import java.util.Map;

import javax.xml.bind.annotation.XmlElement;

import org.joda.time.DateTime;

import com.ning.billing.recurly.model.AbstractTransaction;

public class PushTransaction extends AbstractTransaction {

    @XmlElement
    private String id;

    @XmlElement(name = "invoice_id")
    private String invoiceId;

    @XmlElement(name = "invoice_number")
    private Integer invoiceNumber;

    @XmlElement(name = "subscription_id")
    private String subscriptionId;

    @XmlElement
    private DateTime date;

    @XmlElement
    private String message;

    public String getId() {
        return id;
    }

    public void setId(final Object id) {
        this.id = stringOrNull(id);
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(final Object invoiceId) {
        this.invoiceId = stringOrNull(invoiceId);
    }

    public Integer getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(final Object invoiceNumber) {
        this.invoiceNumber = integerOrNull(invoiceNumber);
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(final Object subscriptionId) {
        this.subscriptionId = stringOrNull(subscriptionId);
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(final Object date) {
        this.date = dateTimeOrNull(date);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final Object message) {
        this.message = stringOrNull(message);
    }



    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PushTransaction)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        final PushTransaction that = (PushTransaction) o;

        if (date != null ? !date.equals(that.date) : that.date != null) {
            return false;
        }
        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (invoiceId != null ? !invoiceId.equals(that.invoiceId) : that.invoiceId != null) {
            return false;
        }
        if (invoiceNumber != null ? !invoiceNumber.equals(that.invoiceNumber) : that.invoiceNumber != null) {
            return false;
        }
        if (message != null ? !message.equals(that.message) : that.message != null) {
            return false;
        }
        if (subscriptionId != null ? !subscriptionId.equals(that.subscriptionId) : that.subscriptionId != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (invoiceId != null ? invoiceId.hashCode() : 0);
        result = 31 * result + (invoiceNumber != null ? invoiceNumber.hashCode() : 0);
        result = 31 * result + (subscriptionId != null ? subscriptionId.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }
}
