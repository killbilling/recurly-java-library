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

import com.ning.billing.recurly.TestUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.ning.billing.recurly.TestUtils.randomString;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class TestBillingInfo extends TestModelBase {

    @Test(groups = "fast")
    public void testSerialization() throws Exception {
        final BillingInfo billingInfo = new BillingInfo();
        billingInfo.setType(randomString());
        billingInfo.setAddress1(randomString());
        billingInfo.setAddress2(randomString());
        billingInfo.setCardType(randomString());
        billingInfo.setCity(randomString());
        billingInfo.setCompany(randomString());
        billingInfo.setCountry(randomString());
        billingInfo.setFirstName(randomString());
        billingInfo.setFirstSix(randomString());
        billingInfo.setIpAddress(randomString());
        billingInfo.setIpAddressCountry(randomString());
        billingInfo.setAccountType(randomString());
        billingInfo.setLastFour(randomString());
        billingInfo.setRoutingNumber(randomString());
        billingInfo.setAccountNumber(randomString());
        billingInfo.setLastName(randomString());
        billingInfo.setMonth(3);
        billingInfo.setNumber(randomString());
        billingInfo.setPhone(randomString());
        billingInfo.setState(randomString());
        billingInfo.setVatNumber(randomString());
        billingInfo.setVerificationValue("009"); //CVV can have leading zeroes
        billingInfo.setYear(Integer.MIN_VALUE);
        billingInfo.setZip(randomString());
        billingInfo.setGeoCode(randomString());
        billingInfo.setGatewayToken(randomString());
        billingInfo.setGatewayCode(randomString());
        billingInfo.setAmazonBillingAgreementId(randomString());
        billingInfo.setAmazonRegion(randomString());

        final String xml = xmlMapper.writeValueAsString(billingInfo);
        Assert.assertEquals(xmlMapper.readValue(xml, BillingInfo.class), billingInfo);
    }

    @Test(groups = "fast")
    public void testHashCodeAndEquality() throws Exception {
        // create billing infos of the same value but difference references
        BillingInfo info = TestUtils.createRandomBillingInfo(0);
        BillingInfo otherInfo = TestUtils.createRandomBillingInfo(0);

        assertNotEquals(System.identityHashCode(info), System.identityHashCode(otherInfo));
        assertEquals(info.hashCode(), otherInfo.hashCode());
        assertEquals(info, otherInfo);
    }
}
