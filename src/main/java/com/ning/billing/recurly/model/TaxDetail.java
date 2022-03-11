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

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "tax_detail")
public class TaxDetail extends RecurlyObject{

    @XmlElement(name = "name")
    protected String name;

    @XmlElement(name = "type")
    private String type;

    @XmlElement(name = "level")
    private String level;

    @XmlElement(name = "billable")
    private Boolean billable;

    @XmlElement(name = "tax_rate")
    private BigDecimal taxRate;

    @XmlElement(name = "tax_in_cents")
    private Integer taxInCents;

    @XmlElement(name = "tax_type")
    private String taxType;

    @XmlElement(name = "tax_region")
    private String taxRegion;

    public String getName() {
        return name;
    }

    public void setName(final Object name) {
        this.name = stringOrNull(name);
    }

    public String getType() {
        return type;
    }

    public void setType(final Object type) {
        this.type = stringOrNull(type);
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(final Object level) {
        this.level = stringOrNull(level);
    }

    public Boolean getBillable() {
        return billable;
    }

    public void setBillable(final Object billable) {
        this.billable = booleanOrNull(billable);
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(final Object taxRate) {
        this.taxRate = bigDecimalOrNull(taxRate);
    }

    public Integer getTaxInCents() {
        return taxInCents;
    }

    public void setTaxInCents(final Object taxInCents) {
        this.taxInCents = integerOrNull(taxInCents);
    }

    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(final Object taxType) {
        this.taxType = stringOrNull(taxType);
    }

    public String getTaxRegion() {
        return taxRegion;
    }

    public void setTaxRegion(final Object taxRegion) {
        this.taxRegion = stringOrNull(taxRegion);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("TaxDetail{");
        sb.append("name='").append(name).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", level='").append(level).append('\'');
        sb.append(", billable=").append(billable);
        sb.append(", taxRate=").append(taxRate);
        sb.append(", taxInCents=").append(taxInCents);
        sb.append(", taxType='").append(taxType).append('\'');
        sb.append(", taxRegion='").append(taxRegion).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final TaxDetail that = (TaxDetail) o;

        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        }
        if (type != null ? !type.equals(that.type) : that.type != null) {
            return false;
        }
        if (level != null ? !level.equals(that.level) : that.level != null) {
            return false;
        }
        if (billable != null ? !billable.equals(that.billable) : that.billable != null) {
            return false;
        }
        if (taxRate != null ? !taxRate.equals(that.taxRate) : that.taxRate != null) {
            return false;
        }
        if (taxInCents != null ? !taxInCents.equals(that.taxInCents) : that.taxInCents != null) {
            return false;
        }
        if (taxType != null ? !taxType.equals(that.taxType) : that.taxType != null) {
            return false;
        }
        if (taxRegion != null ? !taxRegion.equals(that.taxRegion) : that.taxRegion != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
            name,
            type,
            level,
            billable,
            taxRate,
            taxInCents,
            taxType,
            taxRegion
        );
    }

}
