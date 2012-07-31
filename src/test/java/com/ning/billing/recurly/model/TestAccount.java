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

import java.util.UUID;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableList;

public class TestAccount extends TestModelBase {

    @Test(groups = "fast")
    public void testSerialization() throws Exception {
        final Account accountData = new Account();
        accountData.setAccountCode(UUID.randomUUID().toString().substring(0, 5));
        accountData.setEmail(UUID.randomUUID().toString().substring(0, 5) + "@laposte.net");
        accountData.setFirstName(UUID.randomUUID().toString().substring(0, 5));
        accountData.setLastName(UUID.randomUUID().toString().substring(0, 5));
        accountData.setUsername(UUID.randomUUID().toString().substring(0, 5));
        accountData.setAcceptLanguage("en_US");
        accountData.setCompanyName(UUID.randomUUID().toString().substring(0, 5));

        final Adjustment adjustment = new Adjustment();
        adjustment.setCurrency("USD");
        accountData.setAdjustments(ImmutableList.<Adjustment>of(adjustment));

        final String xml = xmlMapper.writeValueAsString(accountData);
        Assert.assertEquals(xmlMapper.readValue(xml, Account.class), accountData);
    }
}
