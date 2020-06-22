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
import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class TestDelivery extends TestModelBase {

    @Test(groups = "fast")
    public void testSerialization() throws Exception {
        final String deliveryData = "" +
                "    <delivery>\n" +
                "         <method>email</method>\n" +
                "         <email_address>john@example.com</email_address>\n" +
                "         <deliver_at type=\"dateTime\">2016-12-27T07:00:00Z</deliver_at>\n" +
                "         <first_name>John</first_name>\n" +
                "         <last_name>Smith</last_name>\n" +
                "         <address>\n" +
                "            <address1>400 Alabama St</address1>\n" +
                "            <address2></address2>\n" +
                "            <city>San Francisco</city>\n" +
                "            <state>CA</state>\n" +
                "            <zip>94110</zip>\n" +
                "            <country>US</country>\n" +
                "            <phone>555-555-5555</phone>\n" +
                "         </address>\n" +
                "         <gifter_name>Sally</gifter_name>\n" +
                "         <personal_message>\n" +
                "        Hi John, Happy Birthday! I hope you have a great day! Love, Sally</personal_message>\n" +
                "    </delivery>";

        final Delivery delivery = xmlMapper.readValue(deliveryData, Delivery.class);

        Assert.assertEquals(delivery.getMethod(), "email");
        Assert.assertEquals(delivery.getEmailAddress(), "john@example.com");
        Assert.assertEquals(delivery.getFirstName(), "John");
        Assert.assertEquals(delivery.getLastName(), "Smith");
        Assert.assertEquals(delivery.getGifterName(), "Sally");
        Assert.assertEquals(delivery.getPersonalMessage(), "Hi John, Happy Birthday! I hope you have a great day! Love, Sally");
        Assert.assertEquals(delivery.getDeliverAt(), new DateTime("2016-12-27T07:00:00Z"));

        final Address address = delivery.getAddress();

        Assert.assertEquals(address.getAddress1(), "400 Alabama St");
        Assert.assertEquals(address.getAddress2(), "");
        Assert.assertEquals(address.getCity(), "San Francisco");
        Assert.assertEquals(address.getState(), "CA");
        Assert.assertEquals(address.getCountry(), "US");
        Assert.assertEquals(address.getZip(), "94110");
        Assert.assertEquals(address.getPhone(), "555-555-5555");
    }

    @Test(groups = "fast")
    public void testHashCodeAndEquality() throws Exception {
        Delivery delivery = TestUtils.createRandomDelivery(0);
        Delivery otherDelivery = TestUtils.createRandomDelivery(0);

        assertNotEquals(System.identityHashCode(delivery), System.identityHashCode(otherDelivery));
        assertEquals(delivery.hashCode(), otherDelivery.hashCode());
        assertEquals(delivery, otherDelivery);
    }
}

