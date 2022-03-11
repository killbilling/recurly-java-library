/*
 * Copyright 2018 The Billing Project, LLC
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

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Objects;

import javax.xml.bind.annotation.XmlRootElement;

@JsonSerialize(using = ItemCodeSerializer.class)
@XmlRootElement(name = "item_code")
public class ItemCode extends RecurlyObject {

    private String name;

    public ItemCode() { /* Required for Jackson */ }

    public ItemCode(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(final Object name) {
        this.name = stringOrNull(name);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ItemCode{");
        sb.append("name=").append(name);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final ItemCode field = (ItemCode) o;

        if (name != null ? !name.equals(field.name) : field.name != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
