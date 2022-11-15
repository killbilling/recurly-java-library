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
import javax.xml.bind.annotation.XmlRootElement;
import org.joda.time.DateTime;

@XmlRootElement(name = "external_product")
public class ExternalProduct extends RecurlyObject {

    @XmlElement(name = "plan")
    private Plan plan;

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "created_at")
    private DateTime createdAt;

    @XmlElement(name = "updated_at")
    private DateTime updatedAt;

    @XmlElement(name = "external_product_reference")
    @XmlElementWrapper(name = "external_product_references")
    private ExternalProductReferences externalProductReferences;

    public Plan getPlan() {
      return this.plan;
    }

    public void setPlan(final Plan plan) {
      this.plan = plan;
    }

    public String getName() {
      return this.name;
    }

    public void setName(final Object name) {
      this.name = stringOrNull(name);
    }

    public ExternalProductReferences getExternalProductReferences() {
      return this.externalProductReferences;
    }

    public void setExternalProductReferences(final ExternalProductReferences externalProductReferences) {
      this.externalProductReferences = externalProductReferences;
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