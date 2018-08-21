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
import com.google.common.base.Objects;

@XmlRootElement(name = "address")
public class Address extends RecurlyObject {

    @XmlElement(name = "first_name")
    private String firstName;

    @XmlElement(name = "last_name")
    private String lastName;

    @XmlElement(name = "name_on_account")
    private String nameOnAccount;

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

    @XmlElement(name = "geo_code")
    private String geoCode;

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

    public String getNameOnAccount() {
        return nameOnAccount;
    }

    public void setNameOnAccount(final Object nameOnAccount) {
        this.nameOnAccount = stringOrNull(nameOnAccount);
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

    public String getGeoCode() {
        return geoCode;
    }

    public void setGeoCode(final Object geoCode) {
        this.geoCode = stringOrNull(geoCode);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Address{");
        sb.append("firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", nameOnAccount='").append(nameOnAccount).append('\'');
        sb.append(", company='").append(company).append('\'');
        sb.append(", address1='").append(address1).append('\'');
        sb.append(", address2='").append(address2).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", state='").append(state).append('\'');
        sb.append(", zip=").append(zip);
        sb.append(", country='").append(country).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", geoCode='").append(geoCode).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Address address = (Address) o;

        if (firstName != null ? !firstName.equals(address.firstName) : address.firstName != null) {
            return false;
        }
        if (lastName != null ? !lastName.equals(address.lastName) : address.lastName != null) {
            return false;
        }
        if (nameOnAccount != null ? !nameOnAccount.equals(address.nameOnAccount) : address.nameOnAccount != null) {
            return false;
        }
        if (company != null ? !company.equals(address.company) : address.company != null) {
            return false;
        }
        if (address1 != null ? !address1.equals(address.address1) : address.address1 != null) {
            return false;
        }
        if (address2 != null ? !address2.equals(address.address2) : address.address2 != null) {
            return false;
        }
        if (city != null ? !city.equals(address.city) : address.city != null) {
            return false;
        }
        if (country != null ? !country.equals(address.country) : address.country != null) {
            return false;
        }
        if (phone != null ? !phone.equals(address.phone) : address.phone != null) {
            return false;
        }
        if (state != null ? !state.equals(address.state) : address.state != null) {
            return false;
        }
        if (zip != null ? !zip.equals(address.zip) : address.zip != null) {
            return false;
        }
        if (geoCode != null ? !geoCode.equals(address.geoCode) : address.geoCode != null) {
            return false;
        }

        return true;
    }


    @Override
    public int hashCode() {
        return Objects.hashCode(
                firstName,
                lastName,
                nameOnAccount,
                company,
                address1,
                address2,
                city,
                state,
                zip,
                country,
                phone,
                geoCode
        );
    }
}
