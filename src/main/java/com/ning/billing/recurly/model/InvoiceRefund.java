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

import com.google.common.base.Objects;
import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "invoice")
public class InvoiceRefund extends RecurlyObject {
    @XmlElement(name = "refund_method")
    private RefundMethod refundMethod;

    @XmlElement(name = "amount_in_cents")
    private Integer amountInCents;

    @XmlElement(name = "percentage")
    private Integer percentage;

    @XmlElementWrapper(name = "line_items")
    @XmlElement(name = "adjustment")
    private List<AdjustmentRefund> lineItems;

    @XmlElement(name = "external_refund")
    private Boolean externalRefund;

    @XmlElement(name = "credit_customer_notes")
    private String creditCustomerNotes;

    @XmlElement(name = "payment_method")
    private String paymentMethod;

    @XmlElement(name = "description")
    private String description;

    @XmlElement(name = "refunded_at")
    private DateTime refundedAt;

    public void setRefundMethod(final RefundMethod refundMethod) {
        this.refundMethod = refundMethod;
    }

    public RefundMethod getRefundMethod() {
        return this.refundMethod;
    }

    public void setAmountInCents(final Object amountInCents) {
        this.amountInCents = integerOrNull(amountInCents);
    }

    public Integer getAmountInCents() {
        return this.amountInCents;
    }

    public void setPercentage(final Object percentage) {
        this.percentage = integerOrNull(percentage);
    }

    public Integer getPercentage() {
        return this.percentage;
    }

    public void setLineItems(final List<AdjustmentRefund> lineItems) {
        this.lineItems = lineItems;
    }

    public List<AdjustmentRefund> getLineItems() {
        return this.lineItems;
    }

    public String getCreditCustomerNotes() {
        return creditCustomerNotes;
    }

    public void setCreditCustomerNotes(final Object creditCustomerNotes) {
        this.creditCustomerNotes = stringOrNull(creditCustomerNotes);
    }

    public void setExternalRefund(final Object externalRefund) {
        this.externalRefund = booleanOrNull(externalRefund);
    }

    public Boolean getExternalRefund() {
        return this.externalRefund;
    }

    public void setPaymentMethod(final Object paymentMethod) {
        this.paymentMethod = stringOrNull(paymentMethod);
    }

    public String getPaymentMethod() {
        return this.paymentMethod;
    }

    public void setDescription(final Object description) {
        this.description = stringOrNull(description);
    }

    public String getDescription() {
        return this.description;
    }

    public void setRefundedAt(final Object refundedAt) {
        this.refundedAt = dateTimeOrNull(refundedAt);
    }

    public DateTime getRefundedAt() {
        return this.refundedAt;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
            refundMethod,
            amountInCents,
            percentage
        );
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final InvoiceRefund refund = (InvoiceRefund) o;

        if (amountInCents != null ? !amountInCents.equals(refund.amountInCents) : refund.amountInCents != null) {
            return false;
        }
        if (percentage != null ? !percentage.equals(refund.percentage) : refund.percentage != null) {
            return false;
        }
        if (externalRefund != null ? !externalRefund.equals(refund.externalRefund) : refund.externalRefund != null) {
            return false;
        }
        if (refundMethod != null ? !refundMethod.equals(refund.refundMethod) : refund.refundMethod != null) {
            return false;
        }
        if (creditCustomerNotes != null ? !creditCustomerNotes.equals(refund.creditCustomerNotes) : refund.creditCustomerNotes != null) {
            return false;
        }
        if (description != null ? !description.equals(refund.description) : refund.description != null) {
            return false;
        }
        if (paymentMethod != null ? !paymentMethod.equals(refund.paymentMethod) : refund.paymentMethod != null) {
            return false;
        }
        if (refundedAt != null ? refundedAt.compareTo(refund.refundedAt) != 0: refund.refundedAt != null) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("InvoiceRefund{");
        sb.append("percentage=").append(percentage);
        sb.append("amountInCents=").append(amountInCents);
        sb.append(", refundMethod='").append(refundMethod).append('\'');
        sb.append(", externalRefund='").append(externalRefund).append('\'');
        sb.append(", creditCustomerNotes='").append(creditCustomerNotes).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", paymentMethod='").append(paymentMethod).append('\'');
        sb.append(", refundedAt='").append(refundedAt).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
