package com.dead10cc;

class Demand extends Transaction {

    private final Buyer buyer;
    Demand(Buyer buyer, int price, Share shareType, int amount) {
        super(price, shareType, amount);
        this.buyer = buyer;
    }

    @Override
    public String toString() {
        return "Demand{" +
                "buyer=" + buyer.getId() +
                '}' + super.toString();
    }

    Buyer getBuyer() {
        return buyer;
    }
}
