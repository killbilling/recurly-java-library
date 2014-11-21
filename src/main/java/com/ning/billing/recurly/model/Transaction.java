/*
 * Copyright 2010-2013 Ning, Inc.
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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.joda.time.DateTime;

/**
 * Transaction model. Example transaction:
 * 
<transaction href="https://xxxxxx.recurly.com/v2/transactions/1234567890abcdefghijk" type="credit_card">
  <account href="https://xxxxxx.recurly.com/v2/accounts/ericjung@foo.com"/>
  <invoice href="https://xxxxxx.recurly.com/v2/invoices/2055"/>
  <subscription href="https://xxxxxx.recurly.com/v2/subscriptions/889440abcd3223edfg3312"/>
  <uuid>1234567890abcdefghijk</uuid>
  <action>purchase</action>
  <amount_in_cents type="integer">89432</amount_in_cents>
  <tax_in_cents type="integer">0</tax_in_cents>
  <currency>USD</currency>
  <status>success</status>
  <payment_method>credit_card</payment_method>
  <reference>5436062</reference>
  <source>subscription</source>
  <recurring type="boolean">false</recurring>
  <test type="boolean">true</test>
  <voidable type="boolean">true</voidable>
  <refundable type="boolean">true</refundable>
  <cvv_result code="M">Match</cvv_result>
  <avs_result code="D">Street address and postal code match.</avs_result>
  <avs_result_street nil="nil"></avs_result_street>
  <avs_result_postal nil="nil"></avs_result_postal>
  <created_at type="datetime">2017-10-21T17:10:19Z</created_at>
  <details>
    <account>
      <account_code>ericjung@foo.com</account_code>
      <first_name>Eric</first_name>
      <last_name>Jung</last_name>
      <company nil="nil"></company>
      <email>ericjung@foo.com</email>
      <billing_info type="credit_card">
        <first_name>Eric</first_name>
        <last_name>Jung</last_name>
        <address1>666 Dante Way</address1>
        <address2>United States</address2>
        <city>Styx</city>
        <state>NJ</state>
        <zip>08002</zip>
        <country>US</country>
        <phone nil="nil"></phone>
        <vat_number nil="nil"></vat_number>
        <card_type>Visa</card_type>
        <year type="integer">2017</year>
        <month type="integer">12</month>
        <first_six>411111</first_six>
        <last_four>1111</last_four>
      </billing_info>
    </account>
  </details>
  <a name="refund" href="https://xxxxxx.recurly.com/v2/transactions/1234567890abcdefghijk" method="delete"/>
</transaction>

 * @author fidel
 *
 */
@XmlRootElement(name = "transaction")
public class Transaction extends AbstractTransaction {

    @XmlElement(name = "account")
    private Account account;

    @XmlElement(name = "invoice")
    private Invoice invoice;

    @XmlElement(name = "subscription")
    private String subscription;

    @XmlElement(name = "uuid")
    private String uuid;

    @XmlElement(name = "tax_in_cents")
    private Integer taxInCents;

    @XmlElement(name = "currency")
    private String currency;

    @XmlElement(name = "payment_method")
    private String paymentMethod;    

    @XmlElement(name = "source")
    private String source;    

    @XmlElement(name = "cvv_result")
    private String cvvResult;    
        
    @XmlElement(name = "avs_result")
    private String avsResult;
    
    @XmlElement(name = "avs_result_street")
    private String avsResultStreet;    

    @XmlElement(name = "created_at")
    private DateTime createdAt;

    @XmlElement(name = "details")
    private TransactionDetails details;

    @XmlElement(name = "refundUrl")
    private String refundUrl;

    public Account getAccount() {
        if (account != null && account.getCreatedAt() == null) {
            account = fetch(account, Account.class);
        }
        return account;
    }

    public void setAccount(final Account account) {
        this.account = account;
    }

    public Invoice getInvoice() {
        if (invoice != null && invoice.getCreatedAt() == null) {
            invoice = fetch(invoice, Invoice.class);
        }
        return invoice;
    }

