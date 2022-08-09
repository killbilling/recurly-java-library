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

@XmlRootElement(name = "ramp_interval")
public class AbstractRampInterval<T> extends RecurlyObject {

  @XmlElement(name = "starting_billing_cycle")
  protected Integer startingBillingCycle;

  @XmlElement(name = "unit_amount_in_cents")
  protected T unitAmountInCents;

  public Integer getStartingBillingCycle() {
    return startingBillingCycle;
  }

  public void setStartingBillingCycle(final Object startingBillingCycle) {
    this.startingBillingCycle = integerOrNull(startingBillingCycle);
  }

  public T getUnitAmountInCents() {
    return unitAmountInCents;
  }

  public void setUnitAmountInCents(final T unitAmountInCents) {
    this.unitAmountInCents = unitAmountInCents;
  }

  @Override
  public String toString() {
    final String className = getClass().getSimpleName();
    final StringBuilder builder = new StringBuilder(className + "{ ");
    builder.append("startingBillingCycle=").append(startingBillingCycle);
    builder.append(", unitAmountInCents=").append(unitAmountInCents);
    builder.append(" }");
    return builder.toString();
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final AbstractRampInterval rampInterval = (AbstractRampInterval) o;

    if (unitAmountInCents != null ? !unitAmountInCents.equals(rampInterval.unitAmountInCents) : rampInterval.unitAmountInCents != null) {
      return false;
    }
    if (startingBillingCycle != null ? !startingBillingCycle.equals(rampInterval.startingBillingCycle) : rampInterval.startingBillingCycle != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return Objects.hash(
      startingBillingCycle,
      unitAmountInCents
    );
  }
}
