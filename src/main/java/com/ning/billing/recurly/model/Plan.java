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
import javax.xml.bind.annotation.XmlTransient;

import org.joda.time.DateTime;
import com.google.common.base.Objects;

@XmlRootElement(name = "plan")
public class Plan extends RecurlyObject {

    @XmlTransient
    public static final String PLANS_RESOURCE = "/plans";

    @XmlElementWrapper(name = "add_ons")
    @XmlElement(name = "add_on")
    private AddOns addOns;

    @XmlElement(name = "plan_code")
    private String planCode;

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "description")
    private String description;

    @XmlElement(name = "success_url")
    private String successLink;

    @XmlElement(name = "cancel_url")
    private String cancelLink;

    @XmlElement(name = "display_donation_amounts")
    private Boolean displayDonationAmounts;

    @XmlElement(name = "display_quantity")
    private Boolean displayQuantity;

    @XmlElement(name = "display_phone_number")
    private Boolean displayPhoneNumber;

    @XmlElement(name = "bypass_hosted_confirmation")
    private Boolean bypassHostedConfirmation;

    @XmlElement(name = "unit_name")
    private String unitName;

    @XmlElement(name = "plan_interval_unit")
    private String planIntervalUnit;

    @XmlElement(name = "plan_interval_length")
    private Integer planIntervalLength;

    @XmlElement(name = "trial_interval_length")
    private Integer trialIntervalLength;

    @XmlElement(name = "trial_interval_unit")
    private String trialIntervalUnit;

    @XmlElement(name = "accounting_code")
    private String accountingCode;

    @XmlElement(name = "revenue_schedule_type")
    private RevenueScheduleType revenueScheduleType;

    @XmlElement(name = "setup_fee_revenue_schedule_type")
    private RevenueScheduleType setupFeeRevenueScheduleType;

    @XmlElement(name = "created_at")
    private DateTime createdAt;

    @XmlElement(name = "updated_at")
    private DateTime updatedAt;

    @XmlElement(name = "unit_amount_in_cents")
    private RecurlyUnitCurrency unitAmountInCents;

    @XmlElement(name = "setup_fee_in_cents")
    private RecurlyUnitCurrency setupFeeInCents;

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(final Object planCode) {
        this.planCode = stringOrNull(planCode);
    }

    public String getName() {
        return name;
    }

    public void setName(final Object name) {
        this.name = stringOrNull(name);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final Object description) {
        this.description = stringOrNull(description);
    }

    public String getSuccessLink() {
        return successLink;
    }

    public void setSuccessLink(final Object link) {
        this.successLink = stringOrNull(link);
    }

    public String getCancelLink() {
        return cancelLink;
    }

    public void setCancelLink(final Object link) {
        this.cancelLink = stringOrNull(link);
    }

    public Boolean getDisplayDonationAmounts() {
        return displayDonationAmounts;
    }

    public void setDisplayDonationAmounts(final Object displayAmounts) {
        this.displayDonationAmounts = booleanOrNull(displayAmounts);
    }

    public Boolean getDisplayQuantity() {
        return displayQuantity;
    }

    public void setDisplayQuantity(final Object displayQuantity) {
        this.displayQuantity = booleanOrNull(displayQuantity);
    }

    public Boolean getDisplayPhoneNumber() {
        return displayPhoneNumber;
    }

    public void setDisplayPhoneNumber(final Object displayPhoneNumber) {
        this.displayPhoneNumber = booleanOrNull(displayPhoneNumber);
    }

    public Boolean getBypassHostedConfirmation() {
        return bypassHostedConfirmation;
    }

    public void setBypassHostedConfirmation(final Object bypassHostedConfirmation) {
        this.bypassHostedConfirmation = booleanOrNull(bypassHostedConfirmation);
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(final Object unitName) {
        this.unitName = stringOrNull(unitName);
    }

    public String getPlanIntervalUnit() {
        return planIntervalUnit;
    }

    public void setPlanIntervalUnit(final Object planIntervalUnit) {
        this.planIntervalUnit = stringOrNull(planIntervalUnit);
    }

    public Integer getPlanIntervalLength() {
        return planIntervalLength;
    }

    public void setPlanIntervalLength(final Object planIntervalLength) {
        this.planIntervalLength = integerOrNull(planIntervalLength);
    }

    public String getTrialIntervalUnit() {
        return trialIntervalUnit;
    }

    public void setTrialIntervalUnit(final Object trialIntervalUnit) {
        this.trialIntervalUnit = stringOrNull(trialIntervalUnit);
    }

    public Integer getTrialIntervalLength() {
        return trialIntervalLength;
    }

    public void setTrialIntervalLength(final Object trialIntervalLength) {
        this.trialIntervalLength = integerOrNull(trialIntervalLength);
    }

    public String getAccountingCode() {
        return accountingCode;
    }

    public void setAccountingCode(final Object accountingCode) {
        this.accountingCode = stringOrNull(accountingCode);
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

    public RecurlyUnitCurrency getUnitAmountInCents() {
        return unitAmountInCents;
    }

    public void setUnitAmountInCents(final Object unitAmountInCents) {
        this.unitAmountInCents = RecurlyUnitCurrency.build(unitAmountInCents);
    }

    public RecurlyUnitCurrency getSetupFeeInCents() {
        return setupFeeInCents;
    }

    public void setSetupFeeInCents(final Object setupFeeInCents) {
        this.setupFeeInCents = RecurlyUnitCurrency.build(setupFeeInCents);
    }

    public AddOns getAddOns() {
        return this.addOns;
    }

    public void setAddOns(final AddOns addOns) {
        this.addOns = addOns;
    }

    public RevenueScheduleType getSetupFeeRevenueScheduleType() {
        return setupFeeRevenueScheduleType;
    }

    public void setSetupFeeRevenueScheduleType(final String setupFeeRevenueScheduleType) {
        this.setupFeeRevenueScheduleType = RevenueScheduleType.valueOf(setupFeeRevenueScheduleType.toUpperCase());
    }

    public RevenueScheduleType getRevenueScheduleType() {
        return revenueScheduleType;
    }

    public void setRevenueScheduleType(final String revenueScheduleType) {
        this.revenueScheduleType = RevenueScheduleType.valueOf(revenueScheduleType.toUpperCase());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Plan");
        sb.append("{addOns=").append(addOns);
        sb.append(", planCode='").append(planCode).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", successLink='").append(successLink).append('\'');
        sb.append(", cancelLink='").append(cancelLink).append('\'');
        sb.append(", displayDonationAmounts=").append(displayDonationAmounts);
        sb.append(", displayQuantity=").append(displayQuantity);
        sb.append(", displayPhoneNumber=").append(displayPhoneNumber);
        sb.append(", bypassHostedConfirmation=").append(bypassHostedConfirmation);
        sb.append(", unitName='").append(unitName).append('\'');
        sb.append(", planIntervalUnit='").append(planIntervalUnit).append('\'');
        sb.append(", planIntervalLength=").append(planIntervalLength);
        sb.append(", trialIntervalLength=").append(trialIntervalLength);
        sb.append(", trialIntervalUnit='").append(trialIntervalUnit).append('\'');
        sb.append(", accountingCode='").append(accountingCode).append('\'');
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", unitAmountInCents=").append(unitAmountInCents);
        sb.append(", setupFeeInCents=").append(setupFeeInCents);
        sb.append(", revenueScheduleType=").append(revenueScheduleType);
        sb.append(", setupFeeRevenueScheduleType=").append(setupFeeRevenueScheduleType);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Plan plan = (Plan) o;

        if (bypassHostedConfirmation != plan.bypassHostedConfirmation) {
            return false;
        }
        if (displayPhoneNumber != plan.displayPhoneNumber) {
            return false;
        }
        if (accountingCode != null ? !accountingCode.equals(plan.accountingCode) : plan.accountingCode != null) {
            return false;
        }
        if (addOns != null ? !addOns.equals(plan.addOns) : plan.addOns != null) {
            return false;
        }
        if (cancelLink != null ? !cancelLink.equals(plan.cancelLink) : plan.cancelLink != null) {
            return false;
        }
        if (createdAt != null ? createdAt.compareTo(plan.createdAt) != 0: plan.createdAt != null) {
            return false;
        }
        if (description != null ? !description.equals(plan.description) : plan.description != null) {
            return false;
        }
        if (displayDonationAmounts != null ? !displayDonationAmounts.equals(plan.displayDonationAmounts) : plan.displayDonationAmounts != null) {
            return false;
        }
        if (displayQuantity != null ? !displayQuantity.equals(plan.displayQuantity) : plan.displayQuantity != null) {
            return false;
        }
        if (name != null ? !name.equals(plan.name) : plan.name != null) {
            return false;
        }
        if (planCode != null ? !planCode.equals(plan.planCode) : plan.planCode != null) {
            return false;
        }
        if (planIntervalLength != null ? !planIntervalLength.equals(plan.planIntervalLength) : plan.planIntervalLength != null) {
            return false;
        }
        if (planIntervalUnit != null ? !planIntervalUnit.equals(plan.planIntervalUnit) : plan.planIntervalUnit != null) {
            return false;
        }
        if (revenueScheduleType != null ? !revenueScheduleType.equals(plan.revenueScheduleType) : plan.revenueScheduleType != null) {
            return false;
        }
        if (setupFeeInCents != null ? !setupFeeInCents.equals(plan.setupFeeInCents) : plan.setupFeeInCents != null) {
            return false;
        }
        if (setupFeeRevenueScheduleType != null ? !setupFeeRevenueScheduleType.equals(plan.setupFeeRevenueScheduleType) : plan.setupFeeRevenueScheduleType != null) {
            return false;
        }
        if (successLink != null ? !successLink.equals(plan.successLink) : plan.successLink != null) {
            return false;
        }
        if (trialIntervalLength != null ? !trialIntervalLength.equals(plan.trialIntervalLength) : plan.trialIntervalLength != null) {
            return false;
        }
        if (trialIntervalUnit != null ? !trialIntervalUnit.equals(plan.trialIntervalUnit) : plan.trialIntervalUnit != null) {
            return false;
        }
        if (unitAmountInCents != null ? !unitAmountInCents.equals(plan.unitAmountInCents) : plan.unitAmountInCents != null) {
            return false;
        }
        if (unitName != null ? !unitName.equals(plan.unitName) : plan.unitName != null) {
            return false;
        }
        if (updatedAt != null ? updatedAt.compareTo(plan.updatedAt) != 0: plan.updatedAt != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
                addOns,
                planCode,
                name,
                description,
                successLink,
                cancelLink,
                displayDonationAmounts,
                displayQuantity,
                displayPhoneNumber,
                bypassHostedConfirmation,
                unitName,
                planIntervalUnit,
                planIntervalLength,
                trialIntervalUnit,
                trialIntervalLength,
                accountingCode,
                createdAt,
                updatedAt,
                unitAmountInCents,
                setupFeeInCents,
                revenueScheduleType,
                setupFeeRevenueScheduleType
        );
    }
}
