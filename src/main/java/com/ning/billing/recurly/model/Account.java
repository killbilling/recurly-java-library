/*
 * Copyright 2010-2012 Ning, Inc.
 *
 * Ning licenses this file to you under the Apache License, version 2.0
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

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@XmlRootElement(name = "account")
public class Account extends RecurlyObject {

    @XmlTransient
    public static final String ACCOUNT_RESOURCE = "/accounts";

    @XmlTransient
    public static final Pattern ACCOUNT_CODE_PATTERN = Pattern.compile(ACCOUNT_RESOURCE + "/(.+)$");

    @XmlTransient
    private String href;

    @XmlElementWrapper(name = "adjustments")
    @XmlElement(name = "adjustment")
    private List<Adjustment> adjustments;

    @XmlElementWrapper(name = "invoices")
    @XmlElement(name = "invoice")
    private List<Invoice> invoices;

    @XmlElementWrapper(name = "subscriptions")
    @XmlElement(name = "subscription")
    private List<Subscription> subscriptions;

    @XmlElementWrapper(name = "transactions")
    @XmlElement(name = "transaction")
    private List<Transaction> transactions;

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

    @XmlElement(name = "billing_info")
    private BillingInfo billingInfo;

    // Note: I'm not sure why @JsonIgnore is required here - shouldn't @XmlTransient be enough?
    @JsonIgnore
    public String getHref() {
        return href;
    }

    public void setHref(final Object href) {
        this.href = stringOrNull(href);

        // If there was an href try to parse out the account code since
        // Recurly doesn't currently provide it elsewhere.
        if (this.href != null) {
            Matcher m = ACCOUNT_CODE_PATTERN.matcher(this.href);
            if (m.find()) {
                setAccountCode(m.group(1));
            }
        }
    }

    public List<Adjustment> getAdjustments() {
        return adjustments;
    }

    public void setAdjustments(final List<Adjustment> adjustments) {
        this.adjustments = adjustments;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(final List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(final List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(final List<Transaction> transactions) {
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

    public BillingInfo getBillingInfo() {
        return billingInfo;
    }

    public void setBillingInfo(final BillingInfo billingInfo) {
        this.billingInfo = billingInfo;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Account");
        sb.append("{href=").append(href);
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
        sb.append(", billingInfo=").append(billingInfo);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Account account = (Account) o;

        if (acceptLanguage != null ? !acceptLanguage.equals(account.acceptLanguage) : account.acceptLanguage != null) {
            return false;
        }
        if (accountCode != null ? !accountCode.equals(account.accountCode) : account.accountCode != null) {
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
        if (createdAt != null ? !createdAt.equals(account.createdAt) : account.createdAt != null) {
            return false;
        }
        if (email != null ? !email.equals(account.email) : account.email != null) {
            return false;
        }
        if (firstName != null ? !firstName.equals(account.firstName) : account.firstName != null) {
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
        if (username != null ? !username.equals(account.username) : account.username != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = href != null ? href.hashCode() : 0;
        result = 31 * result + (adjustments != null ? adjustments.hashCode() : 0);
        result = 31 * result + (invoices != null ? invoices.hashCode() : 0);
        result = 31 * result + (subscriptions != null ? subscriptions.hashCode() : 0);
        result = 31 * result + (transactions != null ? transactions.hashCode() : 0);
        result = 31 * result + (accountCode != null ? accountCode.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (companyName != null ? companyName.hashCode() : 0);
        result = 31 * result + (acceptLanguage != null ? acceptLanguage.hashCode() : 0);
        result = 31 * result + (hostedLoginToken != null ? hostedLoginToken.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (billingInfo != null ? billingInfo.hashCode() : 0);
        return result;
    }
}
