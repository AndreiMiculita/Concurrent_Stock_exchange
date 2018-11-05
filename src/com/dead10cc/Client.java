package com.dead10cc;

import java.util.HashMap;
import java.util.List;

/**
 * The Client is a thread representing an actor on the stock exchange, who can buy and sell shares.
 */
class Client implements Runnable {
    private static int counter; //counts the number of instances
    private final String id;
    private final HashMap<Share, Integer> shareInventory;
    private int wallet;
    private boolean wantsToTrade;
    private final ConcurrentProposalList<Offer> offerList;
    private final ConcurrentProposalList<Demand> demandList;


    Client(ConcurrentProposalList<Offer> offerList, ConcurrentProposalList<Demand> demandList) {
        this.shareInventory = new HashMap<>();
        this.wallet = 0;
        this.offerList = offerList;
        this.demandList = demandList;

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

        //read offerList and demandList
        List<Offer> unmodifiableOfferList = offerList.getProposalList();
        List<Demand> unmodifiableDemandList = demandList.getProposalList();

        // TODO: this will run until the client runs out of money or decides to quit (by small random chance)
        while (wantsToTrade) {
            //read history
            //
            //decide trade
            System.out.println("client " + id + " is alive and wants to trade");
        }
    }

    private void changeWalletBalanceBy(int amount) {
        if (wallet + amount < 0) {
            //here the client is out of money
            wantsToTrade = false;
        } else {
            wallet += amount;
        }
    }

    private void addSharesToInventory(Share shareType, int amount) {
        shareInventory.put(shareType, shareInventory.getOrDefault(shareType, 0) + amount);
    }

    private void removeSharesFromInventory(Share shareType, int amount) {
        if (shareInventory.getOrDefault(shareType, 0) > amount) {
            shareInventory.put(shareType, shareInventory.getOrDefault(shareType, 0) - amount);
        }
    }

    /**
     *
     * @param o Given an offer o from
     * @return buy the shares they offer
     */
    public Transaction buy(Offer o) {
        changeWalletBalanceBy(- o.getPrice() * o.getAmount());
        addSharesToInventory(o.getShareType(), o.getAmount());
        offerList.remove(o);

        //TODO: what should the other party do now so they gain money and lose shares? can't call otherParty.sell()

        return new Transaction(this, o.getShareType(), o.getPrice(), o.getAmount(), o.getCreator());
    }

    /**
     *
     * @param d Given a demand d from
     * @return sell the shares they demand
     */
    public Transaction sell(Demand d) {
        changeWalletBalanceBy(d.getPrice() * d.getAmount());
        removeSharesFromInventory(d.getShareType(), d.getAmount());
        demandList.remove(d);

        return new Transaction(d.getCreator(), d.getShareType(), d.getPrice(), d.getAmount(), this);
    }
}
