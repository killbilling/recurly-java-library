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
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.joda.time.DateTime;
import com.google.common.base.Objects;

import java.math.BigDecimal;

@XmlRootElement(name = "invoice")
public class Invoice extends RecurlyObject {

    @XmlElement(name = "account")
    private Account account;

    @XmlElement(name = "original_invoice")
    private Invoice originalInvoice;

    @XmlElement(name = "uuid")
    private String uuid;

    @XmlElement(name = "state")
    private String state;

    @XmlElement(name = "invoice_number")
    private Integer invoiceNumber;

    @XmlElement(name = "invoice_number_prefix")
    private String invoiceNumberPrefix;

    @XmlElement(name = "po_number")
    private String poNumber;

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

    @XmlElement(name = "tax_region")
    private String taxRegion;

    @XmlElement(name = "tax_type")
    private String taxType;

    @XmlElement(name = "tax_rate")
    private BigDecimal taxRate;

    @XmlElement(name = "created_at")
    private DateTime createdAt;

    @XmlElement(name = "updated_at")
    private DateTime updatedAt;

    @XmlElement(name = "closed_at")
    private DateTime closedAt;

    @XmlElement(name = "collection_method")
    private String collectionMethod;

    @XmlElement(name = "net_terms")
    private Integer netTerms;

    @XmlElement(name = "subtotal_after_discount_in_cents")
    private Integer subtotalAfterDiscountInCents;

    @XmlElement(name = "attempt_next_collection_at")
    private DateTime attemptNextCollectionAt;

    @XmlElement(name = "recovery_reason")
    private String recoveryReason;

    @XmlElementWrapper(name = "line_items")
    @XmlElement(name = "adjustment")
    private Adjustments lineItems;

    @XmlElementWrapper(name = "transactions")
    @XmlElement(name = "transaction")
    private Transactions transactions;

    @XmlElement(name = "customer_notes")
    private String customerNotes;

    @XmlElement(name = "terms_and_conditions")
    private String termsAndConditions;

    @XmlElement(name = "vat_reverse_charge_notes")
    private String vatReverseChargeNotes;

    public Account getAccount() {
        if (account != null && account.getCreatedAt() == null) {
            account = fetch(account, Account.class);
        }
        return account;
    }

    /**
     * Set this original invoice to the passed in original invoice.
     *
     * @param originalInvoice original invoice
     *
     */
    public void setOriginalInvoice(Invoice originalInvoice) {
        this.originalInvoice = originalInvoice;
    }

    /**
     * Fetches the original invoice if the href is populated, otherwise return the current original invoice.
     *
     * @return fully loaded original invoice
     */
    public Invoice getOriginalInvoice() {
        if (originalInvoice != null && originalInvoice.getHref() != null && !originalInvoice.getHref().isEmpty()) {
            originalInvoice = fetch(originalInvoice, Invoice.class);
        }
        return originalInvoice;
    }

