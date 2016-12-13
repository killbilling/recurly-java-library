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

import com.google.common.base.Objects;

@XmlRootElement(name = "tax_type")
public class TaxType extends RecurlyObject {

    @XmlElement(name = "description")
    private String description;

    @XmlElement(name = "tax_in_cents")
    private Integer taxInCents;

    @XmlElement(name = "type")
    private String type;

    @XmlElementWrapper(name = "juris_details")
    private JurisDetails jurisDetails;

    public String getDescription() { return description; }

    public void setDescription(final Object description) { this.description = stringOrNull(description); }

    public Integer getTaxInCents() {
        return taxInCents;
    }

    public void setTaxInCents(final Object taxInCents) {
        this.taxInCents = integerOrNull(taxInCents);
    }

    public String getType() { return type; }

    public void setType(final Object type) { this.type = stringOrNull(type); }

    public JurisDetails getJurisDetails() { return this.jurisDetails; }

    public void setJurisDetails(final JurisDetails jurisDetails) { this.jurisDetails = jurisDetails; }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TaxType{");
        sb.append("description=").append(description);
        sb.append(", taxInCents='").append(taxInCents).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", jurisDetails=").append(jurisDetails);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final TaxType taxType = (TaxType) o;

        if (description != null ? !description.equals(taxType.description) : taxType.description != null) {
            return false;
        }
        if (taxInCents != null ? !taxInCents.equals(taxType.taxInCents) : taxType.taxInCents != null) {
            return false;
        }
        if (type != null ? !type.equals(taxType.type) : taxType.type != null) {
            return false;
        }
        if (jurisDetails != null ? !jurisDetails.equals(taxType.jurisDetails) : taxType.jurisDetails != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
                description,
                taxInCents,
                type,
                jurisDetails
        );
    }
}