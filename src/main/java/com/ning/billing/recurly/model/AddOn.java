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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.joda.time.DateTime;

@XmlRootElement(name = "add_on")
public class AddOn extends RecurlyObject {

    @XmlTransient
    public static final String ADDONS_RESOURCE = "/add_ons";

    @XmlElement(name = "add_on_code")
    private String addOnCode;

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "display_quantity_on_hosted_page")
    private Boolean displayQuantityOnHostedPage;

    @XmlElement(name = "defaultQuantity")
    private Integer defaultQuantity;

    @XmlElement(name = "unit_amount_in_cents")
    private Plan.RecurlyUnitCurrency unitAmountInCents;

    @XmlElement(name = "createdAt")
    private DateTime createdAt;

    @XmlElement(name = "quantity")
    private Integer quantity;

    public String getAddOnCode() {
        return addOnCode;
    }

    public void setAddOnCode(final Object addOnCode) {
        this.addOnCode = stringOrNull(addOnCode);
    }

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

    public Plan.RecurlyUnitCurrency getUnitAmountInCents() {
        return unitAmountInCents;
    }

    public void setUnitAmountInCents(final Plan.RecurlyUnitCurrency unitAmountInCents) {
        this.unitAmountInCents = unitAmountInCents;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final Object createdAt) {
        this.createdAt = dateTimeOrNull(createdAt);
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(final Object quantity) {
        this.quantity = integerOrNull(quantity);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("AddOn");
        sb.append("{addOnCode='").append(addOnCode).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", displayQuantityOnHostedPage=").append(displayQuantityOnHostedPage);
        sb.append(", defaultQuantity=").append(defaultQuantity);
        sb.append(", unitAmountInCents=").append(unitAmountInCents);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", quantity=").append(quantity);
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

        final AddOn addOn = (AddOn) o;

        if (addOnCode != null ? !addOnCode.equals(addOn.addOnCode) : addOn.addOnCode != null) {
            return false;
        }
        if (createdAt != null ? !createdAt.equals(addOn.createdAt) : addOn.createdAt != null) {
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

        if (quantity != null ? !quantity.equals(addOn.quantity) : addOn.quantity != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = addOnCode != null ? addOnCode.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (displayQuantityOnHostedPage != null ? displayQuantityOnHostedPage.hashCode() : 0);
        result = 31 * result + (defaultQuantity != null ? defaultQuantity.hashCode() : 0);
        result = 31 * result + (unitAmountInCents != null ? unitAmountInCents.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        return result;
    }
}
