package com.dead10cc;

import net.jcip.annotations.Immutable;

@Immutable
abstract class Proposal {
    private final int price;
    private final Share shareType;
    private final int amount;

    Proposal(int price, Share shareType, int amount) {
        this.price = price;
        this.shareType = shareType;
        this.amount = amount;
    }

    public int getPrice() {
        return price;
    }
    public Share getShareType() {
        return shareType;
    }
    public int getAmount() {
        return amount;
    }
}
