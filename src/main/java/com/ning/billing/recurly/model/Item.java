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

import com.google.common.base.Objects;
import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "item")
public class Item extends RecurlyObject {

  @XmlTransient
  public static final String ITEMS_RESOURCE = "/items";

  @XmlElement(name = "item_code")
  private String itemCode;

  @XmlElement(name = "name")
  private String name;

  @XmlElement(name = "description")
  private String description;

  @XmlElement(name = "external_sku")
  private String externalSku;

  @XmlElement(name = "accounting_code")
  private String accountingCode;

  @XmlElement(name = "revenue_schedule_type")
  private String revenueScheduleType;

  @XmlElement(name = "liability_gl_account_id")
  private String liabilityGlAccountId;

  @XmlElement(name = "revenue_gl_account_id")
  private String revenueGlAccountId;

  @XmlElement(name = "performance_obligation_id")
  private String performanceObligationId;

  @XmlElement(name = "state")
  private String state;

  @XmlElementWrapper(name = "custom_fields")
  @XmlElement(name = "custom_field")
  private CustomFields customFields;

  @XmlElement(name = "created_at")
  private DateTime createdAt;

  @XmlElement(name = "updated_at")
  private DateTime updatedAt;

  public String getItemCode() {
    return itemCode;
  }

  public void setItemCode(final Object itemCode) {
    this.itemCode = stringOrNull(itemCode);
  }

  public String getName() {
    return name;
  }

  public void setName(final Object name) {
    this.name = stringOrNull(name);
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(final Object description) {
      this.description = stringOrNull(description);
  }

  public String getExternalSku() {
    return externalSku;
  }

  public void setExternalSku(final Object externalSku) {
    this.externalSku = stringOrNull(externalSku);
  }

  public String getRevenueScheduleType() {
    return revenueScheduleType;
  }

  public void setRevenueScheduleType(final Object revenueScheduleType) {
    this.revenueScheduleType = stringOrNull(revenueScheduleType);
  }

  public String getLiabilityGlAccountId() {
    return liabilityGlAccountId;
  }

  public void setLiabilityGlAccountId(final Object liabilityGlAccountId) {
    this.liabilityGlAccountId = stringOrNull(liabilityGlAccountId);
  }

  public String getRevenueGlAccountId() {
    return revenueGlAccountId;
  }

  public void setRevenueGlAccountId(final Object revenueGlAccountId) {
    this.revenueGlAccountId = stringOrNull(revenueGlAccountId);
  }

  public String getPerformanceObligationId() {
    return performanceObligationId;
  }

  public void setPerformanceObligationId(final Object performanceObligationId) {
    this.performanceObligationId = stringOrNull(performanceObligationId);
  }

  public String getAccountingCode() {
    return accountingCode;
  }

  public void setAccountingCode(final Object accountingCode) {
    this.accountingCode = stringOrNull(accountingCode);
  }

  public String getState() {
    return state;
  }

  public void setState(final Object state) {
    this.state = stringOrNull(state);
  }

  public CustomFields getCustomFields() {
    return customFields;
  }

  public void setCustomFields(final CustomFields customFields) {
      this.customFields = customFields;
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

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("Item");
    sb.append(", itemCode='").append(itemCode).append('\'');
    sb.append(", name='").append(name).append('\'');
    sb.append(", description='").append(description).append('\'');
    sb.append(", externalSku='").append(externalSku).append('\'');
    sb.append(", accountingCode='").append(accountingCode).append('\'');
    sb.append(", revenueScheduleType='").append(revenueScheduleType).append('\'');
    sb.append(", liabilityGlAccountId='").append(liabilityGlAccountId).append('\'');
    sb.append(", revenueGlAccountId='").append(revenueGlAccountId).append('\'');
    sb.append(", performanceObligationId='").append(performanceObligationId).append('\'');
    sb.append(", state='").append(state).append('\'');
    sb.append(", customFields=").append(customFields);
    sb.append(", createdAt=").append(createdAt);
    sb.append(", updatedAt=").append(updatedAt);
    sb.append('}');
    return sb.toString();
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final Item item = (Item) o;

    if (accountingCode != null ? !accountingCode.equals(item.accountingCode) : item.accountingCode != null) {
      return false;
    }

    if (createdAt != null ? createdAt.compareTo(item.createdAt) != 0: item.createdAt != null) {
      return false;
    }

    if (customFields != null ? !customFields.equals(item.customFields) : item.customFields != null) {
      return false;
    }

    if (description != null ? !description.equals(item.description) : item.description != null) {
      return false;
    }

    if (externalSku != null ? !externalSku.equals(item.externalSku) : item.externalSku != null) {
      return false;
    }

    if (itemCode != null ? !itemCode.equals(item.itemCode) : item.itemCode != null) {
      return false;
    }

    if (name != null ? !name.equals(item.name) : item.name != null) {
      return false;
    }

    if (revenueScheduleType != null ? !revenueScheduleType.equals(item.revenueScheduleType) : item.revenueScheduleType != null) {
      return false;
    }

    if (liabilityGlAccountId != null ? !liabilityGlAccountId.equals(item.liabilityGlAccountId) : item.liabilityGlAccountId != null) {
      return false;
    }

    if (revenueGlAccountId != null ? !revenueGlAccountId.equals(item.revenueGlAccountId) : item.revenueGlAccountId != null) {
      return false;
    }

    if (performanceObligationId != null ? !performanceObligationId.equals(item.performanceObligationId) : item.performanceObligationId != null) {
      return false;
    }

    if (state != null ? !state.equals(item.state) : item.state != null) {
      return false;
    }

    if (updatedAt != null ? updatedAt.compareTo(item.updatedAt) != 0: item.updatedAt != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(
      accountingCode,
      customFields,
      createdAt,
      description,
      externalSku,
      itemCode,
      name,
      revenueScheduleType,
      liabilityGlAccountId,
      revenueGlAccountId,
      performanceObligationId,
      state,
      updatedAt
    );
  }
}
