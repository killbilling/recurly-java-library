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
import javax.xml.bind.annotation.XmlElementWrapper;

@XmlRootElement(name = "percentage_tier")
public class CurrencyPercentageTier extends RecurlyObject {

  @XmlElement(name = "currency")
  private String currency;

  @XmlElementWrapper(name = "tiers")
  @XmlElement(name = "tier")
  private AddonPercentageTiers tiers;

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(final Object currency) {
      this.currency = stringOrNull(currency);
  }

  public AddonPercentageTiers getTiers() {
    return tiers;
  }

  public void setTiers(final AddonPercentageTiers tiers) {
    this.tiers = tiers;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("CurrencyPercentageTier{");
    sb.append("currency=").append(currency);
    sb.append(", tiers=").append(tiers);
    sb.append('}');
    return sb.toString();
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final CurrencyPercentageTier currencyPercentageTier = (CurrencyPercentageTier) o;

    if (currency != null ? !currency.equals(currencyPercentageTier.currency) : currencyPercentageTier.currency != null) {
      return false;
    }
    if (tiers != null ? !tiers.equals(currencyPercentageTier.tiers) : currencyPercentageTier.tiers != null) {
      return false;
    }

    return true;
  }

  @Override 
  public int hashCode() {
    return Objects.hash(
      currency,
      tiers
    );
  }
}
