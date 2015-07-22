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
import javax.xml.bind.annotation.XmlElementWrapper;

public class SubscriptionNotes extends AbstractSubscription {

    @XmlElement(name = "terms_and_conditions")
    private String termsAndConditions;
    
    @XmlElement(name = "customer_notes")
    private String customerNotes;
    
    @XmlElement(name = "vat_reverse_charge_notes")
    private String vatReverseChargeNotes;    

    public String getTermsAndConditions() {
        return termsAndConditions;
    }

    public void setTermsAndConditions(final Object termsAndConditions) {
        this.termsAndConditions = stringOrNull(termsAndConditions);
    }
    
    public String getCustomerNotes() {
        return customerNotes;
    }

    public void setCustomerNotes(final Object customerNotes) {
        this.customerNotes = stringOrNull(customerNotes);
    }
    
    public String getVatReverseChargeNotes() {
        return vatReverseChargeNotes;
    }

    public void setVatReverseChargeNotes(final Object getVatReverseChargeNotes) {
        this.vatReverseChargeNotes = stringOrNull(vatReverseChargeNotes);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Subscription{");
        sb.append("termsAndConditions=").append(termsAndConditions).append('\'');
        sb.append(", customerNotes=").append(customerNotes).append('\'');
        sb.append(", vatReverseChargeNotes=").append(vatReverseChargeNotes).append('\'');
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
        if (!super.equals(o)) {
            return false;
        }

        final SubscriptionNotes that = (SubscriptionNotes) o;

        if (termsAndConditions != null ? !termsAndConditions.equals(that.termsAndConditions) : that.termsAndConditions != null) {
            return false;
        }
        
        if (customerNotes != null ? !customerNotes.equals(that.customerNotes) : that.customerNotes != null) {
            return false;
        }
        
        if (vatReverseChargeNotes != null ? !vatReverseChargeNotes.equals(that.vatReverseChargeNotes) : that.vatReverseChargeNotes != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (termsAndConditions != null ? termsAndConditions.hashCode() : 0);
        result = 31 * result + (customerNotes != null ? customerNotes.hashCode() : 0);
        result = 31 * result + (vatReverseChargeNotes != null ? vatReverseChargeNotes.hashCode() : 0);             
        return result;
    }
}
