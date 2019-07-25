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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@XmlRootElement(name = "account")
public class Account extends RecurlyObject {

    @XmlTransient
    public static final String ACCOUNT_RESOURCE = "/accounts";

    @XmlTransient
    public static final Pattern ACCOUNT_CODE_PATTERN = Pattern.compile(ACCOUNT_RESOURCE + "/(.+)$");

    @XmlElement(name = "address")
    private Address address;

    @XmlElement(name = "parent_account_code")
    private String parentAccountCode;

    @XmlElementWrapper(name = "adjustments")
    @XmlElement(name = "adjustment")
    private Adjustments adjustments;

    @XmlElementWrapper(name = "invoices")
    @XmlElement(name = "invoice")
    private Invoices invoices;

    @XmlElementWrapper(name = "subscriptions")
    @XmlElement(name = "subscription")
    private Subscriptions subscriptions;

    @XmlElementWrapper(name = "transactions")
    @XmlElement(name = "transaction")
    private Transactions transactions;

    @XmlElement(name = "account_code")
    private String accountCode;

    @XmlElement(name = "state")
    private String state;

    @XmlElement(name = "username")
    private String username;

    @XmlElement(name = "email")
    private String email;

    @XmlElement(name = "cc_emails")
    private String ccEmails;

    @XmlElement(name = "first_name")
    private String firstName;

    @XmlElement(name = "last_name")
    private String lastName;

    @XmlElement(name = "company_name")
    private String companyName;

    // This property appears instead of "company_name" on some Account xml,
    // such as when listing invoices:
    // invoices -> invoice -> transactions -> transaction -> details -> account -> company
    @XmlElement(name = "company")
    private String company;

    @XmlElement(name = "accept_language")
    private String acceptLanguage;

    @XmlElement(name = "hosted_login_token")
    private String hostedLoginToken;

    @XmlElement(name = "created_at")
    private DateTime createdAt;

    @XmlElement(name = "updated_at")
    private DateTime updatedAt;

    @XmlElement(name = "billing_info")
    private BillingInfo billingInfo;

    @XmlElement(name = "tax_exempt")
    private Boolean taxExempt;

    @XmlElement(name = "exemption_certificate")
    private String exemptionCertificate;

    @XmlElementWrapper(name = "shipping_addresses")
    @XmlElement(name = "shipping_address")
    private ShippingAddresses shippingAddresses;

    @XmlElementWrapper(name = "custom_fields")
    @XmlElement(name = "custom_field")
    private CustomFields customFields;

    @XmlElement(name = "has_live_subscription")
    private Boolean hasLiveSubscription;

    @XmlElement(name = "has_active_subscription")
    private Boolean hasActiveSubscription;

    @XmlElement(name = "has_future_subscription")
    private Boolean hasFutureSubscription;

    @XmlElement(name = "has_canceled_subscription")
    private Boolean hasCanceledSubscription;

    @XmlElement(name = "has_past_due_invoice")
    private Boolean hasPastDueInvoice;

    @XmlElement(name = "has_paused_subscription")
    private Boolean hasPausedSubscription;

    @XmlElement(name = "vat_number")
    private String vatNumber;

    @XmlElement(name = "account_acquisition")
    private AccountAcquisition accountAcquisition;

    @XmlElement(name = "preferred_locale")
    private String preferredLocale;

