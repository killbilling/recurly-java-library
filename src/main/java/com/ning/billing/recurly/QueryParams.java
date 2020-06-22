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

import com.google.common.base.Charsets;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

        Sort(final String type) {
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

        Order(final String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    public enum DateTimeType {
        USAGE("usage"),
        RECORDING("recording");

        private final String type;

       DateTimeType(final String type) {
           this.type = type;
       }

       public String getType() {
           return type;
       }
    }

    public enum BillingStatus {
        ALL("all"), UNBILLED("unbilled"), BILLED("billed");

        private final String type;

        BillingStatus(final String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    private final Map<String,String> params;

    public QueryParams() {
        params = new HashMap<>();

        Integer pageSize;

        try {
            pageSize = new Integer(System.getProperty(RECURLY_PAGE_SIZE_KEY));
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

    // Parameters to support List Subscription Add-On's Usage
    public void setStartDateTime(final DateTime startDateTime) {
        params.put("start_datetime", formatDate(startDateTime));
    }

    public void setEndDateTime(final DateTime endDateTime) {
        params.put("end_datetime", formatDate(endDateTime));
    }

    private String formatDate(final DateTime dateTime) {
        return dateTime.toDateTimeISO().toString();
    }

    public void setDateTimeType(final DateTimeType dateTimeType) {
        params.put("datetime_type", dateTimeType.getType());
    }

    public void setBillingStatus(final BillingStatus billingStatus) {
        params.put("biiling_status", billingStatus.getType());
    }

    public void put(final String key, final String value) { params.put(key, value); }

    @Override
    public String toString() {
        if (params.isEmpty()) return "";
        final List<NameValuePair> pairList = new ArrayList<>(params.size());
        for (Map.Entry<String, String> entry : params.entrySet()) {
            pairList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return '?' + URLEncodedUtils.format(pairList, Charsets.UTF_8);
    }
}
