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
import javax.xml.bind.annotation.XmlTransient;

import org.joda.time.DateTime;

import com.google.common.base.Objects;

@XmlRootElement(name = "shipping_method")
public class ShippingMethod extends RecurlyObject {

    @XmlTransient
    public static final String SHIPPING_METHOD_RESOURCE = "/shipping_methods";

    @XmlElement(name = "code")
    private String code;

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "accounting_code")
    private String accountingCode;

    @XmlElement(name = "tax_code")
    private String taxCode;

    @XmlElement(name = "liability_gl_account_id")
    private String liabilityGlAccountId;

    @XmlElement(name = "revenue_gl_account_id")
    private String revenueGlAccountId;

    @XmlElement(name = "performance_obligation_id")
    private String performanceObligationId;

    @XmlElement(name = "created_at")
    private DateTime createdAt;

    @XmlElement(name = "updated_at")
    private DateTime updatedAt;

    public String getCode() {
        return code;
    }

    public void setCode(final Object code) {
        this.code = stringOrNull(code);
    }

    public String getName() {
        return name;
    }

    public void setName(final Object name) {
        this.name = stringOrNull(name);
    }

    public String getAccountingCode() {
        return accountingCode;
    }

    public void setAccountingCode(final Object accountingCode) {
        this.accountingCode = stringOrNull(accountingCode);
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(final Object taxCode) {
        this.taxCode = stringOrNull(taxCode);
    }

    public String getLiabilityGlAccountId() {
        return liabilityGlAccountId;
    }

    public void setLiabilityGlAccountId(final Object liabilityGlAccountId) {
        this.liabilityGlAccountId = stringOrNull(liabilityGlAccountId);
    }

    public String getRevenueGlAccountId() {
        return revenueGlAccountId;
    }

    public void setRevenueGlAccountId(final Object revenueGlAccountId) {
        this.revenueGlAccountId = stringOrNull(revenueGlAccountId);
    }

    public String getPerformanceObligationId() {
        return performanceObligationId;
    }

    public void setPerformanceObligationId(final Object performanceObligationId) {
        this.performanceObligationId = stringOrNull(performanceObligationId);
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
        sb.append("ShippingMethod");
        sb.append("{code=").append(code);
        sb.append(", name=").append(name);
        sb.append(", accountingCode=").append(accountingCode);
        sb.append(", taxCode=").append(taxCode);
        sb.append(", liabilityGlAccountId=").append(liabilityGlAccountId);
        sb.append(", revenueGlAccountId=").append(revenueGlAccountId);
        sb.append(", performanceObligationId=").append(performanceObligationId);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final ShippingMethod that = (ShippingMethod) o;

        if (code != null ? !code.equals(that.code) : that.code != null) {
            return false;
        }
        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        }
        if (accountingCode != null ? !accountingCode.equals(that.accountingCode) : that.accountingCode != null) {
            return false;
        }
        if (taxCode != null ? !taxCode.equals(that.taxCode) : that.taxCode != null) {
            return false;
        }
        if (liabilityGlAccountId != null ? !liabilityGlAccountId.equals(that.liabilityGlAccountId) : that.liabilityGlAccountId != null) {
            return false;
        }
        if (revenueGlAccountId != null ? !revenueGlAccountId.equals(that.revenueGlAccountId) : that.revenueGlAccountId != null) {
            return false;
        }
        if (performanceObligationId != null ? !performanceObligationId.equals(that.performanceObligationId) : that.performanceObligationId != null) {
            return false;
        }
        if (createdAt != null ? createdAt.compareTo(that.createdAt) != 0 : that.createdAt != null) {
            return false;
        }
        if (updatedAt != null ? updatedAt.compareTo(that.updatedAt) != 0 : that.updatedAt != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
            code,
            name,
            accountingCode,
            taxCode,
            liabilityGlAccountId,
            revenueGlAccountId,
            performanceObligationId,
            createdAt,
            updatedAt
        );
    }
}