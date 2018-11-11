package com.dead10cc;

import java.util.*;
import java.util.concurrent.Semaphore;

/**
 * The Client is a thread representing an actor on the stock exchange, who can buy and sell shares.
 * The 2 interfaces ensure that whoever accepts their transaction offer can only *give* them money or shares
 */
class Client implements Runnable, Seller, Buyer {
    private static int counter; //counts the number of instances
    private final String id;

    private Semaphore shareInventorySemaphore = new Semaphore(1, true);
    private final HashMap<Share, Integer> shareInventory;
    private int walletBalance;
    private boolean wantsToTrade;

    //Reference to server
    StockMarketServer server;
    private List<Offer> unmodifiableOfferList;
    private List<Demand> unmodifiableDemandList;

    Client(StockMarketServer server) {
        this.server = server;
        this.shareInventory = new HashMap<>();
        this.walletBalance = 0;

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
        refreshLists();
        Random r = new Random();

        // TODO: this will run until the client runs out of money or decides to quit (by small random chance)
        while (wantsToTrade) {
            //read history
            //parse the unmodifiable lists
            refreshLists();

            if (!unmodifiableOfferList.isEmpty()) {
                Iterator<Offer> offerIterator = unmodifiableOfferList.iterator();
                while (offerIterator.hasNext()) {
                    Offer o = offerIterator.next();
                    if (r.nextInt(100) == 1 && walletBalance > o.getPrice() * o.getAmount() && !o.getSeller().getId().equals(this.getId())) {
                        server.buy(o, this);
                    }
                }
            }

            if (!unmodifiableDemandList.isEmpty()) {
                for (Demand d : unmodifiableDemandList) {
                    if (r.nextInt(100) == 1 && shareInventory.containsKey(d.getShareType()) && shareInventory.get(d.getShareType()) > d.getAmount()) {
                        server.sell(d, this);
                        break;
                    }
                }
            }

            if (r.nextInt(5000) == 1) {
                //get random entry from shareInventory
                List<Share> keyArray = new ArrayList<>(shareInventory.keySet());
                Share randomKey = keyArray.get( r.nextInt(keyArray.size()) );

                //Put all the shares up for sale
                Offer offer = new Offer(this, r.nextInt(3) + 1, randomKey, shareInventory.get(randomKey));
                server.proposeOffer(offer,this);
            }


            //System.out.println("client " + id + " is alive and wants to trade");
            if (r.nextInt(10000) == 1) {
                wantsToTrade = false;
            }
        }
        System.out.println("client " + id + "ran out of money or decided to quit, with share inventory" +
                shareInventory + " and wallet balance " + walletBalance);
    }

    private void refreshLists() {
        //read offerList and demandList
        unmodifiableOfferList = server.getOfferList();
        unmodifiableDemandList = server.getDemandList();
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
    public synchronized void increaseWalletBalanceBy(int amount) {
        if (amount > 0)
            walletBalance += amount;
    }

    /**
     * May empty wallet - then it will stop the client from trading
     * Decrease wallet balance by
     *
     * @param amount positive amount
     */
    synchronized void decreaseWalletBalanceBy(int amount) {
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
        try {
            shareInventorySemaphore.acquire();
            shareInventory.put(shareType, shareInventory.getOrDefault(shareType, 0) + amount);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            shareInventorySemaphore.release();
        }
    }

    void removeSharesFromInventory(Share shareType, int amount) {
        try {
            shareInventorySemaphore.acquire();
            if (shareInventory.getOrDefault(shareType, 0) > amount) {
                shareInventory.put(shareType, shareInventory.getOrDefault(shareType, 0) - amount);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            shareInventorySemaphore.release();
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
        server.proposeOffer(o, this);
    }

    public void proposeDemand(int price, Share shareType, int amount) {
        Demand d = new Demand(this, price, shareType, amount);
        server.proposeDemand(d, this);
    }

    /**
     * @param o Buy shares from a seller
     */
    public void buy(Offer o) {
        server.buy(o, this);
    }

    /**
     * @param d Sell shares to a buyer
     */
    public void sell(Demand d) {
        server.sell(d, this);
    }
}
