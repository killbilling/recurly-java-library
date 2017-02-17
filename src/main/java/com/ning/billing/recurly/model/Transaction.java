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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.joda.time.DateTime;

import com.google.common.base.Objects;

@XmlRootElement(name = "transaction")
public class Transaction extends AbstractTransaction {

    @XmlElement(name = "account")
    private Account account;

    @XmlElement(name = "invoice")
    private Invoice invoice;

    @XmlElement(name = "subscription")
    private String subscription;

    @XmlElement(name = "uuid")
    private String uuid;

    @XmlElement(name = "tax_in_cents")
    private Integer taxInCents;

    @XmlElement(name = "currency")
    private String currency;

    @XmlElement(name = "description")
    private String description;

    @XmlElement(name = "recurring")
    private Boolean recurring;

    @XmlElement(name = "product_code")
    private String productCode;

    @XmlElement(name = "created_at")
    private DateTime createdAt;

    @XmlElement(name = "updated_at")
    private DateTime updatedAt;

    @XmlElement(name = "details")
    private TransactionDetails details;

    @XmlElement(name = "payment_method")
    private String paymentMethod;

    @XmlElement(name = "collected_at")
    private DateTime collectedAt;

    public Account getAccount() {
        if (account != null && account.getCreatedAt() == null) {
            account = fetch(account, Account.class);
        }
        return account;
    }

    public void setAccount(final Account account) {
        this.account = account;
    }

    public Invoice getInvoice() {
        if (invoice != null && invoice.getCreatedAt() == null) {
            invoice = fetch(invoice, Invoice.class);
        }
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

    public String getDescription() {
        return description;
    }

    public void setDescription(final Object description) {
        this.description = stringOrNull(description);
    }

    public Boolean getRecurring() {
        return recurring;
    }

    public void setRecurring(final Object recurring) { this.recurring = booleanOrNull(recurring); }

    protected String getProductCode() { return productCode; }

    public void setProductCode(final Object productCode) { this.productCode = stringOrNull(productCode); }

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

    public TransactionDetails getDetails() {
        return details;
    }

    public void setDetails(final TransactionDetails details) {
        this.details = details;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(final Object paymentMethod) {
        this.paymentMethod = stringOrNull(paymentMethod);
    }

    public DateTime getCollectedAt() {
        return collectedAt;
    }

    public void setCollectedAt(final Object collectedAt) {
        this.collectedAt = dateTimeOrNull(collectedAt);
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Transaction{");
        sb.append("account=").append(account);
        sb.append(", invoice=").append(invoice);
        sb.append(", subscription='").append(subscription).append('\'');
        sb.append(", uuid='").append(uuid).append('\'');
        sb.append(", taxInCents=").append(taxInCents);
        sb.append(", currency='").append(currency).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", recurring=").append(recurring);
        sb.append(", productCode=").append(productCode);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", details=").append(details);
        sb.append(", paymentMethod=").append(paymentMethod);
        sb.append(", collectedAt=").append(collectedAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Transaction that = (Transaction) o;

        if (account != null ? !account.equals(that.account) : that.account != null) {
            return false;
        }
        if (createdAt != null ? createdAt.compareTo(that.createdAt) != 0 : that.createdAt != null) {
            return false;
        }
        if (currency != null ? !currency.equals(that.currency) : that.currency != null) {
            return false;
        }
        if (description != null ? !description.equals(that.description) : that.description != null) {
            return false;
        }
        if (details != null ? !details.equals(that.details) : that.details != null) {
            return false;
        }
        if (invoice != null ? !invoice.equals(that.invoice) : that.invoice != null) {
            return false;
        }
        if (recurring != null ? !recurring.equals(that.recurring) : that.recurring != null) {
            return false;
        }
        if (productCode != null ? !productCode.equals(that.productCode) : that.productCode != null) {
            return false;
        }
        if (subscription != null ? !subscription.equals(that.subscription) : that.subscription != null) {
            return false;
        }
        if (taxInCents != null ? !taxInCents.equals(that.taxInCents) : that.taxInCents != null) {
            return false;
        }
        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) {
            return false;
        }
        if (updatedAt != null ? updatedAt.compareTo(that.updatedAt) != 0 : that.updatedAt != null) {
            return false;
        }
        if (paymentMethod != null ? !paymentMethod.equals(that.paymentMethod) : that.paymentMethod != null) {
            return false;
        }
        if (collectedAt != null ? collectedAt.compareTo(that.collectedAt) != 0 : that.collectedAt != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
                account,
                invoice,
                subscription,
                uuid,
                taxInCents,
                currency,
                description,
                productCode,
                recurring,
                createdAt,
                updatedAt,
                details
        );
    }
}
