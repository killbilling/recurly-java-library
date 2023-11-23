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
import javax.xml.bind.annotation.XmlRootElement;

import org.joda.time.DateTime;

@XmlRootElement(name = "external_payment_phase")
public class ExternalPaymentPhase extends RecurlyObject {

  @XmlElement(name = "started_at")
  private DateTime startedAt;

  @XmlElement(name = "ends_at")
  private DateTime endsAt;

  @XmlElement(name = "starting_billing_period_index")
  private int startingBillingPeriodIndex;

  @XmlElement(name = "ending_billing_period_index")
  private int endingBillingPeriodIndex;

  @XmlElement(name = "offer_type")
  private String offerType;

  @XmlElement(name = "offer_name")
  private String offerName;

  @XmlElement(name = "period_count")
  private int periodCount;

  @XmlElement(name = "period_length")
  private String periodLength;

  @XmlElement(name = "amount")
  private BigDecimal amount;

  @XmlElement(name = "currency")
  private String currency;

  @XmlElement(name = "created_at")
  private DateTime createdAt;

  @XmlElement(name = "updated_at")
  private DateTime updatedAt;

  public DateTime getStartedAt() {
    return this.startedAt;
  }

  public void setStartedAt(final Object startedAt) {
    this.startedAt = dateTimeOrNull(startedAt);
  }

  public DateTime getEndsAt() {
    return this.endsAt;
  }

  public void setEndsAt(final Object endsAt) {
    this.endsAt = dateTimeOrNull(endsAt);
  }

  public int getStartingBillingPeriodIndex() {
    return this.startingBillingPeriodIndex;
  }

  public void setStartingBillingPeriodIndex(final Object startingBillingPeriodIndex) {
    this.startingBillingPeriodIndex = integerOrNull(startingBillingPeriodIndex);
  }

  public int getEndingBillingPeriodIndex() {
    return this.endingBillingPeriodIndex;
  }

  public void setEndingBillingPeriodIndex(final Object endingBillingPeriodIndex) {
    this.endingBillingPeriodIndex = integerOrNull(endingBillingPeriodIndex);
  }

  public String getOfferType() {
    return this.offerType;
  }

  public void setOfferType(final Object offerType) {
    this.offerType = stringOrNull(offerType);
  }

  public String getOfferName() {
    return this.offerName;
  }

  public void setOfferName(final Object offerName) {
    this.offerName = stringOrNull(offerName);
  }

  public int getPeriodCount() {
    return this.periodCount;
  }

  public void setPeriodCount(final Object periodCount) {
    this.periodCount = integerOrNull(periodCount);
  }

  public String getPeriodLength() {
    return this.periodLength;
  }

  public void setPeriodLength(final Object periodLength) {
    this.periodLength = stringOrNull(periodLength);
  }

  public BigDecimal getAmount() {
    return this.amount;
  }

  public void setAmount(final Object amount) {
    this.amount = bigDecimalOrNull(amount);
  }

  public String getCurrency() {
    return this.currency;
  }

  public void setCurrency(final Object currency) {
    this.currency = stringOrNull(currency);
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

  @Override
  public String toString() {
      final StringBuilder sb = new StringBuilder("ExternalPaymentPhase{");
      sb.append("started_at=").append(startedAt);
      sb.append(", ends_at=").append(endsAt);
      sb.append(", starting_billing_period_index=").append(startingBillingPeriodIndex);
      sb.append(", ending_billing_period_index=").append(endingBillingPeriodIndex);
      sb.append(", offer_type=").append(offerType);
      sb.append(", offer_name=").append(offerName);
      sb.append(", period_count=").append(periodCount);
      sb.append(", period_length=").append(periodLength);
      sb.append(", amount=").append(amount);
      sb.append(", currency=").append(currency);
      sb.append(", createdAt=").append(createdAt);
      sb.append(", updatedAt=").append(updatedAt);
      sb.append('}');
      return sb.toString();
  }
}
