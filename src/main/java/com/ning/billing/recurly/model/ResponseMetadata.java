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

import org.apache.http.Header;
import org.apache.http.HttpResponse;

public class ResponseMetadata {
    /**
     * Represents the unique id given to this
     * request by Recurly. Comes from the X-Request-Id
     * header in the response.
     */
    private String requestId;

    /**
     * Represents the unique id given to this
     * request by Cloudflare (if request is proxied through
     * Cloudflare). Comes from the CF-RAY header in the response.
     */
    private String cfRay;

    /**
     * The HTTP Status Code of the response.
     */
    private int statusCode;

    public ResponseMetadata(HttpResponse response) {
        final Header requestIdHeader = response.getFirstHeader("X-Request-Id");
        this.requestId = requestIdHeader == null ? null : requestIdHeader.getValue();
        final Header cfRayHeader = response.getFirstHeader("CF-RAY");
        this.cfRay = cfRayHeader == null ? null : cfRayHeader.getValue();
        this.statusCode = response.getStatusLine().getStatusCode();
    }

    public String getRequestId() {
        return this.requestId;
    }

    public String getCfRay() {
        return this.cfRay;
    }

    public int getStatusCode() {
        return this.getStatusCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ResponseMetadata{");
        sb.append("requestId=").append(requestId);
        sb.append(", cfRay=").append(cfRay);
        sb.append(", statusCode=").append(statusCode);
        sb.append('}');
        return sb.toString();
    }
}
