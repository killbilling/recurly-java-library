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
import javax.xml.bind.annotation.XmlTransient;

import org.joda.time.DateTime;
import com.google.common.base.Objects;

@XmlRootElement(name = "add_on")
public class AddOn extends AbstractAddOn {

    @XmlTransient
    public static final String ADDONS_RESOURCE = "/add_ons";

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "display_quantity_on_hosted_page")
    private Boolean displayQuantityOnHostedPage;

    @XmlElement(name = "default_quantity")
    private Integer defaultQuantity;

    @XmlElement(name = "unit_amount_in_cents")
    private RecurlyUnitCurrency unitAmountInCents;

    @XmlElement(name = "created_at")
    private DateTime createdAt;

    public String getName() {
        return name;
    }

    public void setName(final Object name) {
        this.name = stringOrNull(name);
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

    public void setUnitAmountInCents(final RecurlyUnitCurrency unitAmountInCents) {
        this.unitAmountInCents = unitAmountInCents;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final Object createdAt) {
        this.createdAt = dateTimeOrNull(createdAt);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AddOn{");
        sb.append("name='").append(name).append('\'');
        sb.append(", displayQuantityOnHostedPage=").append(displayQuantityOnHostedPage);
        sb.append(", defaultQuantity=").append(defaultQuantity);
        sb.append(", unitAmountInCents=").append(unitAmountInCents);
        sb.append(", createdAt=").append(createdAt);
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

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
                name,
                displayQuantityOnHostedPage,
                defaultQuantity,
                unitAmountInCents,
                createdAt
        );
    }
}
