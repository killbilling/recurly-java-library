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

    @XmlElement(name = "tax_exempt")
    private Boolean taxExempt;

    @XmlElement(name = "product_code")
    private String productCode;

    @XmlElement(name = "start_date")
    private DateTime startDate;

    @XmlElement(name = "end_date")
    private DateTime endDate;

    @XmlElement(name = "created_at")
    private DateTime createdAt;

    @XmlElement(name = "updated_at")
    private DateTime updatedAt;

    @XmlElement(name = "revenue_schedule_type")
    private RevenueScheduleType revenueScheduleType;

    @XmlElement(name = "credit_reason_code")
    private String creditReasonCode;

    @XmlElement(name = "original_adjustment_uuid")
    private String originalAdjustmentUuid;

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

    public Boolean getTaxExempt() {
        return taxExempt;
    }

    public void setTaxExempt(final Object taxExempt) {
        this.taxExempt = booleanOrNull(taxExempt);
    }

    public String getProductCode() { return productCode; }

    public void setProductCode(final Object productCode) { this.productCode = stringOrNull(productCode); }

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

    public DateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(final Object updatedAt) {
        this.updatedAt = dateTimeOrNull(updatedAt);
    }

    public AdjustmentRefund toAdjustmentRefund() {
        final AdjustmentRefund adjustmentRefund = new AdjustmentRefund();
        adjustmentRefund.setUuid(uuid);
        adjustmentRefund.setQuantity(quantity);
        adjustmentRefund.setProrate(false);
        return adjustmentRefund;
    }

    public RevenueScheduleType getRevenueScheduleType() {
        return revenueScheduleType;
    }

    public void setRevenueScheduleType(final Object revenueScheduleType) {
        this.revenueScheduleType = enumOrNull(RevenueScheduleType.class, revenueScheduleType, true);
    }

    public String getCreditReasonCode() {
        return creditReasonCode;
    }

    public void setCreditReasonCode(final Object creditReasonCode) {
        this.creditReasonCode = stringOrNull(creditReasonCode);
    }

    public String getOriginalAdjustmentUuid() {
        return originalAdjustmentUuid;
    }

    public void setOriginalAdjustmentUuid(final Object originalAdjustmentUuid) {
        this.originalAdjustmentUuid = stringOrNull(originalAdjustmentUuid);
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
        sb.append(", taxExempt=").append(taxExempt);
        sb.append(", totalInCents=").append(totalInCents);
        sb.append(", currency='").append(currency).append('\'');
        sb.append(", taxable=").append(taxable);
        sb.append(", productCode=").append(productCode);
        sb.append(", startDate=").append(startDate);
        sb.append(", endDate=").append(endDate);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", revenueScheduleType=").append(revenueScheduleType);
        sb.append(", creditReasonCode=").append(creditReasonCode);
        sb.append(", originalAdjustmentUuid=").append(originalAdjustmentUuid);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Adjustment that = (Adjustment) o;

        if (account != null ? !account.equals(that.account) : that.account != null) {
            return false;
        }
        if (accountingCode != null ? !accountingCode.equals(that.accountingCode) : that.accountingCode != null) {
            return false;
        }
        if (createdAt != null ? createdAt.compareTo(that.createdAt) != 0 : that.createdAt != null) {
            return false;
        }
        if (currency != null ? !currency.equals(that.currency) : that.currency != null) {
            return false;
        }
        if (creditReasonCode != null ? !creditReasonCode.equals(that.creditReasonCode) : that.creditReasonCode != null) {
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
        if (originalAdjustmentUuid != null ? !originalAdjustmentUuid.equals(that.originalAdjustmentUuid) : that.originalAdjustmentUuid != null) {
            return false;
        }
        if (productCode != null ? !productCode.equals(that.productCode) : that.productCode != null) {
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
        if (taxExempt != null ? !taxExempt.equals(that.taxExempt) : that.taxExempt != null) {
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
        if (updatedAt != null ? updatedAt.compareTo(that.updatedAt) != 0 : that.updatedAt != null) {
            return false;
        }
        if (revenueScheduleType != null ? !revenueScheduleType.equals(that.revenueScheduleType) : that.revenueScheduleType != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
                account,
                uuid,
                description,
                accountingCode,
                origin,
                unitAmountInCents,
                quantity,
                productCode,
                discountInCents,
                taxInCents,
                totalInCents,
                currency,
                taxable,
                taxExempt,
                startDate,
                endDate,
                createdAt,
                updatedAt,
                revenueScheduleType,
                creditReasonCode,
                originalAdjustmentUuid
        );
    }

}
