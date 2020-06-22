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

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "tier")
public class Tier extends RecurlyObject {

  @XmlElement(name = "unit_amount_in_cents")
  private RecurlyUnitCurrency unitAmountInCents;

  @XmlElement(name = "ending_quantity")
  private Integer endingQuantity;

  public RecurlyUnitCurrency getUnitAmountInCents() {
    return unitAmountInCents;
  }

  public void setUnitAmountInCents(final RecurlyUnitCurrency unitAmountInCents) {
      this.unitAmountInCents = unitAmountInCents;
  }

  public Integer getEndingQuantity() {
    return endingQuantity;
  }

  public void setEndingQuantity(final Object endingQuantity) {
    this.endingQuantity = integerOrNull(endingQuantity);
  }

  @Override
  public String toString() {
      String sb = "Tier{" + "unitAmountInCents=" + unitAmountInCents +
              ", endingQuantity=" + endingQuantity +
              '}';
      return sb;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final Tier tier = (Tier) o;

    if (unitAmountInCents != null ? !unitAmountInCents.equals(tier.unitAmountInCents) : tier.unitAmountInCents != null) {
      return false;
    }
    if (endingQuantity != null ? !endingQuantity.equals(tier.endingQuantity) : tier.endingQuantity != null) {
      return false;
    }
    return true;
  }

  @Override 
  public int hashCode() {
    return Objects.hash(
      unitAmountInCents,
      endingQuantity
    );
  }
}
