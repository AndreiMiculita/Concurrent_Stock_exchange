package com.dead10cc;

class Demand extends Proposal {

    private final Buyer buyer;
    Demand(Buyer buyer, int price, Share shareType, int amount) {
        super(price, shareType, amount);
        this.buyer = buyer;
    }
    Buyer getBuyer() {
        return buyer;
    }
}
