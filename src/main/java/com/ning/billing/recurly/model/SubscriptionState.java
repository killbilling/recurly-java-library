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

// see Subscription pagination: https://dev.recurly.com/docs/list-subscriptions
public enum SubscriptionState {
    ACTIVE("active"),
    CANCELED("canceled"),
    PAUSED("paused"),
    EXPIRED("expired"),
    FUTURE("future"),
    IN_TRIAL("in_trial"),
    LIVE("live"),
    PAST_DUE("past_due");

    private final String type;

    private SubscriptionState(final String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}