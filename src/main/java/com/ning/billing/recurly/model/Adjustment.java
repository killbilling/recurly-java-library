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
import javax.xml.bind.annotation.XmlRootElement;

import org.joda.time.DateTime;

@XmlRootElement(name = "adjustment")
public class Adjustment extends RecurlyObject {

    @XmlElement(name = "account")
    private Account account;

    @XmlElement(name = "uuid")
    private String uuid;

    @XmlElement(name = "description")
    private String description;

    @XmlElement(name = "accounting_code")
    private String accountingCode;

    @XmlElement(name = "origin")
    private String origin;

    @XmlElement(name = "unit_amount_in_cents")
    private Integer unitAmountInCents;

    @XmlElement(name = "quantity")
    private Integer quantity;

    @XmlElement(name = "discount_in_cents")
    private Integer discountInCents;

    @XmlElement(name = "tax_in_cents")
    private Integer taxInCents;

    @XmlElement(name = "total_in_cents")
    private Integer totalInCents;

    @XmlElement(name = "currency")
    private String currency;

    @XmlElement(name = "taxable")
    private Boolean taxable;

    @XmlElement(name = "start_date")
    private DateTime startDate;

    @XmlElement(name = "end_date")
    private DateTime endDate;

    @XmlElement(name = "created_at")
    private DateTime createdAt;

    public Account getAccount() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(final Object description) {
        this.description = stringOrNull(description);
    }

    public String getAccountingCode() {
        return accountingCode;
    }

    public void setAccountingCode(final Object accountingCode) {
        this.accountingCode = stringOrNull(accountingCode);
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(final Object origin) {
        this.origin = stringOrNull(origin);
    }

    public Integer getUnitAmountInCents() {
        return unitAmountInCents;
    }

    public void setUnitAmountInCents(final Object unitAmountInCents) {
        this.unitAmountInCents = integerOrNull(unitAmountInCents);
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(final Object quantity) {
        this.quantity = integerOrNull(quantity);
    }

    public Integer getDiscountInCents() {
        return discountInCents;
    }

    public void setDiscountInCents(final Object discountInCents) {
        this.discountInCents = integerOrNull(discountInCents);
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

    public Boolean getTaxable() {
        return taxable;
    }

    public void setTaxable(final Object taxable) {
        this.taxable = booleanOrNull(taxable);
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(final Object startDate) {
        this.startDate = dateTimeOrNull(startDate);
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(final Object endDate) {
        this.endDate = dateTimeOrNull(endDate);
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final Object createdAt) {
        this.createdAt = dateTimeOrNull(createdAt);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Adjustment");
        sb.append("{account=").append(account);
        sb.append(", uuid='").append(uuid).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", accountingCode='").append(accountingCode).append('\'');
        sb.append(", origin='").append(origin).append('\'');
        sb.append(", unitAmountInCents=").append(unitAmountInCents);
        sb.append(", quantity=").append(quantity);
        sb.append(", discountInCents=").append(discountInCents);
        sb.append(", taxInCents=").append(taxInCents);
        sb.append(", totalInCents=").append(totalInCents);
        sb.append(", currency='").append(currency).append('\'');
        sb.append(", taxable=").append(taxable);
        sb.append(", startDate=").append(startDate);
        sb.append(", endDate=").append(endDate);
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

        final Adjustment that = (Adjustment) o;

        if (account != null ? !account.equals(that.account) : that.account != null) {
            return false;
        }
        if (accountingCode != null ? !accountingCode.equals(that.accountingCode) : that.accountingCode != null) {
            return false;
        }
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) {
            return false;
        }
        if (currency != null ? !currency.equals(that.currency) : that.currency != null) {
            return false;
        }
        if (description != null ? !description.equals(that.description) : that.description != null) {
            return false;
        }
        if (discountInCents != null ? !discountInCents.equals(that.discountInCents) : that.discountInCents != null) {
            return false;
        }
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) {
            return false;
        }
        if (origin != null ? !origin.equals(that.origin) : that.origin != null) {
            return false;
        }
        if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) {
            return false;
        }
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) {
            return false;
        }
        if (taxInCents != null ? !taxInCents.equals(that.taxInCents) : that.taxInCents != null) {
            return false;
        }
        if (taxable != null ? !taxable.equals(that.taxable) : that.taxable != null) {
            return false;
        }
        if (totalInCents != null ? !totalInCents.equals(that.totalInCents) : that.totalInCents != null) {
            return false;
        }
        if (unitAmountInCents != null ? !unitAmountInCents.equals(that.unitAmountInCents) : that.unitAmountInCents != null) {
            return false;
        }
        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = account != null ? account.hashCode() : 0;
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (accountingCode != null ? accountingCode.hashCode() : 0);
        result = 31 * result + (origin != null ? origin.hashCode() : 0);
        result = 31 * result + (unitAmountInCents != null ? unitAmountInCents.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (discountInCents != null ? discountInCents.hashCode() : 0);
        result = 31 * result + (taxInCents != null ? taxInCents.hashCode() : 0);
        result = 31 * result + (totalInCents != null ? totalInCents.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (taxable != null ? taxable.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        return result;
    }
}