    @Override
    public void setHref(final Object href) {
        super.setHref(href);

        // If there was an href try to parse out the account code since
        // Recurly doesn't currently provide it elsewhere.
        if (this.href != null) {
            final Matcher m = ACCOUNT_CODE_PATTERN.matcher(this.href);
            if (m.find()) {
                setAccountCode(m.group(1));
            }
        }
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(final Address address) {
        this.address = address;
    }

    public Adjustments getAdjustments() {
        return adjustments;
    }

    public void setAdjustments(final Adjustments adjustments) {
        this.adjustments = adjustments;
    }

    public Invoices getInvoices() {
        return invoices;
    }

    public void setInvoices(final Invoices invoices) {
        this.invoices = invoices;
    }

    public Subscriptions getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(final Subscriptions subscriptions) {
        this.subscriptions = subscriptions;
    }

    public Transactions getTransactions() {
        return transactions;
    }

    public void setTransactions(final Transactions transactions) {
        this.transactions = transactions;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(final Object accountCode) {
        this.accountCode = stringOrNull(accountCode);
    }

    public String getParentAccountCode() {
        return this.parentAccountCode;
    }

    public void setParentAccountCode(final Object parentAccountCode) {
        this.parentAccountCode = stringOrNull(parentAccountCode);
    }

    public String getState() {
        return state;
    }

    public void setState(final Object state) {
        this.state = stringOrNull(state);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final Object username) {
        this.username = stringOrNull(username);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final Object email) {
        this.email = stringOrNull(email);
    }

    public String getCcEmails() {
        return ccEmails;
    }

    public void setCcEmails(final Object ccEmails) {
        this.ccEmails = stringOrNull(ccEmails);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final Object firstName) {
        this.firstName = stringOrNull(firstName);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final Object lastName) {
        this.lastName = stringOrNull(lastName);
    }

    public String getCompanyName() {
        return companyName != null ? companyName : company;
    }

    public void setCompanyName(final Object companyName) {
        this.companyName = stringOrNull(companyName);
    }

    public void setCompany(final Object company) {
        this.company = stringOrNull(company);
    }

    public String getAcceptLanguage() {
        return acceptLanguage;
    }

    public void setAcceptLanguage(final Object acceptLanguage) {
        this.acceptLanguage = stringOrNull(acceptLanguage);
    }

    public String getHostedLoginToken() {
        return hostedLoginToken;
    }

    public void setHostedLoginToken(final Object hostedLoginToken) {
        this.hostedLoginToken = stringOrNull(hostedLoginToken);
    }

    public DateTime getCreatedAt() {
        return createdAt;
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

    public BillingInfo getBillingInfo() {
        return billingInfo;
    }

    public void setBillingInfo(final BillingInfo billingInfo) {
        this.billingInfo = billingInfo;
    }

    public Boolean getTaxExempt() {
        return taxExempt;
    }

    public void setTaxExempt(final Object taxExempt) {
        this.taxExempt = booleanOrNull(taxExempt);
    }

    public String getExemptionCertificate() {
        return exemptionCertificate;
    }

    public void setExemptionCertificate(final Object exemptionCertificate) {
        this.exemptionCertificate = stringOrNull(exemptionCertificate);
    }

    public Boolean getHasLiveSubscription() {
        return hasLiveSubscription;
    }

    protected void setHasLiveSubscription(final Object hasLiveSubscription) {
        this.hasLiveSubscription = booleanOrNull(hasLiveSubscription);
    }

    public Boolean getHasActiveSubscription() {
        return hasActiveSubscription;
    }

    protected void setHasActiveSubscription(final Object hasActiveSubscription) {
        this.hasActiveSubscription = booleanOrNull(hasActiveSubscription);
    }

    public Boolean getHasFutureSubscription() {
        return hasFutureSubscription;
    }

    protected void setHasFutureSubscription(final Object hasFutureSubscription) {
        this.hasFutureSubscription = booleanOrNull(hasFutureSubscription);
    }

    public Boolean getHasCanceledSubscription() {
        return hasCanceledSubscription;
    }

    protected void setHasCanceledSubscription(final Object hasCanceledSubscription) {
        this.hasCanceledSubscription = booleanOrNull(hasCanceledSubscription);
    }

    public Boolean getHasPastDueInvoice() {
        return hasPastDueInvoice;
    }

    protected void setHasPastDueInvoice(final Object hasPastDueInvoice) {
        this.hasPastDueInvoice = booleanOrNull(hasPastDueInvoice);
    }

    public Boolean getHasPausedSubscription() {
        return hasPausedSubscription;
    }

    protected void setHasPausedSubscription(final Object hasPausedSubscription) {
        this.hasPausedSubscription = booleanOrNull(hasPausedSubscription);
    }

    public ShippingAddresses getShippingAddresses() {
        return shippingAddresses;
    }

    public void setShippingAddresses(final ShippingAddresses shippingAddresses) {
        this.shippingAddresses = shippingAddresses;
    }

    public CustomFields getCustomFields() {
        return customFields;
    }

    public void setCustomFields(final CustomFields customFields) {
        this.customFields = customFields;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(final Object vatNumber) {
        this.vatNumber = stringOrNull(vatNumber);
    }

    public AccountAcquisition getAccountAcquisition() {
        return accountAcquisition;
    }

    public void setAccountAcquisition(final AccountAcquisition accountAcquisition) {
        this.accountAcquisition = accountAcquisition;
    }

    public String getPreferredLocale() {
        return preferredLocale;
    }

    public void setPreferredLocale(final Object preferredLocale) {
        this.preferredLocale = stringOrNull(preferredLocale);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Account{");
        sb.append("address=").append(address);
        sb.append(", href=").append(href);
        sb.append(", adjustments=").append(adjustments);
        sb.append(", invoices=").append(invoices);
        sb.append(", subscriptions=").append(subscriptions);
        sb.append(", transactions=").append(transactions);
        sb.append(", accountCode='").append(accountCode).append('\'');
        sb.append(", parent_account_code='").append(parentAccountCode).append('\'');
        sb.append(", state='").append(state).append('\'');
        sb.append(", username='").append(username).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", ccEmails='").append(ccEmails).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", companyName='").append(this.getCompanyName()).append('\'');
        sb.append(", acceptLanguage='").append(acceptLanguage).append('\'');
        sb.append(", hostedLoginToken='").append(hostedLoginToken).append('\'');
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", billingInfo=").append(billingInfo);
        sb.append(", taxExempt=").append(taxExempt);
        sb.append(", exemptionCertificate='").append(exemptionCertificate).append('\'');
        sb.append(", shippingAddresses=").append(shippingAddresses);
        sb.append(", customFields=").append(customFields);
        sb.append(", hasLiveSubscription=").append(hasLiveSubscription);
        sb.append(", hasActiveSubscription=").append(hasActiveSubscription);
        sb.append(", hasFutureSubscription=").append(hasFutureSubscription);
        sb.append(", hasCanceledSubscription=").append(hasCanceledSubscription);
        sb.append(", hasPastDueInvoice=").append(hasPastDueInvoice);
        sb.append(", hasPausedSubscription=").append(hasPausedSubscription);
        sb.append(", vatNumber=").append(vatNumber);
        sb.append(", accountAcquisition=").append(accountAcquisition);
        sb.append(", preferredLocale=").append(preferredLocale);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Account account = (Account) o;

        if (acceptLanguage != null ? !acceptLanguage.equals(account.acceptLanguage) : account.acceptLanguage != null) {
            return false;
        }
        if (accountAcquisition != null ? !accountAcquisition.equals(account.accountAcquisition) : account.accountAcquisition != null) {
            return false;
        }
        if (accountCode != null ? !accountCode.equals(account.accountCode) : account.accountCode != null) {
            return false;
        }
        if (parentAccountCode != null ? !parentAccountCode.equals(account.parentAccountCode) : account.parentAccountCode != null) {
            return false;
        }
        if (address != null ? !address.equals(account.address) : account.address != null) {
            return false;
        }
        if (adjustments != null ? !adjustments.equals(account.adjustments) : account.adjustments != null) {
            return false;
        }
        if (billingInfo != null ? !billingInfo.equals(account.billingInfo) : account.billingInfo != null) {
            return false;
        }
        if (this.getCompanyName() != null ? !this.getCompanyName().equals(account.getCompanyName()) : account.getCompanyName() != null) {
            return false;
        }
        if (createdAt != null ? createdAt.compareTo(account.createdAt) != 0 : account.createdAt != null) {
            return false;
        }
        if (email != null ? !email.equals(account.email) : account.email != null) {
            return false;
        }
        if (ccEmails != null ? !ccEmails.equals(account.ccEmails) : account.ccEmails != null) {
            return false;
        }
        if (firstName != null ? !firstName.equals(account.firstName) : account.firstName != null) {
            return false;
        }
        if (hasLiveSubscription != null ? !hasLiveSubscription.equals(account.hasLiveSubscription) : account.hasLiveSubscription != null) {
            return false;
        }
        if (hasActiveSubscription != null ? !hasActiveSubscription.equals(account.hasActiveSubscription) : account.hasActiveSubscription != null) {
            return false;
        }
        if (hasFutureSubscription != null ? !hasFutureSubscription.equals(account.hasFutureSubscription) : account.hasFutureSubscription != null) {
            return false;
        }
        if (hasCanceledSubscription != null ? !hasCanceledSubscription.equals(account.hasCanceledSubscription) : account.hasCanceledSubscription != null) {
            return false;
        }
        if (hasPausedSubscription != null ? !hasPausedSubscription.equals(account.hasPausedSubscription) : account.hasPausedSubscription != null) {
            return false;
        }
        if (href != null ? !href.equals(account.href) : account.href != null) {
            return false;
        }
        if (hostedLoginToken != null ? !hostedLoginToken.equals(account.hostedLoginToken) : account.hostedLoginToken != null) {
            return false;
        }
        if (invoices != null ? !invoices.equals(account.invoices) : account.invoices != null) {
            return false;
        }
        if (lastName != null ? !lastName.equals(account.lastName) : account.lastName != null) {
            return false;
        }
        if (preferredLocale != null ? !preferredLocale.equals(account.preferredLocale) : account.preferredLocale != null) {
            return false;
        }
        if (state != null ? !state.equals(account.state) : account.state != null) {
            return false;
        }
        if (subscriptions != null ? !subscriptions.equals(account.subscriptions) : account.subscriptions != null) {
            return false;
        }
        if (transactions != null ? !transactions.equals(account.transactions) : account.transactions != null) {
            return false;
        }
        if (updatedAt != null ? updatedAt.compareTo(account.updatedAt) != 0 : account.updatedAt != null) {
            return false;
        }
        if (username != null ? !username.equals(account.username) : account.username != null) {
            return false;
        }
        if (taxExempt != null ? !taxExempt.equals(account.taxExempt) : account.taxExempt != null) {
            return false;
        }
        if (exemptionCertificate != null ? !exemptionCertificate.equals(account.exemptionCertificate) : account.exemptionCertificate != null) {
            return false;
        }
        if (shippingAddresses != null ? !shippingAddresses.equals(account.shippingAddresses) : account.shippingAddresses != null) {
            return false;
        }
        if (customFields != null ? !customFields.equals(account.customFields) : account.customFields != null) {
            return false;
        }
        if (vatNumber != null ? !vatNumber.equals(account.vatNumber) : account.vatNumber != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
                address,
                parentAccountCode,
                href,
                adjustments,
                invoices,
                subscriptions,
                transactions,
                accountCode,
                state,
                username,
                email,
                ccEmails,
                firstName,
                hasLiveSubscription,
                hasActiveSubscription,
                hasCanceledSubscription,
                hasFutureSubscription,
                hasPastDueInvoice,
                hasPausedSubscription,
                lastName,
                this.getCompanyName(),
                acceptLanguage,
                hostedLoginToken,
                createdAt,
                billingInfo,
                updatedAt,
                taxExempt,
                exemptionCertificate,
                shippingAddresses,
                customFields,
                vatNumber,
                accountAcquisition,
                preferredLocale
        );
    }
}
