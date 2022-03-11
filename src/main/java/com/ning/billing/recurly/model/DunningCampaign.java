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
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "dunning_campaign")
public class DunningCampaign extends RecurlyObject {

    @XmlTransient
    public static final String DUNNING_CAMPAIGNS_RESOURCE = "/dunning_campaigns";

    @XmlElement(name = "id")
    private String id;

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "code")
    private String code;

    @XmlElement(name = "description")
    private String description;

    @XmlElement(name = "default_campaign")
    private Boolean defaultCampaign;

    @XmlElement(name = "dunning_cycle")
    @XmlElementWrapper(name = "dunning_cycles")
    private DunningCycles dunningCycles;

    @XmlElement(name = "created_at")
    private DateTime createdAt;

    @XmlElement(name = "updated_at")
    private DateTime updatedAt;

    @XmlElement(name = "deleted_at")
    private DateTime deletedAt;

    public String getId() {
        return id;
    }

    public void setId(final Object id) {
        this.id = stringOrNull(id);
    }

    public String getName() {
        return name;
    }

    public void setName(final Object name) {
        this.name = stringOrNull(name);
    }

    public String getCode() {
        return code;
    }

    public void setCode(final Object code) {
        this.code = stringOrNull(code);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final Object description) {
        this.description = stringOrNull(description);
    }

    public Boolean getDefaultCampaign() {
        return defaultCampaign;
    }

    public void setDefaultCampaign(final Object defaultCampaign) {
        this.defaultCampaign = booleanOrNull(defaultCampaign);
    }

    public DunningCycles getDunningCycles() {
        return dunningCycles;
    }

    public void setDunningCycles(final DunningCycles dunningCycles) {
        this.dunningCycles = dunningCycles;
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

    public DateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(final Object deletedAt) {
        this.deletedAt = dateTimeOrNull(deletedAt);
    }
}
