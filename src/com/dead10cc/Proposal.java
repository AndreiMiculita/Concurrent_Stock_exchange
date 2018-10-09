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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Share getShareType() {
        return shareType;
    }

    public void setShareType(Share shareType) {
        this.shareType = shareType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
