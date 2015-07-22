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

@XmlRootElement(name = "adjustments")
public class Adjustments extends RecurlyObjects<Adjustment> {

    @XmlTransient
    public static final String ADJUSTMENTS_RESOURCE = "/adjustments";

    @XmlTransient
    private static final String PROPERTY_NAME = "adjustment";

    @JsonSetter(value = PROPERTY_NAME)
    @Override
    public void setRecurlyObject(final Adjustment value) {
        super.setRecurlyObject(value);
    }

    public enum AdjustmentType {
        CHARGE("charge"),
        CREDIT("credit");

        private final String type;

        private AdjustmentType(final String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    public enum AdjustmentState {
        PENDING("pending"),
        INVOICED("invoiced");

        private final String state;

        private AdjustmentState(final String state) {
            this.state = state;
        }

        public String getState() {
            return state;
        }
    }

    @JsonIgnore
    public Adjustments getStart() {
        return getStart(Adjustments.class);
    }

    @JsonIgnore
    public Adjustments getPrev() {
        return getPrev(Adjustments.class);
    }

    @JsonIgnore
    public Adjustments getNext() {
        return getNext(Adjustments.class);
    }
}
