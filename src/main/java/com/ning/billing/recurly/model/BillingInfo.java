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
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlAttribute;

import com.google.common.base.Objects;
import org.joda.time.DateTime;

@XmlRootElement(name = "billing_info")
public class BillingInfo extends RecurlyObject {

    @XmlTransient
    public static final String BILLING_INFO_RESOURCE = "/billing_info";

    @XmlElement(name = "type")
    private String type;

    @XmlAttribute(name = "type")
    private String attributeType;

    @XmlElement(name = "account")
    private Account account;

    @XmlElement(name = "uuid")
    private String uuid;

    @XmlElement(name = "name_on_account")
    private String nameOnAccount;

    @XmlElement(name = "first_name")
    private String firstName;

    @XmlElement(name = "last_name")
    private String lastName;

    @XmlElement(name = "mandate_reference")
    private String mandateReference;

    @XmlElement(name = "company")
    private String company;

    @XmlElement(name = "address1")
    private String address1;

    @XmlElement(name = "address2")
    private String address2;

    @XmlElement(name = "city")
    private String city;

    @XmlElement(name = "state")
    private String state;

    @XmlElement(name = "zip")
    private String zip;

    @XmlElement(name = "country")
    private String country;

    @XmlElement(name = "phone")
    private String phone;

    @XmlElement(name = "vat_number")
    private String vatNumber;

    @XmlElement(name = "ip_address")
    private String ipAddress;

    @XmlElement(name = "ip_address_country")
    private String ipAddressCountry;

    @XmlElement(name = "account_type")
    private String accountType;

    @XmlElement(name = "card_type")
    private String cardType;

    @XmlElement(name = "year")
    private Integer year;

    @XmlElement(name = "month")
    private Integer month;

    @XmlElement(name = "first_six")
    private String firstSix;

    @XmlElement(name = "last_four")
    private String lastFour;

    @XmlElement(name = "number")
    private String number;

    @XmlElement(name = "card_network_preference")
    private String cardNetworkPreference;

    @XmlElement(name = "routing_number")
    private String routingNumber;

    @XmlElement(name = "account_number")
    private String accountNumber;

    @XmlElement(name = "verification_value")
    private String verificationValue;

    @XmlElement(name = "token_id")
    private String tokenId;

    @XmlElement(name = "currency")
    private String currency;

    @XmlElement(name = "geo_code")
    private String geoCode;

    @XmlElement(name = "updated_at")
    private DateTime updatedAt;

    @XmlElement(name = "external_hpp_type")
    private String externalHppType;

    @XmlElement(name = "online_banking_payment_type")
    private String onlineBankingPaymentType;

    @XmlElement(name = "gateway_token")
    private String gatewayToken;

    @XmlElement(name = "gateway_code")
    private String gatewayCode;

    @XmlElement(name = "gateway_attributes")
    private GatewayAttributes gatewayAttributes;

    @XmlElement(name = "amazon_billing_agreement_id")
    private String amazonBillingAgreementId;

    @XmlElement(name = "amazon_region")
    private String amazonRegion;

    @XmlElement(name = "three_d_secure_action_result_token_id")
    private String threeDSecureActionResultTokenId;

    @XmlElement(name = "transaction_type")
    private String transactionType;

    @XmlElement(name = "iban")
    private String iban;

    @XmlElement(name = "last_two")
    private String lastTwo;

    @XmlElement(name = "sort_code")
    private String sortCode;

    @XmlElement(name = "bsb_code")
    private String bsbCode;

    @XmlElement(name = "tax_identifier")
    private String taxIdentifier;

    @XmlElement(name = "tax_identifier_type")
    private String taxIdentifierType;

    @XmlElement(name = "primary_payment_method")
    private String primaryPaymentMethod;

    @XmlElement(name = "backup_payment_method")
    private String backupPaymentMethod;

    public String getType() {
        return this.type == null ? this.attributeType : this.type;
    }

    public void setType(final Object type) {
        this.type = stringOrNull(type);
    }

    /**
     * Account object associated to this BillingInfo
     * <p>
     * Note: when fetching a BillingInfo object from Recurly, the account object is not guaranteed to be populated.
     *
     * @return account object
     */
    public Account getAccount() {
        if (account != null && account.getCreatedAt() == null) {
            account = fetch(account, Account.class);
        }
        return account;
    }

