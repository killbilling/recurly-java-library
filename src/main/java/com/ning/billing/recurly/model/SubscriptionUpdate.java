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

/**
 * Subscription object for update calls.
 * <p>
 * The timeframe parameter is specific to the update.
 */
public class SubscriptionUpdate extends AbstractSubscription {

    public static enum Timeframe {
        now,
        renewal
    }

    @XmlElement
    private Timeframe timeframe;

    public Timeframe getTimeframe() {
        return timeframe;
    }

    public void setTimeframe(final Timeframe timeframe) {
        this.timeframe = timeframe;
    }

    @XmlElement(name = "collection_method")
    private String collectionMethod;

    public String getCollectionMethod() {
        return collectionMethod;
    }

    public void setCollectionMethod(Object collectionMethod) {
        this.collectionMethod = stringOrNull(collectionMethod);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        final SubscriptionUpdate that = (SubscriptionUpdate) o;

        if (collectionMethod != null ? !collectionMethod.equals(that.collectionMethod) : that.collectionMethod != null) {
            return false;
        }
        if (timeframe != that.timeframe) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (timeframe != null ? timeframe.hashCode() : 0);
        result = 31 * result + (collectionMethod != null ? collectionMethod.hashCode() : 0);
        return result;
    }
}
