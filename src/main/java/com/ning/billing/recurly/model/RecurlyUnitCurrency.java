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

import java.util.Map;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RecurlyUnitCurrency {

    // United States Dollars
    @XmlElement(name = "USD")
    private Integer unitAmountUSD;

    // Australian Dollars
    @XmlElement(name = "AUD")
    private Integer unitAmountAUD;

    // Canadian Dollars
    @XmlElement(name = "CAD")
    private Integer unitAmountCAD;

    // Euros
    @XmlElement(name = "EUR")
    private Integer unitAmountEUR;

    // British Pounds
    @XmlElement(name = "GBP")
    private Integer unitAmountGBP;

    // Czech Korunas
    @XmlElement(name = "CZK")
    private Integer unitAmountCZK;

    // Danish Krones
    @XmlElement(name = "DKK")
    private Integer unitAmountDKK;

    // Hungarian Forints
    @XmlElement(name = "HUF")
    private Integer unitAmountHUF;

    // Norwegian Krones
    @XmlElement(name = "NOK")
    private Integer unitAmountNOK;

    // New Zealand Dollars
    @XmlElement(name = "NZD")
    private Integer unitAmountNZD;

    // Polish Zloty
    @XmlElement(name = "PLN")
    private Integer unitAmountPLN;

    // Singapore Dollars
    @XmlElement(name = "SGD")
    private Integer unitAmountSGD;

    // Swedish Kronas
    @XmlElement(name = "SEK")
    private Integer unitAmountSEK;

    // Swiss Francs
    @XmlElement(name = "CHF")
    private Integer unitAmountCHF;

    // South African Rand
    @XmlElement(name = "ZAR")
    private Integer unitAmountZAR;

    public static RecurlyUnitCurrency build(@Nullable final Object unitAmountInCents) {
        if (RecurlyObject.isNull(unitAmountInCents)) {
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
        this.unitAmountUSD = RecurlyObject.integerOrNull(unitAmountUSD);
    }

    public Integer getUnitAmountAUD() {
        return unitAmountAUD;
    }

    public void setUnitAmountAUD(final Object unitAmountAUD) {
        this.unitAmountAUD = RecurlyObject.integerOrNull(unitAmountAUD);
    }

    public Integer getUnitAmountCAD() {
        return unitAmountCAD;
    }

    public void setUnitAmountCAD(final Object unitAmountCAD) {
        this.unitAmountCAD = RecurlyObject.integerOrNull(unitAmountCAD);
    }

    public Integer getUnitAmountEUR() {
        return unitAmountEUR;
    }

    public void setUnitAmountEUR(final Object unitAmountEUR) {
        this.unitAmountEUR = RecurlyObject.integerOrNull(unitAmountEUR);
    }

    public Integer getUnitAmountGBP() {
        return unitAmountGBP;
    }

    public void setUnitAmountGBP(final Object unitAmountGBP) {
        this.unitAmountGBP = RecurlyObject.integerOrNull(unitAmountGBP);
    }

    public Integer getUnitAmountCZK() {
        return unitAmountCZK;
    }

    public void setUnitAmountCZK(final Object unitAmountCZK) {
        this.unitAmountCZK = RecurlyObject.integerOrNull(unitAmountCZK);
    }

    public Integer getUnitAmountDKK() {
        return unitAmountDKK;
    }

    public void setUnitAmountDKK(final Object unitAmountDKK) {
        this.unitAmountDKK = RecurlyObject.integerOrNull(unitAmountDKK);
    }

    public Integer getUnitAmountHUF() {
        return unitAmountHUF;
    }

    public void setUnitAmountHUF(final Object unitAmountHUF) {
        this.unitAmountHUF = RecurlyObject.integerOrNull(unitAmountHUF);
    }

    public Integer getUnitAmountNOK() {
        return unitAmountNOK;
    }

    public void setUnitAmountNOK(final Object unitAmountNOK) {
        this.unitAmountNOK = RecurlyObject.integerOrNull(unitAmountNOK);
    }

    public Integer getUnitAmountNZD() {
        return unitAmountNZD;
    }

    public void setUnitAmountNZD(final Object unitAmountNZD) {
        this.unitAmountNZD = RecurlyObject.integerOrNull(unitAmountNZD);
    }

    public Integer getUnitAmountPLN() {
        return unitAmountPLN;
    }

    public void setUnitAmountPLN(final Object unitAmountPLN) {
        this.unitAmountPLN = RecurlyObject.integerOrNull(unitAmountPLN);
    }

    public Integer getUnitAmountSGD() {
        return unitAmountSGD;
    }

    public void setUnitAmountSGD(final Object unitAmountSGD) {
        this.unitAmountSGD = RecurlyObject.integerOrNull(unitAmountSGD);
    }

    public Integer getUnitAmountSEK() {
        return unitAmountSEK;
    }

    public void setUnitAmountSEK(final Object unitAmountSEK) {
        this.unitAmountSEK = RecurlyObject.integerOrNull(unitAmountSEK);
    }

    public Integer getUnitAmountCHF() {
        return unitAmountCHF;
    }

    public void setUnitAmountCHF(final Object unitAmountCHF) {
        this.unitAmountCHF = RecurlyObject.integerOrNull(unitAmountCHF);
    }

    public Integer getUnitAmountZAR() {
        return unitAmountZAR;
    }

    public void setUnitAmountZAR(final Object unitAmountZAR) {
        this.unitAmountZAR = RecurlyObject.integerOrNull(unitAmountZAR);
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
