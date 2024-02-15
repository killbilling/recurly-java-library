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
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "add_on")
public class AddOn extends AbstractAddOn {

    @XmlTransient
    public static final String ADDONS_RESOURCE = "/add_ons";

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "item_code")
    private String itemCode;

    @XmlElement(name = "item_state")
    private String itemState;

    @XmlElement(name = "external_sku")
    private String externalSku;

    @XmlElement(name = "display_quantity_on_hosted_page")
    private Boolean displayQuantityOnHostedPage;

    @XmlElement(name = "default_quantity")
    private Integer defaultQuantity;

    @XmlElement(name = "unit_amount_in_cents")
    private RecurlyUnitCurrency unitAmountInCents;

    @XmlElement(name = "liability_gl_account_id")
    private String liabilityGlAccountId;

    @XmlElement(name = "revenue_gl_account_id")
    private String revenueGlAccountId;

    @XmlElement(name = "performance_obligation_id")
    private String performanceObligationId;

    @XmlElement(name = "created_at")
    private DateTime createdAt;

    @XmlElement(name = "updated_at")
    private DateTime updatedAt;

    @XmlElement(name = "measured_unit")
    private MeasuredUnit measuredUnit;

    @XmlElement(name = "add_on_type")
    private String addOnType;

    @XmlElement(name = "tax_code")
    private String taxCode;

    @XmlElement(name = "accounting_code")
    private String accountingCode;

    @XmlElement(name = "optional")
    private Boolean optional;

    @XmlElement(name = "tier_type")
    private String tierType;

    @XmlElementWrapper(name = "percentage_tiers")
    @XmlElement(name = "percentage_tier")
    private CurrencyPercentageTiers percentageTiers;

    public String getName() {
        return name;
    }

    public void setName(final Object name) {
        this.name = stringOrNull(name);
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(final Object itemCode) {
        this.itemCode = stringOrNull(itemCode);
    }

    public String getItemState() {
        return itemState;
    }

    public void setItemState(final Object itemState) {
        this.itemState = stringOrNull(itemState);
    }

    public String getExternalSku() {
        return externalSku;
    }

    public void setExternalSku(final Object externalSku) {
        this.externalSku = stringOrNull(externalSku);
    }

    public Boolean getDisplayQuantityOnHostedPage() {
        return displayQuantityOnHostedPage;
    }

    public void setDisplayQuantityOnHostedPage(final Object displayQuantityOnHostedPage) {
        this.displayQuantityOnHostedPage = booleanOrNull(displayQuantityOnHostedPage);
    }

    public Integer getDefaultQuantity() {
        return defaultQuantity;
    }

    public void setDefaultQuantity(final Object defaultQuantity) {
        this.defaultQuantity = integerOrNull(defaultQuantity);
    }

    public RecurlyUnitCurrency getUnitAmountInCents() {
        return unitAmountInCents;
    }

    public void setUnitAmountInCents(final Object unitAmountInCents) {
        this.unitAmountInCents = RecurlyUnitCurrency.build(unitAmountInCents);
    }

    public String getLiabilityGlAccountId() {
        return liabilityGlAccountId;
    }

    public void setLiabilityGlAccountId(final Object liabilityGlAccountId) {
        this.liabilityGlAccountId = stringOrNull(liabilityGlAccountId);
    }

    public String getRevenueGlAccountId() {
        return revenueGlAccountId;
    }

    public void setRevenueGlAccountId(final Object revenueGlAccountId) {
        this.revenueGlAccountId = stringOrNull(revenueGlAccountId);
    }

    public String getPerformanceObligationId() {
        return performanceObligationId;
    }

    public void setPerformanceObligationId(final Object performanceObligationId) {
        this.performanceObligationId = stringOrNull(performanceObligationId);
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

    public MeasuredUnit getMeasuredUnit() {
        if (measuredUnit != null && measuredUnit.getHref() != null && !measuredUnit.getHref().isEmpty()) {
            measuredUnit = fetch(measuredUnit, MeasuredUnit.class);
        }
        return measuredUnit;
    }

    public void setMeasuredUnit(final MeasuredUnit measuredUnit) {
        this.measuredUnit = measuredUnit;
    }

    public String getAddOnType() {
        return addOnType;
    }

    public void setAddOnType(final Object addOnType) {
        this.addOnType = stringOrNull(addOnType);
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(final Object taxCode) {
        this.taxCode = stringOrNull(taxCode);
    }

    public String getAccountingCode() {
        return accountingCode;
    }

    public void setAccountingCode(final Object accountingCode) {
        this.accountingCode = stringOrNull(accountingCode);
    }

    public Boolean getOptional() {
        return optional;
    }

    public void setOptional(final Object optional) {
        this.optional = booleanOrNull(optional);
    }

    public String getTierType() {
        return tierType;
    }

    public void setTierType(final Object tierType) {
        this.tierType = stringOrNull(tierType);
    }

    public CurrencyPercentageTiers getPercentageTiers() {
        return percentageTiers;
    }

    public void setPercentageTiers(final CurrencyPercentageTiers percentageTiers) {
        this.percentageTiers = percentageTiers;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AddOn{");
        sb.append("name='").append(name).append('\'');
        sb.append(", itemCode='").append(itemCode).append('\'');
        sb.append(", itemState='").append(itemState).append('\'');
        sb.append(", externalSku='").append(externalSku).append('\'');
        sb.append(", measuredUnit='").append(measuredUnit).append('\'');
        sb.append(", addOnType='").append(addOnType).append('\'');
        sb.append(", displayQuantityOnHostedPage=").append(displayQuantityOnHostedPage);
        sb.append(", defaultQuantity=").append(defaultQuantity);
        sb.append(", unitAmountInCents=").append(unitAmountInCents);
        sb.append(", liabilityGlAccountId='").append(liabilityGlAccountId).append('\'');
        sb.append(", revenueGlAccountId='").append(revenueGlAccountId).append('\'');
        sb.append(", performanceObligationId='").append(performanceObligationId).append('\'');
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", taxCode=").append(taxCode);
        sb.append(", accountingCode=").append(accountingCode);
        sb.append(", optional=").append(optional);
        sb.append(", tierType=").append(tierType);
        sb.append(", usageCalculationType=").append(usageCalculationType);
        sb.append(", usageTimeframe=").append(usageTimeframe);
        sb.append(", percentage_tiers=").append(percentageTiers);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final AddOn addOn = (AddOn) o;

        if (createdAt != null ? createdAt.compareTo(addOn.createdAt) != 0 : addOn.createdAt != null) {
            return false;
        }
        if (measuredUnit != null ? !measuredUnit.equals(addOn.measuredUnit) : addOn.measuredUnit != null) {
            return false;
        }
        if (addOnType != null ? !addOnType.equals(addOn.addOnType) : addOn.addOnType != null) {
            return false;
        }
        if (updatedAt != null ? updatedAt.compareTo(addOn.updatedAt) != 0 : addOn.updatedAt != null) {
            return false;
        }
        if (defaultQuantity != null ? !defaultQuantity.equals(addOn.defaultQuantity) : addOn.defaultQuantity != null) {
            return false;
        }
        if (displayQuantityOnHostedPage != null ? !displayQuantityOnHostedPage.equals(addOn.displayQuantityOnHostedPage) : addOn.displayQuantityOnHostedPage != null) {
            return false;
        }
        if (name != null ? !name.equals(addOn.name) : addOn.name != null) {
            return false;
        }
        if (unitAmountInCents != null ? !unitAmountInCents.equals(addOn.unitAmountInCents) : addOn.unitAmountInCents != null) {
            return false;
        }
        if (taxCode != null ? !taxCode.equals(addOn.taxCode) : addOn.taxCode != null) {
            return false;
        }
        if (accountingCode != null ? !accountingCode.equals(addOn.accountingCode) : addOn.accountingCode != null) {
            return false;
        }
        if (liabilityGlAccountId != null ? !liabilityGlAccountId.equals(addOn.liabilityGlAccountId) : addOn.liabilityGlAccountId != null) {
            return false;
        }
        if (revenueGlAccountId != null ? !revenueGlAccountId.equals(addOn.revenueGlAccountId) : addOn.revenueGlAccountId != null) {
            return false;
        }
        if (performanceObligationId != null ? !performanceObligationId.equals(addOn.performanceObligationId) : addOn.performanceObligationId != null) {
            return false;
        }
        if (optional != null ? !optional.equals(addOn.optional) : addOn.optional != null) {
            return false;
        }
        if (itemCode != null ? !itemCode.equals(addOn.itemCode) : addOn.itemCode != null) {
            return false;
        }
        if (itemState != null ? !itemState.equals(addOn.itemState) : addOn.itemState != null) {
            return false;
        }
        if (externalSku != null ? !externalSku.equals(addOn.externalSku) : addOn.externalSku != null) {
            return false;
        }
        if (tierType != null ? !tierType.equals(addOn.tierType) : addOn.tierType != null) {
            return false;
        }
        if (percentageTiers != null ? !percentageTiers.equals(addOn.percentageTiers) : addOn.percentageTiers != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
                name,
                itemCode,
                itemState,
                externalSku,
                measuredUnit,
                addOnType,
                displayQuantityOnHostedPage,
                defaultQuantity,
                unitAmountInCents,
                liabilityGlAccountId,
                revenueGlAccountId,
                performanceObligationId,
                createdAt,
                updatedAt,
                taxCode,
                accountingCode,
                optional,
                tierType,
                percentageTiers
        );
    }
}
