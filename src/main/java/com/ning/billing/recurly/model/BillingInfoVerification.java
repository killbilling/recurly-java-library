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

@XmlRootElement(name = "verify")
public class BillingInfoVerification extends RecurlyObject {

    @XmlElement(name = "gateway_code")
    private String gatewayCode;

    public String getGatewayCode() {
        return gatewayCode;
    }

    public void setGatewayCode(final Object gatewayCode) {
        this.gatewayCode = stringOrNull(gatewayCode);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Verify");
        sb.append("{gatewayCode=").append(gatewayCode);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final BillingInfoVerification that = (BillingInfoVerification) o;

        if (gatewayCode != null ? !gatewayCode.equals(that.gatewayCode) : that.gatewayCode != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
                gatewayCode
        );
    }

}
