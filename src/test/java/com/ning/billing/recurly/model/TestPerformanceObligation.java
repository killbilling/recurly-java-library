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

 import java.util.List;

 import org.joda.time.DateTime;
 import org.testng.Assert;
 import static org.testng.Assert.assertTrue;
 import org.testng.annotations.Test;

 public class TestPerformanceObligation extends TestModelBase {

     @Test(groups = "fast")
     public void testDeserialization() throws Exception {
        final String performanceObligationData =
           "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
           "<performance_obligation href=\"https://your-subdomain.recurly.com/v2/performance_obligation/scaig66ovogw\">" +
           "  <id>scaig66ovogw</id>" +
           "  <name>pob-1</name>" +
           "  <created_at type=\"datetime\">2023-05-04T17:45:43Z</created_at>" +
           "  <updated_at type=\"datetime\">2023-05-04T17:45:43Z</updated_at>" +
           "</performance_obligation>";

        final PerformanceObligation performanceObligation = xmlMapper.readValue(performanceObligationData, PerformanceObligation.class);

        Assert.assertEquals(performanceObligation.getHref(), "https://your-subdomain.recurly.com/v2/performance_obligation/scaig66ovogw");
        Assert.assertEquals(performanceObligation.getId(), "scaig66ovogw");
        Assert.assertEquals(performanceObligation.getName(), "pob-1");
        Assert.assertEquals(performanceObligation.getCreatedAt(), new DateTime("2023-05-04T17:45:43Z"));
        Assert.assertEquals(performanceObligation.getUpdatedAt(), new DateTime("2023-05-04T17:45:43Z"));
     }
 }
