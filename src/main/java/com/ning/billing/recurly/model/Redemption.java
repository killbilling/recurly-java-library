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

import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.google.common.base.Objects;

@XmlRootElement(name = "redemption")
public class Redemption extends RecurlyObject {

    @XmlTransient
    public static final String REDEEM_RESOURCE = "/redeem";

    @XmlTransient
    public static final String REDEMPTION_RESOURCE = "/redemption";

    @XmlTransient
    public static final String REDEMPTIONS_RESOURCE = "/redemptions";

    @XmlElement(name = "uuid")
    private String uuid;

    @XmlElement(name = "account_code")
    private String accountCode;

    @XmlElement(name = "subscription_uuid")
    private String subscriptionUuid;

    @XmlElement(name = "coupon")
    private Coupon coupon;

    @XmlElement(name = "account")
    private Account account;

    @XmlElement(name = "single_use")
    private Boolean singleUse;

    @XmlElement(name = "total_discounted_in_cents")
    private Integer totalDiscountedInCents;

    @XmlElement(name = "currency")
    private String currency;

    @XmlElement(name = "state")
    private String state;

    @XmlElement(name = "created_at")
    private DateTime createdAt;

    @XmlElement(name = "updated_at")
    private DateTime updatedAt;

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(final Object accountCode) {
        this.accountCode = stringOrNull(accountCode);
    }

    public String getSubscriptionUuid() {
        return subscriptionUuid;
    }

    public void setSubscriptionUuid(final Object subscriptionUuid) {
        this.subscriptionUuid = stringOrNull(subscriptionUuid);
    }

    public Coupon getCoupon() {
        if (coupon != null && coupon.getCouponCode() == null) {
            coupon = fetch(coupon, Coupon.class);
        }
        return coupon;
    }

    public void setCoupon(final Coupon coupon) {
        this.coupon = coupon;
    }

    public Account getAccount() {
        if (account != null && account.getCreatedAt() == null) {
            account = fetch(account, Account.class);
        }
        return account;
    }

    public void setAccount(final Account account) {
        this.account = account;
    }

    public Boolean getSingleUse() {
        return singleUse;
    }

    public void setSingleUse(final Object singleUse) {
        this.singleUse = booleanOrNull(singleUse);
    }

    public Integer getTotalDiscountedInCents() {
        return totalDiscountedInCents;
    }

    public void setTotalDiscountedInCents(final Object totalDiscountedInCents) {
        this.totalDiscountedInCents = integerOrNull(totalDiscountedInCents);
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(final Object currency) {
        this.currency = stringOrNull(currency);
    }

    public String getState() {
        return state;
    }

    public void setState(final Object state) {
        this.state = stringOrNull(state);
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(final Object uuid) {
        this.uuid = stringOrNull(uuid);
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final Object createdAt) {
        this.createdAt = dateTimeOrNull(createdAt);
    }

    public DateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(final Object updatedAt) {
        this.updatedAt = dateTimeOrNull(updatedAt);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Redemption");
        sb.append("{accountCode=").append(accountCode);
        sb.append(", subscriptionUuid=").append(subscriptionUuid);
        sb.append(", coupon=").append(coupon);
        sb.append(", account=").append(account);
        sb.append(", uuid=").append(uuid);
        sb.append(", single_use=").append(singleUse);
        sb.append(", totalDiscountedInCents=").append(totalDiscountedInCents);
        sb.append(", currency='").append(currency).append('\'');
        sb.append(", state='").append(state).append('\'');
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Redemption that = (Redemption) o;

        if (accountCode != null ? !accountCode.equals(that.accountCode) : that.accountCode != null) {
            return false;
        }
        if (subscriptionUuid != null ? !subscriptionUuid.equals(that.subscriptionUuid) : that.subscriptionUuid != null) {
            return false;
        }
        if (coupon != null ? !coupon.equals(that.coupon) : that.coupon != null) {
            return false;
        }
        if (account != null ? !account.equals(that.account) : that.account != null) {
            return false;
        }
        if (singleUse != null ? !singleUse.equals(that.singleUse) : that.singleUse != null) {
            return false;
        }
        if (totalDiscountedInCents != null ?
                !totalDiscountedInCents.equals(that.totalDiscountedInCents) : that.totalDiscountedInCents != null) {
            return false;
        }
        if (currency != null ? !currency.equals(that.currency) : that.currency != null) {
            return false;
        }
        if (state != null ? !state.equals(that.state) : that.state != null) {
            return false;
        }
        if (createdAt != null ? createdAt.compareTo(that.createdAt) != 0 : that.createdAt != null) {
            return false;
        }
        if (updatedAt != null ? updatedAt.compareTo(that.updatedAt) != 0 : that.updatedAt != null) {
            return false;
        }
        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
                accountCode,
                subscriptionUuid,
                coupon,
                account,
                singleUse,
                totalDiscountedInCents,
                currency,
                state,
                uuid,
                createdAt,
                updatedAt
        );
    }

}
