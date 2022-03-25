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
import java.util.Map;

@XmlRootElement(name = "errors")
public class Errors extends RecurlyObject {

    @XmlElement(name = "transaction_error")
    private TransactionError transactionError;

    @XmlElement(name = "transaction")
    private Transaction transaction;

    @XmlElement(name = "error")
    private RecurlyErrors recurlyErrors;

    public TransactionError getTransactionError() {
        return transactionError;
    }

    public void setTransactionError(final TransactionError transactionError) {
        this.transactionError = transactionError;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(final Transaction transaction) {
        this.transaction = transaction;
    }

    public RecurlyErrors getRecurlyErrors() {
        return recurlyErrors;
    }

    public void setRecurlyErrors(final Object recurlyErrors) {
        if (recurlyErrors instanceof Map) {
            final RecurlyError error = new RecurlyError();
            error.setField((String) ((Map) recurlyErrors).get("field"));
            error.setSymbol((String) ((Map) recurlyErrors).get("symbol"));
            error.setMessage((String) ((Map) recurlyErrors).get("message"));

            if (this.recurlyErrors == null) {
                this.recurlyErrors = new RecurlyErrors();
            }
            this.recurlyErrors.add(error);
        } else {
            this.recurlyErrors = (RecurlyErrors) recurlyErrors;
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Errors{");
        sb.append("transactionError=").append(transactionError);
        sb.append(", transaction=").append(transaction);
        sb.append(", recurlyErrors=").append(recurlyErrors);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Errors errors = (Errors) o;

        if (recurlyErrors != null ? !recurlyErrors.equals(errors.recurlyErrors) : errors.recurlyErrors != null) {
            return false;
        }
        if (transaction != null ? !transaction.equals(errors.transaction) : errors.transaction != null) {
            return false;
        }
        if (transactionError != null ? !transactionError.equals(errors.transactionError) : errors.transactionError != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
                transactionError,
                transaction,
                recurlyErrors
        );
    }
}