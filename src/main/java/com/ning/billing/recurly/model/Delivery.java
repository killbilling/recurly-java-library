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
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class that represents Delivery details in the Recurly API.
 */
@XmlRootElement(name = "delivery")
public class Delivery extends RecurlyObject {

    @XmlElement(name = "address")
    private Address address;

    @XmlElement(name = "method")
    private String method;

    @XmlElement(name = "email_address")
    private String emailAddress;

    @XmlElement(name = "first_name")
    private String firstName;

    @XmlElement(name = "last_name")
    private String lastName;

    @XmlElement(name = "gifter_name")
    private String gifterName;

    @XmlElement(name = "personal_message")
    private String personalMessage;

    @XmlElement(name = "deliver_at")
    private DateTime deliverAt;

    public Address getAddress() {
        return address;
    }

    public void setAddress(final Address address) {
        this.address = address;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(final Object emailAddress) {
        this.emailAddress = stringOrNull(emailAddress);
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

    public String getGifterName() {
        return gifterName;
    }

    public void setGifterName(final Object gifterName) {
        this.gifterName= stringOrNull(gifterName);
    }

    public String getPersonalMessage() { return personalMessage; }

    public void setPersonalMessage(final Object personalMessage) {
        this.personalMessage = stringOrNull(personalMessage);
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(final String method) {
        this.method = method.toLowerCase();
    }

    public DateTime getDeliverAt() {
        return deliverAt;
    }

    public void setDeliverAt(final Object deliverAt) {
        this.deliverAt = dateTimeOrNull(deliverAt);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Delivery");
        sb.append("{ address='").append(address).append('\'');
        sb.append(", deliverAt='").append(deliverAt).append('\'');
        sb.append(", emailAddress='").append(emailAddress).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", gifterName='").append(gifterName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", method='").append(method).append('\'');
        sb.append(", personalMessage='").append(personalMessage).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Delivery delivery = (Delivery) o;

        if (address != null ? !address.equals(delivery.address) : delivery.address != null) {
            return false;
        }
        if (deliverAt != null ? deliverAt.compareTo(delivery.deliverAt) != 0 : delivery.deliverAt != null) {
            return false;
        }
        if (emailAddress != null ? !emailAddress.equals(delivery.emailAddress) : delivery.emailAddress != null) {
            return false;
        }
        if (firstName != null ? !firstName.equals(delivery.firstName) : delivery.firstName != null) {
            return false;
        }
        if (gifterName != null ? !gifterName.equals(delivery.gifterName) : delivery.gifterName != null) {
            return false;
        }
        if (lastName != null ? !lastName.equals(delivery.lastName) : delivery.lastName != null) {
            return false;
        }
        if (method != null ? !method.equals(delivery.method) : delivery.method != null) {
            return false;
        }
        if (personalMessage != null ? !personalMessage.equals(delivery.personalMessage) : delivery.personalMessage != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
                address,
                deliverAt,
                emailAddress,
                firstName,
                gifterName,
                lastName,
                method,
                personalMessage
        );
    }
}
