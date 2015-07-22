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

@XmlRootElement(name = "transaction_error")
public class TransactionError extends RecurlyObject {

    @XmlElement(name = "error_code")
    private String errorCode;

    @XmlElement(name = "error_category")
    private String errorCategory;

    @XmlElement(name = "merchant_message")
    private String merchantMessage;

    @XmlElement(name = "customer_message")
    private String customerMessage;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(final Object errorCode) {
        this.errorCode = stringOrNull(errorCode);
    }

    public String getErrorCategory() {
        return errorCategory;
    }

    public void setErrorCategory(final Object errorCategory) {
        this.errorCategory = stringOrNull(errorCategory);
    }

    public String getMerchantMessage() {
        return merchantMessage;
    }

    public void setMerchantMessage(final Object merchantMessage) {
        this.merchantMessage = stringOrNull(merchantMessage);
    }

    public String getCustomerMessage() {
        return customerMessage;
    }

    public void setCustomerMessage(final Object customerMessage) {
        this.customerMessage = stringOrNull(customerMessage);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TransactionError{");
        sb.append("errorCode='").append(errorCode).append('\'');
        sb.append(", errorCategory='").append(errorCategory).append('\'');
        sb.append(", merchantMessage='").append(merchantMessage).append('\'');
        sb.append(", customerMessage='").append(customerMessage).append('\'');
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

        final TransactionError that = (TransactionError) o;

        if (customerMessage != null ? !customerMessage.equals(that.customerMessage) : that.customerMessage != null) {
            return false;
        }
        if (errorCategory != null ? !errorCategory.equals(that.errorCategory) : that.errorCategory != null) {
            return false;
        }
        if (errorCode != null ? !errorCode.equals(that.errorCode) : that.errorCode != null) {
            return false;
        }
        if (merchantMessage != null ? !merchantMessage.equals(that.merchantMessage) : that.merchantMessage != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = errorCode != null ? errorCode.hashCode() : 0;
        result = 31 * result + (errorCategory != null ? errorCategory.hashCode() : 0);
        result = 31 * result + (merchantMessage != null ? merchantMessage.hashCode() : 0);
        result = 31 * result + (customerMessage != null ? customerMessage.hashCode() : 0);
        return result;
    }
}
