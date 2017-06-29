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

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlList;

import com.google.common.base.Objects;

import java.util.List;

@XmlRootElement(name = "purchase")
public class Purchase extends RecurlyObject {
    @XmlTransient
    public static final String PURCHASES_ENDPOINT = "/purchases";

    @XmlElement(name = "currency")
    private String currency;

    @XmlElement(name = "collection_method")
    private String collectionMethod;

    @XmlElement(name = "po_number")
    private String poNumber;

    @XmlElement(name = "net_terms")
    private String netTerms;

    @XmlElement(name = "account")
    private Account account;

    @XmlElementWrapper(name = "adjustments")
    @XmlElement(name = "adjustment")
    private Adjustments adjustments;

    @XmlElementWrapper(name = "subscriptions")
    @XmlElement(name = "subscription")
    private Subscriptions subscriptions;

    @XmlElement(name = "gift_card")
    private GiftCard giftCard;

    @XmlList
    @XmlElementWrapper(name = "coupon_codes")
    @XmlElement(name = "coupon_code")
    private List<String> couponCodes;

    public void setAccount(final Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public void setAdjustments(final Adjustments adjustments) {
        this.adjustments = adjustments;
    }

    public Adjustments getAdjustments() {
        return adjustments;
    }

    public String getCollectionMethod() {
        return collectionMethod;
    }

    public void setCollectionMethod(final Object collectionMethod) {
        this.collectionMethod = stringOrNull(collectionMethod);
    }

    public void setCouponCodes(final List<String> couponCodes) {
        this.couponCodes = couponCodes;
    }

    public List<String> getCouponCodes() {
        return this.couponCodes;
    }

    public GiftCard getGiftCard() {
        return giftCard;
    }

    public void setGiftCard(final GiftCard giftCard) {
        this.giftCard = giftCard;
    }

    public String getNetTerms() {
        return netTerms;
    }

    public void setNetTerms(final Object terms) {
        this.netTerms = stringOrNull(terms);
    }

    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(final Object poNumber) {
        this.poNumber = stringOrNull(poNumber);
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(final Object currency) {
        this.currency = stringOrNull(currency);
    }

    public void setSubscriptions(final Subscriptions subscriptions) {
        this.subscriptions = subscriptions;
    }

    public Subscriptions getSubscriptions() {
        return subscriptions;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Purchase");
        sb.append("{account=").append(account);
        sb.append(", adjustments=").append(adjustments);
        sb.append(", collectionMethod='").append(collectionMethod).append('\'');
        sb.append(", currency='").append(currency).append('\'');
        sb.append(", poNumber='").append(poNumber).append('\'');
        sb.append(", netTerms='").append(netTerms).append('\'');
        sb.append(", giftCard='").append(giftCard).append('\'');
        sb.append(", subscriptions='").append(subscriptions).append('\'');
        sb.append(", couponCodes='").append(couponCodes).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Purchase purchase = (Purchase) o;

        if (account != null ? !account.equals(purchase.account) : purchase.account != null) {
            return false;
        }
        if (adjustments != null ? !adjustments.equals(purchase.adjustments) : purchase.adjustments != null) {
            return false;
        }
        if (collectionMethod != null ? !collectionMethod.equals(purchase.collectionMethod) : purchase.collectionMethod != null) {
            return false;
        }
        if (couponCodes != null ? !couponCodes.equals(purchase.couponCodes) : purchase.couponCodes != null) {
            return false;
        }
        if (currency != null ? !currency.equals(purchase.currency) : purchase.currency != null) {
            return false;
        }
        if (giftCard != null ? !giftCard.equals(purchase.giftCard) : purchase.giftCard != null) {
            return false;
        }
        if (poNumber != null ? !poNumber.equals(purchase.poNumber) : purchase.poNumber != null) {
            return false;
        }
        if (netTerms != null ? !netTerms.equals(purchase.netTerms) : purchase.netTerms != null) {
            return false;
        }
        if (subscriptions != null ? !subscriptions.equals(purchase.subscriptions) : purchase.subscriptions != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
                account,
                adjustments,
                collectionMethod,
                currency,
                giftCard,
                poNumber,
                netTerms,
                subscriptions,
                couponCodes
        );
    }

}


