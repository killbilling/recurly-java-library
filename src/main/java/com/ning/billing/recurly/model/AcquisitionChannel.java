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

// see AccountAcquisition docs: https://dev.recurly.com/docs/create-account-acquisition
@XmlEnum(String.class)
public enum AcquisitionChannel {
    @XmlEnumValue("referral")
    REFERAL,
    @XmlEnumValue("social_media")
    SOCIAL_MEDIA,
    @XmlEnumValue("email")
    EMAIL,
    @XmlEnumValue("paid_search")
    PAID_SEARCH,
    @XmlEnumValue("organic_search")
    ORGANIC_SEARCH,
    @XmlEnumValue("direct_traffic")
    DIRECT_TRAFFIC,
    @XmlEnumValue("marketing_content")
    MARKETING_CONTENT,
    @XmlEnumValue("blog")
    BLOG,
    @XmlEnumValue("events")
    EVENTS,
    @XmlEnumValue("outbound_sales")
    OUTBOUND_SALES,
    @XmlEnumValue("advertising")
    ADVERTISING,
    @XmlEnumValue("public_relations")
    PUBLIC_RELATIONS,
    @XmlEnumValue("other")
    OTHER
}