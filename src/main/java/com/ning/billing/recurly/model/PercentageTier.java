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

import java.math.BigDecimal;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "percentage_tier")
public class PercentageTier extends RecurlyObject {

  @XmlElement(name = "ending_amount_in_cents")
  private Integer endingAmountInCents;

  @XmlElement(name = "usage_percentage")
  private BigDecimal usagePercentage;

  public Integer getEndingAmountInCents() {
    return endingAmountInCents;
  }

  public void setEndingAmountInCents(final Object endingAmountInCents) {
      this.endingAmountInCents = RecurlyObject.integerOrNull(endingAmountInCents);
  }

  public BigDecimal getUsagePercentage() {
    return usagePercentage;
  }

  public void setUsagePercentage(final Object usagePercentage) {
    this.usagePercentage = bigDecimalOrNull(usagePercentage);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("PercentageTier{");
    sb.append("endingAmountInCents=").append(endingAmountInCents);
    sb.append(", usagePercentage=").append(usagePercentage);
    sb.append('}');
    return sb.toString();
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final PercentageTier percentageTier = (PercentageTier) o;

    if (endingAmountInCents != null ? !endingAmountInCents.equals(percentageTier.endingAmountInCents) : percentageTier.endingAmountInCents != null) {
      return false;
    }
    if (usagePercentage != null ? !usagePercentage.equals(percentageTier.usagePercentage) : percentageTier.usagePercentage != null) {
      return false;
    }

    return true;
  }

  @Override 
  public int hashCode() {
    return Objects.hash(
      endingAmountInCents,
      usagePercentage
    );
  }
}
