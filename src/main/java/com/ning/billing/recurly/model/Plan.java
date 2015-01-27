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

import java.util.Map;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

    @XmlElement(name = "created_at")
    private DateTime createdAt;
    
    @XmlElement(name = "tax_code")
    private String taxCode;

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

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(final Object taxCode) {
        this.taxCode = stringOrNull(taxCode);
    }

	public AddOns getAddOns() {
        return this.addOns;
    }

    public void setAddOns(final AddOns addOns) {
        this.addOns = addOns;
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
        sb.append(", unitAmountInCents=").append(unitAmountInCents);
        sb.append(", setupFeeInCents=").append(setupFeeInCents);
        sb.append(", taxCode=").append(taxCode);
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
        if (createdAt != null ? !createdAt.equals(plan.createdAt) : plan.createdAt != null) {
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
        if (setupFeeInCents != null ? !setupFeeInCents.equals(plan.setupFeeInCents) : plan.setupFeeInCents != null) {
            return false;
        }
        if (successLink != null ? !successLink.equals(plan.successLink) : plan.successLink != null) {
            return false;
        }
        if (taxCode != null ? !taxCode.equals(plan.taxCode) : plan.taxCode != null) {
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

        return true;
    }

    @Override
    public int hashCode() {
        int result = addOns != null ? addOns.hashCode() : 0;
        result = 31 * result + (planCode != null ? planCode.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (successLink != null ? successLink.hashCode() : 0);
        result = 31 * result + (cancelLink != null ? cancelLink.hashCode() : 0);
        result = 31 * result + (displayDonationAmounts != null ? displayDonationAmounts.hashCode() : 0);
        result = 31 * result + (displayQuantity != null ? displayQuantity.hashCode() : 0);
        result = 31 * result + (displayPhoneNumber ? 1 : 0);
        result = 31 * result + (bypassHostedConfirmation ? 1 : 0);
        result = 31 * result + (unitName != null ? unitName.hashCode() : 0);
        result = 31 * result + (planIntervalUnit != null ? planIntervalUnit.hashCode() : 0);
        result = 31 * result + (planIntervalLength != null ? planIntervalLength.hashCode() : 0);
        result = 31 * result + (taxCode != null ? taxCode.hashCode() : 0);
        result = 31 * result + (trialIntervalLength != null ? trialIntervalLength.hashCode() : 0);
        result = 31 * result + (trialIntervalUnit != null ? trialIntervalUnit.hashCode() : 0);
        result = 31 * result + (accountingCode != null ? accountingCode.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (unitAmountInCents != null ? unitAmountInCents.hashCode() : 0);
        result = 31 * result + (setupFeeInCents != null ? setupFeeInCents.hashCode() : 0);
        return result;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RecurlyUnitCurrency {

        // United States Dollars
        @XmlElement(name = "USD")
        @XmlValue
        private Integer unitAmountUSD;

        // Australian Dollars
        @XmlElement(name = "AUD")
        @XmlValue
        private Integer unitAmountAUD;

        // Canadian Dollars
        @XmlElement(name = "CAD")
        @XmlValue
        private Integer unitAmountCAD;

        // Euros
        @XmlElement(name = "EUR")
        @XmlValue
        private Integer unitAmountEUR;

        // British Pounds
        @XmlElement(name = "GBP")
        @XmlValue
        private Integer unitAmountGBP;

        // Czech Korunas
        @XmlElement(name = "CZK")
        @XmlValue
        private Integer unitAmountCZK;

        // Danish Krones
        @XmlElement(name = "DKK")
        @XmlValue
        private Integer unitAmountDKK;

        // Hungarian Forints
        @XmlElement(name = "HUF")
        @XmlValue
        private Integer unitAmountHUF;

        // Norwegian Krones
        @XmlElement(name = "NOK")
        @XmlValue
        private Integer unitAmountNOK;

        // New Zealand Dollars
        @XmlElement(name = "NZD")
        @XmlValue
        private Integer unitAmountNZD;

        // Polish Zloty
        @XmlElement(name = "PLN")
        @XmlValue
        private Integer unitAmountPLN;

        // Singapore Dollars
        @XmlElement(name = "SGD")
        @XmlValue
        private Integer unitAmountSGD;

        // Swedish Kronas
        @XmlElement(name = "SEK")
        @XmlValue
        private Integer unitAmountSEK;

        // Swiss Francs
        @XmlElement(name = "CHF")
        @XmlValue
        private Integer unitAmountCHF;

        // South African Rand
        @XmlElement(name = "ZAR")
        @XmlValue
        private Integer unitAmountZAR;

        public static RecurlyUnitCurrency build(@Nullable final Object unitAmountInCents) {
            if (isNull(unitAmountInCents)) {
                return null;
            }

            if (unitAmountInCents instanceof RecurlyUnitCurrency) {
                return (RecurlyUnitCurrency) unitAmountInCents;
            }

            final RecurlyUnitCurrency recurlyUnitCurrency = new RecurlyUnitCurrency();

            if (unitAmountInCents instanceof Map) {
                final Map amounts = (Map) unitAmountInCents;

                recurlyUnitCurrency.setUnitAmountUSD(amounts.get("USD"));
                recurlyUnitCurrency.setUnitAmountAUD(amounts.get("AUD"));
                recurlyUnitCurrency.setUnitAmountCAD(amounts.get("CAD"));
                recurlyUnitCurrency.setUnitAmountEUR(amounts.get("EUR"));
                recurlyUnitCurrency.setUnitAmountGBP(amounts.get("GBP"));
                recurlyUnitCurrency.setUnitAmountCZK(amounts.get("CZK"));
                recurlyUnitCurrency.setUnitAmountDKK(amounts.get("DKK"));
                recurlyUnitCurrency.setUnitAmountHUF(amounts.get("HUF"));
                recurlyUnitCurrency.setUnitAmountNOK(amounts.get("NOK"));
                recurlyUnitCurrency.setUnitAmountNZD(amounts.get("NZD"));
                recurlyUnitCurrency.setUnitAmountPLN(amounts.get("PLN"));
                recurlyUnitCurrency.setUnitAmountSGD(amounts.get("SGD"));
                recurlyUnitCurrency.setUnitAmountSEK(amounts.get("SEK"));
                recurlyUnitCurrency.setUnitAmountCHF(amounts.get("CHF"));
                recurlyUnitCurrency.setUnitAmountZAR(amounts.get("ZAR"));
            }

            return recurlyUnitCurrency;
        }

        public Integer getUnitAmountUSD() {
            return unitAmountUSD;
        }

        public void setUnitAmountUSD(final Object unitAmountUSD) {
            this.unitAmountUSD = integerOrNull(unitAmountUSD);
        }

        public Integer getUnitAmountAUD() {
            return unitAmountAUD;
        }

        public void setUnitAmountAUD(final Object unitAmountAUD) {
            this.unitAmountAUD = integerOrNull(unitAmountAUD);
        }

        public Integer getUnitAmountCAD() {
            return unitAmountCAD;
        }

        public void setUnitAmountCAD(final Object unitAmountCAD) {
            this.unitAmountCAD = integerOrNull(unitAmountCAD);
        }

        public Integer getUnitAmountEUR() {
            return unitAmountEUR;
        }

        public void setUnitAmountEUR(final Object unitAmountEUR) {
            this.unitAmountEUR = integerOrNull(unitAmountEUR);
        }

        public Integer getUnitAmountGBP() {
            return unitAmountGBP;
        }

        public void setUnitAmountGBP(final Object unitAmountGBP) {
            this.unitAmountGBP = integerOrNull(unitAmountGBP);
        }

        public Integer getUnitAmountCZK() {
            return unitAmountCZK;
        }

        public void setUnitAmountCZK(final Object unitAmountCZK) {
            this.unitAmountCZK = integerOrNull(unitAmountCZK);
        }

        public Integer getUnitAmountDKK() {
            return unitAmountDKK;
        }

        public void setUnitAmountDKK(final Object unitAmountDKK) {
            this.unitAmountDKK = integerOrNull(unitAmountDKK);
        }

        public Integer getUnitAmountHUF() {
            return unitAmountHUF;
        }

        public void setUnitAmountHUF(final Object unitAmountHUF) {
            this.unitAmountHUF = integerOrNull(unitAmountHUF);
        }

        public Integer getUnitAmountNOK() {
            return unitAmountNOK;
        }

        public void setUnitAmountNOK(final Object unitAmountNOK) {
            this.unitAmountNOK = integerOrNull(unitAmountNOK);
        }

        public Integer getUnitAmountNZD() {
            return unitAmountNZD;
        }

        public void setUnitAmountNZD(final Object unitAmountNZD) {
            this.unitAmountNZD = integerOrNull(unitAmountNZD);
        }

        public Integer getUnitAmountPLN() {
            return unitAmountPLN;
        }

        public void setUnitAmountPLN(final Object unitAmountPLN) {
            this.unitAmountPLN = integerOrNull(unitAmountPLN);
        }

        public Integer getUnitAmountSGD() {
            return unitAmountSGD;
        }

        public void setUnitAmountSGD(final Object unitAmountSGD) {
            this.unitAmountSGD = integerOrNull(unitAmountSGD);
        }

        public Integer getUnitAmountSEK() {
            return unitAmountSEK;
        }

        public void setUnitAmountSEK(final Object unitAmountSEK) {
            this.unitAmountSEK = integerOrNull(unitAmountSEK);
        }

        public Integer getUnitAmountCHF() {
            return unitAmountCHF;
        }

        public void setUnitAmountCHF(final Object unitAmountCHF) {
            this.unitAmountCHF = integerOrNull(unitAmountCHF);
        }

        public Integer getUnitAmountZAR() {
            return unitAmountZAR;
        }

        public void setUnitAmountZAR(final Object unitAmountZAR) {
            this.unitAmountZAR = integerOrNull(unitAmountZAR);
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append("RecurlyUnitCurrency");
            sb.append("{unitAmountUSD=").append(unitAmountUSD);
            sb.append(", unitAmountAUD=").append(unitAmountAUD);
            sb.append(", unitAmountCAD=").append(unitAmountCAD);
            sb.append(", unitAmountEUR=").append(unitAmountEUR);
            sb.append(", unitAmountGBP=").append(unitAmountGBP);
            sb.append(", unitAmountCZK=").append(unitAmountCZK);
            sb.append(", unitAmountDKK=").append(unitAmountDKK);
            sb.append(", unitAmountHUF=").append(unitAmountHUF);
            sb.append(", unitAmountNOK=").append(unitAmountNOK);
            sb.append(", unitAmountNZD=").append(unitAmountNZD);
            sb.append(", unitAmountPLN=").append(unitAmountPLN);
            sb.append(", unitAmountSGD=").append(unitAmountSGD);
            sb.append(", unitAmountSEK=").append(unitAmountSEK);
            sb.append(", unitAmountCHF=").append(unitAmountCHF);
            sb.append(", unitAmountZAR=").append(unitAmountZAR);
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

            final RecurlyUnitCurrency that = (RecurlyUnitCurrency) o;

            if (unitAmountAUD != null ? !unitAmountAUD.equals(that.unitAmountAUD) : that.unitAmountAUD != null) {
                return false;
            }
            if (unitAmountCAD != null ? !unitAmountCAD.equals(that.unitAmountCAD) : that.unitAmountCAD != null) {
                return false;
            }
            if (unitAmountCHF != null ? !unitAmountCHF.equals(that.unitAmountCHF) : that.unitAmountCHF != null) {
                return false;
            }
            if (unitAmountCZK != null ? !unitAmountCZK.equals(that.unitAmountCZK) : that.unitAmountCZK != null) {
                return false;
            }
            if (unitAmountDKK != null ? !unitAmountDKK.equals(that.unitAmountDKK) : that.unitAmountDKK != null) {
                return false;
            }
            if (unitAmountEUR != null ? !unitAmountEUR.equals(that.unitAmountEUR) : that.unitAmountEUR != null) {
                return false;
            }
            if (unitAmountGBP != null ? !unitAmountGBP.equals(that.unitAmountGBP) : that.unitAmountGBP != null) {
                return false;
            }
            if (unitAmountHUF != null ? !unitAmountHUF.equals(that.unitAmountHUF) : that.unitAmountHUF != null) {
                return false;
            }
            if (unitAmountNOK != null ? !unitAmountNOK.equals(that.unitAmountNOK) : that.unitAmountNOK != null) {
                return false;
            }
            if (unitAmountNZD != null ? !unitAmountNZD.equals(that.unitAmountNZD) : that.unitAmountNZD != null) {
                return false;
            }
            if (unitAmountPLN != null ? !unitAmountPLN.equals(that.unitAmountPLN) : that.unitAmountPLN != null) {
                return false;
            }
            if (unitAmountSEK != null ? !unitAmountSEK.equals(that.unitAmountSEK) : that.unitAmountSEK != null) {
                return false;
            }
            if (unitAmountSGD != null ? !unitAmountSGD.equals(that.unitAmountSGD) : that.unitAmountSGD != null) {
                return false;
            }
            if (unitAmountUSD != null ? !unitAmountUSD.equals(that.unitAmountUSD) : that.unitAmountUSD != null) {
                return false;
            }
            if (unitAmountZAR != null ? !unitAmountZAR.equals(that.unitAmountZAR) : that.unitAmountZAR != null) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            int result = unitAmountUSD != null ? unitAmountUSD.hashCode() : 0;
            result = 31 * result + (unitAmountAUD != null ? unitAmountAUD.hashCode() : 0);
            result = 31 * result + (unitAmountCAD != null ? unitAmountCAD.hashCode() : 0);
            result = 31 * result + (unitAmountEUR != null ? unitAmountEUR.hashCode() : 0);
            result = 31 * result + (unitAmountGBP != null ? unitAmountGBP.hashCode() : 0);
            result = 31 * result + (unitAmountCZK != null ? unitAmountCZK.hashCode() : 0);
            result = 31 * result + (unitAmountDKK != null ? unitAmountDKK.hashCode() : 0);
            result = 31 * result + (unitAmountHUF != null ? unitAmountHUF.hashCode() : 0);
            result = 31 * result + (unitAmountNOK != null ? unitAmountNOK.hashCode() : 0);
            result = 31 * result + (unitAmountNZD != null ? unitAmountNZD.hashCode() : 0);
            result = 31 * result + (unitAmountPLN != null ? unitAmountPLN.hashCode() : 0);
            result = 31 * result + (unitAmountSGD != null ? unitAmountSGD.hashCode() : 0);
            result = 31 * result + (unitAmountSEK != null ? unitAmountSEK.hashCode() : 0);
            result = 31 * result + (unitAmountCHF != null ? unitAmountCHF.hashCode() : 0);
            result = 31 * result + (unitAmountZAR != null ? unitAmountZAR.hashCode() : 0);
            return result;
        }
    }
}