    public String getId() {
        if (invoiceNumber == null) return null;
        if (invoiceNumberPrefix == null) return invoiceNumber.toString();
        return invoiceNumberPrefix + invoiceNumber.toString();
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

    public String getInvoiceNumberPrefix() {
        return invoiceNumberPrefix;
    }

    public void setInvoiceNumberPrefix(final Object invoiceNumberPrefix) {
        this.invoiceNumberPrefix = stringOrNull(invoiceNumberPrefix);
    }

    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(final Object poNumber) {
        this.poNumber = stringOrNull(poNumber);
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

    public void setTaxRegion(final Object taxRegion) {
      this.taxRegion = stringOrNull(taxRegion);
    }

    public String getTaxRegion() {
      return taxRegion;
    }

    public void setTaxRate(final Object taxRate) {
      this.taxRate = bigDecimalOrNull(taxRate);
    }

    public BigDecimal getTaxRate() {
      return taxRate;
    }

    public void setTaxType(final Object taxType) {
      this.taxType = stringOrNull(taxType);
    }

    public String getTaxType() {
      return taxType;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public void setClosedAt(final Object closedAt) {
        this.closedAt = dateTimeOrNull(closedAt);
    }

    public DateTime getClosedAt() {
        return closedAt;
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

    public String getCollectionMethod() {
        return collectionMethod;
    }

    public void setCollectionMethod(final Object collectionMethod) {
        this.collectionMethod = stringOrNull(collectionMethod);
    }

    public Integer getNetTerms() {
        return netTerms;
    }

    public void setNetTerms(final Object netTerms) {
        this.netTerms = integerOrNull(netTerms);
    }

    public Integer getSubtotalAfterDiscountInCents() {
        return this.subtotalAfterDiscountInCents;
    }

    public void setSubtotalAfterDiscountInCents(final Object subtotalAfterDiscountInCents) {
        this.subtotalAfterDiscountInCents = integerOrNull(subtotalAfterDiscountInCents);
    }

    public DateTime getAttemptNextCollectionAt() {
        return this.attemptNextCollectionAt;
    }

    public void setAttemptNextCollectionAt(final Object attemptNextCollectionAt) {
        this.attemptNextCollectionAt = dateTimeOrNull(attemptNextCollectionAt);
    }

    public String getRecoveryReason() {
        return this.recoveryReason;
    }

    public void setRecoveryReason(final Object recoveryReason) {
        this.recoveryReason = stringOrNull(recoveryReason);
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

    public String getCustomerNotes() {
        return customerNotes;
    }

    public void setCustomerNotes(final Object customerNotes) {
        this.customerNotes = stringOrNull(customerNotes);
    }

    public String getTermsAndConditions() {
        return termsAndConditions;
    }

    public void setTermsAndConditions(final Object termsAndConditions) {
        this.termsAndConditions = stringOrNull(termsAndConditions);
    }

    public String getVatReverseChargeNotes() {
        return vatReverseChargeNotes;
    }

    public void setVatReverseChargeNotes(final Object vatReverseChargeNotes) {
        this.vatReverseChargeNotes = stringOrNull(vatReverseChargeNotes);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Invoice{");
        sb.append("account=").append(account);
        sb.append(", originalInvoice='").append(originalInvoice).append('\'');
        sb.append(", uuid='").append(uuid).append('\'');
        sb.append(", state='").append(state).append('\'');
        sb.append(", invoiceNumber=").append(invoiceNumber);
        sb.append(", invoiceNumberPrefix=").append(invoiceNumberPrefix);
        sb.append(", poNumber=").append(poNumber);
        sb.append(", vatNumber='").append(vatNumber).append('\'');
        sb.append(", subtotalInCents=").append(subtotalInCents);
        sb.append(", taxInCents=").append(taxInCents);
        sb.append(", totalInCents=").append(totalInCents);
        sb.append(", currency='").append(currency).append('\'');
        sb.append(", taxRegion=").append(taxRegion);
        sb.append(", taxType=").append(taxType);
        sb.append(", taxRate=").append(taxRate);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", closedAt=").append(closedAt);
        sb.append(", collectionMethod='").append(collectionMethod).append('\'');
        sb.append(", netTerms=").append(netTerms);
        sb.append(", subtotalAfterDiscountInCents=").append(subtotalAfterDiscountInCents);
        sb.append(", attemptNextCollectionAt=").append(attemptNextCollectionAt);
        sb.append(", recoveryReason=").append(recoveryReason);
        sb.append(", lineItems=").append(lineItems);
        sb.append(", transactions=").append(transactions);
        sb.append(", customerNotes='").append(customerNotes).append('\'');
        sb.append(", termsAndConditions='").append(termsAndConditions).append('\'');
        sb.append(", vatReverseChargeNotes='").append(vatReverseChargeNotes).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Invoice invoice = (Invoice) o;

        if (account != null ? !account.equals(invoice.account) : invoice.account != null) {
            return false;
        }
        if (attemptNextCollectionAt != null ? attemptNextCollectionAt.compareTo(invoice.attemptNextCollectionAt) != 0 : invoice.attemptNextCollectionAt != null) {
            return false;
        }
        if (originalInvoice != null ? !originalInvoice.equals(invoice.originalInvoice) : invoice.originalInvoice != null) {
            return false;
        }
        if (closedAt != null ? closedAt.compareTo(invoice.closedAt) != 0 : invoice.closedAt != null) {
            return false;
        }
        if (collectionMethod != null ? !collectionMethod.equals(invoice.collectionMethod) : invoice.collectionMethod != null) {
            return false;
        }
        if (createdAt != null ? createdAt.compareTo(invoice.createdAt) != 0 : invoice.createdAt != null) {
            return false;
        }
        if (currency != null ? !currency.equals(invoice.currency) : invoice.currency != null) {
            return false;
        }
        if (customerNotes != null ? !customerNotes.equals(invoice.customerNotes) : invoice.customerNotes != null) {
            return false;
        }
        if (invoiceNumber != null ? !invoiceNumber.equals(invoice.invoiceNumber) : invoice.invoiceNumber != null) {
            return false;
        }
        if (invoiceNumberPrefix != null ? !invoiceNumberPrefix.equals(invoice.invoiceNumberPrefix) : invoice.invoiceNumberPrefix != null) {
            return false;
        }
        if (lineItems != null ? !lineItems.equals(invoice.lineItems) : invoice.lineItems != null) {
            return false;
        }
        if (netTerms != null ? !netTerms.equals(invoice.netTerms) : invoice.netTerms != null) {
            return false;
        }
        if (poNumber != null ? !poNumber.equals(invoice.poNumber) : invoice.poNumber != null) {
            return false;
        }
        if (recoveryReason != null ? !recoveryReason.equals(invoice.recoveryReason) : invoice.recoveryReason != null) {
            return false;
        }
        if (state != null ? !state.equals(invoice.state) : invoice.state != null) {
            return false;
        }
        if (subtotalAfterDiscountInCents != null ? !subtotalAfterDiscountInCents.equals(invoice.subtotalAfterDiscountInCents) : invoice.subtotalAfterDiscountInCents != null) {
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
        if (taxRegion != null ? !taxRegion.equals(invoice.taxRegion) : invoice.taxRegion != null) {
          return false;
        }
        if (taxType != null ? !taxType.equals(invoice.taxType) : invoice.taxType != null) {
          return false;
        }
        if (taxRate != null ? !taxRate.equals(invoice.taxRate) : invoice.taxRate != null) {
          return false;
        }
        if (termsAndConditions != null ? !termsAndConditions.equals(invoice.termsAndConditions) : invoice.termsAndConditions != null) {
            return false;
        }
        if (transactions != null ? !transactions.equals(invoice.transactions) : invoice.transactions != null) {
            return false;
        }
        if (updatedAt != null ? updatedAt.compareTo(invoice.updatedAt) != 0 : invoice.updatedAt != null) {
            return false;
        }
        if (uuid != null ? !uuid.equals(invoice.uuid) : invoice.uuid != null) {
            return false;
        }
        if (vatNumber != null ? !vatNumber.equals(invoice.vatNumber) : invoice.vatNumber != null) {
            return false;
        }
        if (vatReverseChargeNotes != null ? !vatReverseChargeNotes.equals(invoice.vatReverseChargeNotes) : invoice.vatReverseChargeNotes != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
                account,
                originalInvoice,
                uuid,
                state,
                invoiceNumber,
                invoiceNumberPrefix,
                poNumber,
                vatNumber,
                subtotalInCents,
                totalInCents,
                taxInCents,
                taxRegion,
                taxType,
                taxRate,
                currency,
                createdAt,
                updatedAt,
                closedAt,
                collectionMethod,
                netTerms,
                subtotalAfterDiscountInCents,
                attemptNextCollectionAt,
                recoveryReason,
                lineItems,
                transactions,
                customerNotes,
                termsAndConditions,
                vatReverseChargeNotes
        );
    }
}
