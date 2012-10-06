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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Class that represents the Concept of a Coupon within the Recurly API.
 */
@XmlRootElement(name = "coupon")
public class Coupon extends RecurlyObject {

    @XmlTransient
    public static final String COUPON_RESOURCE = "/coupons";

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "coupon_code")
    private String couponCode;

    @XmlElement(name = "discount_type")
    private String discountType;

    @XmlElement(name = "discount_percent")
    private String discountPercent;

    /**
     * Gets the name of the {@link Coupon}
     *
     * @return The {@link Coupon} name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the {@link Coupon}
     *
     * @param name The Name that is to be given to the {@link Coupon}
     */
    public void setName(final String name) {
        this.name = stringOrNull(name);
    }

    /**
     * Gets the coupon code for a {@link Coupon}
     *
     * @return The coupon code for the {@link Coupon}
     */
    public String getCouponCode() {
        return couponCode;
    }

    /**
     * Sets the coupon code for the {@link Coupon}
     *
     * @param couponCode The coupon code
     */
    public void setCouponCode(final String couponCode) {
        this.couponCode = stringOrNull(couponCode);
    }

    /**
     * Sets the discount type for a {@link Coupon}
     *
     * @param discountType A String of: 'percent'; 'dollars';
     */
    public void setDiscountType(final String discountType) {
        this.discountType = stringOrNull(discountType);
    }

    /**
     * Gets the discount type associated with the {@link Coupon}
     *
     * @return A String defining the discount type: 'percent' or 'dollars'.
     */
    public String getDiscountType() {
        return discountType;
    }

    /**
     * Gets the percentage discount for a coupon
     *
     * @return The percentage
     */
    public String getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(final String discountPercent) {
        this.discountPercent = stringOrNull(discountPercent);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Coupon");
        sb.append("{name='").append(name).append('\'');
        sb.append(", couponCode='").append(couponCode).append('\'');
        sb.append(", discountType='").append(discountType).append('\'');
        sb.append(", discountPercent='").append(discountPercent).append('\'');
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

        final Coupon coupon = (Coupon) o;

        if (couponCode != null ? !couponCode.equals(coupon.couponCode) : coupon.couponCode != null) {
            return false;
        }
        if (discountPercent != null ? !discountPercent.equals(coupon.discountPercent) : coupon.discountPercent != null) {
            return false;
        }
        if (discountType != null ? !discountType.equals(coupon.discountType) : coupon.discountType != null) {
            return false;
        }
        if (name != null ? !name.equals(coupon.name) : coupon.name != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (couponCode != null ? couponCode.hashCode() : 0);
        result = 31 * result + (discountType != null ? discountType.hashCode() : 0);
        result = 31 * result + (discountPercent != null ? discountPercent.hashCode() : 0);
        return result;
    }
}
