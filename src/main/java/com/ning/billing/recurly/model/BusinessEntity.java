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
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;
import org.joda.time.DateTime;
import java.util.List;

@XmlRootElement(name = "business_entity")
public class BusinessEntity extends RecurlyObject {

    @XmlElement(name = "id")
    private String id;

    @XmlElement(name = "code")
    private String code;

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "invoice_display_address")
    private InvoiceDisplayAddress invoiceDisplayAddress;

    @XmlElement(name = "tax_address")
    private TaxAddress taxAddress;

    @XmlElement(name = "origin_tax_address_source")
    private String originTaxAddressSource;

    @XmlElement(name = "destination_tax_address_source")
    private String destinationTaxAddressSource;

    @XmlList
    @XmlElementWrapper(name = "subscriber_location_countries")
    @XmlElement(name = "subscriber_location_country")
    private List<String> subscriberLocationCountries;

    @XmlElement(name = "default_vat_number")
    private String defaultVatNumber;

    @XmlElement(name = "default_registration_number")
    private String defaultRegistrationNumber;

    @XmlElement(name = "default_liability_gl_account_id")
    private String defaultLiabilityGlAccountId;

    @XmlElement(name = "default_revenue_gl_account_id")
    private String defaultRevenueGlAccountId;

    @XmlElement(name = "created_at")
    private DateTime createdAt;

    @XmlElement(name = "updated_at")
    private DateTime updatedAt;

    @XmlElementWrapper(name = "invoices")
    private Invoice invoices;

    public String getId() {
      return this.id;
    }

    public void setId(final Object id) {
      this.id = stringOrNull(id);
    }

    public String getCode() {
      return this.code;
    }

    public void setCode(final Object code) {
      this.code = stringOrNull(code);
    }

    public String getName() {
      return this.name;
    }

    public void setName(final Object name) {
      this.name = stringOrNull(name);
    }

    public InvoiceDisplayAddress getInvoiceDisplayAddress() {
      return this.invoiceDisplayAddress;
    }

    public void setInvoiceDisplayAddress(final InvoiceDisplayAddress invoiceDisplayAddress) {
      this.invoiceDisplayAddress = invoiceDisplayAddress;
    }

    public void setTaxAddress(final TaxAddress taxAddress) {
      this.taxAddress = taxAddress;
    }

    public TaxAddress getTaxAddress() {
      return this.taxAddress;
    }

    public void setOriginTaxAddressSource(final Object originTaxAddressSource) {
      this.originTaxAddressSource = stringOrNull(originTaxAddressSource);
    }

    public String getOriginTaxAddressSource() {
      return this.originTaxAddressSource;
    }

    public void setDestinationTaxAddressSource(final Object destinationTaxAddressSource) {
      this.destinationTaxAddressSource = stringOrNull(destinationTaxAddressSource);
    }

    public String getDestinationTaxAddressSource() {
      return this.destinationTaxAddressSource;
    }

    public void setDefaultVatNumber(final Object defaultVatNumber) {
      this.defaultVatNumber = stringOrNull(defaultVatNumber);
    }

    public String getDefaultVatNumber() {
      return this.defaultVatNumber;
    }

    public void setDefaultRegistrationNumber(final Object defaultRegistrationNumber) {
      this.defaultRegistrationNumber = stringOrNull(defaultRegistrationNumber);
    }

    public String getDefaultRegistrationNumber() {
      return this.defaultRegistrationNumber;
    }

    public void setDefaultLiabilityGlAccountId(final Object defaultLiabilityGlAccountId) {
      this.defaultLiabilityGlAccountId = stringOrNull(defaultLiabilityGlAccountId);
    }

    public String getDefaultLiabilityGlAccountId() {
      return this.defaultLiabilityGlAccountId;
    }

    public void setDefaultRevenueGlAccountId(final Object defaultRevenueGlAccountId) {
      this.defaultRevenueGlAccountId = stringOrNull(defaultRevenueGlAccountId);
    }

    public String getDefaultRevenueGlAccountId() {
      return this.defaultRevenueGlAccountId;
    }

    public DateTime getCreatedAt() {
      return this.createdAt;
    }

    public void setCreatedAt(final Object createdAt) {
      this.createdAt = dateTimeOrNull(createdAt);
    }

    public DateTime getUpdatedAt() {
      return updatedAt;
    }

    public void setUpdatedAt(final Object updatedAt) {
      this.updatedAt = dateTimeOrNull(updatedAt);
    }

    public Invoice getInvoices() {
        return this.invoices;
    }

    public void setInvoices(final Invoice invoices) {
        this.invoices = invoices;
    }

    public void setSubscriberLocationCountries(final List<String> subscriberLocationCountries) {
        this.subscriberLocationCountries = subscriberLocationCountries;
    }

    public List<String> getSubscriberLocationCountries() {
        return this.subscriberLocationCountries;
    }

    private String getInvoiceHref() {
      if (getInvoices() == null) {
        return null;
      }
      return getInvoices().getHref();
    }


    @Override
    public String toString() {
      return "{" +
        " id='" + getId() + "'" +
        ", code='" + getCode() + "'" +
        ", name='" + getName() + "'" +
        ", invoiceDisplayAddress='" + getInvoiceDisplayAddress() + "'" +
        ", taxAddress='" + getTaxAddress() + "'" +
        ", originTaxAddressSource='" + getOriginTaxAddressSource() + "'" +
        ", destinationTaxAddressSource='" + getDestinationTaxAddressSource() + "'" +
        ", subscriberLocationCountries='" + getSubscriberLocationCountries() + "'" +
        ", defaultVatNumber='" + getDefaultVatNumber() + "'" +
        ", defaultRegistrationNumber='" + getDefaultRegistrationNumber() + "'" +
        ", defaultLiabilityGlAccountId='" + getDefaultLiabilityGlAccountId() + "'" +
        ", defaultRevenueGlAccountId='" + getDefaultRevenueGlAccountId() + "'" +
        ", createdAt='" + getCreatedAt() + "'" +
        ", updatedAt='" + getUpdatedAt() + "'" +
        ", invoices='" + getInvoiceHref() + "'" +
        "}";
    }

}