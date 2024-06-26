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

/**
* Class that represents ProrationSettings details in the Recurly API.
*/
@XmlRootElement(name = "proration_settings")
public class ProrationSettings extends RecurlyObject {

    @XmlElement(name = "credit")
    private String credit;

    @XmlElement(name = "charge")
    private String charge;

    public String getCharge() {
        return charge;
    }

    public void setCharge(final Object charge) {
      this.charge = stringOrNull(charge);
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(final Object credit) {
        this.credit = stringOrNull(credit);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("ProrationSettings");
        sb.append("{ charge='").append(charge).append('\'');
        sb.append(", credit='").append(credit).append('\'');
        sb.append(" }");
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final ProrationSettings proration_settings = (ProrationSettings) o;

        if (charge != null ? !charge.equals(proration_settings.charge) : proration_settings.charge != null) {
            return false;
        }

        if (credit != null ? !credit.equals(proration_settings.credit) : proration_settings.credit != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
            charge,
            credit
        );
    }
}
