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

package com.ning.billing.recurly;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class PaginationUtils {

    public static String[] getLinks(final String linkHeader) {
        String start = null;
        String prev = null;
        String next = null;

        final Pattern pattern = Pattern.compile("\\<([^>]+)\\>; rel=\\\"([^\"]+)\\\"");
        final Matcher matcher = pattern.matcher(linkHeader);
        while (matcher.find()) {
            if ("start".equals(matcher.group(2))) {
                start = matcher.group(1);
            } else if ("prev".equals(matcher.group(2))) {
                prev = matcher.group(1);
            } else if ("next".equals(matcher.group(2))) {
                next = matcher.group(1);
            }
        }

        return new String[]{start, prev, next};
    }
}
