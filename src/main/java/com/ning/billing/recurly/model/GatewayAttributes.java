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
 * Class that represents Gateway Attributes in the Recurly API.
 */
@XmlRootElement(name = "gateway_attributes")
public class GatewayAttributes extends RecurlyObject {

    @XmlElement(name = "account_reference")
    private String accountReference;

    public String getAccountReference() {
        return accountReference;
    }

    public void setAccountReference(final Object accountReference) {
        this.accountReference = stringOrNull(accountReference);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("GatewayAttributes");
        sb.append("{ accountReference='").append(accountReference).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final GatewayAttributes gatewayAttributes = (GatewayAttributes) o;

        if (accountReference != null ? !accountReference.equals(gatewayAttributes.accountReference) : gatewayAttributes.accountReference != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
                accountReference
        );
    }
}
