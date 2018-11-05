package com.dead10cc;

class Offer extends Proposal {

    private Seller seller;
    public Offer(Seller seller, int price, Share shareType, int amount) {
        super(price, shareType, amount);
        this.seller = seller;
    }
    public Seller getSeller(){
        return seller;
    }
}