    /**
     * @deprecated Please do not attach an account to a BillingInfo object. Pass the account code into {@link com.ning.billing.recurly.RecurlyClient#createOrUpdateBillingInfo(String, BillingInfo)}
     * @param account
     */
    @Deprecated
    public void setAccount(final Account account) {
        this.account = account;
    }

    public String getNameOnAccount() {
        return nameOnAccount;
    }

    public void setNameOnAccount(final Object nameOnAccount) {
        this.nameOnAccount = stringOrNull(nameOnAccount);
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(final Object uuid) {
        this.uuid = stringOrNull(uuid);
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

    public String getMandateReference() {
        return mandateReference;
    }

    public void setMandateReference(final Object mandateReference) {
        this.mandateReference = stringOrNull(mandateReference);
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(final Object company) {
        this.company = stringOrNull(company);
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(final Object address1) {
        this.address1 = stringOrNull(address1);
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(final Object address2) {
        this.address2 = stringOrNull(address2);
    }

    public String getCity() {
        return city;
    }

    public void setCity(final Object city) {
        this.city = stringOrNull(city);
    }

    public String getState() {
        return state;
    }

    public void setState(final Object state) {
        this.state = stringOrNull(state);
    }

    public String getZip() {
        return zip;
    }

    public void setZip(final Object zip) {
        this.zip = stringOrNull(zip);
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final Object country) {
        this.country = stringOrNull(country);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final Object phone) {
        this.phone = stringOrNull(phone);
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(final Object vatNumber) {
        this.vatNumber = stringOrNull(vatNumber);
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(final Object ipAddress) {
        this.ipAddress = stringOrNull(ipAddress);
    }

    public String getIpAddressCountry() {
        return ipAddressCountry;
    }

    public void setIpAddressCountry(final Object ipAddressCountry) {
        this.ipAddressCountry = stringOrNull(ipAddressCountry);
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(final Object accountType) {
        this.accountType = stringOrNull(accountType);
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(final Object cardType) {
        this.cardType = stringOrNull(cardType);
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(final Object year) {
        this.year = integerOrNull(year);
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(final Object month) {
        this.month = integerOrNull(month);
    }

    public String getFirstSix() {
        return firstSix;
    }

    public void setFirstSix(final Object firstSix) {
        this.firstSix = stringOrNull(firstSix);
    }

    public String getLastFour() {
        return lastFour;
    }

    public void setLastFour(final Object lastFour) {
        this.lastFour = stringOrNull(lastFour);
    }

    public String getCardNetworkPreference() {
        return cardNetworkPreference;
    }

    public void setCardNetworkPreference(final Object cardNetworkPreference) {
        this.cardNetworkPreference = stringOrNull(cardNetworkPreference);
    }

    public String getRoutingNumber() {
        return routingNumber;
    }

    public void setRoutingNumber(final Object routingNumber) {
        this.routingNumber = stringOrNull(routingNumber);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(final Object accountNumber) {
        this.accountNumber = stringOrNull(accountNumber);
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(final Object number) {
        this.number = stringOrNull(number);
    }

    public String getVerificationValue() {
        return verificationValue;
    }

    public void setVerificationValue(final Object verificationValue) {
        this.verificationValue = stringOrNull(verificationValue);
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(final String tokenId) {
        this.tokenId = tokenId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(final Object currency) {
        this.currency = stringOrNull(currency);
    }

    public String getGeoCode() {
        return geoCode;
    }

    public void setGeoCode(final Object geoCode) {
        this.geoCode = stringOrNull(geoCode);
    }

    public DateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(final Object updatedAt) {
        this.updatedAt = dateTimeOrNull(updatedAt);
    }

    public String getExternalHppType() {
        return externalHppType;
    }

    public void setExternalHppType(final Object externalHppType) {
        this.externalHppType = stringOrNull(externalHppType);
    }

    public String getOnlineBankingPaymentType() {
        return onlineBankingPaymentType;
    }

    public void setOnlineBankingPaymentType(final Object onlineBankingPaymentType) {
        this.onlineBankingPaymentType = stringOrNull(onlineBankingPaymentType);
    }

    public String getGatewayToken() {
        return gatewayToken;
    }

    public void setGatewayToken(final Object gatewayToken) {
        this.gatewayToken = stringOrNull(gatewayToken);
    }

    public String getGatewayCode() {
        return gatewayCode;
    }

    public void setGatewayCode(final Object gatewayCode) {
        this.gatewayCode = stringOrNull(gatewayCode);
    }

    public GatewayAttributes getGatewayAttributes() { return gatewayAttributes; }

    public void setGatewayAttributes(final GatewayAttributes gatewayAttributes) {
        this.gatewayAttributes = gatewayAttributes;
    }

    public String getAmazonBillingAgreementId() {
        return amazonBillingAgreementId;
    }

    public void setAmazonBillingAgreementId(final Object amazonBillingAgreementId) {
        this.amazonBillingAgreementId = stringOrNull(amazonBillingAgreementId);
    }

    public String getAmazonRegion() {
        return amazonRegion;
    }

    public void setAmazonRegion(final Object amazonRegion) {
        this.amazonRegion = stringOrNull(amazonRegion);
    }

    public String getThreeDSecureActionResultTokenId() {
        return threeDSecureActionResultTokenId;
    }

    public void setThreeDSecureActionResultTokenId(final Object threeDSecureActionResultTokenId) {
        this.threeDSecureActionResultTokenId = stringOrNull(threeDSecureActionResultTokenId);
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(final Object transactionType) {
        this.transactionType = stringOrNull(transactionType);
    }

    public String getIban() {
        return iban;
    }

    public void setIban(final Object iban) {
        this.iban = stringOrNull(iban);
    }

    public String getLastTwo() {
        return lastTwo;
    }

    public void setLastTwo(final Object lastTwo) {
        this.lastTwo = stringOrNull(lastTwo);
    }

    public String getSortCode() {
        return sortCode;
    }

    public void setSortCode(final Object sortCode) {
        this.sortCode = stringOrNull(sortCode);
    }

    public String getBsbCode() {
        return bsbCode;
    }

    public void setBsbCode(final Object bsbCode) {
        this.bsbCode = stringOrNull(bsbCode);
    }

    public String getTaxIdentifier() {
        return taxIdentifier; 
    }

    public void setTaxIdentifier(final Object taxIdentifier) {
        this.taxIdentifier = stringOrNull(taxIdentifier);
    }

    public String getTaxIdentifierType() {
      return taxIdentifierType; 
    }

    public void setTaxIdentifierType(final Object taxIdentifierType) {
        this.taxIdentifierType = stringOrNull(taxIdentifierType);
    }

    public String getPrimaryPaymentMethod() {
      return primaryPaymentMethod; 
    }

    public void setPrimaryPaymentMethod(final Object primaryPaymentMethod) {
        this.primaryPaymentMethod = stringOrNull(primaryPaymentMethod);
    }

    public String getBackupPaymentMethod() {
      return backupPaymentMethod; 
    }

    public void setBackupPaymentMethod(final Object backupPaymentMethod) {
        this.backupPaymentMethod = stringOrNull(backupPaymentMethod);
    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("BillingInfo");

        // Prevent infinite loop when printing account.
        // See https://github.com/killbilling/recurly-java-library/issues/326
        // Prevent Null Pointer Exception when printing updated billing info
        // See https://github.com/killbilling/recurly-java-library/issues/405
        Account account = getAccount();
        if (account != null && account.getBillingInfo() != null && this.getHref() != null
        && this.getHref().equals(account.getBillingInfo().getHref())) {
            sb.append("{account='").append(account.getAccountCode()).append('\'');
        } else {
            sb.append("{account='").append(account).append('\'');
        }

        sb.append(", type='").append(type).append('\'');
        sb.append(", nameOnAccount='").append(nameOnAccount).append('\'');
        sb.append(", uuid='").append(uuid);
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", mandateReference='").append(mandateReference).append('\'');
        sb.append(", company='").append(company).append('\'');
        sb.append(", address1='").append(address1).append('\'');
        sb.append(", address2='").append(address2).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", state='").append(state).append('\'');
        sb.append(", zip='").append(zip).append('\'');
        sb.append(", country='").append(country).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", vatNumber='").append(vatNumber).append('\'');
        sb.append(", ipAddress='").append(ipAddress).append('\'');
        sb.append(", ipAddressCountry='").append(ipAddressCountry).append('\'');
        sb.append(", accountType='").append(accountType).append('\'');
        sb.append(", cardType='").append(cardType).append('\'');
        sb.append(", year=").append(year);
        sb.append(", month=").append(month);
        sb.append(", firstSix='").append(firstSix).append('\'');
        sb.append(", lastFour='").append(lastFour).append('\'');
        sb.append(", cardNetworkPreference='").append(cardNetworkPreference).append('\'');
        sb.append(", routingNumber='").append(routingNumber).append('\'');
        sb.append(", geoCode='").append(geoCode).append('\'');
        sb.append(", updatedAt='").append(updatedAt).append('\'');
        sb.append(", externalHppType='").append(externalHppType).append('\'');
        sb.append(", onlineBankingPaymentType='").append(onlineBankingPaymentType).append('\'');
        sb.append(", gatewayToken='").append(gatewayToken).append('\'');
        sb.append(", gatewayCode='").append(gatewayCode).append('\'');
        sb.append(", gatewayAttributes='").append(gatewayAttributes).append('\'');
        sb.append(", amazonBillingAgreementId='").append(amazonBillingAgreementId).append('\'');
        sb.append(", amazonRegion='").append(amazonRegion).append('\'');
        sb.append(", threeDSecureActionResultTokenId='").append(threeDSecureActionResultTokenId).append('\'');
        sb.append(", iban='").append(iban).append('\'');
        sb.append(", lastTwo='").append(lastTwo).append('\'');
        sb.append(", sortCode='").append(sortCode).append('\'');
        sb.append(", bsbCode='").append(bsbCode).append('\'');
        sb.append(", taxIdentifier='").append(taxIdentifier).append('\'');
        sb.append(", taxIdentifierType='").append(taxIdentifierType).append('\'');
        sb.append(", primaryPaymentMethod='").append(primaryPaymentMethod).append('\'');
        sb.append(", backupPaymentMethod='").append(backupPaymentMethod).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final BillingInfo that = (BillingInfo) o;

        if (account != null ? !account.equals(that.account) : that.account != null) {
            return false;
        }
        if (nameOnAccount != null ? !nameOnAccount.equals(that.nameOnAccount) : that.nameOnAccount != null) {
            return false;
        }
        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) {
            return false;
        }
        if (address1 != null ? !address1.equals(that.address1) : that.address1 != null) {
            return false;
        }
        if (address2 != null ? !address2.equals(that.address2) : that.address2 != null) {
            return false;
        }
        if (cardType != null ? !cardType.equals(that.cardType) : that.cardType != null) {
            return false;
        }
        if (city != null ? !city.equals(that.city) : that.city != null) {
            return false;
        }
        if (company != null ? !company.equals(that.company) : that.company != null) {
            return false;
        }
        if (country != null ? !country.equals(that.country) : that.country != null) {
            return false;
        }
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) {
            return false;
        }
        if (mandateReference != null ? !mandateReference.equals(that.mandateReference) : that.mandateReference != null) {
            return false;
        }
        if (firstSix != null ? !firstSix.equals(that.firstSix) : that.firstSix != null) {
            return false;
        }
        if (ipAddress != null ? !ipAddress.equals(that.ipAddress) : that.ipAddress != null) {
            return false;
        }
        if (ipAddressCountry != null ? !ipAddressCountry.equals(that.ipAddressCountry) : that.ipAddressCountry != null) {
            return false;
        }
        if (accountType != null ? !accountType.equals(that.accountType) : that.accountType != null) {
            return false;
        }
        if (lastFour != null ? !lastFour.equals(that.lastFour) : that.lastFour != null) {
            return false;
        }
        if (cardNetworkPreference != null ? !cardNetworkPreference.equals(that.cardNetworkPreference) : that.cardNetworkPreference != null) {
            return false;
        }
        if (routingNumber != null ? !routingNumber.equals(that.routingNumber) : that.routingNumber != null) {
            return false;
        }
        if (accountNumber != null ? !accountNumber.equals(that.accountNumber) : that.accountNumber != null) {
            return false;
        }
        if (number != null ? !number.equals(that.number) : that.number != null) {
            return false;
        }
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) {
            return false;
        }
        if (month != null ? !month.equals(that.month) : that.month != null) {
            return false;
        }
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) {
            return false;
        }
        if (state != null ? !state.equals(that.state) : that.state != null) {
            return false;
        }
        if (type != null ? !type.equals(that.type) : that.type != null) {
            return false;
        }
        if (vatNumber != null ? !vatNumber.equals(that.vatNumber) : that.vatNumber != null) {
            return false;
        }
        if (year != null ? !year.equals(that.year) : that.year != null) {
            return false;
        }
        if (zip != null ? !zip.equals(that.zip) : that.zip != null) {
            return false;
        }
        if (geoCode != null ? !geoCode.equals(that.geoCode) : that.geoCode != null) {
            return false;
        }
        if (gatewayToken != null ? !gatewayToken.equals(that.gatewayToken) : that.gatewayToken != null) {
            return false;
        }
        if (gatewayCode != null ? !gatewayCode.equals(that.gatewayCode) : that.gatewayCode != null) {
            return false;
        }
        if (gatewayAttributes != null ? !gatewayAttributes.equals(that.gatewayAttributes) : that.gatewayAttributes != null) {
            return false;
        }
        if (updatedAt != null ? updatedAt.compareTo(that.updatedAt) != 0 : that.updatedAt != null) {
            return false;
        }
        if (externalHppType != null ? !externalHppType.equals(that.externalHppType) : that.externalHppType != null) {
            return false;
        }
        if (onlineBankingPaymentType != null ? !onlineBankingPaymentType.equals(that.onlineBankingPaymentType) : that.onlineBankingPaymentType != null) {
            return false;
        }
        if (amazonBillingAgreementId != null ? !amazonBillingAgreementId.equals(that.amazonBillingAgreementId) : that.amazonBillingAgreementId != null) {
            return false;
        }
        if (amazonRegion != null ? !amazonRegion.equals(that.amazonRegion) : that.amazonRegion != null) {
            return false;
        }
        if (threeDSecureActionResultTokenId != null ? !threeDSecureActionResultTokenId.equals(that.threeDSecureActionResultTokenId) : that.threeDSecureActionResultTokenId != null) {
            return false;
        }
        if (transactionType != null ? !transactionType.equals(that.transactionType) : that.transactionType != null) {
            return false;
        }
        if (iban != null ? !iban.equals(that.iban) : that.iban != null) {
            return false;
        }
        if (lastTwo != null ? !lastTwo.equals(that.lastTwo) : that.lastTwo != null) {
            return false;
        }
        if (type != null ? !type.equals(that.type) : that.type != null) {
            return false;
        }
        if (sortCode != null ? !sortCode.equals(that.sortCode) : that.sortCode != null ) {
            return false;
        }
        if (bsbCode != null ? !bsbCode.equals(that.bsbCode) : that.bsbCode != null) {
            return false;
        }
        if (taxIdentifier != null ? !taxIdentifier.equals(that.taxIdentifier) : that.taxIdentifier != null) {
            return false;
        }
        if (taxIdentifierType != null ? !taxIdentifierType.equals(that.taxIdentifierType) : that.taxIdentifierType != null) {
            return false;
        }
        if (primaryPaymentMethod != null ? !primaryPaymentMethod.equals(that.primaryPaymentMethod) : that.primaryPaymentMethod != null) {
            return false;
        }
        if (backupPaymentMethod != null ? !backupPaymentMethod.equals(that.backupPaymentMethod) : that.backupPaymentMethod != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
                account,
                nameOnAccount,
                uuid,
                firstName,
                lastName,
                mandateReference,
                company,
                address1,
                address2,
                city,
                state,
                zip,
                country,
                phone,
                vatNumber,
                ipAddress,
                ipAddressCountry,
                accountType,
                cardType,
                year,
                month,
                firstSix,
                lastFour,
                number,
                cardNetworkPreference,
                routingNumber,
                accountNumber,
                updatedAt,
                geoCode,
                type,
                externalHppType,
                onlineBankingPaymentType,
                gatewayToken,
                gatewayCode,
                gatewayAttributes,
                amazonBillingAgreementId,
                amazonRegion,
                threeDSecureActionResultTokenId,
                transactionType,
                iban,
                lastTwo,
                sortCode,
                bsbCode,
                taxIdentifier,
                taxIdentifierType,
                primaryPaymentMethod,
                backupPaymentMethod
        );
    }
}
