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

package com.ning.billing.recurly.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.joda.time.DateTime;

@XmlRootElement(name = "invoice")
public class Invoice extends RecurlyObject {

    @XmlElement(name = "account")
    private Account account;

    @XmlElement(name = "uuid")
    private String uuid;

    @XmlElement(name = "state")
    private String state;

    @XmlElement(name = "invoice_number")
    private Integer invoiceNumber;

    @XmlElement(name = "po_number")
    private Integer poNumber;

    @XmlElement(name = "vat_number")
    private String vatNumber;

    @XmlElement(name = "subtotal_in_cents")
    private Integer subtotalInCents;

    @XmlElement(name = "tax_in_cents")
    private Integer taxInCents;

    @XmlElement(name = "total_in_cents")
    private Integer totalInCents;

    @XmlElement(name = "currency")
    private String currency;

    @XmlElement(name = "created_at")
    private DateTime createdAt;
	
    @XmlElement(name = "collection_method")
    private String collectionMethod;

    @XmlElement(name = "net_terms")
    private Integer netTerms;

    @XmlElementWrapper(name = "line_items")
    @XmlElement(name = "adjustment")
	private Adjustments lineItems;

    @XmlElementWrapper(name = "transactions")
    @XmlElement(name = "transaction")
    private Transactions transactions;

    public Account getAccount() {
        if (account != null && account.getCreatedAt() == null) {
            account = fetch(account, Account.class);
        }
        return account;
    }

    public void setAccount(final Account account) {
        this.account = account;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(final Object uuid) {
        this.uuid = stringOrNull(uuid);
    }

    public String getState() {
        return state;
    }

    public void setState(final Object state) {
        this.state = stringOrNull(state);
    }

    public Integer getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(final Object invoiceNumber) {
        this.invoiceNumber = integerOrNull(invoiceNumber);
    }

    public Integer getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(final Object poNumber) {
        this.poNumber = integerOrNull(poNumber);
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(final Object varNumber) {
        this.vatNumber = stringOrNull(varNumber);
    }

    public Integer getSubtotalInCents() {
        return subtotalInCents;
    }

    public void setSubtotalInCents(final Object subtotalInCents) {
        this.subtotalInCents = integerOrNull(subtotalInCents);
    }

    public Integer getTaxInCents() {
        return taxInCents;
    }

    public void setTaxInCents(final Object taxInCents) {
        this.taxInCents = integerOrNull(taxInCents);
    }

    public Integer getTotalInCents() {
        return totalInCents;
    }

    public void setTotalInCents(final Object totalInCents) {
        this.totalInCents = integerOrNull(totalInCents);
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
	
    public String getCollectionMethod() {
        return collectionMethod;
    }

    public void setCollectionMethod(Object collectionMethod) {
        this.collectionMethod = stringOrNull(collectionMethod);
    }

    public Integer getNetTerms() {
        return netTerms;
    }

    public void setNetTerms(final Object netTerms) {
        this.netTerms = integerOrNull(netTerms);
    }

    public Adjustments getLineItems() {
        return lineItems;
    }

    public void setLineItems(final Adjustments lineItems) {
        this.lineItems = lineItems;
    }

    public Transactions getTransactions() {
        return transactions;
    }

    public void setTransactions(final Transactions transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Invoice");
        sb.append("{account=").append(account);
        sb.append(", uuid='").append(uuid).append('\'');
        sb.append(", state='").append(state).append('\'');
        sb.append(", invoiceNumber=").append(invoiceNumber);
        sb.append(", poNumber=").append(poNumber);
        sb.append(", vatNumber=").append(vatNumber);
        sb.append(", subtotalInCents=").append(subtotalInCents);
        sb.append(", taxInCents=").append(taxInCents);
        sb.append(", totalInCents=").append(totalInCents);
        sb.append(", currency='").append(currency).append('\'');
        sb.append(", createdAt=").append(createdAt);
		sb.append(", collectionMethod=").append(collectionMethod);
		sb.append(", netTerms=").append(netTerms);
        sb.append(", lineItems=").append(lineItems);
        sb.append(", transactions=").append(transactions);
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

        final Invoice invoice = (Invoice) o;

        if (account != null ? !account.equals(invoice.account) : invoice.account != null) {
            return false;
        }
        if (createdAt != null ? !createdAt.equals(invoice.createdAt) : invoice.createdAt != null) {
            return false;
        }
        if (currency != null ? !currency.equals(invoice.currency) : invoice.currency != null) {
            return false;
        }
        if (invoiceNumber != null ? !invoiceNumber.equals(invoice.invoiceNumber) : invoice.invoiceNumber != null) {
            return false;
        }
        if (lineItems != null ? !lineItems.equals(invoice.lineItems) : invoice.lineItems != null) {
            return false;
        }
        if (poNumber != null ? !poNumber.equals(invoice.poNumber) : invoice.poNumber != null) {
            return false;
        }
        if (state != null ? !state.equals(invoice.state) : invoice.state != null) {
            return false;
        }
        if (subtotalInCents != null ? !subtotalInCents.equals(invoice.subtotalInCents) : invoice.subtotalInCents != null) {
            return false;
        }
        if (taxInCents != null ? !taxInCents.equals(invoice.taxInCents) : invoice.taxInCents != null) {
            return false;
        }
        if (totalInCents != null ? !totalInCents.equals(invoice.totalInCents) : invoice.totalInCents != null) {
            return false;
        }
        if (transactions != null ? !transactions.equals(invoice.transactions) : invoice.transactions != null) {
            return false;
        }
        if (uuid != null ? !uuid.equals(invoice.uuid) : invoice.uuid != null) {
            return false;
        }
        if (vatNumber != null ? !vatNumber.equals(invoice.vatNumber) : invoice.vatNumber != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = account != null ? account.hashCode() : 0;
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (invoiceNumber != null ? invoiceNumber.hashCode() : 0);
        result = 31 * result + (poNumber != null ? poNumber.hashCode() : 0);
        result = 31 * result + (vatNumber != null ? vatNumber.hashCode() : 0);
        result = 31 * result + (subtotalInCents != null ? subtotalInCents.hashCode() : 0);
        result = 31 * result + (taxInCents != null ? taxInCents.hashCode() : 0);
        result = 31 * result + (totalInCents != null ? totalInCents.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (lineItems != null ? lineItems.hashCode() : 0);
        result = 31 * result + (transactions != null ? transactions.hashCode() : 0);
        return result;
    }
}
