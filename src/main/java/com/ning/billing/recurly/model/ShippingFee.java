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

import com.google.common.base.Objects;

@XmlRootElement(name = "shipping_fee")
public class ShippingFee extends RecurlyObject {

    @XmlElement(name = "shipping_method_code")
    private String shippingMethodCode;

    @XmlElement(name = "shipping_amount_in_cents")
    private Integer shippingAmountInCents;

    @XmlElement(name = "shipping_address_id")
    private Long shippingAddressId;

    @XmlElement(name = "shipping_address")
    private ShippingAddress shippingAddress;

    public String getShippingMethodCode() {
        return shippingMethodCode;
    }

    public void setShippingMethodCode(final Object shippingMethodCode) {
        this.shippingMethodCode = stringOrNull(shippingMethodCode);
    }

    public Integer getShippingAmountInCents() {
        return shippingAmountInCents;
    }

    public void setShippingAmountInCents(final Object shippingAmountInCents) {
        this.shippingAmountInCents = integerOrNull(shippingAmountInCents);
    }

    public Long getShippingAddressId() {
        return shippingAddressId;
    }

    public void setShippingAddressId(final Object shippingAddressId) {
        this.shippingAddressId = longOrNull(shippingAddressId);
    }

    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(final ShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("ShippingFee");
        sb.append("{shippingMethodCode=").append(shippingMethodCode);
        sb.append(", shippingAmountInCents=").append(shippingAmountInCents);
        sb.append(", shippingAddressId=").append(shippingAddressId);
        sb.append(", shippingAddress=").append(shippingAddress);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final ShippingFee that = (ShippingFee) o;

        if (shippingMethodCode != null ? !shippingMethodCode.equals(that.shippingMethodCode) : that.shippingMethodCode != null) {
            return false;
        }
        if (shippingAmountInCents != null ? !shippingAmountInCents.equals(that.shippingAmountInCents) : that.shippingAmountInCents != null) {
            return false;
        }
        if (shippingAddressId != null ? !shippingAddressId.equals(that.shippingAddressId) : that.shippingAddressId != null) {
            return false;
        }
        if (shippingAddress != null ? !shippingAddress.equals(that.shippingAddress) : that.shippingAddress != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
            shippingMethodCode,
            shippingAmountInCents,
            shippingAddressId,
            shippingAddress
        );
    }
}