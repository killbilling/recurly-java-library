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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@JsonSerialize(using = ItemCodesSerializer.class)
@XmlRootElement(name = "item_codes")
public class ItemCodes extends RecurlyObjects<ItemCode> {

    @XmlTransient
    private static final String PROPERTY_NAME = "item_code";

    @JsonSetter(value = PROPERTY_NAME)
    @Override
    public void setRecurlyObject(final ItemCode value) {
        super.setRecurlyObject(value);
    }

    @JsonIgnore
    @Override
    public ItemCodes getStart() {
        return getStart(ItemCodes.class);
    }

    @JsonIgnore
    @Override
    public ItemCodes getNext() {
        return getNext(ItemCodes.class);
    }
}
