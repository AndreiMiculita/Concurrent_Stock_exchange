package com.dead10cc;

import net.jcip.annotations.Immutable;

@Immutable
abstract class Proposal {
    private final Client creator;
    private final int price;
    private final Share shareType;
    private final int amount;

    Proposal(Client creator, int price, Share shareType, int amount) {
        this.creator = creator;
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

    public Client getCreator() {
        return creator;
    }
}
