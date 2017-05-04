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
import javax.xml.bind.annotation.XmlTransient;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@XmlRootElement(name = "measured_unit")
public class MeasuredUnit extends RecurlyObject{

    @XmlTransient
    public static final String MEASURED_UNITS_RESOURCE = "/measured_units";

    @XmlTransient
    public static final Pattern MEASURED_UNITS_CODE_PATTERN = Pattern.compile(MEASURED_UNITS_RESOURCE + "/(.+)$");

    @XmlElement(name = "id")
    private Long id;

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "display_name")
    private String displayName;

    @XmlElement(name = "description")
    private String description;


    @Override
    public String toString() {
        return "MeasuredUnit{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", displayName=" + displayName +
                ", description=" + description +
                '}';
    }

    @Override
    public void setHref(final Object href) {
        super.setHref(href);

        if (this.href != null) {
            final Matcher m = MEASURED_UNITS_CODE_PATTERN.matcher(this.href);
            if (m.find()) {
                setId(m.group(1));
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MeasuredUnit measuredUnit = (MeasuredUnit) o;
        if (id != null ? !id.equals(measuredUnit.id) : measuredUnit.id != null) return false;
        if (name != null ? !name.equals(measuredUnit.name) : measuredUnit.name != null) return false;
        if (displayName != null ? !displayName.equals(measuredUnit.displayName) : measuredUnit.displayName != null) return false;
        if (description != null ? !description.equals(measuredUnit.description) : measuredUnit.description != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
                id,
                name,
                displayName,
                description
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(final Object id) {
        this.id = longOrNull(id);
    }

    public String getName() {
        return name;
    }

    public void setName(final Object name) {
        this.name = stringOrNull(name);
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(final Object displayName) {
        this.displayName = stringOrNull(displayName);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final Object description) {
        this.description = stringOrNull(description);
    }
}
