/*
 * Copyright 2010-2012 Ning, Inc.
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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.joda.time.DateTime;

@XmlRootElement(name = "transaction")
public class Transaction extends RecurlyObject {

    @XmlElement(name = "account")
    private Account account;

    @XmlElement(name = "invoice")
    private Invoice invoice;

    @XmlElement(name = "subscription")
    private String subscription;

    @XmlElement(name = "uuid")
    private String uuid;

    @XmlElement(name = "action")
    private String action;

    @XmlElement(name = "amount_in_cents")
    private Integer amountInCents;

    @XmlElement(name = "tax_in_cents")
    private Integer taxInCents;

    @XmlElement(name = "currency")
    private String currency;

    @XmlElement(name = "status")
    private String status;

    @XmlElement(name = "reference")
    private String reference;

    //@XmlElement(name = "test", type=java.lang.Boolean.class)
    //private Boolean test;

    //@XmlElement(name = "voidable")
    //private Boolean voidable;

    //@XmlElement(name = "refundable")
    //private Boolean refundable;

    @XmlElement(name = "created_at")
    private DateTime createdAt;

    public Account getAccount() {
        return account;
    }

    public void setAccount(final Account account) {
        this.account = account;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(final Invoice invoice) {
        this.invoice = invoice;
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(final Object subscription) {
        this.subscription = stringOrNull(subscription);
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(final Object uuid) {
        this.uuid = stringOrNull(uuid);
    }

    public String getAction() {
        return action;
    }

    public void setAction(final Object action) {
        this.action = stringOrNull(action);
    }

    public Integer getAmountInCents() {
        return amountInCents;
    }

    public void setAmountInCents(final Object amountInCents) {
        this.amountInCents = integerOrNull(amountInCents);
    }

    public Integer getTaxInCents() {
        return taxInCents;
    }

    public void setTaxInCents(final Object taxInCents) {
        this.taxInCents = integerOrNull(taxInCents);
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(final Object currency) {
        this.currency = stringOrNull(currency);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final Object status) {
        this.status = stringOrNull(status);
    }

    public String getReference() {
        return reference;
    }

    public void setReference(final Object reference) {
        this.reference = stringOrNull(reference);
    }

    /*
    public Boolean getTest() {
        return test;
    }

    public void setTest(final Boolean test) {
        this.test = test;
    }

    public Boolean getVoidable() {
        return voidable;
    }

    public void setVoidable(final Boolean voidable) {
        this.voidable = voidable;
    }

    public Boolean getRefundable() {
        return refundable;
    }

    public void setRefundable(final Boolean refundable) {
        this.refundable = refundable;
    }
    */
    public DateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final Object createdAt) {
        this.createdAt = dateTimeOrNull(createdAt);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Transaction");
        sb.append("{account=").append(account);
        sb.append(", invoice=").append(invoice);
        sb.append(", subscription='").append(subscription).append('\'');
        sb.append(", uuid='").append(uuid).append('\'');
        sb.append(", action='").append(action).append('\'');
        sb.append(", amountInCents=").append(amountInCents);
        sb.append(", taxInCents=").append(taxInCents);
        sb.append(", currency='").append(currency).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append(", reference='").append(reference).append('\'');
        /*
        sb.append(", test=").append(test);
        sb.append(", voidable=").append(voidable);
        sb.append(", refundable=").append(refundable);
        */
        sb.append(", createdAt=").append(createdAt);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Transaction that = (Transaction) o;

        if (account != null ? !account.equals(that.account) : that.account != null) {
            return false;
        }
        if (action != null ? !action.equals(that.action) : that.action != null) {
            return false;
        }
        if (amountInCents != null ? !amountInCents.equals(that.amountInCents) : that.amountInCents != null) {
            return false;
        }
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) {
            return false;
        }
        if (currency != null ? !currency.equals(that.currency) : that.currency != null) {
            return false;
        }
        if (invoice != null ? !invoice.equals(that.invoice) : that.invoice != null) {
            return false;
        }
        if (reference != null ? !reference.equals(that.reference) : that.reference != null) {
            return false;
        }
        /*
        if (refundable != null ? !refundable.equals(that.refundable) : that.refundable != null) {
            return false;
        }
        */
        if (status != null ? !status.equals(that.status) : that.status != null) {
            return false;
        }
        if (subscription != null ? !subscription.equals(that.subscription) : that.subscription != null) {
            return false;
        }
        if (taxInCents != null ? !taxInCents.equals(that.taxInCents) : that.taxInCents != null) {
            return false;
        }
        /*
        if (test != null ? !test.equals(that.test) : that.test != null) {
            return false;
        }
        */
        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) {
            return false;
        }
        /*
        if (voidable != null ? !voidable.equals(that.voidable) : that.voidable != null) {
            return false;
        }
        */
        return true;
    }

    @Override
    public int hashCode() {
        int result = account != null ? account.hashCode() : 0;
        result = 31 * result + (invoice != null ? invoice.hashCode() : 0);
        result = 31 * result + (subscription != null ? subscription.hashCode() : 0);
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        result = 31 * result + (action != null ? action.hashCode() : 0);
        result = 31 * result + (amountInCents != null ? amountInCents.hashCode() : 0);
        result = 31 * result + (taxInCents != null ? taxInCents.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (reference != null ? reference.hashCode() : 0);
        /*
        result = 31 * result + (test != null ? test.hashCode() : 0);
        result = 31 * result + (voidable != null ? voidable.hashCode() : 0);
        result = 31 * result + (refundable != null ? refundable.hashCode() : 0);
        */
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        return result;
    }
}
