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
import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.util.List;

@XmlRootElement(name = "subscription")
public class Subscription extends AbstractSubscription {

    @XmlElement(name = "account")
    private Account account;

    @XmlElement(name = "invoice")
    private Invoice invoice;

    @XmlElement(name = "plan")
    private Plan plan;

    @XmlElement(name = "uuid")
    private String uuid;

    @XmlElement(name = "state", required = false)
    private String state;

    @XmlElement(name = "tax_in_cents")
    private Integer taxInCents;

    @XmlElement(name = "tax_region")
    private String taxRegion;

    @XmlElement(name = "tax_type")
    private String taxType;

    @XmlElement(name = "tax_rate")
    private BigDecimal taxRate;

    @XmlElement(name = "currency")
    private String currency;

    @XmlElement(name = "activated_at")
    private DateTime activatedAt;

    @XmlElement(name = "canceled_at")
    private DateTime canceledAt;

    @XmlElement(name = "expires_at")
    private DateTime expiresAt;

    @XmlElement(name = "remaining_billing_cycles")
    private Integer remainingBillingCycles;

    @XmlElement(name = "current_period_started_at")
    private DateTime currentPeriodStartedAt;

    @XmlElement(name = "current_period_ends_at")
    private DateTime currentPeriodEndsAt;

    @XmlElement(name = "trial_started_at")
    private DateTime trialStartedAt;

    @XmlElement(name = "trial_ends_at")
    private DateTime trialEndsAt;

    @XmlElement(name = "pending_subscription")
    private Subscription pendingSubscription;

    @XmlElement(name = "starts_at")
    private DateTime startsAt;

    @XmlElement(name = "updated_at")
    private DateTime updatedAt;

    @XmlElement(name = "collection_method")
    private String collectionMethod;

    @XmlElement(name = "net_terms")
    private Integer netTerms;

    @XmlElement(name = "coupon_code")
    private String couponCode;

    @XmlList
    @XmlElementWrapper(name = "coupon_codes")
    private List<String> couponCodes;

    //Purchase Order Number
    @XmlElement(name = "po_number")
    private String poNumber;
    
    @XmlElement(name = "terms_and_conditions")
    private String termsAndConditions;
    
    @XmlElement(name = "customer_notes")
    private String customerNotes;

    @XmlElement(name = "first_renewal_date")
    private DateTime firstRenewalDate;
    
    @XmlElement(name = "bulk")
    private Boolean bulk;

    @XmlElement(name = "revenue_schedule_type")
    private RevenueScheduleType revenueScheduleType;

    @XmlElement(name = "gift_card")
    private GiftCard giftCard;

    @XmlElement(name = "started_with_gift")
    private Boolean startedWithGift;

    @XmlElement(name = "converted_at")
    private DateTime convertedAt;

    @XmlElement(name = "shipping_address")
    private ShippingAddress shippingAddress;

    @XmlElement(name = "shipping_address_id")
    private Long shippingAddressId;

    @XmlElement(name = "no_billing_info_reason")
    private String noBillingInfoReason;

    @XmlElement(name = "imported_trial")
    private Boolean importedTrial;

    @XmlElement(name = "credit_customer_notes")
    private String creditCustomerNotes;

    @XmlElement(name = "invoice_collection")
    private InvoiceCollection invoiceCollection;

    @XmlElement(name = "remaining_pause_cycles")
    private Integer remainingPauseCycles;

    @XmlElement(name = "paused_at")
    private DateTime pausedAt;

    @XmlElement(name = "auto_renew")
    private Boolean autoRenew;

    @XmlElement(name = "renewal_billing_cycles")
    private Integer renewalBillingCycles;

    @XmlElement(name = "first_bill_date")
    private DateTime firstBillDate;

    @XmlElement(name = "next_bill_date")
    private DateTime nextBillDate;

    @XmlElement(name = "current_term_started_at")
    private DateTime currentTermStartedAt;

    @XmlElement(name = "current_term_ends_at")
    private DateTime currentTermEndsAt;

    public Account getAccount() {
        if (account != null && account.getHref() != null && !account.getHref().isEmpty()) {
            account = fetch(account, Account.class);
        }
        return account;
    }

    public void setAccount(final Account account) {
        this.account = account;
    }

