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

import org.joda.time.DateTime;

@XmlRootElement(name = "external_account")
public class ExternalAccount extends RecurlyObject {

  @XmlElement(name = "id")
  private String id;

  @XmlElement(name = "external_account_code")
  private String externalAccountCode;

  @XmlElement(name = "external_connection_type")
  private String externalConnectionType;

  @XmlElement(name = "created_at")
  private DateTime createdAt;

  @XmlElement(name = "updated_at")
  private DateTime updatedAt;

  public String getId() {
    return this.id;
  }

  public void setId(final Object id) {
    this.id = stringOrNull(id);
  }

  public String getExternalAccountCode() {
    return this.externalAccountCode;
  }

  public void setExternalAccountCode(final Object externalAccountCode) {
    this.externalAccountCode = stringOrNull(externalAccountCode);
  }

  public String getExternalConnectionType() {
    return this.externalConnectionType;
  }

  public void setExternalConnectionType(final Object externalConnectionType) {
    this.externalConnectionType = stringOrNull(externalConnectionType);
  }

  public DateTime getCreatedAt() {
    return this.createdAt;
  }

  public void setCreatedAt(final Object createdAt) {
    this.createdAt = dateTimeOrNull(createdAt);
  }

  public DateTime getUpdatedAt() {
    return this.updatedAt;
  }

  public void setUpdatedAt(final Object updatedAt) {
    this.updatedAt = dateTimeOrNull(updatedAt);
  }
}
