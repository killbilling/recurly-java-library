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

import com.google.common.primitives.Booleans;
import org.joda.time.DateTime;

@XmlRootElement(name = "account_balance")
public class AccountBalance extends RecurlyObject {

    @XmlTransient
    public static final String ACCOUNT_BALANCE_RESOURCE = "/balance";

    @XmlElement(name = "past_due")
    private Boolean pastDue;

    @XmlElement(name = "balance_in_cents")
    private RecurlyUnitCurrency balanceInCents;

    @XmlElement(name = "processing_prepayment_balance_in_cents")
    private RecurlyUnitCurrency processingPrepaymentBalanceInCents;

    @XmlElement(name = "available_credit_balance_in_cents")
    private RecurlyUnitCurrency availableCreditBalanceInCents;

    public Boolean getPastDue() {
        return pastDue;
    }

    public void setPastDue(final Object pastDue) { this.pastDue = booleanOrNull(pastDue); }

    public RecurlyUnitCurrency getBalanceInCents() {
        return balanceInCents;
    }

    public void setBalanceInCents(final RecurlyUnitCurrency balanceInCents) { this.balanceInCents = balanceInCents; }

    public RecurlyUnitCurrency getProcessingPrepaymentBalanceInCents() {
        return processingPrepaymentBalanceInCents;
    }

    public void setProcessingPrepaymentBalanceInCents(final RecurlyUnitCurrency processingPrepaymentBalanceInCents) { this.processingPrepaymentBalanceInCents = processingPrepaymentBalanceInCents; }

    public RecurlyUnitCurrency getAvailableCreditBalanceInCents() {
        return availableCreditBalanceInCents;
    }

    public void setAvailableCreditBalanceInCents(final RecurlyUnitCurrency availableCreditBalanceInCents) { this.availableCreditBalanceInCents = availableCreditBalanceInCents; }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AccountBalance{");
        sb.append(", pastDue=").append(pastDue);
        sb.append(", balanceInCents=").append(balanceInCents);
        sb.append(", processingPrepaymentBalanceInCents=").append(processingPrepaymentBalanceInCents);
        sb.append(", availableCreditBalanceInCents=").append(availableCreditBalanceInCents);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final AccountBalance accountBalance = (AccountBalance) o;

        if (pastDue != null ? !pastDue.equals(accountBalance.pastDue) : accountBalance.pastDue != null) {
            return false;
        }
        if (balanceInCents != null ? !balanceInCents.equals(accountBalance.balanceInCents) : accountBalance.balanceInCents != null) {
            return false;
        }
        if (processingPrepaymentBalanceInCents != null ? !processingPrepaymentBalanceInCents.equals(accountBalance.processingPrepaymentBalanceInCents) : accountBalance.processingPrepaymentBalanceInCents != null) {
            return false;
        }
        if (availableCreditBalanceInCents != null ? !availableCreditBalanceInCents.equals(accountBalance.availableCreditBalanceInCents) : accountBalance.availableCreditBalanceInCents != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
                pastDue,
                balanceInCents,
                processingPrepaymentBalanceInCents,
                availableCreditBalanceInCents
        );
    }


}
