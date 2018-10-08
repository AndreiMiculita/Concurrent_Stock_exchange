package com.dead10cc;

public class Transaction {
    private String buyer;
    private Share shareType;
    private int price;
    private int amount;
    private String seller;

    public Transaction(String buyer, Share shareType, int price, int amount, String seller) {
        this.buyer = buyer;
        this.shareType = shareType;
        this.price = price;
        this.amount = amount;
        this.seller = seller;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public Share getShareType() {
        return shareType;
    }

    public void setShareType(Share shareType) {
        this.shareType = shareType;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }
}
