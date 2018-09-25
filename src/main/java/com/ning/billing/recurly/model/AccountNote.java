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

import com.google.common.base.Objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.ning.billing.recurly.model.Account;

import org.joda.time.DateTime;

@XmlRootElement(name = "note")
public class AccountNote extends RecurlyObject {

    @XmlTransient
    public static final String ACCOUNT_NOTES_RESOURCE = "/notes";

    @XmlElement(name = "account")
    private Account account;

    @XmlElement(name = "message")
    private String message;

    @XmlElement(name = "created_at")
    private DateTime createdAt;

    public String getMessage() {
        return message;
    }

    public void setMessage(final Object message) {
        this.message = stringOrNull(message);
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final Object createdAt) {
        this.createdAt = dateTimeOrNull(createdAt);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AccountNote{");
        sb.append("account=").append(account.getAccountCode());
        sb.append(", message=").append(message);
        sb.append(", createdAt=").append(createdAt);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final AccountNote that = (AccountNote) o;

        if (account != null ? !account.equals(that.account) : that.account != null) {
            return false;
        }
        if (message != null ? !message.equals(that.message) : that.message != null) {
            return false;
        }
        if (createdAt != null ? createdAt.compareTo(that.createdAt) != 0 : that.createdAt != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
                account,
                message,
                createdAt
        );
    }
}
