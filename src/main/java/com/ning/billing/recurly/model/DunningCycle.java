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
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "dunning_cycle")
public class DunningCycle extends RecurlyObject{

    @XmlElement(name = "type")
    private String type;

    @XmlElement(name = "applies_to_manual_trial")
    private Boolean appliesToManualTrial;

    @XmlElement(name = "first_communication_interval")
    private Integer firstCommunicationInterval;

    @XmlElement(name = "send_immediately_on_hard_decline")
    private Boolean sendImmediatelyOnHardDecline;

    @XmlElementWrapper(name = "intervals")
    @XmlElement(name = "interval")
    private DunningIntervals intervals;

    @XmlElement(name = "expire_subscription")
    private Boolean expireSubscription;

    @XmlElement(name = "fail_invoice")
    private Boolean failInvoice;

    @XmlElement(name = "total_dunning_days")
    private Integer totalDunningDays;

    @XmlElement(name = "total_recycling_days")
    private Integer totalRecyclingDays;

    @XmlElement(name = "version")
    private Integer version;

    @XmlElement(name = "created_at")
    private DateTime createdAt;

    @XmlElement(name = "updated_at")
    private DateTime updatedAt;

    public String getType() {
        return type;
    }

    public void setType(final Object type) {
        this.type = stringOrNull(type);
    }

    public Boolean getAppliesToManualTrial() {
        return appliesToManualTrial;
    }

    public void setAppliesToManualTrial(final Object appliesToManualTrial) {
        this.appliesToManualTrial = booleanOrNull(appliesToManualTrial);
    }

    public Integer getFirstCommunicationInterval() {
        return firstCommunicationInterval;
    }

    public void setFirstCommunicationInterval(final Object firstCommunicationInterval) {
        this.firstCommunicationInterval = integerOrNull(firstCommunicationInterval);
    }

    public Boolean getSendImmediatelyOnHardDecline() {
        return sendImmediatelyOnHardDecline;
    }

    public void setSendImmediatelyOnHardDecline(final Object sendImmediatelyOnHardDecline) {
        this.sendImmediatelyOnHardDecline = booleanOrNull(sendImmediatelyOnHardDecline);
    }

    public DunningIntervals getIntervals() {
         return this.intervals;
     }

    public void setIntervals(final DunningIntervals intervals) {
        this.intervals = intervals;
    }

    public Boolean getExpireSubscription() {
        return expireSubscription;
    }

    public void setExpireSubscription(final Object expireSubscription) {
        this.expireSubscription = booleanOrNull(expireSubscription);
    }

    public Boolean getFailInvoice() {
        return failInvoice;
    }

    public void setFailInvoice(final Object failInvoice) {
        this.failInvoice = booleanOrNull(failInvoice);
    }

    public Integer getTotalDunningDays() {
        return totalDunningDays;
    }

    public void setTotalDunningDays(final Object totalDunningDays) {
        this.totalDunningDays = integerOrNull(totalDunningDays);
    }

    public Integer getTotalRecyclingDays() {
        return totalRecyclingDays;
    }

    public void setTotalRecyclingDays(final Object totalRecyclingDays) {
        this.totalRecyclingDays = integerOrNull(totalRecyclingDays);
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(final Object version) {
        this.version = integerOrNull(version);
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
}
