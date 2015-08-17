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

@XmlRootElement(name = "billing_info")
public class BillingInfo extends RecurlyObject {

    @XmlTransient
    public static final String BILLING_INFO_RESOURCE = "/billing_info";

    @XmlElement(name = "account")
    private Account account;

    @XmlElement(name = "first_name")
    private String firstName;

    @XmlElement(name = "last_name")
    private String lastName;

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

    @XmlElement(name = "verification_value")
    private String verificationValue;

    @XmlElement(name = "token_id")
    private String tokenId;


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

    public void setAccount(final Account account) {
        this.account = account;
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

    public String getTokenId(){
        return tokenId;
    }

    public void setTokenId(final String tokenId){
        this.tokenId = tokenId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("BillingInfo");
        sb.append("{account='").append(account).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
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
        sb.append(", cardType='").append(cardType).append('\'');
        sb.append(", year=").append(year);
        sb.append(", month=").append(month);
        sb.append(", firstSix='").append(firstSix).append('\'');
        sb.append(", lastFour='").append(lastFour).append('\'');
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

        final BillingInfo that = (BillingInfo) o;

        if (account != null ? !account.equals(that.account) : that.account != null) {
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
        if (firstSix != null ? !firstSix.equals(that.firstSix) : that.firstSix != null) {
            return false;
        }
        if (ipAddress != null ? !ipAddress.equals(that.ipAddress) : that.ipAddress != null) {
            return false;
        }
        if (ipAddressCountry != null ? !ipAddressCountry.equals(that.ipAddressCountry) : that.ipAddressCountry != null) {
            return false;
        }
        if (lastFour != null ? !lastFour.equals(that.lastFour) : that.lastFour != null) {
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
        if (vatNumber != null ? !vatNumber.equals(that.vatNumber) : that.vatNumber != null) {
            return false;
        }
        if (year != null ? !year.equals(that.year) : that.year != null) {
            return false;
        }
        if (zip != null ? !zip.equals(that.zip) : that.zip != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = account != null ? account.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (company != null ? company.hashCode() : 0);
        result = 31 * result + (address1 != null ? address1.hashCode() : 0);
        result = 31 * result + (address2 != null ? address2.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (zip != null ? zip.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (vatNumber != null ? vatNumber.hashCode() : 0);
        result = 31 * result + (ipAddress != null ? ipAddress.hashCode() : 0);
        result = 31 * result + (ipAddressCountry != null ? ipAddressCountry.hashCode() : 0);
        result = 31 * result + (cardType != null ? cardType.hashCode() : 0);
        result = 31 * result + (year != null ? year.hashCode() : 0);
        result = 31 * result + (month != null ? month.hashCode() : 0);
        result = 31 * result + (firstSix != null ? firstSix.hashCode() : 0);
        result = 31 * result + (lastFour != null ? lastFour.hashCode() : 0);
        return result;
    }
}
