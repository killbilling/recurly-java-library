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

import javax.xml.bind.annotation.XmlElement;

/**
 * Subscription object for update calls.
 * <p>
 * The timeframe parameter is specific to the update.
 */
public class SubscriptionUpdate extends AbstractSubscription {

    public static enum Timeframe {
        now,
        renewal
    }

    @XmlElement
    private Timeframe timeframe;

    @XmlElement(name = "coupon_code")
    private String couponCode;

    @XmlElement(name = "collection_method")
    private String collectionMethod;

    @XmlElement(name = "shipping_address")
    private ShippingAddress shippingAddress;

    @XmlElement(name = "shipping_address_id")
    private Long shippingAddressId;

    public Timeframe getTimeframe() {
        return timeframe;
    }

    public void setTimeframe(final Timeframe timeframe) {
        this.timeframe = timeframe;
    }

    public String getCollectionMethod() {
        return collectionMethod;
    }

    public void setCollectionMethod(final Object collectionMethod) {
        this.collectionMethod = stringOrNull(collectionMethod);
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(final Object couponCode) {
        this.couponCode = stringOrNull(couponCode);
    }

    public void setShippingAddress(final ShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddressId(final Object shippingAddressId) {
        this.shippingAddressId = longOrNull(shippingAddressId);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final SubscriptionUpdate that = (SubscriptionUpdate) o;

        if (collectionMethod != null ? !collectionMethod.equals(that.collectionMethod) : that.collectionMethod != null) {
            return false;
        }
        if (timeframe != that.timeframe) {
            return false;
        }
        if (couponCode != null ? !couponCode.equals(that.couponCode) : that.couponCode != null) {
            return false;
        }
        if (shippingAddress != null ? !shippingAddress.equals(that.shippingAddress) : that.shippingAddress != null) {
            return false;
        }
        if (shippingAddressId != null ? !shippingAddressId.equals(that.shippingAddressId) : that.shippingAddressId != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
                timeframe,
                couponCode,
                collectionMethod,
                shippingAddress,
                shippingAddressId
        );
    }
}
