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

package com.ning.billing.recurly.model.push;

import com.ning.billing.recurly.model.TestModelBase;
import com.ning.billing.recurly.model.push.payment.VoidPaymentNotification;
import org.testng.Assert;
import org.testng.annotations.Test;

// See https://recurly.readme.io/v2.0/page/webhooks
public class TestVoidNotification extends TestModelBase {

    @Test(groups = "fast")
    public void testDeserialization() throws Exception {
        final String voidData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                "<void_payment_notification>\n" +
                                "  <account>\n" +
                                "    <account_code>1</account_code>\n" +
                                "    <username nil=\"true\"></username>\n" +
                                "    <email>verena@example.com</email>\n" +
                                "    <first_name>Verena</first_name>\n" +
                                "    <last_name>Example</last_name>\n" +
                                "    <company_name nil=\"true\"></company_name>\n" +
                                "  </account>\n" +
                                "  <transaction>\n" +
                                "    <id>4997ace0f57341adb3e857f9f7d15de8</id>\n" +
                                "    <invoice_id>ffc64d71d4b5404e93f13aac9c63b007</invoice_id>\n" +
                                "    <invoice_number_prefix></invoice_number_prefix>\n" +
                                "    <invoice_number type=\"integer\">2059</invoice_number>\n" +
                                "    <subscription_id>1974a098jhlkjasdfljkha898326881c</subscription_id>\n" +
                                "    <action>purchase</action>\n" +
                                "    <date type=\"datetime\">2010-10-05T23:00:50Z</date>\n" +
                                "    <amount_in_cents type=\"integer\">235</amount_in_cents>\n" +
                                "    <status>void</status>\n" +
                                "    <message>Test Gateway: Successful test transaction</message>\n" +
                                "    <reference></reference>\n" +
                                "    <source>subscription</source>\n" +
                                "    <cvv_result code=\"M\">Match</cvv_result>\n" +
                                "    <avs_result code=\"D\">Street address and postal code match.</avs_result>\n" +
                                "    <avs_result_street></avs_result_street>\n" +
                                "    <avs_result_postal></avs_result_postal>\n" +
                                "    <test type=\"boolean\">true</test>\n" +
                                "    <voidable type=\"boolean\">false</voidable>\n" +
                                "    <refundable type=\"boolean\">false</refundable>\n" +
                                "  </transaction>\n" +
                                "</void_payment_notification>";

        Notification.Type detected = Notification.detect(voidData);
        Assert.assertEquals(detected.getJavaType(), VoidPaymentNotification.class);

        final VoidPaymentNotification voidPaymentNotification = Notification.read(voidData, VoidPaymentNotification.class);
        Assert.assertNotNull(voidPaymentNotification);
    }

}
