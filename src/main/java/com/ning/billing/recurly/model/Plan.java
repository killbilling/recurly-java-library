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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import java.util.List;

import org.joda.time.DateTime;

@XmlRootElement(name = "plan")
public class Plan extends RecurlyObject {
    @XmlTransient
    public static final String PLANS_RESOURCE = "/plans";

    /////////////////////////////////////////////////////
    // Attributes...

    @XmlElementWrapper(name = "add_ons")
    @XmlElement(name = "add_on")
    private List<AddOn> addOns;

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
    private boolean displayDonationAmounts;

    @XmlElement(name = "display_quantity")
    private boolean displayQuantity;

    // For some reason these don't work...
    // @XmlElement(name = "display_phone_number")
    // private boolean displayPhoneNumber;

    // @XmlElement(name = "bypass_hosted_confirmation")
    // private boolean bypassHostedConfirmation;

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

    @XmlElement(name = "created_at")
    private DateTime createdAt;

    @XmlElement(name = "unit_amount_in_cents")
    private RecurlyUnitCurrency unitAmountInCents;

    @XmlElement(name = "setup_fee_in_cents")
    private RecurlyUnitCurrency setupFeeInCents;

    /////////////////////////////////////////////////////
    // Getters & Setters

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

    public boolean getDisplayDonationAmounts() {
        return displayDonationAmounts;
    }

    public void setDisplayDonationAmounts(boolean displayAmounts) {
        this.displayDonationAmounts = displayAmounts;
    }

    public boolean getDisplayQuantity() {
        return displayQuantity;
    }

    public void setDisplayQuantity(boolean displayQuantity) {
        this.displayQuantity = displayQuantity;
    }

    // public boolean getDisplayPhoneNumber() {
    //     return displayPhoneNumber;
    // }

    // public void setDisplayPhoneNumber(boolean displayPhoneNumber) {
    //     this.displayPhoneNumber = displayPhoneNumber;
    // }

    // public boolean getBypassHostedConfirmation() {
    //     return bypassHostedConfirmation;
    // }

    // public void setBypassHostedConfirmation(boolean bypassHostedConfirmation) {
    //     this.bypassHostedConfirmation = bypassHostedConfirmation;
    // }

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

    public RecurlyUnitCurrency getUnitAmountInCents() {
        return unitAmountInCents;
    }

    public void setUnitAmountInCents(final RecurlyUnitCurrency unitAmountInCents) {
        this.unitAmountInCents = unitAmountInCents;
    }

    public RecurlyUnitCurrency getSetupFeeInCents() {
        return setupFeeInCents;
    }

    public void setSetupFeeInCents(final RecurlyUnitCurrency setupFeeInCents) {
        this.setupFeeInCents = setupFeeInCents;
    }

    public List<AddOn> getAddOns() {
        return this.addOns;
    }

    public void setAddOns(final List<AddOn> addOns) {
        this.addOns = addOns;
    }

    /////////////////////////////////////////////////////
    // Other

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Plan");
        sb.append("{planCode='").append(planCode).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", successLink='").append(successLink).append('\'');
        sb.append(", cancelLink='").append(cancelLink).append('\'');
        sb.append(", displayDonationAmounts='").append(displayDonationAmounts).append('\'');
        sb.append(", displayQuantity='").append(displayQuantity).append('\'');
        // sb.append(", displayPhoneNumber='").append(displayPhoneNumber).append('\'');
        // sb.append(", bypassHostedConfirmation='").append(bypassHostedConfirmation).append('\'');
        sb.append(", unitName='").append(unitName).append('\'');
        sb.append(", planIntervalUnit='").append(planIntervalUnit).append('\'');
        sb.append(", planIntervalLength='").append(planIntervalLength).append('\'');
        sb.append(", trialIntervalUnit='").append(trialIntervalUnit).append('\'');
        sb.append(", trialIntervalLength='").append(trialIntervalLength).append('\'');
        sb.append(", accountingCode='").append(accountingCode).append('\'');
        sb.append(", createdAt='").append(createdAt).append('\'');
        sb.append(", unitAmountInCents='").append(unitAmountInCents).append('\'');
        sb.append(", setupFeeInCents='").append(setupFeeInCents).append('\'');
        sb.append(", addOns='").append(addOns).append('\'');
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

        final Plan plan = (Plan) o;

        if (name != null ? !name.equals(plan.name) : plan.name != null) {
            return false;
        }
        if (planCode != null ? !planCode.equals(plan.planCode) : plan.planCode != null) {
            return false;
        }
        if (description != null ? !description.equals(plan.description) : plan.description != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = planCode != null ? planCode.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    ////////////////////////////////////////////////////////////////////
    //
    //@XmlRootElement(name = "unit_amount_in_cents")
    @JsonIgnoreProperties(ignoreUnknown = true)
    protected static class RecurlyUnitCurrency {
        @XmlElement(name = "EUR")
        @XmlValue
        private Integer unitAmountEUR;

        @XmlElement(name = "GBP")
        @XmlValue
        private Integer unitAmountGBP;

        @XmlElement(name = "USD")
        @XmlValue
        private Integer unitAmountUSD;

        @XmlElement(name = "SEK")
        @XmlValue
        private Integer unitAmountSEK;

        public void setUnitAmountEUR(final Object unitAmountEUR) {
            this.unitAmountEUR = integerOrNull(unitAmountEUR);
        }

        public Integer getUnitAmountEUR() {
            return this.unitAmountEUR;
        }

        public void setUnitAmountGBP(final Object unitAmountGBP) {
            this.unitAmountGBP = integerOrNull(unitAmountGBP);
        }

        public Integer getUnitAmountGBP() {
            return this.unitAmountGBP;
        }

        public void setUnitAmountUSD(final Object unitAmountUSD) {
            this.unitAmountUSD = integerOrNull(unitAmountUSD);
        }

        public Integer getUnitAmountUSD() {
            return this.unitAmountUSD;
        }

        public void setUnitAmountSEK(final Object unitAmountSEK) {
            this.unitAmountSEK = integerOrNull(unitAmountSEK);
        }

        public Integer getUnitAmountSEK() {
            return this.unitAmountSEK;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append("UnitAmountInCents");
            sb.append("{amount-in-EUR='").append(unitAmountEUR).append('\'');
            sb.append(",amount-in-GBP='").append(unitAmountGBP).append('\'');
            sb.append(",amount-in-USD='").append(unitAmountUSD).append('\'');
            sb.append(",amount-in-SEK='").append(unitAmountSEK).append('\'');
            sb.append('}');
            return sb.toString();
        }

    }

}
