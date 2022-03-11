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
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "invoice_template")
public class InvoiceTemplate extends RecurlyObject {

    @XmlTransient
    public static final String INVOICE_TEMPLATES_RESOURCE = "/invoice_templates";

    @XmlElement(name = "uuid")
    private String uuid;

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "code")
    private String code;

    @XmlElement(name = "description")
    private String description;

    @XmlElement(name = "created_at")
    private DateTime createdAt;

    @XmlElement(name = "updated_at")
    private DateTime updatedAt;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(final Object uuid) {
        this.uuid = stringOrNull(uuid);
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
        return "InvoiceTemplate{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                ", code=" + code +
                ", description=" + description +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
                uuid,
                name,
                code,
                description,
                updatedAt,
                createdAt
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InvoiceTemplate invoiceTemplate = (InvoiceTemplate) o;

        if (uuid != null ? !uuid.equals(invoiceTemplate.uuid) : invoiceTemplate.uuid != null)
            return false;
        if (code != null ? !code.equals(invoiceTemplate.code) : invoiceTemplate.code != null)
            return false;
        if (description != null ? !description.equals(invoiceTemplate.description) : invoiceTemplate.description != null)
            return false;
        if (name != null ? !name.equals(invoiceTemplate.name) : invoiceTemplate.name != null)
            return false;
        if (createdAt != null ? createdAt.compareTo(invoiceTemplate.createdAt) != 0 : invoiceTemplate.createdAt != null)
          return false;
        if (updatedAt != null ? updatedAt.compareTo(invoiceTemplate.updatedAt) != 0 : invoiceTemplate.updatedAt != null)
          return false;
        return true;
    }
}
