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
import org.joda.time.DateTime;

@XmlRootElement(name = "ramp_interval")
public class SubscriptionRampInterval extends RecurlyObject {

  @XmlElement(name = "starting_billing_cycle")
  protected Integer startingBillingCycle;

  @XmlElement(name = "remaining_billing_cycles")
  private Integer remainingBillingCycles;

  @XmlElement(name = "unit_amount_in_cents")
  protected Integer unitAmountInCents;

  @XmlElement(name = "starting_on")
  private DateTime startingOn;

  @XmlElement(name = "ending_on")
  private DateTime endingOn;

  public Integer getStartingBillingCycle() {
    return startingBillingCycle;
  }

  public void setStartingBillingCycle(final Object startingBillingCycle) {
    this.startingBillingCycle = integerOrNull(startingBillingCycle);
  }

  public Integer getRemainingBillingCycles() {
    return remainingBillingCycles;
  }

  public void setRemainingBillingCycles(final Object remainingBillingCycles) {
    this.remainingBillingCycles = integerOrNull(remainingBillingCycles);
  }

  public Integer getUnitAmountInCents() {
    return unitAmountInCents;
  }

  public void setUnitAmountInCents(final Object unitAmountInCents) {
    this.unitAmountInCents = integerOrNull(unitAmountInCents);
  }

  public DateTime getStartingOn() {
    return startingOn;
  }

  public void setStartingOn(final Object startingOn) {
    this.startingOn = dateTimeOrNull(startingOn);
  }

  public DateTime getEndingOn() {
    return endingOn;
  }

  public void setEndingOn(final Object endingOn) {
    this.endingOn = dateTimeOrNull(endingOn);
  }

  public String toString() {
    final String className = getClass().getSimpleName();
    final StringBuilder builder = new StringBuilder(className + " { ");

    builder.append("startingBillingCycle=").append(startingBillingCycle);
    builder.append(", unitAmountInCents=").append(unitAmountInCents);
    builder.append(", remainingBillingCycles=").append(remainingBillingCycles);
    builder.append(", startingOn=").append(startingOn);
    builder.append(", endingOn=").append(endingOn);
    builder.append(" }\n ");

    return builder.toString();
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final SubscriptionRampInterval rampInterval = (SubscriptionRampInterval) o;

    if (unitAmountInCents != null ? !unitAmountInCents.equals(rampInterval.unitAmountInCents) : rampInterval.unitAmountInCents != null) {
      return false;
    }
    if (startingBillingCycle != null ? !startingBillingCycle.equals(rampInterval.startingBillingCycle) : rampInterval.startingBillingCycle != null) {
      return false;
    }
    if (remainingBillingCycles != null ? !remainingBillingCycles.equals(rampInterval.remainingBillingCycles) : rampInterval.remainingBillingCycles != null) {
      return false;
    }
    if (startingOn != null ? startingOn.compareTo(rampInterval.startingOn) != 0 : rampInterval.startingOn != null) {
        return false;
    }
    if (endingOn != null ? endingOn.compareTo(rampInterval.endingOn) != 0 : rampInterval.endingOn != null) {
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return Objects.hash(
      startingBillingCycle,
      unitAmountInCents,
      remainingBillingCycles,
      startingOn,
      endingOn
    );
  }

}
