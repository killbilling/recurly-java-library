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

@XmlRootElement(name = "customer_permission")
public class CustomerPermission extends RecurlyObject {

    @XmlElement(name = "id")
    private String id;

    @XmlElement(name = "code")
    private String code;

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "description")
    private String description;

    public String getId() {
      return this.id;
    }

    public void setId(final Object id) {
      this.id = stringOrNull(id);
    }

    public String getCode() {
      return this.code;
    }

    public void setCode(final Object code) {
      this.code = stringOrNull(code);
    }

    public String getName() {
      return this.name;
    }

    public void setName(final Object name) {
      this.name = stringOrNull(name);
    }

    public String getDescription() {
      return this.description;
    }

    public void setDescription(final Object description) {
      this.description = stringOrNull(description);
    }
}