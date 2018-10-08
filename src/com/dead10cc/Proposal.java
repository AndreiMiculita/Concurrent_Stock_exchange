package com.dead10cc;

abstract class Proposal {
    private int id;
    private int price;
    private Share shareType;
    private int amount;

    protected Proposal(int price, Share shareType, int amount) {
        this.price = price;
        this.shareType = shareType;
        this.amount = amount;
        // TODO: generate ID
    }
}
