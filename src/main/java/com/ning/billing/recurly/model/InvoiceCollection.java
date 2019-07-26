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
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "invoice_collection")
public class InvoiceCollection extends RecurlyObject {

    @XmlElement(name = "charge_invoice")
    private Invoice chargeInvoice;

    @XmlElementWrapper(name = "credit_invoices")
    @XmlElement(name = "credit_invoice")
    private CreditInvoices creditInvoices;

    public void setChargeInvoice(final Invoice chargeInvoice) {
        this.chargeInvoice = chargeInvoice;
    }

    public Invoice getChargeInvoice() {
        return this.chargeInvoice;
    }

    public void setCreditInvoices(final CreditInvoices creditInvoices) {
        this.creditInvoices = creditInvoices;
    }

    public CreditInvoices getCreditInvoices() {
        return this.creditInvoices;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("InvoiceCollection{");
        sb.append("chargeInvoice='").append(chargeInvoice).append('\'');
        sb.append(", creditInvoices='").append(creditInvoices).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final InvoiceCollection collection = (InvoiceCollection) o;

        if (chargeInvoice != null ? !chargeInvoice.equals(collection.chargeInvoice) : collection.chargeInvoice != null) {
            return false;
        }
        if (creditInvoices != null ? !creditInvoices.equals(collection.creditInvoices) : collection.creditInvoices != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
                chargeInvoice,
                creditInvoices
        );
    }
}
