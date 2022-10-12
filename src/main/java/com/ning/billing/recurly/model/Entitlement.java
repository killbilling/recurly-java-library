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
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;
import org.joda.time.DateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;

@XmlRootElement(name = "entitlement")
public class Entitlement extends RecurlyObject {

    @XmlElement(name = "customer_permission")
    private CustomerPermission customerPermission;

    @XmlList
    @XmlElementWrapper(name = "granted_by")
    private List grantedBy;

    @XmlElement(name = "created_at")
    private DateTime createdAt;

    @XmlElement(name = "updated_at")
    private DateTime updatedAt;

    public CustomerPermission getCustomerPermission() {
      return this.customerPermission;
    }

    public void setCustomerPermission(final CustomerPermission customerPermission) {
      this.customerPermission = customerPermission;
    }

    public List<String> getGrantedBy() {
      List<String> hrefList = new ArrayList<String>();
      for (Object grantedByObject : this.grantedBy) {
          hrefList.add(((LinkedHashMap) grantedByObject).get("href").toString());
      }
      return hrefList;
    }

    public void setGrantedBy(final List grantedBy) {
      this.grantedBy = grantedBy;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final Object createdAt) {
        this.createdAt = dateTimeOrNull(createdAt);
    }

    public DateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(final Object updatedAt) {
        this.updatedAt = dateTimeOrNull(updatedAt);
    }
}