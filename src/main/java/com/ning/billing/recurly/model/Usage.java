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
import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@XmlRootElement(name = "usage")
public class Usage extends RecurlyObject{

    @XmlTransient
    public static final String USAGE_RESOURCE = "/usage";

    @XmlElement(name = "amount")
    protected Integer amount;

    @XmlElement(name = "merchant_tag")
    private String merchantTag;

    @XmlElement(name = "recording_timestamp")
    private DateTime recordingAt;

    @XmlElement(name = "usage_timestamp")
    private DateTime usageAt;

    @XmlElement(name = "created_at")
    private DateTime createdAt;

    @XmlElement(name = "updated_at")
    private DateTime updatedAt;

    @XmlElement(name = "billed_at")
    private DateTime billedAt;

    @XmlElement(name = "usage_type")
    private String usageType;

    @XmlElement(name = "unit_amount_in_cents")
    protected Integer unitAmountInCents;

    @XmlElement(name = "usage_percentage")
    private Integer usagePercentage;

    @Override
    public String toString() {
        return "Usage{" +
                "amount=" + amount +
                ", merchantTag='" + merchantTag + '\'' +
                ", recordingAt=" + recordingAt +
                ", usageAt=" + usageAt +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", billedAt=" + billedAt +
                ", usageType='" + usageType + '\'' +
                ", unitAmountInCents=" + unitAmountInCents +
                ", usagePercentage=" + usagePercentage +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Usage usage = (Usage) o;

        if (!amount.equals(usage.amount)) return false;
        if (merchantTag != null ? !merchantTag.equals(usage.merchantTag) : usage.merchantTag != null) return false;
        if (recordingAt != null ? !recordingAt.equals(usage.recordingAt) : usage.recordingAt != null) return false;
        if (!usageAt.equals(usage.usageAt)) return false;
        if (createdAt != null ? createdAt.compareTo(usage.createdAt) != 0 : usage.createdAt != null) return false;
        if (updatedAt != null ? updatedAt.compareTo(usage.updatedAt) != 0: usage.updatedAt != null) return false;
        if (billedAt != null ? billedAt.compareTo(usage.billedAt) != 0 : usage.billedAt != null) return false;
        if (usageType != null ? !usageType.equals(usage.usageType) : usage.usageType != null) return false;
        if (unitAmountInCents != null ? !unitAmountInCents.equals(usage.unitAmountInCents) : usage.unitAmountInCents != null)
            return false;
        return usagePercentage != null ? usagePercentage.equals(usage.usagePercentage) : usage.usagePercentage == null;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
                amount,
                merchantTag,
                recordingAt,
                usageAt,
                createdAt,
                updatedAt,
                billedAt,
                usageType,
                unitAmountInCents,
                usagePercentage
        );
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(final Object amount) {
        this.amount = integerOrNull(amount);
    }

    public String getMerchantTag() {
        return merchantTag;
    }

    public void setMerchantTag(final Object merchantTag) {
        this.merchantTag = stringOrNull(merchantTag);
    }

    public DateTime getRecordingAt() {
        return recordingAt;
    }

    public void setRecordingAt(final Object recordingAt) {
        this.recordingAt = dateTimeOrNull(recordingAt);
    }

    public DateTime getUsageAt() {
        return usageAt;
    }

    public void setUsageAt(final Object usageAt) {
        this.usageAt = dateTimeOrNull(usageAt);
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

    public DateTime getBilledAt() {
        return billedAt;
    }

    public void setBilledAt(final Object billedAt) {
        this.billedAt = dateTimeOrNull(billedAt);
    }

    public String getUsageType() {
        return usageType;
    }

    public void setUsageType(final Object usageType) {
        this.usageType = stringOrNull(usageType);
    }

    public Integer getUnitAmountInCents() {
        return unitAmountInCents;
    }

    public void setUnitAmountInCents(final Object unitAmountInCents) {
        this.unitAmountInCents = integerOrNull(unitAmountInCents);
    }

    public Integer getUsagePercentage() {
        return usagePercentage;
    }

    public void setUsagePercentage(final Object usagePercentage) {
        this.usagePercentage = integerOrNull(usagePercentage);
    }
}
