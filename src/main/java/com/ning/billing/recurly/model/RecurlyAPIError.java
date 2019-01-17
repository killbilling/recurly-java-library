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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.common.base.Objects;
import com.ning.http.client.Response;

import java.io.IOException;

@XmlRootElement(name = "error")
public class RecurlyAPIError extends RecurlyObject {

    @XmlTransient
    private ResponseMetadata responseMetadata;

    @XmlElement(name = "description")
    private String description;

    @XmlElement(name = "symbol")
    private String symbol;

    @XmlElement(name = "details")
    private String details;

    private int httpStatusCode;

    public static RecurlyAPIError buildFromResponse(final Response response) {
        final RecurlyAPIError recurlyAPIError = new RecurlyAPIError();
        recurlyAPIError.setResponse(response);
        return recurlyAPIError;
    }

    public static RecurlyAPIError buildFromXml(final XmlMapper xmlMapper, final String payload, final Response response) throws IOException {
        final RecurlyAPIError recurlyAPIError = xmlMapper.readValue(payload, RecurlyAPIError.class);
        recurlyAPIError.setResponse(response);
        return recurlyAPIError;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(final String symbol) {
        this.symbol = symbol;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(final String details) {
        this.details = details;
    }

    public void setHttpStatusCode(final int httpStatusCode) {
      this.httpStatusCode = httpStatusCode;
    }

    public int getHttpStatusCode() { return this.httpStatusCode; }

    public void setResponseMetadata(final ResponseMetadata meta) {
        this.responseMetadata = meta;
    }

    public ResponseMetadata getResponseMetadata() {
        return this.responseMetadata;
    }

    protected void setResponse(final Response response) {
        this.setHttpStatusCode(response.getStatusCode());
        this.setResponseMetadata(new ResponseMetadata(response));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RecurlyAPIError{");
        sb.append("description='").append(description).append('\'');
        sb.append(", symbol='").append(symbol).append('\'');
        sb.append(", details='").append(details).append('\'');
        sb.append(", httpStatusCode='").append(httpStatusCode).append('\'');
        sb.append(", responseMetadata='").append(responseMetadata).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final RecurlyAPIError that = (RecurlyAPIError) o;

        if (description != null ? !description.equals(that.description) : that.description != null) {
            return false;
        }
        if (details != null ? !details.equals(that.details) : that.details != null) {
            return false;
        }
        if (symbol != null ? !symbol.equals(that.symbol) : that.symbol != null) {

            return false;
        }
        if (responseMetadata != null ? !responseMetadata.equals(that.responseMetadata) : that.responseMetadata != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
                description,
                symbol,
                details,
                httpStatusCode,
                responseMetadata
        );
    }
}
