package com.dead10cc;

class Offer extends Transaction {

    private final Seller seller;
    Offer(Seller seller, int price, Share shareType, int amount) {
        super(price, shareType, amount);
        this.seller = seller;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "seller=" + seller.getId() +
                '}' + super.toString();
    }

    Seller getSeller(){
        return seller;
    }
}
