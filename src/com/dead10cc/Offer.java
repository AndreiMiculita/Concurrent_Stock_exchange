package com.dead10cc;

class Offer extends Transaction {

    private final Seller seller;
    Offer(Seller seller, int price, Share shareType, int amount) {
        super(price, shareType, amount);
        this.seller = seller;
    }
    Seller getSeller(){
        return seller;
    }
}
