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
import java.util.Map;
import com.google.common.base.Objects;

@XmlRootElement(name = "transaction")
public class AbstractTransaction extends RecurlyObject {

    public static class VerificationResult {

        private String code;

        private String message;

        public VerificationResult() {
        }

        public VerificationResult(final String code, final String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public void setCode(final String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(final String message) {
            this.message = message;
        }

        public static VerificationResult as(final Object object) {
            if (isNull(object)) {
                return null;
            }

            if (object instanceof Map) {
                final Map map = (Map) object;
                return new VerificationResult(stringOrNull(map.get("code")), stringOrNull(map.get("")));
            }

            return new VerificationResult(null, object.toString());
        }
    }

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

    @XmlElement(name = "transaction_error")
    private TransactionError transactionError;

    @XmlElement(name = "source")
    private String source;

    @XmlElement(name = "ip_address")
    private String ipAddress;

    @XmlElement(name = "cvv_result")
    private VerificationResult cvvResult;

    @XmlElement(name = "avs_result")
    private VerificationResult avsResult;

    @XmlElement(name = "avs_result_street")
    private String avsResultStreet;

    @XmlElement(name = "avs_result_postal")
    private String avsResultPostal;

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

    public TransactionError getTransactionError() {
        return transactionError;
    }

    public void setTransactionError(final TransactionError transactionError) {
        this.transactionError = transactionError;
    }

    public String getSource() {
        return source;
    }

    public void setSource(final Object source) {
        this.source = stringOrNull(source);
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(final Object ipAddress) {
        this.ipAddress = stringOrNull(ipAddress);
    }

    public VerificationResult getCvvResult() {
        return cvvResult;
    }

    public void setCvvResult(final Object cvvResult) {
        this.cvvResult = VerificationResult.as(cvvResult);
    }

    public VerificationResult getAvsResult() {
        return avsResult;
    }

    public void setAvsResult(final Object avsResult) {
        this.avsResult = VerificationResult.as(avsResult);
    }

    public String getAvsResultStreet() {
        return avsResultStreet;
    }

    public void setAvsResultStreet(final Object avsResultStreet) {
        this.avsResultStreet = stringOrNull(avsResultStreet);
    }

    public String getAvsResultPostal() {
        return avsResultPostal;
    }

    public void setAvsResultPostal(final Object avsResultPostal) {
        this.avsResultPostal = stringOrNull(avsResultPostal);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

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
        if (transactionError != null ? !transactionError.equals(that.transactionError) : that.transactionError != null) {
            return false;
        }
        if (voidable != null ? !voidable.equals(that.voidable) : that.voidable != null) {
            return false;
        }
        if (source != null ? !source.equals(that.source) : that.source != null) {
            return false;
        }
        if (ipAddress != null ? !ipAddress.equals(that.ipAddress) : that.ipAddress != null) {
            return false;
        }
        if (avsResult != null ? !avsResult.equals(that.avsResult) : that.avsResult != null) {
            return false;
        }
        if (cvvResult != null ? !cvvResult.equals(that.cvvResult) : that.cvvResult != null) {
            return false;
        }
        if (avsResultStreet != null ? !avsResultStreet.equals(that.avsResultStreet) : that.avsResultStreet != null) {
            return false;
        }
        if (avsResultPostal != null ? !avsResultPostal.equals(that.avsResultPostal) : that.avsResultPostal != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
                action,
                amountInCents,
                status,
                reference,
                test,
                voidable,
                refundable,
                transactionError,
                source,
                ipAddress,
                cvvResult,
                avsResult,
                avsResultStreet,
                avsResultPostal
        );
    }
}
