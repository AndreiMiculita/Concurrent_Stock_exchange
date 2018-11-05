package com.dead10cc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.CopyOnWriteArrayList;

class StockMarketServer {

    private final ArrayList<Thread> threadList = new ArrayList<>();
    private ArrayList<Transaction> transactionHistory;

    StockMarketServer(int numberOfClients) {
        // here we will add the reporter and the clients
        ArrayList<Client> clientList = new ArrayList<>();
        for (int i = 0; i < numberOfClients; i++) {
            ConcurrentProposalList<Offer> offerList = new ConcurrentProposalList<>(new CopyOnWriteArrayList<>());
            ConcurrentProposalList<Demand> demandList = new ConcurrentProposalList<>(new CopyOnWriteArrayList<>());
            clientList.add(new Client(offerList, demandList));
            System.out.println("client is alive and wants to trade");
        }
        Reporter reporter = new Reporter();
        threadList.add(new Thread(reporter));
        for (Client c : clientList) {
            threadList.add(new Thread(c));
            System.out.println("client is alive and wants to trade");
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
}
