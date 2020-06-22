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
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "credit_payment")
public class CreditPayment extends RecurlyObject {

    @XmlElement(name = "action")
    private String action;

    @XmlElement(name = "amount_in_cents")
    private Integer amountInCents;

    @XmlElement(name = "applied_to_invoice")
    private String appliedToInvoice;

    @XmlElement(name = "currency")
    private String currency;

    @XmlElement(name = "uuid")
    private String uuid;

    @XmlElement(name = "created_at")
    private DateTime createdAt;

    @XmlElement(name = "updated_at")
    private DateTime updatedAt;

    @XmlElement(name = "voided_at")
    private DateTime voidedAt;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(final Object uuid) {
        this.uuid = stringOrNull(uuid);
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(final Object currency) {
        this.currency = stringOrNull(currency);
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final Object createdAt) {
        this.createdAt = dateTimeOrNull(createdAt);
    }

    public DateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(final Object updatedAt) {
        this.updatedAt = dateTimeOrNull(updatedAt);
    }

    public DateTime getVoidedAt() {
        return voidedAt;
    }

    public void setVoidedAt(final Object voidedAt) {
        this.voidedAt = dateTimeOrNull(voidedAt);
    }

    public Integer getAmountInCents() {
        return amountInCents;
    }

    public void setAmountInCents(final Object amountInCents) {
        this.amountInCents = integerOrNull(amountInCents);
    }

    public String getAppliedToInvoice() {
        return appliedToInvoice;
    } 

    public void setAppliedToInvoice(final Object appliedToInvoice) {
        this.appliedToInvoice = stringOrNull(appliedToInvoice);
    }


    @Override
    public String toString() {
        String sb = "CreditPayment" +
                "{uuid='" + uuid + '\'' +
                ", currency='" + currency + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", voidedAt=" + voidedAt +
                ", appliedToInvoice=" + appliedToInvoice +
                '}';
        return sb;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final CreditPayment that = (CreditPayment) o;

        if (action != null ? !action.equals(that.action) : that.action != null) {
            return false;
        }
        if (amountInCents != null ? !amountInCents.equals(that.amountInCents) : that.amountInCents != null) {
            return false;
        }
        if (createdAt != null ? createdAt.compareTo(that.createdAt) != 0 : that.createdAt != null) {
            return false;
        }
        if (currency != null ? !currency.equals(that.currency) : that.currency != null) {
            return false;
        }
        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) {
            return false;
        }
        if (updatedAt != null ? updatedAt.compareTo(that.updatedAt) != 0 : that.updatedAt != null) {
            return false;
        }
        if (voidedAt != null ? voidedAt.compareTo(that.voidedAt) != 0 : that.voidedAt != null) {
            return false;
        }
        if (appliedToInvoice != null ? appliedToInvoice.equals(that.appliedToInvoice) : that.appliedToInvoice != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
                action,
                amountInCents,
                createdAt,
                currency,
                updatedAt,
                uuid,
                voidedAt,
                appliedToInvoice
        );
    }
}