    public void setInvoice(final Invoice invoice) {
        this.invoice = invoice;
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(final Object subscription) {
        this.subscription = stringOrNull(subscription);
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(final Object uuid) {
        this.uuid = stringOrNull(uuid);
    }

    public Integer getTaxInCents() {
        return taxInCents;
    }

    public void setTaxInCents(final Object taxInCents) {
        this.taxInCents = integerOrNull(taxInCents);
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(final Object currency) {
        this.currency = stringOrNull(currency);
    }

    public String getSource() {
        return source;
    }

    public void setSource(final Object source) {
        this.source = stringOrNull(source);
    }

    public void setCvvResult(final Object cvvResult) {
        this.cvvResult = stringOrNull(cvvResult);
    }

    public String getCvvResult() {
        return cvvResult;
    }

    public void setAvsResult(final Object avsResult) {
        this.avsResult = stringOrNull(avsResult);
    }

    public String getAvsResult() {
        return avsResult;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final Object createdAt) {
        this.createdAt = dateTimeOrNull(createdAt);
    }

    public TransactionDetails getDetails() {
        return details;
    }

    public void setDetails(final TransactionDetails details) {
        this.details = details;
    }

    /**
     * @return the status
     */
    public String getStatus() {
      return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
      this.status = status;
    }

    /**
     * @return the paymentMethod
     */
    public String getPaymentMethod() {
      return paymentMethod;
    }

    /**
     * @param paymentMethod the paymentMethod to set
     */
    public void setPaymentMethod(String paymentMethod) {
      this.paymentMethod = paymentMethod;
    }

    /**
     * @return the reference
     */
    public String getReference() {
      return reference;
    }

    /**
     * @param reference the reference to set
     */
    public void setReference(String reference) {
      this.reference = reference;
    }

    /**
     * @return the avsResultStreet
     */
    public String getAvsResultStreet() {
      return avsResultStreet;
    }

    /**
     * @param avsResultStreet the avsResultStreet to set
     */
    public void setAvsResultStreet(String avsResultStreet) {
      this.avsResultStreet = avsResultStreet;
    }

    /**
     * @return the refundUrl
     */
    public String getRefundUrl() {
      return refundUrl;
    }

    /**
     * @param refundUrl the refundUrl to set
     */
    public void setRefundUrl(String refundUrl) {
      this.refundUrl = refundUrl;
    }

    /**
     * @return the action
     */
    public String getAction() {
      return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(String action) {
      this.action = action;
    }

    /**
     * @param subscription the subscription to set
     */
    public void setSubscription(String subscription) {
      this.subscription = subscription;
    }

    /**
     * @param uuid the uuid to set
     */
    public void setUuid(String uuid) {
      this.uuid = uuid;
    }

    /**
     * @param taxInCents the taxInCents to set
     */
    public void setTaxInCents(Integer taxInCents) {
      this.taxInCents = taxInCents;
    }

    /**
     * @param currency the currency to set
     */
    public void setCurrency(String currency) {
      this.currency = currency;
    }

    /**
     * @param source the source to set
     */
    public void setSource(String source) {
      this.source = source;
    }

    /**
     * @param cvvResult the cvvResult to set
     */
    public void setCvvResult(String cvvResult) {
      this.cvvResult = cvvResult;
    }

    /**
     * @param avsResult the avsResult to set
     */
    public void setAvsResult(String avsResult) {
      this.avsResult = avsResult;
    }

    /**
     * @param createdAt the createdAt to set
     */
    public void setCreatedAt(DateTime createdAt) {
      this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Transaction{");
        sb.append("account=").append(account);
        sb.append(", invoice=").append(invoice);
        sb.append(", subscription='").append(subscription).append('\'');
        sb.append(", uuid='").append(uuid).append('\'');
        sb.append(", taxInCents=").append(taxInCents);
        sb.append(", currency='").append(currency).append('\'');
        sb.append(", source='").append(source).append('\'');
        sb.append(", createdAt=").append(createdAt);
        sb.append(", details=").append(details);
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
        if (!super.equals(o)) {
            return false;
        }

        final Transaction that = (Transaction) o;

        if (account != null ? !account.equals(that.account) : that.account != null) {
            return false;
        }
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) {
            return false;
        }
        if (currency != null ? !currency.equals(that.currency) : that.currency != null) {
            return false;
        }
        if (details != null ? !details.equals(that.details) : that.details != null) {
            return false;
        }
        if (invoice != null ? !invoice.equals(that.invoice) : that.invoice != null) {
            return false;
        }
       if (cvvResult != null ? !cvvResult.equals(that.cvvResult) : that.cvvResult != null) {
            return false;
        }
        if (avsResult != null ? !avsResult.equals(that.avsResult) : that.avsResult != null) {
            return false;
        }
        if (source != null ? !source.equals(that.source) : that.source != null) {
            return false;
        }
        if (subscription != null ? !subscription.equals(that.subscription) : that.subscription != null) {
            return false;
        }
        if (taxInCents != null ? !taxInCents.equals(that.taxInCents) : that.taxInCents != null) {
            return false;
        }
        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (account != null ? account.hashCode() : 0);
        result = 31 * result + (invoice != null ? invoice.hashCode() : 0);
        result = 31 * result + (subscription != null ? subscription.hashCode() : 0);
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        result = 31 * result + (taxInCents != null ? taxInCents.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (source != null ? source.hashCode() : 0);
        result = 31 * result + (cvvResult != null ? cvvResult.hashCode() : 0);
        result = 31 * result + (avsResult != null ? avsResult.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (details != null ? details.hashCode() : 0);
        return result;
    }
}
