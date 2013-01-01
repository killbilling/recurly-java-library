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

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class RecurlyObject {

    public static final String NIL_STR = "nil";
    public static final List<String> NIL_VAL = Arrays.asList("nil", "true");

    public static Boolean booleanOrNull(@Nullable final Object object) {
        if (isNull(object)) {
            return null;
        }

        // Booleans are represented as objects (e.g. <display_quantity type="boolean">false</display_quantity>), which Jackson
        // will interpret as an Object (Map), not Booleans.
        if (object instanceof Map) {
            final Map map = (Map) object;
            if (map.keySet().size() >= 1 && "boolean".equals(map.get("type"))) {
                return Boolean.valueOf((String) map.get(""));
            }
        }

        return Boolean.valueOf(object.toString());
    }

    public static String stringOrNull(@Nullable final Object object) {
        if (isNull(object)) {
            return null;
        }

        return object.toString();
    }

    public static Integer integerOrNull(@Nullable final Object object) {
        if (isNull(object)) {
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

    public static DateTime dateTimeOrNull(@Nullable final Object object) {
        if (isNull(object)) {
            return null;
        }

        // DateTimes are represented as objects (e.g. <created_at type="datetime">2011-04-19T07:00:00Z</created_at>), which Jackson
        // will interpret as an Object (Map), not DateTimes.
        if (object instanceof Map) {
            final Map map = (Map) object;
            if (map.keySet().size() == 2 && "datetime".equals(map.get("type"))) {
                return new DateTime(map.get(""));
            }
        }

        return new DateTime(object.toString());
    }

    public static boolean isNull(@Nullable final Object object) {
        if (object == null) {
            return true;
        }

        // Hack to work around Recurly output for nil values: the response will contain
        // an element with a nil attribute (e.g. <city nil="nil"></city> or <username nil="true"></username>) which Jackson will
        // interpret as an Object (Map), not a String.
        if (object instanceof Map) {
            final Map map = (Map) object;
            if (map.keySet().size() >= 1 && map.get(NIL_STR) != null && NIL_VAL.contains(map.get(NIL_STR).toString())) {
                return true;
            }
        }

        return false;
    }
}
