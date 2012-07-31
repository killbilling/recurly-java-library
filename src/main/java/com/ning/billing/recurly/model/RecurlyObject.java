/*
 * Copyright 2010-2012 Ning, Inc.
 *
 * Ning licenses this file to you under the Apache License, version 2.0
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

import java.util.Map;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlValue;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class RecurlyObject {

    public String stringOrNull(@Nullable final Object object) {
        if (object == null) {
            return null;
        }

        // Hack to work around Recurly output for nil values: the response will contain
        // an element with a nil attribute (e.g. <city nil="nil"></city>) which Jackson will
        // interpret as an Object (Map), not a String.
        if (object instanceof Map) {
            final Map map = (Map) object;
            if (map.keySet().size() == 1 && "nil".equals(map.get("nil"))) {
                return null;
            }
        }

        return object.toString();
    }

    public Integer integerOrNull(@Nullable final Object object) {
        if (object == null) {
            return null;
        }

        // Integers are represented as objects (e.g. <year type="integer">2015</year>), which Jackson
        // will interpret as an Object (Map), not Integers.
        if (object instanceof Map) {
            final Map map = (Map) object;
            if (map.keySet().size() == 2 && "integer".equals(map.get("type"))) {
                return Integer.valueOf((String) map.get(""));
            }
        }

        return Integer.valueOf(object.toString());
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    protected static class RecurlyDateTime {

        @XmlValue
        private DateTime dateTime;

        public DateTime getDateTime() {
            return dateTime;
        }

        public void setDateTime(final DateTime dateTime) {
            this.dateTime = dateTime;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append("RecurlyDateTime");
            sb.append("{dateTime=").append(dateTime);
            sb.append('}');
            return sb.toString();
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            final RecurlyDateTime that = (RecurlyDateTime) o;

            if (dateTime != null ? !dateTime.equals(that.dateTime) : that.dateTime != null) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            return dateTime != null ? dateTime.hashCode() : 0;
        }
    }
}
