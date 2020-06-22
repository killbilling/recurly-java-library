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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class TestAccountAcquisition extends TestModelBase {

    @Test(groups = "fast")
    public void testSerialization() throws Exception {
        // See https://dev.recurly.com/docs/create-account-acquisition
        final String acquisitionData = "<account_acquisition href=\"https://api.recurly.com/v2/accounts/1/acquisition\">\n" +
                "  <account href=\"https://your-subdomain.recurly.com/v2/accounts/1\"/>\n" +
                "  <cost_in_cents type=\"integer\">199</cost_in_cents>\n" +
                "  <currency>USD</currency>\n" +
                "  <channel>blog</channel>\n" +
                "  <subchannel>Whitepaper Blog Post</subchannel>\n" +
                "  <campaign>mailchimp67a904de95.0914d8f4b4</campaign>\n" +
                "  <created_at type=\"datetime\">2016-08-12T19:45:14Z</created_at>\n" +
                "  <updated_at type=\"datetime\">2016-08-12T19:45:14Z</updated_at>\n" +
                "</account_acquisition>";

        final AccountAcquisition acquisition = xmlMapper.readValue(acquisitionData, AccountAcquisition.class);
        Assert.assertEquals(acquisition.getHref(), "https://api.recurly.com/v2/accounts/1/acquisition");
        verifyAccountAcquisition(acquisition);

        // Verify serialization
        final String acquisitionSerialized = xmlMapper.writeValueAsString(acquisition);
        final AccountAcquisition acquisition2 = xmlMapper.readValue(acquisitionSerialized, AccountAcquisition.class);
        verifyAccountAcquisition(acquisition2);
    }

    @Test(groups = "fast")
    public void testHashCodeAndEquality() throws Exception {
        // create accounts of the same value but difference references
        AccountAcquisition acquisition = TestUtils.createRandomAccountAcquisition(0);
        AccountAcquisition otherAcquisition = TestUtils.createRandomAccountAcquisition(0);

        assertNotEquals(System.identityHashCode(acquisition), System.identityHashCode(otherAcquisition));
        assertEquals(acquisition.hashCode(), otherAcquisition.hashCode());
        assertEquals(acquisition, otherAcquisition);
    }

    private void verifyAccountAcquisition(final AccountAcquisition acquisition) {
        Assert.assertEquals(acquisition.getCampaign(), "mailchimp67a904de95.0914d8f4b4");
        Assert.assertEquals(acquisition.getChannel(), AcquisitionChannel.BLOG);
        Assert.assertEquals(acquisition.getCurrency(), "USD");
        Assert.assertEquals(acquisition.getCostInCents(), new Integer(199));
        Assert.assertEquals(acquisition.getSubchannel(), "Whitepaper Blog Post");
    }
}
