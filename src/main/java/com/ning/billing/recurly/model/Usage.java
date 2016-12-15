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
        if (createdAt != null ? !createdAt.equals(usage.createdAt) : usage.createdAt != null) return false;
        if (updatedAt != null ? !updatedAt.equals(usage.updatedAt) : usage.updatedAt != null) return false;
        if (billedAt != null ? !billedAt.equals(usage.billedAt) : usage.billedAt != null) return false;
        if (usageType != null ? !usageType.equals(usage.usageType) : usage.usageType != null) return false;
        if (unitAmountInCents != null ? !unitAmountInCents.equals(usage.unitAmountInCents) : usage.unitAmountInCents != null)
            return false;
        return usagePercentage != null ? usagePercentage.equals(usage.usagePercentage) : usage.usagePercentage == null;
    }

    @Override
    public int hashCode() {
        int result = amount.hashCode();
        result = 31 * result + (merchantTag != null ? merchantTag.hashCode() : 0);
        result = 31 * result + (recordingAt != null ? recordingAt.hashCode() : 0);
        result = 31 * result + usageAt.hashCode();
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        result = 31 * result + (billedAt != null ? billedAt.hashCode() : 0);
        result = 31 * result + (usageType != null ? usageType.hashCode() : 0);
        result = 31 * result + (unitAmountInCents != null ? unitAmountInCents.hashCode() : 0);
        result = 31 * result + (usagePercentage != null ? usagePercentage.hashCode() : 0);
        return result;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Object amount) {
        this.amount = integerOrNull(amount);
    }

    public String getMerchantTag() {
        return merchantTag;
    }

    public void setMerchantTag(Object merchantTag) {
        this.merchantTag = stringOrNull(merchantTag);
    }

    public DateTime getRecordingAt() {
        return recordingAt;
    }

    public void setRecordingAt(Object recordingAt) {
        this.recordingAt = dateTimeOrNull(recordingAt);
    }

    public DateTime getUsageAt() {
        return usageAt;
    }

    public void setUsageAt(Object usageAt) {
        this.usageAt = dateTimeOrNull(usageAt);
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Object createdAt) {
        this.createdAt = dateTimeOrNull(createdAt);
    }

    public DateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = dateTimeOrNull(updatedAt);
    }

    public DateTime getBilledAt() {
        return billedAt;
    }

    public void setBilledAt(Object billedAt) {
        this.billedAt = dateTimeOrNull(billedAt);
    }

    public String getUsageType() {
        return usageType;
    }

    public void setUsageType(Object usageType) {
        this.usageType = stringOrNull(usageType);
    }

    public Integer getUnitAmountInCents() {
        return unitAmountInCents;
    }

    public void setUnitAmountInCents(Object unitAmountInCents) {
        this.unitAmountInCents = integerOrNull(unitAmountInCents);
    }

    public Integer getUsagePercentage() {
        return usagePercentage;
    }

    public void setUsagePercentage(Object usagePercentage) {
        this.usagePercentage = integerOrNull(usagePercentage);
    }
}
