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

import com.google.common.base.Objects;

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

    @XmlElement(name = "gateway_error_code")
    private String gatewayErrorCode;

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

    public String getGatewayErrorCode() {
        return gatewayErrorCode;
    }

    public void setGatewayErrorCode(String gatewayErrorCode) {
        this.gatewayErrorCode = gatewayErrorCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        TransactionError that = (TransactionError) o;

        if (errorCode != null ? !errorCode.equals(that.errorCode) : that.errorCode != null) return false;
        if (errorCategory != null ? !errorCategory.equals(that.errorCategory) : that.errorCategory != null)
            return false;
        if (merchantMessage != null ? !merchantMessage.equals(that.merchantMessage) : that.merchantMessage != null)
            return false;
        if (customerMessage != null ? !customerMessage.equals(that.customerMessage) : that.customerMessage != null)
            return false;
        return gatewayErrorCode != null ? gatewayErrorCode.equals(that.gatewayErrorCode) : that.gatewayErrorCode == null;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(errorCode, errorCategory, merchantMessage, customerMessage, gatewayErrorCode);
    }

    @Override
    public String toString() {
        return "TransactionError{" +
                "errorCode='" + errorCode + '\'' +
                ", errorCategory='" + errorCategory + '\'' +
                ", merchantMessage='" + merchantMessage + '\'' +
                ", customerMessage='" + customerMessage + '\'' +
                ", gatewayErrorCode='" + gatewayErrorCode + '\'' +
                '}';
    }

}
