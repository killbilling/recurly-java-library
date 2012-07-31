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

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "subscription")
public class Subscription extends RecurlyObject {

    @XmlElement(name = "account")
    private Account account;

    @XmlElement(name = "plan")
    private Plan plan;

    @XmlElement(name = "uuid")
    private String uuid;

    @XmlElement(name = "state")
    private String state;

    @XmlElement(name = "unit_amount_in_cents")
    private Integer unitAmountInCents;

    @XmlElement(name = "currency")
    private String currency;

    @XmlElement(name = "quantity")
    private Integer quantity;

    @XmlElement(name = "activated_at")
    private RecurlyDateTime activatedAt;

    @XmlElement(name = "canceled_at")
    private RecurlyDateTime canceledAt;

    @XmlElement(name = "expires_at")
    private RecurlyDateTime expiresAt;

    @XmlElement(name = "current_period_started_at")
    private RecurlyDateTime currentPeriodStartedAt;

    @XmlElement(name = "current_period_ends_at")
    private RecurlyDateTime currentPeriodEndsAt;

    @XmlElement(name = "trial_started_at")
    private RecurlyDateTime trialStartedAt;

    @XmlElement(name = "trial_ends_at")
    private RecurlyDateTime trialEndsAt;

    @XmlElementWrapper(name = "subscription_add_ons")
    @XmlElement(name = "subscription_add_on")
    private List<AddOn> addOns;

    public Account getAccount() {
        return account;
    }

    public void setAccount(final Account account) {
        this.account = account;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(final Plan plan) {
        this.plan = plan;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(final Object uuid) {
        this.uuid = stringOrNull(uuid);
    }

    public String getState() {
        return state;
    }

    public void setState(final Object state) {
        this.state = stringOrNull(state);
    }

    public Integer getUnitAmountInCents() {
        return unitAmountInCents;
    }

    public void setUnitAmountInCents(final Object unitAmountInCents) {
        this.unitAmountInCents = integerOrNull(unitAmountInCents);
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(final Object currency) {
        this.currency = stringOrNull(currency);
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(final Object quantity) {
        this.quantity = integerOrNull(quantity);
    }

    public RecurlyDateTime getActivatedAt() {
        return activatedAt;
    }

    public void setActivatedAt(final RecurlyDateTime activatedAt) {
        this.activatedAt = activatedAt;
    }

    public RecurlyDateTime getCanceledAt() {
        return canceledAt;
    }

    public void setCanceledAt(final RecurlyDateTime canceledAt) {
        this.canceledAt = canceledAt;
    }

    public RecurlyDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(final RecurlyDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public RecurlyDateTime getCurrentPeriodStartedAt() {
        return currentPeriodStartedAt;
    }

    public void setCurrentPeriodStartedAt(final RecurlyDateTime currentPeriodStartedAt) {
        this.currentPeriodStartedAt = currentPeriodStartedAt;
    }

    public RecurlyDateTime getCurrentPeriodEndsAt() {
        return currentPeriodEndsAt;
    }

    public void setCurrentPeriodEndsAt(final RecurlyDateTime currentPeriodEndsAt) {
        this.currentPeriodEndsAt = currentPeriodEndsAt;
    }

    public RecurlyDateTime getTrialStartedAt() {
        return trialStartedAt;
    }

    public void setTrialStartedAt(final RecurlyDateTime trialStartedAt) {
        this.trialStartedAt = trialStartedAt;
    }

    public RecurlyDateTime getTrialEndsAt() {
        return trialEndsAt;
    }

    public void setTrialEndsAt(final RecurlyDateTime trialEndsAt) {
        this.trialEndsAt = trialEndsAt;
    }

    public List<AddOn> getAddOns() {
        return addOns;
    }

    public void setAddOns(final List<AddOn> addOns) {
        this.addOns = addOns;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Subscription");
        sb.append("{account=").append(account);
        sb.append(", plan=").append(plan);
        sb.append(", uuid='").append(uuid).append('\'');
        sb.append(", state='").append(state).append('\'');
        sb.append(", unitAmountInCents=").append(unitAmountInCents);
        sb.append(", currency='").append(currency).append('\'');
        sb.append(", quantity=").append(quantity);
        sb.append(", activatedAt=").append(activatedAt);
        sb.append(", canceledAt=").append(canceledAt);
        sb.append(", expiresAt=").append(expiresAt);
        sb.append(", currentPeriodStartedAt=").append(currentPeriodStartedAt);
        sb.append(", currentPeriodEndsAt=").append(currentPeriodEndsAt);
        sb.append(", trialStartedAt=").append(trialStartedAt);
        sb.append(", trialEndsAt=").append(trialEndsAt);
        sb.append(", addOns=").append(addOns);
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

        final Subscription that = (Subscription) o;

        if (account != null ? !account.equals(that.account) : that.account != null) {
            return false;
        }
        if (activatedAt != null ? !activatedAt.equals(that.activatedAt) : that.activatedAt != null) {
            return false;
        }
        if (addOns != null ? !addOns.equals(that.addOns) : that.addOns != null) {
            return false;
        }
        if (canceledAt != null ? !canceledAt.equals(that.canceledAt) : that.canceledAt != null) {
            return false;
        }
        if (currency != null ? !currency.equals(that.currency) : that.currency != null) {
            return false;
        }
        if (currentPeriodEndsAt != null ? !currentPeriodEndsAt.equals(that.currentPeriodEndsAt) : that.currentPeriodEndsAt != null) {
            return false;
        }
        if (currentPeriodStartedAt != null ? !currentPeriodStartedAt.equals(that.currentPeriodStartedAt) : that.currentPeriodStartedAt != null) {
            return false;
        }
        if (expiresAt != null ? !expiresAt.equals(that.expiresAt) : that.expiresAt != null) {
            return false;
        }
        if (plan != null ? !plan.equals(that.plan) : that.plan != null) {
            return false;
        }
        if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) {
            return false;
        }
        if (state != null ? !state.equals(that.state) : that.state != null) {
            return false;
        }
        if (trialEndsAt != null ? !trialEndsAt.equals(that.trialEndsAt) : that.trialEndsAt != null) {
            return false;
        }
        if (trialStartedAt != null ? !trialStartedAt.equals(that.trialStartedAt) : that.trialStartedAt != null) {
            return false;
        }
        if (unitAmountInCents != null ? !unitAmountInCents.equals(that.unitAmountInCents) : that.unitAmountInCents != null) {
            return false;
        }
        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = account != null ? account.hashCode() : 0;
        result = 31 * result + (plan != null ? plan.hashCode() : 0);
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (unitAmountInCents != null ? unitAmountInCents.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (activatedAt != null ? activatedAt.hashCode() : 0);
        result = 31 * result + (canceledAt != null ? canceledAt.hashCode() : 0);
        result = 31 * result + (expiresAt != null ? expiresAt.hashCode() : 0);
        result = 31 * result + (currentPeriodStartedAt != null ? currentPeriodStartedAt.hashCode() : 0);
        result = 31 * result + (currentPeriodEndsAt != null ? currentPeriodEndsAt.hashCode() : 0);
        result = 31 * result + (trialStartedAt != null ? trialStartedAt.hashCode() : 0);
        result = 31 * result + (trialEndsAt != null ? trialEndsAt.hashCode() : 0);
        result = 31 * result + (addOns != null ? addOns.hashCode() : 0);
        return result;
    }
}
