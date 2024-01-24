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

@XmlRootElement(name = "general_ledger_account")
public class GeneralLedgerAccount extends RecurlyObject {

    @XmlElement(name = "id")
    private String id;

    @XmlElement(name = "code")
    private String code;

    @XmlElement(name = "account_type")
    private String accountType;

    @XmlElement(name = "description")
    private String description;

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

    public String getCode() {
      return this.code;
    }

    public void setCode(final Object code) {
      this.code = stringOrNull(code);
    }

    public String getAccountType() {
      return this.accountType;
    }

    public void setAccountType(final Object type) {
      this.accountType = stringOrNull(type);
    }

    public String getDescription() {
      return this.description;
    }

    public void setDescription(final Object description) {
      this.description = stringOrNull(description);
    }

    public DateTime getCreatedAt() {
      return this.createdAt;
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

  @Override
  public String toString() {
    return "{" +
      " id='" + getId() + "'" +
      ", code='" + getCode() + "'" +
      ", accountType='" + getAccountType() + "'" +
      ", description='" + getDescription() + "'" +
      ", createdAt='" + getCreatedAt() + "'" +
      ", updatedAt='" + getUpdatedAt() + "'" +
      "}";
  }

}