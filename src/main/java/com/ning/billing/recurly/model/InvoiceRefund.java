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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "invoice")
public class InvoiceRefund extends RecurlyObject {
    @XmlElement(name = "refund_apply_order")
    private RefundApplyOrder refundApplyOrder;

    @XmlElement(name = "amount_in_cents")
    private Integer amountInCents;

    @XmlElementWrapper(name = "line_items")
    @XmlElement(name = "adjustment")
    private List<AdjustmentRefund> lineItems;

    public void setRefundApplyOrder(final RefundApplyOrder refundApplyOrder) {
        this.refundApplyOrder = refundApplyOrder;
    }

    public RefundApplyOrder getRefundApplyOrder() {
        return this.refundApplyOrder;
    }

    public void setAmountInCents(final Object amountInCents) {
        this.amountInCents = integerOrNull(amountInCents);
    }

    public Integer getAmountInCents() {
        return this.amountInCents;
    }

    public void setLineItems(final List<AdjustmentRefund> lineItems) {
        this.lineItems = lineItems;
    }

    public List<AdjustmentRefund> getLineItems() {
        return this.lineItems;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(refundApplyOrder, amountInCents);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final InvoiceRefund refund = (InvoiceRefund) o;

        if (amountInCents != null ? !amountInCents.equals(refund.amountInCents) : refund.amountInCents != null) {
            return false;
        }
        if (refundApplyOrder != null ? !refundApplyOrder.equals(refund.refundApplyOrder) : refund.refundApplyOrder != null) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("InvoiceRefund{");
        sb.append("amountInCents=").append(amountInCents);
        sb.append(", refundApplyOrder='").append(refundApplyOrder).append('\'');
        sb.append('}');
        return sb.toString();
    }
}