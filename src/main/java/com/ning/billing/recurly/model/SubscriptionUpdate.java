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

/**
 * Subscription object for update calls.
 * <p>
 * The timeframe parameter is specific to the update.
 */
public class SubscriptionUpdate extends AbstractSubscription {

    public static enum Timeframe {
        now,
        renewal,
        bill_date,
        term_end
    }

    @XmlElement
    private Timeframe timeframe;

    @XmlElement(name = "coupon_code")
    private String couponCode;

    @XmlElement(name = "collection_method")
    private String collectionMethod;

    @XmlElement(name = "shipping_address")
    private ShippingAddress shippingAddress;

    @XmlElement(name = "shipping_address_id")
    private Long shippingAddressId;

    @XmlElement(name = "billing_info_uuid")
    private String billingInfoUuid;

    @XmlElement(name = "billing_info")
    private BillingInfo billingInfo;

    @XmlElement(name = "net_terms")
    private Integer netTerms;

    @XmlElement(name = "po_number")
    private String poNumber;

    @XmlElement(name = "revenue_schedule_type")
    private RevenueScheduleType revenueScheduleType;

    @XmlElement(name = "remaining_billing_cycles")
    private Integer remainingBillingCycles;

    @XmlElement(name = "imported_trial")
    private Boolean importedTrial;

    @XmlElement(name = "renewal_billing_cycles")
    private Integer renewalBillingCycles;

    @XmlElement(name = "auto_renew")
    private Boolean autoRenew;

    @XmlElementWrapper(name = "subscription_add_ons")
    @XmlElement(name = "subscription_add_on")
    private SubscriptionAddOns addOns;

    public Timeframe getTimeframe() {
        return timeframe;
    }

    public void setTimeframe(final Timeframe timeframe) {
        this.timeframe = timeframe;
    }

    public String getCollectionMethod() {
        return collectionMethod;
    }

    public void setCollectionMethod(final Object collectionMethod) {
        this.collectionMethod = stringOrNull(collectionMethod);
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(final Object couponCode) {
        this.couponCode = stringOrNull(couponCode);
    }

    public void setShippingAddress(final ShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddressId(final Object shippingAddressId) {
        this.shippingAddressId = longOrNull(shippingAddressId);
    }

    public Integer getNetTerms() {
        return netTerms;
    }

    public String getBillingInfoUuid() {
      return billingInfoUuid;
    }

    public void setBillingInfoUuid(final Object billingInfoUuid) {
        this.billingInfoUuid = stringOrNull(billingInfoUuid);
    }

    public BillingInfo getBillingInfo() {
        return billingInfo;
    }

    public void setBillingInfo(BillingInfo billingInfo) {
        this.billingInfo = billingInfo;
    }

    public void setNetTerms(final Object netTerms) {
        this.netTerms = integerOrNull(netTerms);
    }

    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(Object poNumber) {
        this.poNumber = stringOrNull(poNumber);
    }

    public RevenueScheduleType getRevenueScheduleType() {
        return revenueScheduleType;
    }

    public void setRevenueScheduleType(final Object revenueScheduleType) {
        this.revenueScheduleType = enumOrNull(RevenueScheduleType.class, revenueScheduleType, true);
    }

    public Integer getRemainingBillingCycles() {
        return remainingBillingCycles;
    }

    public void setRemainingBillingCycles(final Object remainingBillingCycles) {
        this.remainingBillingCycles = integerOrNull(remainingBillingCycles);
    }

    public Boolean getImportedTrial() {
        return this.importedTrial;
    }

    public void setImportedTrial(final Object importedTrial) {
        this.importedTrial = booleanOrNull(importedTrial);
    }

    public Integer getRenewalBillingCycles() {
        return renewalBillingCycles;
    }

    public void setRenewalBillingCycles(final Object renewalBillingCycles) {
        this.renewalBillingCycles = integerOrNull(renewalBillingCycles);
    }

    public Boolean getAutoRenew() {
        return this.autoRenew;
    }

    public void setAutoRenew(final Object autoRenew) {
        this.autoRenew = booleanOrNull(autoRenew);
    }

    public SubscriptionAddOns getAddOns() {
        return addOns;
    }

    public void setAddOns(final SubscriptionAddOns addOns) {
        this.addOns = addOns;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final SubscriptionUpdate that = (SubscriptionUpdate) o;

        if (collectionMethod != null ? !collectionMethod.equals(that.collectionMethod) : that.collectionMethod != null) {
            return false;
        }
        if (timeframe != that.timeframe) {
            return false;
        }
        if (couponCode != null ? !couponCode.equals(that.couponCode) : that.couponCode != null) {
            return false;
        }
        if (shippingAddress != null ? !shippingAddress.equals(that.shippingAddress) : that.shippingAddress != null) {
            return false;
        }
        if (shippingAddressId != null ? !shippingAddressId.equals(that.shippingAddressId) : that.shippingAddressId != null) {
            return false;
        }
        if (billingInfoUuid != null ? !billingInfoUuid.equals(that.billingInfoUuid) : that.billingInfoUuid != null) {
            return false;
        }
        if (billingInfo != null ? !billingInfo.equals(that.billingInfo) : that.billingInfo != null) {
            return false;
        }
        if (customFields != null ? !customFields.equals(that.customFields) : that.customFields != null) {
            return false;
        }
        if (netTerms != null ? !netTerms.equals(that.netTerms) : that.netTerms != null) {
            return false;
        }
        if (poNumber != null ? !poNumber.equals(that.poNumber) : that.poNumber != null) {
            return false;
        }
        if (revenueScheduleType != null ? !revenueScheduleType.equals(that.revenueScheduleType) : that.revenueScheduleType != null) {
            return false;
        }
        if (remainingBillingCycles != null ? !remainingBillingCycles.equals(that.remainingBillingCycles) : that.remainingBillingCycles != null) {
            return false;
        }
        if (importedTrial != null ? !importedTrial.equals(that.importedTrial) : that.importedTrial != null) {
            return false;
        }
        if (renewalBillingCycles != null ? !renewalBillingCycles.equals(that.renewalBillingCycles) : that.renewalBillingCycles != null) {
            return false;
        }
        if (autoRenew != null ? !autoRenew.equals(that.autoRenew) : that.autoRenew != null) {
            return false;
        }
        if (addOns != null ? !addOns.equals(that.addOns) : that.addOns != null) {
          return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
                timeframe,
                couponCode,
                collectionMethod,
                shippingAddress,
                shippingAddressId,
                billingInfoUuid,
                billingInfo,
                customFields,
                netTerms,
                poNumber,
                revenueScheduleType,
                remainingBillingCycles,
                importedTrial,
                renewalBillingCycles,
                autoRenew,
                addOns
        );
    }
}
