package com.dead10cc;

class Demand extends Proposal {
    private Buyer buyer;
    public Demand(Buyer buyer, int price, Share shareType, int amount) {
        super(price, shareType, amount);
        this.buyer = buyer;
    }
    public Buyer getBuyer() {
        return buyer;
    }
}
