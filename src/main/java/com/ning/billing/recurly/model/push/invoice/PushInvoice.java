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

package com.ning.billing.recurly.model.push.invoice;

import com.ning.billing.recurly.model.Invoice;
import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlElement;

public class PushInvoice extends Invoice {

    @XmlElement(name = "subscription_id")
    private String subscriptionId;

    @XmlElement(name = "invoice_number_prefix")
    private String invoiceNumberPrefix;

    @XmlElement(name = "date")
    private DateTime date;

    @XmlElement(name = "closed_at")
    private DateTime closedAt;

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(final Object subscriptionId) {
        this.subscriptionId = stringOrNull(subscriptionId);
    }

    public String getInvoiceNumberPrefix() {
        return invoiceNumberPrefix;
    }

    public void setInvoiceNumberPrefix(final Object invoiceNumberPrefix) {
        this.invoiceNumberPrefix = stringOrNull(invoiceNumberPrefix);
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(final Object date) {
        this.date = dateTimeOrNull(date);
    }

    public DateTime getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(final Object closedAt) {
        this.closedAt = dateTimeOrNull(closedAt);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PushInvoice)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        final PushInvoice that = (PushInvoice) o;

        if (subscriptionId != null ? !subscriptionId.equals(that.subscriptionId) : that.subscriptionId != null) {
            return false;
        }

        if (invoiceNumberPrefix != null ? !invoiceNumberPrefix.equals(that.invoiceNumberPrefix) : that.invoiceNumberPrefix != null) {
            return false;
        }

        if (date != null ? !date.equals(that.date) : that.date != null) {
            return false;
        }

        if (closedAt != null ? !closedAt.equals(that.closedAt) : that.closedAt != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (subscriptionId != null ? subscriptionId.hashCode() : 0);
        result = 31 * result + (invoiceNumberPrefix != null ? invoiceNumberPrefix.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (closedAt != null ? closedAt.hashCode() : 0);
        return result;
    }
}
