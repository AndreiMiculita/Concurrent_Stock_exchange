package com.dead10cc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

class StockMarketServer {

    private final ArrayList<Thread> threadList = new ArrayList<>();

    private final Semaphore offerSemaphore = new Semaphore(1, true);
    private final ArrayList<Offer> offerList = new ArrayList<>();
    private final Semaphore demandSemaphore = new Semaphore(1, true);
    private final ArrayList<Demand> demandList = new ArrayList<>();

    private final Semaphore historySemaphore = new Semaphore(1, true);
    private final ArrayList<CompletedTransaction> transactionHistory = new ArrayList<>();

    StockMarketServer(int numberOfClients) {

        // here we will add the reporter and the clients
        ArrayList<Client> clientList = new ArrayList<>();
        for (int i = 0; i < numberOfClients; i++) {
            clientList.add(new Client(this));
        }
        Reporter reporter = new Reporter(transactionHistory);
        threadList.add(new Thread(reporter));
        for (Client c : clientList) {
            threadList.add(new Thread(c));
            System.out.println("client " + c.getId() + " is alive and wants to trade");
        }
    }

    void startSimulation() {
        // run all threads
        // wait for them to finish
        for (Thread t : threadList) {
            t.start();
        }
        for (Thread t : threadList) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    void proposeOffer(Offer offer, Client client) {

        try {
            offerSemaphore.acquire();
            offerList.add(offer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            offerSemaphore.release();
        }
        System.out.println(offer);
        client.removeSharesFromInventory(offer.getShareType(), offer.getAmount()); //remove the shares, since they are up for sale
    }

    void proposeDemand(Demand demand, Client client) {

        try {
            demandSemaphore.acquire();
            demandList.add(demand);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            demandSemaphore.release();
        }
        System.out.println(demand);
        client.decreaseWalletBalanceBy(demand.getPrice() * demand.getAmount());
    }

    void buy(Offer offer, Client client) {

        // client loses money and gains shares
        client.decreaseWalletBalanceBy(offer.getPrice() * offer.getAmount());
        client.addSharesToInventory(offer.getShareType(), offer.getAmount());

        try {
            offerSemaphore.acquire();
            offerList.remove(offer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            offerSemaphore.release();
        }

        // the other party gains money
        offer.getSeller().increaseWalletBalanceBy(offer.getPrice() * offer.getAmount());

        CompletedTransaction t = new CompletedTransaction(client, offer.getPrice(), offer.getShareType(), offer.getAmount(), offer.getSeller());
        System.out.println(t);
        this.addToHistory(t);
    }

    void sell(Demand demand, Client client){

        // client gains money and loses shares
        client.increaseWalletBalanceBy(demand.getPrice() * demand.getAmount());
        client.removeSharesFromInventory(demand.getShareType(), demand.getAmount());

        try {
            demandSemaphore.acquire();
            demandList.remove(demand);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            demandSemaphore.release();
        }

        // the other party gains shares
        demand.getBuyer().addSharesToInventory(demand.getShareType(), demand.getAmount());

        CompletedTransaction t = new CompletedTransaction(demand.getBuyer(), demand.getPrice(), demand.getShareType(), demand.getAmount(), client);
        System.out.println(t);
        this.addToHistory(t);
    }

    private void addToHistory(CompletedTransaction t){
        try {
            historySemaphore.acquire();
            transactionHistory.add(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            historySemaphore.release();
        }
    }

    List<Offer> getOfferList(){
        return Collections.unmodifiableList(offerList);
    }

    List<Demand> getDemandList() {
        return Collections.unmodifiableList(demandList);
    }
}
