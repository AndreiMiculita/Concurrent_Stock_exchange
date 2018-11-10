package com.dead10cc;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * The Client is a thread representing an actor on the stock exchange, who can buy and sell shares.
 * The 2 interfaces ensure that whoever accepts their transaction offer can only *give* them money or shares
 */
class Client implements Runnable, Seller, Buyer {
    private static int counter; //counts the number of instances
    private final String id;
    private final HashMap<Share, Integer> shareInventory;
    private int walletBalance;
    private boolean wantsToTrade;

    // References to the shared lists of proposals
    private final ConcurrentTransactionList<Offer> offerList;
    private final ConcurrentTransactionList<Demand> demandList;
    private final ConcurrentTransactionList<CompletedTransaction> transactionHistory;

    Client(ConcurrentTransactionList<Offer> offerList, ConcurrentTransactionList<Demand> demandList, ConcurrentTransactionList<CompletedTransaction> transactionHistory) {
        this.shareInventory = new HashMap<>();
        this.walletBalance = 0;
        this.offerList = offerList;
        this.demandList = demandList;
        this.transactionHistory = transactionHistory;

        // Generate ID here
        this.id = String.valueOf(counter);
        counter++;
    }

    public String getId() {
        return id;
    }

    @Override
    public void run() {
        generateInventory();
        wantsToTrade = true;

        //read offerList and demandList
        List<Offer> unmodifiableOfferList = offerList.getProposalList();
        List<Demand> unmodifiableDemandList = demandList.getProposalList();

        // TODO: this will run until the client runs out of money or decides to quit (by small random chance)
        while (wantsToTrade) {
            //read history
            //parse the unmodifiable lists
            //decide trade

            System.out.println("client " + id + " is alive and wants to trade");
            Random r = new Random();
            if (r.nextInt(5000) == 1) {
                wantsToTrade = false;
            }
        }
        System.out.println("client " + id + "ran out of money or decided to quit, with share inventory" +
                shareInventory + " and wallet balance " + walletBalance);
    }

    /**
     * randomized inventory generation for this client
     */
    private void generateInventory() {
        Random invGenRandom = new Random();
        int iterations = invGenRandom.nextInt(9) + 1;
        for (int i = 0; i < iterations; i++) {
            addSharesToInventory(Share.values()[invGenRandom.nextInt(Share.values().length)], (invGenRandom.nextInt(4) + 1) * 100);
            increaseWalletBalanceBy((invGenRandom.nextInt(4) + 1) * 100);
        }
    }

    /**
     * Increase wallet balance by
     *
     * @param amount positive amount
     */
    @Override
    public void increaseWalletBalanceBy(int amount) {
        if (amount > 0)
            walletBalance += amount;
    }

    /**
     * May empty wallet - then it will stop the client from trading
     * Decrease wallet balance by
     *
     * @param amount positive amount
     */
    private void decreaseWalletBalanceBy(int amount) {
        if (walletBalance - amount >= 0) {
            walletBalance -= amount;
        } else {
            //here the client is out of money
            walletBalance = 0;
            wantsToTrade = false;
        }
    }

    @Override
    public void addSharesToInventory(Share shareType, int amount) {
        shareInventory.put(shareType, shareInventory.getOrDefault(shareType, 0) + amount);
    }

    private void removeSharesFromInventory(Share shareType, int amount) {
        if (shareInventory.getOrDefault(shareType, 0) > amount) {
            shareInventory.put(shareType, shareInventory.getOrDefault(shareType, 0) - amount);
        }
    }

    /**
     * Offer to sell for
     *
     * @param price,     offers of
     * @param shareType, and
     * @param amount     amount
     */
    public void proposeOffer(int price, Share shareType, int amount) {
        Offer o = new Offer(this, price, shareType, amount);
        offerList.add(o);
        removeSharesFromInventory(shareType, amount); //remove the shares, since they are up for sale
    }

    public void proposeDemand(int price, Share shareType, int amount) {
        Demand d = new Demand(this, price, shareType, amount);
        demandList.add(d);
        decreaseWalletBalanceBy(price * amount);
    }

    /**
     * @param o Buy shares from a seller
     */
    public void buy(Offer o) {

        // this loses money and gains shares
        decreaseWalletBalanceBy(o.getPrice() * o.getAmount());
        addSharesToInventory(o.getShareType(), o.getAmount());
        offerList.remove(o);

        // the other party gains money
        o.getSeller().increaseWalletBalanceBy(o.getPrice() * o.getAmount());

        CompletedTransaction t = new CompletedTransaction(this, o.getPrice(), o.getShareType(), o.getAmount(), o.getSeller());
        transactionHistory.add(t);
    }

    /**
     * @param d Sell shares to a buyer
     */
    public void sell(Demand d) {

        // this gains money and loses shares
        increaseWalletBalanceBy(d.getPrice() * d.getAmount());
        removeSharesFromInventory(d.getShareType(), d.getAmount());
        demandList.remove(d);

        // the other party gains shares
        d.getBuyer().addSharesToInventory(d.getShareType(), d.getAmount());

        CompletedTransaction t = new CompletedTransaction(d.getBuyer(), d.getPrice(), d.getShareType(), d.getAmount(), this);
        transactionHistory.add(t);
    }
}
