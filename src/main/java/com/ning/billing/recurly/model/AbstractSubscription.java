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

@XmlRootElement(name = "subscription")
public class AbstractSubscription extends RecurlyObject {

    public static final String SUBSCRIPTION_RESOURCE = "/subscriptions";

    @XmlElement(name = "unit_amount_in_cents")
    protected Integer unitAmountInCents;

    @XmlElement(name = "quantity")
    protected Integer quantity;

    @XmlElementWrapper(name = "subscription_add_ons")
    @XmlElement(name = "subscription_add_on")
    protected SubscriptionAddOns addOns;

    @XmlElementWrapper(name = "custom_fields")
    @XmlElement(name = "custom_field")
    protected CustomFields customFields;

    @XmlElement(name = "plan_code")
    private String planCode;

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(final String planCode) {
        this.planCode = stringOrNull(planCode);
    }

    public CustomFields getCustomFields() {
        return customFields;
    }

    public void setCustomFields(final CustomFields customFields) {
        this.customFields = customFields;
    }

    public Integer getUnitAmountInCents() {
        return unitAmountInCents;
    }

    public void setUnitAmountInCents(final Object unitAmountInCents) {
        this.unitAmountInCents = integerOrNull(unitAmountInCents);
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(final Object quantity) {
        this.quantity = integerOrNull(quantity);
    }

    public SubscriptionAddOns getAddOns() {
        return addOns;
    }

    public void setAddOns(final SubscriptionAddOns addOns) {
        this.addOns = addOns;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final AbstractSubscription that = (AbstractSubscription) o;

        if (addOns != null ? !addOns.equals(that.addOns) : that.addOns != null) {
            return false;
        }
        if (planCode != null ? !planCode.equals(that.planCode) : that.planCode != null) {
            return false;
        }
        if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) {
            return false;
        }
        if (unitAmountInCents != null ? !unitAmountInCents.equals(that.unitAmountInCents) : that.unitAmountInCents != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
                unitAmountInCents,
                quantity,
                addOns,
                planCode,
                customFields
        );
    }
}
