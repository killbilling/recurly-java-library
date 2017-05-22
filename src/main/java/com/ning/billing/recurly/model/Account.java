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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.joda.time.DateTime;

@XmlRootElement(name = "account")
public class Account extends RecurlyObject {

    @XmlTransient
    public static final String ACCOUNT_RESOURCE = "/accounts";

    @XmlTransient
    public static final Pattern ACCOUNT_CODE_PATTERN = Pattern.compile(ACCOUNT_RESOURCE + "/(.+)$");

    @XmlElement(name = "address")
    private Address address;

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

    @XmlElement(name = "first_name")
    private String firstName;

    @XmlElement(name = "last_name")
    private String lastName;

    @XmlElement(name = "company_name")
    private String companyName;

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

    @XmlElementWrapper(name = "shipping_addresses")
    @XmlElement(name = "shipping_address")
    private ShippingAddresses shippingAddresses;

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

    @XmlElement(name = "vat_number")
    private String vatNumber;

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
        return companyName;
    }

    public void setCompanyName(final Object companyName) {
        this.companyName = stringOrNull(companyName);
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

    public ShippingAddresses getShippingAddresses() {
        return shippingAddresses;
    }

    public void setShippingAddresses(final ShippingAddresses shippingAddresses) {
        this.shippingAddresses = shippingAddresses;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(final Object vatNumber) {
        this.vatNumber = stringOrNull(vatNumber);
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
        sb.append(", state='").append(state).append('\'');
        sb.append(", username='").append(username).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", companyName='").append(companyName).append('\'');
        sb.append(", acceptLanguage='").append(acceptLanguage).append('\'');
        sb.append(", hostedLoginToken='").append(hostedLoginToken).append('\'');
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", billingInfo=").append(billingInfo);
        sb.append(", taxExempt=").append(taxExempt);
        sb.append(", shippingAddresses=").append(shippingAddresses);
        sb.append(", hasLiveSubscription=").append(hasLiveSubscription);
        sb.append(", hasActiveSubscription=").append(hasActiveSubscription);
        sb.append(", hasFutureSubscription=").append(hasFutureSubscription);
        sb.append(", hasCanceledSubscription=").append(hasCanceledSubscription);
        sb.append(", hasPastDueInvoice=").append(hasPastDueInvoice);
        sb.append(", vatNumber=").append(vatNumber);
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
        if (accountCode != null ? !accountCode.equals(account.accountCode) : account.accountCode != null) {
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
        if (companyName != null ? !companyName.equals(account.companyName) : account.companyName != null) {
            return false;
        }
        if (createdAt != null ? createdAt.compareTo(account.createdAt) != 0 : account.createdAt != null) {
            return false;
        }
        if (email != null ? !email.equals(account.email) : account.email != null) {
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
        if (shippingAddresses != null ? !shippingAddresses.equals(account.shippingAddresses) : account.shippingAddresses != null) {
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
                href,
                adjustments,
                invoices,
                subscriptions,
                transactions,
                accountCode,
                state,
                username,
                email,
                firstName,
                hasLiveSubscription,
                hasActiveSubscription,
                hasCanceledSubscription,
                hasFutureSubscription,
                hasPastDueInvoice,
                lastName,
                companyName,
                acceptLanguage,
                hostedLoginToken,
                createdAt,
                billingInfo,
                updatedAt,
                taxExempt,
                shippingAddresses,
                vatNumber
        );
    }
}
