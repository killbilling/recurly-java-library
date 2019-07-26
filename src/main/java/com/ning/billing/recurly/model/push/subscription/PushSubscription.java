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

package com.ning.billing.recurly.model.push.subscription;

import com.google.common.base.Objects;

import javax.xml.bind.annotation.XmlElement;

import com.ning.billing.recurly.model.Subscription;

import org.joda.time.DateTime;

public class PushSubscription extends Subscription {

    @XmlElement(name = "total_amount_in_cents")
    private Integer totalAmountInCents;

    @XmlElement(name = "resume_at")
    private DateTime resumeAt;

    public Integer getTotalAmountInCents() {
        return totalAmountInCents;
    }

    public void setTotalAmountInCents(final Object totalAmountInCents) {
        this.totalAmountInCents = integerOrNull(totalAmountInCents);
    }

    public DateTime getResumeAt() {
        return resumeAt;
    }

    public void setResumeAt(final Object resumeAt) {
        this.resumeAt = dateTimeOrNull(resumeAt);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PushSubscription)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        final PushSubscription that = (PushSubscription) o;

        if (totalAmountInCents != null ? !totalAmountInCents.equals(that.totalAmountInCents) : that.totalAmountInCents != null) {
            return false;
        }

        if (resumeAt != null ? !resumeAt.equals(that.resumeAt) : that.resumeAt != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
            totalAmountInCents,
            resumeAt
        );
    }
}
