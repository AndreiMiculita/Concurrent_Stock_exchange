package com.dead10cc;

import net.jcip.annotations.Immutable;

@Immutable
class Transaction {
    private final String buyerId;
    private final Share shareType;
    private final int price;
    private final int amount;
    private final String sellerId;

    public Transaction(Buyer buyer, Share shareType, int price, int amount, Seller seller) {
        this.buyerId = buyer.getId();
        this.shareType = shareType;
        this.price = price;
        this.amount = amount;
        this.sellerId = seller.getId();
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


    public String getSellerId() {
        return sellerId;
    }

    public String getBuyerId() {
        return buyerId;
    }
}
