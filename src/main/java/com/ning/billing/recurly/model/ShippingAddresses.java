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

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

@XmlRootElement(name = "shipping_addresses")
public class ShippingAddresses extends RecurlyObjects<ShippingAddress> {

    @XmlTransient
    public static final String SHIPPING_ADDRESSES_RESOURCE = "/shipping_addresses";

    @XmlTransient
    public static final String PROPERTY_NAME = "shipping_address";

    @JsonSetter(value = PROPERTY_NAME)
    @Override
    public void setRecurlyObject(final ShippingAddress value) {
        super.setRecurlyObject(value);
    }

    @JsonIgnore
    @Override
    public ShippingAddresses getStart() {
        return getStart(ShippingAddresses.class);
    }

    @JsonIgnore
    @Override
    public ShippingAddresses getNext() {
        return getNext(ShippingAddresses.class);
    }
}
