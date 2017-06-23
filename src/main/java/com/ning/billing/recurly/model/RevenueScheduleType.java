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

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum(String.class)
public enum RevenueScheduleType {
    @XmlEnumValue("never")
    NEVER("never"),
    @XmlEnumValue("evenly")
    EVENLY("evenly"),
    @XmlEnumValue("at_invoice")
    AT_INVOICE("at_invoice"),
    @XmlEnumValue("at_range_end")
    AT_RANGE_END("at_range_end"),
    @XmlEnumValue("at_range_start")
    AT_RANGE_START("at_range_start");

    private final String type;

    private RevenueScheduleType(final String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static RevenueScheduleType from(String revenueScheduleType) {
        return revenueScheduleType != null ? valueOf(revenueScheduleType.toUpperCase()) : null;
    }
}