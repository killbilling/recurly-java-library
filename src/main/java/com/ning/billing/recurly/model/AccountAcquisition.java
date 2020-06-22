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

@XmlRootElement(name = "account_acquisition")
public class AccountAcquisition extends RecurlyObject {

    @XmlTransient
    public static final String ACCOUNT_ACQUISITION_RESOURCE = "/acquisition";

    @XmlElement(name = "cost_in_cents")
    private Integer costInCents;

    @XmlElement(name = "currency")
    private String currency;

    @XmlElement(name = "channel")
    private AcquisitionChannel channel;

    @XmlElement(name = "subchannel")
    private String subchannel;

    @XmlElement(name = "campaign")
    private String campaign;

    @XmlElement(name = "created_at")
    private DateTime createdAt;

    @XmlElement(name = "updated_at")
    private DateTime updatedAt;

    public Integer getCostInCents() {
        return costInCents;
    }

    public void setCostInCents(final Object costInCents) {
        this.costInCents = integerOrNull(costInCents);
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(final Object currency) {
        this.currency = stringOrNull(currency);
    }

    public AcquisitionChannel getChannel() {
        return channel;
    }

    public void setChannel(final AcquisitionChannel channel) {
        this.channel = channel;
    }

    public String getSubchannel() {
        return subchannel;
    }

    public void setSubchannel(final Object subchannel) {
        this.subchannel = stringOrNull(subchannel);
    }

    public String getCampaign() {
        return campaign;
    }

    public void setCampaign(final Object campaign) {
        this.campaign = stringOrNull(campaign);
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    protected void setCreatedAt(final Object createdAt) {
        this.createdAt = dateTimeOrNull(createdAt);
    }

    public DateTime getUpdatedAt() {
        return updatedAt;
    }

    protected void setUpdatedAt(final Object updatedAt) {
        this.updatedAt = dateTimeOrNull(updatedAt);
    }

    @Override
    public String toString() {
        String sb = "Account{" + "href=" + href +
                ", costInCents=" + costInCents +
                ", currency=" + currency +
                ", channel=" + channel +
                ", subchannel=" + subchannel +
                ", campaign=" + campaign +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
        return sb;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final AccountAcquisition accountAcquisition = (AccountAcquisition) o;

        if (campaign!= null ? !campaign.equals(accountAcquisition.campaign) : accountAcquisition.campaign!= null) {
            return false;
        }
        if (channel != null ? !channel.equals(accountAcquisition.channel) : accountAcquisition.channel!= null) {
            return false;
        }
        if (costInCents != null ? !costInCents.equals(accountAcquisition.costInCents) : accountAcquisition.costInCents != null) {
            return false;
        }
        if (createdAt != null ? createdAt.compareTo(accountAcquisition.createdAt) != 0 : accountAcquisition.createdAt != null) {
            return false;
        }
        if (currency != null ? !currency.equals(accountAcquisition.currency) : accountAcquisition.currency != null) {
            return false;
        }
        if (href != null ? !href.equals(accountAcquisition.href) : accountAcquisition.href != null) {
            return false;
        }
        if (subchannel != null ? !subchannel.equals(accountAcquisition.subchannel) : accountAcquisition.subchannel!= null) {
            return false;
        }
        if (updatedAt != null ? updatedAt.compareTo(accountAcquisition.updatedAt) != 0 : accountAcquisition.updatedAt != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
                campaign,
                channel,
                costInCents,
                createdAt,
                currency,
                href,
                subchannel,
                updatedAt
        );
    }
}
