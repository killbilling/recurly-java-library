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

import org.joda.time.DateTime;

@XmlRootElement(name = "subscription")
public class Subscription extends AbstractSubscription {

    @XmlElement(name = "account")
    private Account account;
	
	@XmlElement(name = "invoice")
	private Invoice invoice;

    @XmlElement(name = "plan")
    private Plan plan;

    @XmlElement(name = "uuid")
    private String uuid;

    @XmlElement(name = "state", required = false)
    private String state;

    @XmlElement(name = "currency")
    private String currency;

    @XmlElement(name = "activated_at")
    private DateTime activatedAt;

    @XmlElement(name = "canceled_at")
    private DateTime canceledAt;

    @XmlElement(name = "expires_at")
    private DateTime expiresAt;

    @XmlElement(name = "current_period_started_at")
    private DateTime currentPeriodStartedAt;

    @XmlElement(name = "current_period_ends_at")
    private DateTime currentPeriodEndsAt;

    @XmlElement(name = "trial_started_at")
    private DateTime trialStartedAt;

    @XmlElement(name = "trial_ends_at")
    private DateTime trialEndsAt;

    @XmlElement(name = "pending_subscription")
    private Subscription pendingSubscription;

    @XmlElement(name = "starts_at")
    private DateTime startsAt;

    @XmlElement(name = "collection_method")
    private String collectionMethod;

    @XmlElement(name = "net_terms")
    private Integer netTerms;

    //Purchase Order Number
    @XmlElement(name = "po_number")
    private String poNumber;
    
    @XmlElement(name = "first_renewal_date")
    private DateTime firstRenewalDate;

    public Account getAccount() {
        if (account != null && account.getCreatedAt() == null) {
            account = fetch(account, Account.class);
        }
        return account;
    }

    public void setAccount(final Account account) {
        this.account = account;
    }
	
    public Invoice getInvoice() {
        if (invoice != null && invoice.getCreatedAt() == null) {
            invoice = fetch(invoice, Invoice.class);
        }
        return invoice;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(final Object currency) {
        this.currency = stringOrNull(currency);
    }

    public DateTime getActivatedAt() {
        return activatedAt;
    }

    public void setActivatedAt(final Object activatedAt) {
        this.activatedAt = dateTimeOrNull(activatedAt);
    }

    public DateTime getCanceledAt() {
        return canceledAt;
    }

    public void setCanceledAt(final Object canceledAt) {
        this.canceledAt = dateTimeOrNull(canceledAt);
    }

    public DateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(final Object expiresAt) {
        this.expiresAt = dateTimeOrNull(expiresAt);
    }

    public DateTime getCurrentPeriodStartedAt() {
        return currentPeriodStartedAt;
    }

    public void setCurrentPeriodStartedAt(final Object currentPeriodStartedAt) {
        this.currentPeriodStartedAt = dateTimeOrNull(currentPeriodStartedAt);
    }

    public DateTime getCurrentPeriodEndsAt() {
        return currentPeriodEndsAt;
    }

    public void setCurrentPeriodEndsAt(final Object currentPeriodEndsAt) {
        this.currentPeriodEndsAt = dateTimeOrNull(currentPeriodEndsAt);
    }

    public DateTime getTrialStartedAt() {
        return trialStartedAt;
    }

    public void setTrialStartedAt(final Object trialStartedAt) {
        this.trialStartedAt = dateTimeOrNull(trialStartedAt);
    }

    public DateTime getTrialEndsAt() {
        return trialEndsAt;
    }

    public void setTrialEndsAt(final Object trialEndsAt) {
        this.trialEndsAt = dateTimeOrNull(trialEndsAt);
    }

    public Subscription getPendingSubscription() {
        return pendingSubscription;
    }

    public void setPendingSubscription(final Subscription pendingSubscription) {
        this.pendingSubscription = pendingSubscription;
    }

    public DateTime getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(final Object startsAt) {
        this.startsAt = dateTimeOrNull(startsAt);
    }


    public String getCollectionMethod() {
        return collectionMethod;
    }

    public void setCollectionMethod(Object collectionMethod) {
        this.collectionMethod = stringOrNull(collectionMethod);
    }

    public Integer getNetTerms() {
        return netTerms;
    }

    public void setNetTerms(final Object netTerms) {
        this.netTerms = integerOrNull(netTerms);
    }

    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(Object poNumber) {
        this.poNumber = stringOrNull(poNumber);
    }
    
    public DateTime getFirstRenewalDate() {
        return firstRenewalDate;
    }

    public void setFirstRenewalDate(final Object firstRenewalDate) {
        this.firstRenewalDate = dateTimeOrNull(firstRenewalDate);
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
        sb.append(", startsAt=").append(startsAt);
        sb.append(", addOns=").append(addOns);
        sb.append(", pendingSubscription=").append(pendingSubscription);
        sb.append(", firstRenewalDate=").append(firstRenewalDate);
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
        if (startsAt != null ? !startsAt.equals(that.startsAt) : that.startsAt != null) {
            return false;
        }
        if (pendingSubscription != null ? !pendingSubscription.equals(that.pendingSubscription) : that.pendingSubscription != null) {
            return false;
        }
        if (collectionMethod != null ? !collectionMethod.equals(that.collectionMethod) : that.collectionMethod != null) {
            return false;
        }

        if (netTerms != null ? !netTerms.equals(that.netTerms) : that.netTerms != null) {
            return false;
        }

        if (poNumber != null ? !poNumber.equals(that.poNumber) : that.poNumber != null) {
            return false;
        }
        
        if (firstRenewalDate != null ? !firstRenewalDate.equals(that.firstRenewalDate) : that.firstRenewalDate != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = account != null ? account.hashCode() : 0;
        result = 31 * result + (plan != null ? plan.hashCode() : 0);
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        //result = 31 * result + (state != null ? state.hashCode() : 0);
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
        result = 31 * result + (pendingSubscription != null ? pendingSubscription.hashCode() : 0);
        result = 31 * result + (startsAt != null ? startsAt.hashCode() : 0);
        result = 31 * result + (collectionMethod != null ? collectionMethod.hashCode() : 0);
        result = 31 * result + (netTerms != null ? netTerms.hashCode() : 0);
        result = 31 * result + (poNumber != null ? poNumber.hashCode() : 0);
        return result;
    }
}
