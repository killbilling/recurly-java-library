/*
 * Copyright 2010-2013 Ning, Inc.
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

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlTransient;

import com.ning.billing.recurly.RecurlyClient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * Container for a collection of objects (e.g. accounts, coupons, plans, ...)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public abstract class RecurlyObjects<T extends RecurlyObject> extends ArrayList<T> {

    // See https://github.com/FasterXML/jackson-dataformat-xml/issues/76 and https://github.com/killbilling/recurly-java-library/issues/21
    @JsonSetter
    public void setRecurlyObject(final T value) {
        add(value);
    }

    @XmlTransient
    private RecurlyClient recurlyClient;

    @XmlTransient
    private String startUrl;

    @XmlTransient
    private String prevUrl;

    @XmlTransient
    private String nextUrl;

    @XmlTransient
    private Integer nbRecords;

    @JsonIgnore
    <U extends RecurlyObjects> U getStart(final Class<U> clazz) {
        if (recurlyClient == null || startUrl == null) {
            return null;
        }
        return recurlyClient.doGETWithFullURL(clazz, startUrl);
    }

    @JsonIgnore
    <U extends RecurlyObjects> U getPrev(final Class<U> clazz) {
        if (recurlyClient == null || prevUrl == null) {
            return null;
        }
        return recurlyClient.doGETWithFullURL(clazz, prevUrl);
    }

    @JsonIgnore
    <U extends RecurlyObjects> U getNext(final Class<U> clazz) {
        if (recurlyClient == null || nextUrl == null) {
            return null;
        }
        return recurlyClient.doGETWithFullURL(clazz, nextUrl);
    }

    @JsonIgnore
    public void setRecurlyClient(final RecurlyClient recurlyClient) {
        this.recurlyClient = recurlyClient;
    }

    @JsonIgnore
    public String getStartUrl() {
        return startUrl;
    }

    @JsonIgnore
    public void setStartUrl(final String startUrl) {
        this.startUrl = startUrl;
    }

    @JsonIgnore
    public String getPrevUrl() {
        return prevUrl;
    }

    @JsonIgnore
    public void setPrevUrl(final String prevUrl) {
        this.prevUrl = prevUrl;
    }

    @JsonIgnore
    public String getNextUrl() {
        return nextUrl;
    }

    @JsonIgnore
    public void setNextUrl(final String nextUrl) {
        this.nextUrl = nextUrl;
    }

    @JsonIgnore
    public Integer getNbRecords() {
        return nbRecords;
    }

    @JsonIgnore
    public void setNbRecords(final Integer nbRecords) {
        this.nbRecords = nbRecords;
    }

    @Override
    @JsonIgnore // To avoid printing an <empty> tag in the XML
    public boolean isEmpty() {
        return super.isEmpty();
    }
}
