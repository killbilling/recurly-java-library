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

@XmlRootElement(name = "external_subscription")
public class ExternalSubscription extends RecurlyObject {

    @XmlElement(name = "account")
    private Account account;

    @XmlElement(name = "external_product_reference")
    private ExternalProductReference externalProductReference;

    @XmlElement(name = "last_purchased")
    private DateTime lastPurchased;

    @XmlElement(name = "auto_renew")
    private Boolean autoRenew;

    @XmlElement(name = "in_grace_period")
    private Boolean inGracePeriod;

    @XmlElement(name = "app_identifier")
    private String appIdentifier;

    @XmlElement(name = "quantity")
    private Integer quantity;

    @XmlElement(name = "external_id")
    private String externalId;

    @XmlElement(name = "activated_at")
    private DateTime activatedAt;

    @XmlElement(name = "expires_at")
    private DateTime expiresAt;

    @XmlElement(name = "state")
    private String state;

    @XmlElement(name = "canceled_at")
    private DateTime canceledAt;

    @XmlElement(name = "trial_started_at")
    private DateTime trialStartedAt;

    @XmlElement(name = "trial_ends_at")
    private DateTime trialEndsAt;

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

    public ExternalProductReference getExternalProductReference() {
      return this.externalProductReference;
    }

    public void setExternalProductReference(final ExternalProductReference externalProductReference) {
      this.externalProductReference = externalProductReference;
    }

    public DateTime getLastPurchased() {
      return lastPurchased;
    }

    public void setLastPurchased(final Object lastPurchased) {
      this.lastPurchased = dateTimeOrNull(lastPurchased);
    }

    public Boolean getAutoRenew() {
      return autoRenew;
    }

    public void setAutoRenew(final Object autoRenew) {
      this.autoRenew = booleanOrNull(autoRenew);
    }

    public Boolean getInGracePeriod() {
      return inGracePeriod;
    }

    public void setInGracePeriod(final Object inGracePeriod) {
      this.inGracePeriod = booleanOrNull(inGracePeriod);
    }

    public String getAppIdentifier() {
      return this.appIdentifier;
    }

    public void setAppIdentifier(final Object appIdentifier) {
      this.appIdentifier = stringOrNull(appIdentifier);
    }

    public Integer getQuantity() {
      return quantity;
    }

    public void setQuantity(final Object quantity) {
      this.quantity = integerOrNull(quantity);
    }

    public String getExternalId() {
      return this.externalId;
    }

    public void setExternalId(final Object externalId) {
      this.externalId = stringOrNull(externalId);
    }

    public DateTime getActivatedAt() {
      return activatedAt;
    }

    public void setActivatedAt(final Object activatedAt) {
      this.activatedAt = dateTimeOrNull(activatedAt);
    }

    public DateTime getExpiresAt() {
      return expiresAt;
    }

    public void setExpiresAt(final Object expiresAt) {
      this.expiresAt = dateTimeOrNull(expiresAt);
    }

    public String getState() {
      return this.state;
    }

    public void setState(final Object state) {
      this.state = stringOrNull(state);
    }

    public DateTime getCreatedAt() {
      return createdAt;
    }

    public void setCreatedAt(final Object createdAt) {
      this.createdAt = dateTimeOrNull(createdAt);
    }

    public DateTime getCanceledAt() {
      return canceledAt;
    }

    public void setCanceledAt(final Object canceledAt) {
      this.canceledAt = dateTimeOrNull(canceledAt);
    }

    public DateTime getTrialStartedAt() {
      return trialStartedAt;
    }

    public void setTrialStartedAt(final Object trialStartedAt) {
      this.trialStartedAt = dateTimeOrNull(trialStartedAt);
    }

    public DateTime getTrialEndsAt() {
      return trialEndsAt;
    }

    public void setTrialEndsAt(final Object trialEndsAt) {
      this.trialEndsAt = dateTimeOrNull(trialEndsAt);
    }

    public DateTime getUpdatedAt() {
      return updatedAt;
    }

    public void setUpdatedAt(final Object updatedAt) {
      this.updatedAt = dateTimeOrNull(updatedAt);
    }
}