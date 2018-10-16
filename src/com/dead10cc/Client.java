package com.dead10cc;

import java.util.HashMap;

public class Client implements Runnable{
    private static int counter; //counts the number of instances
    private String id;
    private HashMap<Share, Integer> shareInventory;
    private int wallet;
    private boolean wantsToTrade;


    Client() {
        this.shareInventory = new HashMap<>();
        this.wallet = 0;

        // Generate ID here
        this.id = String.valueOf(counter);
        counter++;
    }

    public String getId() {
        return id;
    }

    @Override
    public void run() {
        wantsToTrade = true;
        // TODO: this will run until the client runs out of money or decides to quit (by small random chance)
        while (wantsToTrade) {
            //read history
            //decide trade
            System.out.println("client " + id + " is alive and wants to trade");
        }
    }

    public void changeWalletBalanceBy(int amount) {
        if (wallet + amount < 0) {
            //here the client is out of money
            wantsToTrade = false;
        } else {
            wallet += amount;
        }
    }

    private void addSharesToInventory(Share shareType, int amount){
        shareInventory.put(shareType, shareInventory.getOrDefault(shareType, 0) + amount);
    }

    private void removeSharesFromInventory(int amount, Share shareType){

    }

    public void buy(int offerPrice, Share shareType, int amount) {

    }

    public void sell(int offerPrice, Share shareType, int amount) {

    }
}
