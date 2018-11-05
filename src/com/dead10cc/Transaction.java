package com.dead10cc;

import net.jcip.annotations.Immutable;

@Immutable
class Transaction {
    private final Client buyer;
    private final Share shareType;
    private final int price;
    private final int amount;
    private final Client seller;

    public Transaction(Client buyer, Share shareType, int price, int amount, Client seller) {
        this.buyer = buyer;
        this.shareType = shareType;
        this.price = price;
        this.amount = amount;
        this.seller = seller;
    }

    public Client getBuyer() {
        return buyer;
    }

    public Share getShareType() {
        return shareType;
    }

    public int getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    public Client getSeller() {
        return seller;
    }
}
