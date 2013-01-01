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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "transaction")
public class AbstractTransaction extends RecurlyObject {

    @XmlElement(name = "action")
    protected String action;

    @XmlElement(name = "amount_in_cents")
    protected Integer amountInCents;

    @XmlElement(name = "status")
    protected String status;

    @XmlElement(name = "reference")
    protected String reference;

    @XmlElement(name = "test")
    protected Boolean test;

    @XmlElement(name = "voidable")
    protected Boolean voidable;

    @XmlElement(name = "refundable")
    protected Boolean refundable;

    public String getAction() {
        return action;
    }

    public void setAction(final Object action) {
        this.action = stringOrNull(action);
    }

    public Integer getAmountInCents() {
        return amountInCents;
    }

    public void setAmountInCents(final Object amountInCents) {
        this.amountInCents = integerOrNull(amountInCents);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final Object status) {
        this.status = stringOrNull(status);
    }

    public String getReference() {
        return reference;
    }

    public void setReference(final Object reference) {
        this.reference = stringOrNull(reference);
    }

    public Boolean getTest() {
        return test;
    }

    public void setTest(final Object test) {
        this.test = booleanOrNull(test);
    }

    public Boolean getVoidable() {
        return voidable;
    }

    public void setVoidable(final Object voidable) {
        this.voidable = booleanOrNull(voidable);
    }

    public Boolean getRefundable() {
        return refundable;
    }

    public void setRefundable(final Object refundable) {
        this.refundable = booleanOrNull(refundable);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractTransaction)) {
            return false;
        }

        final AbstractTransaction that = (AbstractTransaction) o;

        if (action != null ? !action.equals(that.action) : that.action != null) {
            return false;
        }
        if (amountInCents != null ? !amountInCents.equals(that.amountInCents) : that.amountInCents != null) {
            return false;
        }
        if (reference != null ? !reference.equals(that.reference) : that.reference != null) {
            return false;
        }
        if (refundable != null ? !refundable.equals(that.refundable) : that.refundable != null) {
            return false;
        }
        if (status != null ? !status.equals(that.status) : that.status != null) {
            return false;
        }
        if (test != null ? !test.equals(that.test) : that.test != null) {
            return false;
        }
        if (voidable != null ? !voidable.equals(that.voidable) : that.voidable != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = action != null ? action.hashCode() : 0;
        result = 31 * result + (amountInCents != null ? amountInCents.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (reference != null ? reference.hashCode() : 0);
        result = 31 * result + (test != null ? test.hashCode() : 0);
        result = 31 * result + (voidable != null ? voidable.hashCode() : 0);
        result = 31 * result + (refundable != null ? refundable.hashCode() : 0);
        return result;
    }
}
