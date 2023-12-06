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

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.joda.time.DateTime;

@XmlRootElement(name = "external_invoice")
public class ExternalInvoice extends RecurlyObject {
  
  @XmlElement(name = "account")
  private Account account;

  @XmlElement(name = "external_subscription")
  private ExternalSubscription externalSubscription;

  @XmlElement(name = "external_payment_phase")
  private ExternalPaymentPhase externalPaymentPhase;

  @XmlElement(name = "external_id")
  private String externalId;

  @XmlElement(name = "state")
  private String state;

  @XmlElement(name = "currency")
  private String currency;

  @XmlElement(name = "total")
  private BigDecimal total;

  @XmlElement(name = "purchased_at")
  private DateTime purchasedAt;

  @XmlElement(name = "created_at")
  private DateTime createdAt;

  @XmlElement(name = "updated_at")
  private DateTime updatedAt;

  @XmlElementWrapper(name = "line_items")
  @XmlElement(name = "external_charge")
  private LineItems lineItems;

  public Account getAccount() {
    return this.account;
  }

  public void setAccount(final Account account) {
    this.account = account;
  }

  public ExternalSubscription getExternalSubscription() {
    return this.externalSubscription;
  }

  public void setExternalSubscription(final ExternalSubscription externalSubscription) {
    this.externalSubscription = externalSubscription;
  }

  public ExternalPaymentPhase getExternalPaymentPhase() {
    return this.externalPaymentPhase;
  }

  public void setExternalPaymentPhase(final ExternalPaymentPhase externalPaymentPhase) {
    this.externalPaymentPhase = externalPaymentPhase;
  }

  public String getExternalId() {
    return this.externalId;
  }

  public void setExternalId(final Object externalId) {
    this.externalId = stringOrNull(externalId);
  }

  public String getState() {
    return this.state;
  }

  public void setState(final Object state) {
    this.state = stringOrNull(state);
  }

  public String getCurrency() {
    return this.currency;
  }

  public void setCurrency(final Object currency) {
    this.currency = stringOrNull(currency);
  }

  public BigDecimal getTotal() {
    return this.total;
  }

  public void setTotal(final Object total) {
    this.total = bigDecimalOrNull(total);
  }

  public DateTime getPurchasedAt() {
    return this.purchasedAt;
  }

  public void setPurchasedAt(final Object purchasedAt) {
    this.purchasedAt = dateTimeOrNull(purchasedAt);
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

  public LineItems getLineItems() {
    return this.lineItems;
  }

  public void setLineItems(final LineItems lineItems) {
    this.lineItems = lineItems;
  }
}
