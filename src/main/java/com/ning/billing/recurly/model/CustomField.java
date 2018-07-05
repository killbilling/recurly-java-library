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

@XmlRootElement(name = "custom_field")
public class CustomField extends RecurlyObject {

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "value")
    private String value;

    public String getName() {
        return name;
    }

    public void setName(final Object name) {
        this.name = stringOrNull(name);
    }

    public String getValue() {
        return value;
    }

    public void setValue(final Object value) {
        this.value = stringOrNull(value);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CustomField{");
        sb.append("name=").append(name);
        sb.append(", value=").append(value);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final CustomField field = (CustomField) o;

        if (name != null ? !name.equals(field.name) : field.name != null) {
            return false;
        }
        if (value != null ? !value.equals(field.value) : field.value != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode( name, value );
    }
}
