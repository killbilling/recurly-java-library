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

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

@XmlRootElement(name = "external_accounts")
public class ExternalAccounts extends RecurlyObjects<ExternalAccount> {
  
  @XmlTransient
  public static final String EXTERNAL_ACCOUNTS_RESOURCE = "/external_accounts";

  @XmlTransient
  public static final String PROPERTY_NAME = "external_account";

  @JsonSetter(value = PROPERTY_NAME)
  @Override
  public void setRecurlyObject(final ExternalAccount value) {
      super.setRecurlyObject(value);
  }

  @JsonIgnore
  @Override
  public ExternalAccounts getStart() {
      return getStart(ExternalAccounts.class);
  }

  @JsonIgnore
  @Override
  public ExternalAccounts getNext() {
      return getNext(ExternalAccounts.class);
  }
}
