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

import com.google.common.base.Objects;

import java.math.BigDecimal;

@XmlRootElement(name = "juris_detail")
public class JurisDetail extends RecurlyObject {

    @XmlElement(name = "description")
    private String description;

    @XmlElement(name = "jurisdiction")
    private String jurisdiction;

    @XmlElement(name = "jurisdiction_name")
    private String jurisdictionName;

    @XmlElement(name = "rate")
    private BigDecimal rate;

    @XmlElement(name = "tax_in_cents")
    private Integer taxInCents;

    @XmlElement(name = "sub_type")
    private String subType;

    public String getDescription() { return description; }

    public void setDescription(final Object description) { this.description = stringOrNull(description); }

    public String getJurisdiction() { return jurisdiction; }

    public void setJurisdiction(final Object jurisdiction) { this.jurisdiction = stringOrNull(jurisdiction); }

    public String getJurisdictionName() { return jurisdictionName; }

    public void setJurisdictionName(final Object jurisdictionName) { this.jurisdictionName = stringOrNull(jurisdictionName); }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(final Object rate) { this.rate = bigDecimalOrNull(rate); }

    public Integer getTaxInCents() {
        return taxInCents;
    }

    public void setTaxInCents(final Object taxInCents) {
        this.taxInCents = integerOrNull(taxInCents);
    }

    public String getSubtype() { return subType; }

    public void setSubType(final Object subType) { this.subType = stringOrNull(subType); }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("JurisDetail{");
        sb.append("description=").append(description);
        sb.append(", jurisdiction='").append(jurisdiction).append('\'');
        sb.append(", jurisdictionName='").append(jurisdictionName).append('\'');
        sb.append(", rate='").append(rate).append('\'');
        sb.append(", taxInCents='").append(taxInCents).append('\'');
        sb.append(", subType='").append(subType).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final JurisDetail jurisDetail = (JurisDetail) o;

        if (description != null ? !description.equals(jurisDetail.description) : jurisDetail.description != null) {
            return false;
        }
        if (jurisdiction != null ? !jurisdiction.equals(jurisDetail.jurisdiction) : jurisDetail.jurisdiction != null) {
            return false;
        }
        if (jurisdictionName != null ? !jurisdictionName.equals(jurisDetail.jurisdictionName) : jurisDetail.jurisdictionName != null) {
            return false;
        }
        if (taxInCents != null ? !taxInCents.equals(jurisDetail.taxInCents) : jurisDetail.taxInCents != null) {
            return false;
        }
        if (rate != null ? rate.compareTo(jurisDetail.rate) != 0 : jurisDetail.rate != null) {
            return false;
        }
        if (subType != null ? !subType.equals(jurisDetail.subType) : jurisDetail.subType != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
                description,
                jurisdiction,
                jurisdictionName,
                taxInCents,
                rate,
                subType
        );
    }
}