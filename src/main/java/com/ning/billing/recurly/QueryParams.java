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

import com.ning.billing.recurly.model.Subscription;
import com.ning.billing.recurly.model.Transaction;
import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for handling query parameters to pageable resources in the Recurly API.
 * See the pagination docs (https://dev.recurly.com/docs/pagination) for the parameters available on
 * every endpoint.
 */
public class QueryParams {
    private static final String RECURLY_PAGE_SIZE_KEY = "recurly.page.size";
    private static final Integer DEFAULT_PAGE_SIZE = 20;

    public enum Sort {
        CREATED_AT("created_at"),
        UPDATED_AT("updated_at");

        private final String type;

        private Sort(final String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    public enum Order {
        ASC("asc"),
        DESC("desc");

        private final String type;

        private Order(final String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    private Map<String,String> params;

    public QueryParams() {
        params = new HashMap<String, String>();

        Integer pageSize;

        try {
            pageSize = Integer.valueOf(System.getProperty(RECURLY_PAGE_SIZE_KEY));
        } catch (NumberFormatException nfex) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        setPerPage(pageSize);
    }

    public void setPerPage(final int perPage) {
        params.put("per_page", Integer.toString(perPage));
    }

    public void setSort(final Sort sortField) {
        params.put("sort", sortField.getType());
    }

    public void setOrder(final Order orderDirection) {
        params.put("order", orderDirection.getType());
    }

    public void setBeginTime(final DateTime beginTime) {
        params.put("begin_time", formatDate(beginTime));
    }

    public void setEndTime(final DateTime endTime) {
        params.put("end_time", formatDate(endTime));
    }

    private String formatDate(final DateTime dateTime) {
        return dateTime.toDateTimeISO().toString();
    }

    public void put(final String key, final String value) { params.put(key, value); }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String,String> entry : params.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            } else {
                sb.append("?");
            }

            sb.append(String.format("%s=%s", entry.getKey(), entry.getValue()));
        }

        return sb.toString();
    }
}
