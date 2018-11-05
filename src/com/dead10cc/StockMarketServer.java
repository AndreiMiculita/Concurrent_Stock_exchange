package com.dead10cc;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

class StockMarketServer {

    private final ArrayList<Thread> threadList = new ArrayList<>();

    StockMarketServer(int numberOfClients) {
        ConcurrentTransactionList<Offer> offerList = new ConcurrentTransactionList<>(new CopyOnWriteArrayList<>());
        ConcurrentTransactionList<Demand> demandList = new ConcurrentTransactionList<>(new CopyOnWriteArrayList<>());
        ConcurrentTransactionList<CompletedTransaction> transactionHistory = new ConcurrentTransactionList<>(new CopyOnWriteArrayList<>());
        // here we will add the reporter and the clients
        ArrayList<Client> clientList = new ArrayList<>();
        for (int i = 0; i < numberOfClients; i++) {
            clientList.add(new Client(offerList, demandList, transactionHistory));
            System.out.println("client is alive and wants to trade");
        }
        Reporter reporter = new Reporter(transactionHistory);
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
