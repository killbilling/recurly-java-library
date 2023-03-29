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

import org.joda.time.DateTime;

@XmlRootElement(name = "external_charge")
public class ExternalCharge extends RecurlyObject {
  
  @XmlElement(name = "account")
  private Account account;

  @XmlElement(name = "external_invoice")
  private ExternalInvoice externalInvoice;

  @XmlElement(name = "description")
  private String description;

  @XmlElement(name = "currency")
  private String currency;

  @XmlElement(name = "unit_amount")
  private Integer unitAmount;

  @XmlElement(name = "quantity")
  private Integer quantity;

  @XmlElement(name = "created_at")
  private DateTime createdAt;

  @XmlElement(name = "updated_at")
  private DateTime updatedAt;

  public Account getAccount() {
    return this.account;
  }

  public void setAccount(final Account account) {
    this.account = account;
  }

  public ExternalInvoice getExternalInvoice() {
    return this.externalInvoice;
  }

  public void setExternalInvoice(final ExternalInvoice externalInvoice) {
    this.externalInvoice = externalInvoice;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(final Object description) {
    this.description = stringOrNull(description);
  }

  public String getCurrency() {
    return this.currency;
  }

  public void setCurrency(final Object currency) {
    this.currency = stringOrNull(currency);
  }

  public Integer getUnitAmount() {
    return this.unitAmount;
  }

  public void setUnitAmount(final Object unitAmount) {
    this.unitAmount = integerOrNull(unitAmount);
  }

  public Integer getQuantity() {
    return this.quantity;
  }

  public void setQuantity(final Object quantity) {
    this.quantity = integerOrNull(quantity);
  }

  public DateTime getCreatedAt() {
    return this.createdAt;
  }

  public void setCreatedAt(final Object createdAt) {
    this.createdAt = dateTimeOrNull(createdAt);
  }

  public DateTime getUpdatedAt() {
    return this.updatedAt;
  }

  public void setUpdatedAt(final Object updatedAt) {
    this.updatedAt = dateTimeOrNull(updatedAt);
  }
}
