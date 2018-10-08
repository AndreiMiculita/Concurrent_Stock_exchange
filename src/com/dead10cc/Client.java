package com.dead10cc;

import java.util.HashMap;

public class Client implements Runnable{
    private String id;
    private HashMap<Integer, Share> shareInventory;
    private Integer wallet;


    public Client(HashMap<Integer, Share> shareInventory, Integer wallet) {
        this.shareInventory = shareInventory;
        this.wallet = wallet;
        // TODO: generate ID here
    }

    @Override
    public void run() {
        // TODO: this will run until the client runs out of money or decides to quit (by small random chance)
    }

    public void changeWalletBalanceBy(int amount) {
        if (wallet + amount < 0) {
            //TODO: here the client is out of money
        } else {
            wallet += amount;
        }
    }

    private void addSharesToInventory(int amount, Share shareType){

    }

    private void removeSharesFromInventory(int amount, Share shareType){

    }

    public void buy(int offerPrice, Share shareType, int amount) {

    }

    public void sell(int offerPrice, Share shareType, int amount) {

    }
}
