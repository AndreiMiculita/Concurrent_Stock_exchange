package com.dead10cc;

import net.jcip.annotations.Immutable;

@Immutable
abstract class Transaction {
    private final int price;
    private final Share shareType;
    private final int amount;

    @Override
    public String toString() {
        return "Transaction{" +
                "price=" + price +
                ", shareType=" + shareType +
                ", amount=" + amount +
                '}';
    }

    Transaction(int price, Share shareType, int amount) {
        this.price = price;
        this.shareType = shareType;
        this.amount = amount;
    }

    int getPrice() {
        return price;
    }
    Share getShareType() {
        return shareType;
    }
    int getAmount() {
        return amount;
    }
}
