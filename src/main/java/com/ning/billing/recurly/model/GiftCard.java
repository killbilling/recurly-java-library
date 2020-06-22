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
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class that represents a gift card in the Recurly API.
 */
@XmlRootElement(name = "gift_card")
public class GiftCard extends RecurlyObject {

    @XmlElement(name = "product_code")
    private String productCode;

    @XmlElement(name = "currency")
    private String currency;

    @XmlElement(name = "id")
    private Long id;

    @XmlElement(name = "redemption_code")
    private String redemptionCode;

    @XmlElement(name = "gifter_account_code")
    private String gifterAccountCode;

    @XmlElement(name = "recipient_account_code")
    private String recipientAccountCode;

    @XmlElement(name = "unit_amount_in_cents")
    private Integer unitAmountInCents;

    @XmlElement(name = "balance_in_cents")
    private Integer balanceInCents;

    @XmlElement(name = "invoice_number")
    private Integer invoiceNumber;

    @XmlElement(name = "gifter_account")
    private Account gifterAccount;

    @XmlElement(name = "delivery")
    private Delivery delivery;

    @XmlElement(name = "created_at")
    private DateTime createdAt;

    @XmlElement(name = "updated_at")
    private DateTime updatedAt;

    @XmlElement(name = "redeemed_at")
    private DateTime redeemedAt;

    @XmlElement(name = "delivered_at")
    private DateTime deliveredAt;

    @XmlElement(name = "canceled_at")
    private DateTime canceledAt;

    public Long getId() {
        return id;
    }

    public void setId(final Object id) { this.id = longOrNull(id); }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(final Object productCode) {
        this.productCode = stringOrNull(productCode);
    }

    public String getRedemptionCode() {
        return redemptionCode;
    }

    public void setRedemptionCode(final Object redemptionCode) {
        this.redemptionCode = stringOrNull(redemptionCode);
    }

    public String getGifterAccountCode() {
        return gifterAccountCode;
    }

    public void setGifterAccountCode(final Object gifterAccountCode) {
        this.gifterAccountCode = stringOrNull(gifterAccountCode);
    }

    public String getRecipientAccountCode() {
        return recipientAccountCode;
    }

    public void setRecipientAccountCode(final Object recipientAccountCode) {
        this.recipientAccountCode = stringOrNull(recipientAccountCode);
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(final Object currency) {
        this.currency = stringOrNull(currency);
    }

    public Delivery getDelivery() { return delivery; }

    public void setDelivery(final Delivery delivery) {
        this.delivery = delivery;
    }

    public Account getGifterAccount() { return gifterAccount; }

    public void setGifterAccount(final Account gifterAccount) {
        this.gifterAccount = gifterAccount;
    }

    public Integer getUnitAmountInCents() { return unitAmountInCents; }

    public void setUnitAmountInCents(final Object unitAmountInCents) {
        this.unitAmountInCents = integerOrNull(unitAmountInCents);
    }

    public Integer getBalanceInCents() { return balanceInCents; }

    public void setBalanceInCents(final Object balanceInCents) {
        this.balanceInCents = integerOrNull(balanceInCents);
    }

    public Integer getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(final Object invoiceNumber) {
        this.invoiceNumber = integerOrNull(invoiceNumber);
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

    public DateTime getRedeemedAt() {
        return redeemedAt;
    }

    public void setRedeemedAt(final Object redeemedAt) {
        this.redeemedAt = dateTimeOrNull(redeemedAt);
    }

    public DateTime getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(final Object deliveredAt) {
        this.deliveredAt = dateTimeOrNull(deliveredAt);
    }

    public DateTime getCanceledAt() {
        return canceledAt;
    }

    public void setCanceledAt(final Object canceledAt) {
        this.canceledAt = dateTimeOrNull(canceledAt);
    }

    /**
     *  Builds a redemption request
     *
     * @param accountCode Account code to redeem
     * @return gift card redemption data for an account
     */
    public static GiftCard.Redemption createRedemption(String accountCode) {
        Redemption redemption = new Redemption();
        redemption.setAccountCode(accountCode);
        return redemption;
    }

    /**
     * Represents gift card redemption data
     */
    @XmlRootElement(name = "recipient_account")
    public static class Redemption extends RecurlyObject {

        @XmlElement(name = "account_code")
        private String accountCode;

        public String getAccountCode() { return accountCode; }

        public void setAccountCode(final String accountCode) {
            this.accountCode = accountCode;
        }
    }

    @Override
    public String toString() {
        String sb = "GiftCard" +
                "{ productCode='" + productCode + '\'' +
                ", currency='" + currency + '\'' +
                ", id='" + id + '\'' +
                ", redemptionCode='" + redemptionCode + '\'' +
                ", gifterAccountCode='" + gifterAccountCode + '\'' +
                ", recipientAccountCode='" + recipientAccountCode + '\'' +
                ", unitAmountInCents='" + unitAmountInCents + '\'' +
                ", balanceInCents='" + balanceInCents + '\'' +
                ", invoiceNumber='" + invoiceNumber + '\'' +
                ", gifterAccount='" + gifterAccount + '\'' +
                ", delivery='" + delivery + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", redeemedAt='" + redeemedAt + '\'' +
                ", canceledAt='" + canceledAt + '\'' +
                '}';
        return sb;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final GiftCard that = (GiftCard) o;

        if (productCode != null ? !productCode.equals(that.productCode) : that.productCode != null) {
            return false;
        }
        if (currency != null ? !currency.equals(that.currency) : that.currency != null) {
            return false;
        }
        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (redemptionCode != null ? !redemptionCode.equals(that.redemptionCode) : that.redemptionCode != null) {
            return false;
        }
        if (gifterAccountCode != null ? !gifterAccountCode.equals(that.gifterAccountCode) : that.gifterAccountCode != null) {
            return false;
        }
        if (recipientAccountCode != null ? !recipientAccountCode.equals(that.recipientAccountCode) : that.recipientAccountCode != null) {
            return false;
        }
        if (unitAmountInCents != null ? !unitAmountInCents.equals(that.unitAmountInCents) : that.unitAmountInCents != null) {
            return false;
        }
        if (balanceInCents != null ? !balanceInCents.equals(that.balanceInCents) : that.balanceInCents != null) {
            return false;
        }
        if (invoiceNumber != null ? !invoiceNumber.equals(that.invoiceNumber) : that.invoiceNumber != null) {
            return false;
        }
        if (gifterAccount != null ? !gifterAccount.equals(that.gifterAccount) : that.gifterAccount != null) {
            return false;
        }
        if (delivery != null ? !delivery.equals(that.delivery) : that.delivery != null) {
            return false;
        }
        if (createdAt != null ? createdAt.compareTo(that.createdAt) != 0 : that.createdAt != null) {
            return false;
        }
        if (updatedAt != null ? updatedAt.compareTo(that.updatedAt) != 0 : that.updatedAt != null) {
            return false;
        }
        if (redeemedAt != null ? redeemedAt.compareTo(that.redeemedAt) != 0 : that.redeemedAt != null) {
            return false;
        }
        if (canceledAt != null ? canceledAt.compareTo(that.canceledAt) != 0 : that.canceledAt != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
            productCode,
            currency,
            id,
            redemptionCode,
            gifterAccountCode,
            recipientAccountCode,
            unitAmountInCents,
            balanceInCents,
            invoiceNumber,
            gifterAccount,
            delivery,
            createdAt,
            updatedAt,
            redeemedAt,
            deliveredAt,
            canceledAt
        );
    }
}
