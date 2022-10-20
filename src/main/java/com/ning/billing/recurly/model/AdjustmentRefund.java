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
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement(name = "adjustment")
public class AdjustmentRefund extends RecurlyObject {

    @XmlElement(name = "uuid")
    private String uuid;

    @XmlElement(name = "quantity")
    private Integer quantity;

    @XmlElement(name = "quantity_decimal")
    private BigDecimal quantityDecimal;

    @XmlElement(name = "prorate")
    private Boolean prorate;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(final Object uuid) {
        this.uuid = stringOrNull(uuid);
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(final Object quantity) {
        this.quantity = integerOrNull(quantity);
    }

    public BigDecimal getQuantityDecimal() {
        return quantityDecimal;
    }

    public void setQuantityDecimal(final Object quantityDecimal) {
        this.quantityDecimal = bigDecimalOrNull(quantityDecimal);
    }

    public Boolean getProrate() {
        return prorate;
    }

    public void setProrate(final Object prorate) {
        this.prorate = booleanOrNull(prorate);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("AdjustmentRefund");
        sb.append("{uuid='").append(uuid).append('\'');
        sb.append(", quantity=").append(quantity);
        sb.append(", quantity_decimal=").append(quantityDecimal);
        sb.append(", prorate=").append(prorate);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final AdjustmentRefund that = (AdjustmentRefund) o;

        if (prorate != null ? !prorate.equals(that.prorate) : that.prorate != null) {
            return false;
        }
        if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) {
            return false;
        }
        if (quantityDecimal != null ? !quantityDecimal.equals(that.quantityDecimal) : that.quantityDecimal != null) {
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
                prorate,
                quantity,
                quantityDecimal,
                uuid
        );
    }
}
