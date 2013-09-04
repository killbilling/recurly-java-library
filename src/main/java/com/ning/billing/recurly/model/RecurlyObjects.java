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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Container for a collection of objects (e.g. accounts, coupons, plans, ...)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class RecurlyObjects<T extends RecurlyObject> extends ArrayList<T> {

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

    <U extends RecurlyObjects> U getStart(final Class<U> clazz) {
        if (recurlyClient == null) {
            return null;
        }
        return recurlyClient.doGETWithFullURL(clazz, startUrl);
    }

    <U extends RecurlyObjects> U getPrev(final Class<U> clazz) {
        if (recurlyClient == null) {
            return null;
        }
        return recurlyClient.doGETWithFullURL(clazz, prevUrl);
    }

    <U extends RecurlyObjects> U getNext(final Class<U> clazz) {
        if (recurlyClient == null) {
            return null;
        }
        return recurlyClient.doGETWithFullURL(clazz, nextUrl);
    }

    public void setRecurlyClient(final RecurlyClient recurlyClient) {
        this.recurlyClient = recurlyClient;
    }

    public String getStartUrl() {
        return startUrl;
    }

    public void setStartUrl(final String startUrl) {
        this.startUrl = startUrl;
    }

    public String getPrevUrl() {
        return prevUrl;
    }

    public void setPrevUrl(final String prevUrl) {
        this.prevUrl = prevUrl;
    }

    public String getNextUrl() {
        return nextUrl;
    }

    public void setNextUrl(final String nextUrl) {
        this.nextUrl = nextUrl;
    }

    public Integer getNbRecords() {
        return nbRecords;
    }

    public void setNbRecords(final Integer nbRecords) {
        this.nbRecords = nbRecords;
    }
}