    public Invoice getInvoice() {
        if (invoice != null && invoice.getHref() != null && !invoice.getHref().isEmpty()) {
            invoice = fetch(invoice, Invoice.class);
        }
        return invoice;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(final Plan plan) {
        this.plan = plan;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(final Object uuid) {
        this.uuid = stringOrNull(uuid);
    }

    public String getState() {
        return state;
    }

    public void setState(final Object state) {
        this.state = stringOrNull(state);
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(final Object currency) {
        this.currency = stringOrNull(currency);
    }

    public Integer getTaxInCents() {
        return taxInCents;
    }

    public void setTaxInCents(final Object taxInCents) {
        this.taxInCents = integerOrNull(taxInCents);
    }

    public void setTaxRegion(final Object taxRegion) {
        this.taxRegion = stringOrNull(taxRegion);
    }

    public String getTaxRegion() {
        return taxRegion;
    }

    public void setTaxRate(final Object taxRate) {
        this.taxRate = bigDecimalOrNull(taxRate);
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxType(final Object taxType) {
        this.taxType = stringOrNull(taxType);
    }

    public String getTaxType() {
        return taxType;
    }

    public DateTime getActivatedAt() {
        return activatedAt;
    }

    public void setActivatedAt(final Object activatedAt) {
        this.activatedAt = dateTimeOrNull(activatedAt);
    }

    public DateTime getCanceledAt() {
        return canceledAt;
    }

    public void setCanceledAt(final Object canceledAt) {
        this.canceledAt = dateTimeOrNull(canceledAt);
    }

    public DateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(final Object expiresAt) {
        this.expiresAt = dateTimeOrNull(expiresAt);
    }

    public DateTime getCurrentPeriodStartedAt() {
        return currentPeriodStartedAt;
    }

    public Integer getRemainingBillingCycles() {
        return remainingBillingCycles;
    }

    public void setRemainingBillingCycles(final Object remainingBillingCycles) {
        this.remainingBillingCycles = integerOrNull(remainingBillingCycles);
    }

    public void setCurrentPeriodStartedAt(final Object currentPeriodStartedAt) {
        this.currentPeriodStartedAt = dateTimeOrNull(currentPeriodStartedAt);
    }

    public DateTime getCurrentPeriodEndsAt() {
        return currentPeriodEndsAt;
    }

    public void setCurrentPeriodEndsAt(final Object currentPeriodEndsAt) {
        this.currentPeriodEndsAt = dateTimeOrNull(currentPeriodEndsAt);
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

    public Subscription getPendingSubscription() {
        return pendingSubscription;
    }

    public void setPendingSubscription(final Subscription pendingSubscription) {
        this.pendingSubscription = pendingSubscription;
    }

    public DateTime getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(final Object startsAt) {
        this.startsAt = dateTimeOrNull(startsAt);
    }

    public String getCollectionMethod() {
        return collectionMethod;
    }

    public void setCollectionMethod(Object collectionMethod) {
        this.collectionMethod = stringOrNull(collectionMethod);
    }

    public Integer getNetTerms() {
        return netTerms;
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

    public DateTime getFirstRenewalDate() {
        return firstRenewalDate;
    }

    public String getCustomerNotes() {
        return customerNotes;
    }

    public void setCustomerNotes(Object customerNotes) {
        this.customerNotes = stringOrNull(customerNotes);
    }

    public String getTermsAndConditions() {
        return termsAndConditions;
    }

    public void setTermsAndConditions(Object termsAndConditions) {
        this.termsAndConditions = stringOrNull(termsAndConditions);
    }

    public void setFirstRenewalDate(final Object firstRenewalDate) {
        this.firstRenewalDate = dateTimeOrNull(firstRenewalDate);
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(final String couponCode) {
        this.couponCode = couponCode;
    }

    public void setCouponCodes(final List<String> couponCodes) {
        this.couponCodes = couponCodes;
    }

    public List<String> getCouponCodes() {
        return this.couponCodes;
    }

    public void setBulk(final Object bulk) {
        this.bulk = booleanOrNull(bulk);
    }

    public RevenueScheduleType getRevenueScheduleType() {
        return revenueScheduleType;
    }

    public void setRevenueScheduleType(final Object revenueScheduleType) {
        this.revenueScheduleType = enumOrNull(RevenueScheduleType.class, revenueScheduleType, true);
    }

    public GiftCard getGiftCard() {
        return giftCard;
    }

    public void setGiftCard(final GiftCard giftCard) {
        this.giftCard = giftCard;
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

    public DateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(final Object updatedAt) {
        this.updatedAt = dateTimeOrNull(updatedAt);
    }

    public Boolean getStartedWithGift() {
        return this.startedWithGift;
    }

    public void setStartedWithGift(final Object startedWithGift) {
        this.startedWithGift = booleanOrNull(startedWithGift);
    }

    public DateTime getConvertedAt() {
        return this.convertedAt;
    }

    public void setConvertedAt(final Object convertedAt) {
        this.convertedAt = dateTimeOrNull(convertedAt);
    }

    public String getNoBillingInfoReason() {
        return this.noBillingInfoReason;
    }

    public void setNoBillingInfoReason(final Object noBillingInfoReason) {
        this.noBillingInfoReason = stringOrNull(noBillingInfoReason);
    }

    public Boolean getImportedTrial() {
        return this.importedTrial;
    }

    public void setImportedTrial(final Object importedTrial) {
        this.importedTrial = booleanOrNull(importedTrial);
    }


    public String getCreditCustomerNotes() {
        return creditCustomerNotes;
    }

    public void setCreditCustomerNotes(final Object creditCustomerNotes) {
        this.creditCustomerNotes = stringOrNull(creditCustomerNotes);
    }

    public InvoiceCollection getInvoiceCollection() {
        return invoiceCollection;
    }

    public void setInvoiceCollection(final InvoiceCollection invoiceCollection) {
        this.invoiceCollection = invoiceCollection;
    }

    public Integer getRemainingPauseCycles() {
        return remainingPauseCycles;
    }

    public void setRemainingPauseCycles(final Object remainingPauseCycles) {
        this.remainingPauseCycles = integerOrNull(remainingPauseCycles);
    }

    public DateTime getPausedAt() {
        return this.pausedAt;
    }

    public void setPausedAt(final Object pausedAt) {
        this.pausedAt = dateTimeOrNull(pausedAt);
    }

    public Boolean getAutoRenew() {
        return this.autoRenew;
    }

    public void setAutoRenew(final Object autoRenew) {
        this.autoRenew = booleanOrNull(autoRenew);
    }

    public Integer getRenewalBillingCycles() {
        return renewalBillingCycles;
    }

    public void setRenewalBillingCycles(final Object renewalBillingCycles) {
        this.renewalBillingCycles = integerOrNull(renewalBillingCycles);
    }

    public DateTime getFirstBillDate() {
        return firstBillDate;
    }

    protected void setFirstBillDate(final Object firstBillDate) {
        this.firstBillDate = dateTimeOrNull(firstBillDate);
    }

    public DateTime getNextBillDate() {
        return nextBillDate;
    }

    protected void setNextBillDate(final Object nextBillDate) {
        this.nextBillDate = dateTimeOrNull(nextBillDate);
    }

    public DateTime getCurrentTermStartedAt() {
        return currentTermStartedAt;
    }

    protected void setCurrentTermStartedAt(final Object currentTermStartedAt) {
        this.currentTermStartedAt = dateTimeOrNull(currentTermStartedAt);
    }

    public DateTime getCurrentTermEndsAt() {
        return currentTermEndsAt;
    }

    protected void setCurrentTermEndsAt(final Object currentTermEndsAt) {
        this.currentTermEndsAt = dateTimeOrNull(currentTermEndsAt);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Subscription");
        sb.append("{account=").append(account);
        sb.append(", plan=").append(plan);
        sb.append(", uuid='").append(uuid).append('\'');
        sb.append(", state='").append(state).append('\'');
        sb.append(", unitAmountInCents=").append(unitAmountInCents);
        sb.append(", currency='").append(currency).append('\'');
        sb.append(", quantity=").append(quantity);
        sb.append(", couponCode=").append(couponCode);
        sb.append(", couponCodes=").append(couponCodes);
        sb.append(", activatedAt=").append(activatedAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", canceledAt=").append(canceledAt);
        sb.append(", expiresAt=").append(expiresAt);
        sb.append(", remainingBillingCycles=").append(remainingBillingCycles);
        sb.append(", currentPeriodStartedAt=").append(currentPeriodStartedAt);
        sb.append(", currentPeriodEndsAt=").append(currentPeriodEndsAt);
        sb.append(", trialStartedAt=").append(trialStartedAt);
        sb.append(", trialEndsAt=").append(trialEndsAt);
        sb.append(", startsAt=").append(startsAt);
        sb.append(", addOns=").append(addOns);
        sb.append(", pendingSubscription=").append(pendingSubscription);
        sb.append(", firstRenewalDate=").append(firstRenewalDate);
        sb.append(", bulk=").append(bulk);
        sb.append(", revenueScheduleType=").append(revenueScheduleType);
        sb.append(", giftCard=").append(giftCard);
        sb.append(", taxInCents=").append(taxInCents);
        sb.append(", taxRegion=").append(taxRegion);
        sb.append(", taxType=").append(taxType);
        sb.append(", taxRate=").append(taxRate);
        sb.append(", shippingAddress=").append(shippingAddress);
        sb.append(", shippingAddressId=").append(shippingAddressId);
        sb.append(", startedWithGift=").append(startedWithGift);
        sb.append(", convertedAt=").append(convertedAt);
        sb.append(", noBillingInfoReason=").append(noBillingInfoReason);
        sb.append(", importedTrial=").append(importedTrial);
        sb.append(", creditCustomerNotes=").append(creditCustomerNotes);
        sb.append(", invoiceCollection=").append(invoiceCollection);
        sb.append(", customFields=").append(customFields);
        sb.append(", remainingPauseCycles=").append(remainingPauseCycles);
        sb.append(", pausedAt=").append(pausedAt);
        sb.append(", autoRenew=").append(autoRenew);
        sb.append(", renewalBillingCycles=").append(renewalBillingCycles);
        sb.append(", firstBillDate=").append(firstBillDate);
        sb.append(", nextBillDate=").append(nextBillDate);
        sb.append(", currentPeriodStartedAt=").append(currentPeriodStartedAt);
        sb.append(", currentPeriodEndsAt=").append(currentPeriodEndsAt);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Subscription that = (Subscription) o;

        if (account != null ? !account.equals(that.account) : that.account != null) {
            return false;
        }
        if (activatedAt != null ? activatedAt.compareTo(that.activatedAt) != 0 : that.activatedAt != null) {
            return false;
        }
        if (addOns != null ? !addOns.equals(that.addOns) : that.addOns != null) {
            return false;
        }
        if (canceledAt != null ? canceledAt.compareTo(that.canceledAt) != 0 : that.canceledAt != null) {
            return false;
        }
        if (convertedAt != null ? convertedAt.compareTo(that.convertedAt) != 0 : that.convertedAt != null) {
            return false;
        }
        if (currency != null ? !currency.equals(that.currency) : that.currency != null) {
            return false;
        }
        if (updatedAt != null ? updatedAt.compareTo(that.updatedAt) != 0 : that.updatedAt != null) {
            return false;
        }
        if (currentPeriodEndsAt != null ? currentPeriodEndsAt.compareTo(that.currentPeriodEndsAt) != 0 : that.currentPeriodEndsAt != null) {
            return false;
        }
        if (currentPeriodStartedAt != null ? currentPeriodStartedAt.compareTo(that.currentPeriodStartedAt) != 0 : that.currentPeriodStartedAt != null) {
            return false;
        }
        if (expiresAt != null ? expiresAt.compareTo(that.expiresAt) != 0 : that.expiresAt != null) {
            return false;
        }
        if (importedTrial != null ? !importedTrial.equals(that.importedTrial) : that.importedTrial != null) {
            return false;
        }
        if (remainingBillingCycles != null ? !remainingBillingCycles.equals(that.remainingBillingCycles) : that.remainingBillingCycles != null) {
            return false;
        }
        if (remainingPauseCycles != null ? !remainingPauseCycles.equals(that.remainingPauseCycles) : that.remainingPauseCycles != null) {
            return false;
        }
        if (plan != null ? !plan.equals(that.plan) : that.plan != null) {
            return false;
        }
        if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) {
            return false;
        }
        if (startedWithGift != null ? !startedWithGift.equals(that.startedWithGift) : that.startedWithGift != null) {
            return false;
        }
        if (state != null ? !state.equals(that.state) : that.state != null) {
            return false;
        }
        if (trialEndsAt != null ? trialEndsAt.compareTo(that.trialEndsAt) != 0 : that.trialEndsAt != null) {
            return false;
        }
        if (trialStartedAt != null ? trialStartedAt.compareTo(that.trialStartedAt) != 0 : that.trialStartedAt != null) {
            return false;
        }
        if (unitAmountInCents != null ? !unitAmountInCents.equals(that.unitAmountInCents) : that.unitAmountInCents != null) {
            return false;
        }
        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) {
            return false;
        }
        if (startsAt != null ? startsAt.compareTo(that.startsAt) != 0 : that.startsAt != null) {
            return false;
        }
        if (pendingSubscription != null ? !pendingSubscription.equals(that.pendingSubscription) : that.pendingSubscription != null) {
            return false;
        }
        if (collectionMethod != null ? !collectionMethod.equals(that.collectionMethod) : that.collectionMethod != null) {
            return false;
        }
        if (netTerms != null ? !netTerms.equals(that.netTerms) : that.netTerms != null) {
            return false;
        }
        if (pausedAt != null ? pausedAt.compareTo(that.pausedAt) != 0 : that.pausedAt != null) {
            return false;
        }
        if (poNumber != null ? !poNumber.equals(that.poNumber) : that.poNumber != null) {
            return false;
        }
        if (firstRenewalDate != null ? firstRenewalDate.compareTo(that.firstRenewalDate) != 0 : that.firstRenewalDate != null) {
            return false;
        }
        if (bulk != null ? !bulk.equals(that.bulk) : that.bulk != null) {
            return false;
        }
        if (revenueScheduleType != null ? !revenueScheduleType.equals(that.revenueScheduleType) : that.revenueScheduleType != null) {
            return false;
        }
        if (giftCard != null ? !giftCard.equals(that.giftCard) : that.giftCard != null) {
            return false;
        }
        if (taxInCents != null ? !taxInCents.equals(that.taxInCents) : that.taxInCents != null) {
            return false;
        }
        if (taxRegion != null ? !taxRegion.equals(that.taxRegion) : that.taxRegion != null) {
            return false;
        }
        if (taxType != null ? !taxType.equals(that.taxType) : that.taxType != null) {
            return false;
        }
        if (taxRate != null ? !taxRate.equals(that.taxRate) : that.taxRate != null) {
            return false;
        }
        if (shippingAddress != null ? !shippingAddress.equals(that.shippingAddress) : that.shippingAddress != null) {
            return false;
        }
        if (shippingAddressId != null ? !shippingAddressId.equals(that.shippingAddressId) : that.shippingAddressId != null) {
            return false;
        }
        if (noBillingInfoReason != null ? !noBillingInfoReason.equals(that.noBillingInfoReason) : that.noBillingInfoReason != null) {
            return false;
        }
        if (creditCustomerNotes != null ? !creditCustomerNotes.equals(that.creditCustomerNotes) : that.creditCustomerNotes != null) {
            return false;
        }
        if (invoiceCollection != null ? !invoiceCollection.equals(that.invoiceCollection) : that.invoiceCollection != null) {
            return false;
        }
        if (customFields != null ? !customFields.equals(that.customFields) : that.customFields != null) {
            return false;
        }
        if (renewalBillingCycles != null ? !renewalBillingCycles.equals(that.renewalBillingCycles) : that.renewalBillingCycles != null) {
            return false;
        }
        if (autoRenew != null ? !autoRenew.equals(that.autoRenew) : that.autoRenew != null) {
            return false;
        }
        if (firstBillDate != null ? firstBillDate.compareTo(that.firstBillDate) != 0 : that.firstBillDate != null) {
            return false;
        }
        if (nextBillDate != null ? nextBillDate.compareTo(that.nextBillDate) != 0 : that.nextBillDate != null) {
            return false;
        }
        if (currentPeriodStartedAt != null ? currentPeriodStartedAt.compareTo(that.currentPeriodStartedAt) != 0 : that.currentPeriodStartedAt != null) {
            return false;
        }
        if (currentPeriodEndsAt != null ? currentPeriodEndsAt.compareTo(that.currentPeriodEndsAt) != 0 : that.currentPeriodEndsAt != null) {
            return false;
        }


        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
                account,
                plan,
                uuid,
                state,
                unitAmountInCents,
                currency,
                quantity,
                activatedAt,
                updatedAt,
                canceledAt,
                expiresAt,
                remainingBillingCycles,
                currentPeriodStartedAt,
                currentPeriodEndsAt,
                trialStartedAt,
                trialEndsAt,
                addOns,
                pendingSubscription,
                startsAt,
                collectionMethod,
                netTerms,
                poNumber,
                revenueScheduleType,
                giftCard,
                taxInCents,
                taxRegion,
                taxType,
                taxRate,
                shippingAddress,
                shippingAddressId,
                couponCode,
                couponCodes,
                convertedAt,
                startedWithGift,
                noBillingInfoReason,
                importedTrial,
                invoiceCollection,
                customFields,
                remainingPauseCycles,
                pausedAt,
                autoRenew,
                renewalBillingCycles,
                firstBillDate,
                nextBillDate,
                currentPeriodStartedAt,
                currentPeriodEndsAt
        );
    }

}
