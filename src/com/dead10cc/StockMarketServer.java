package com.dead10cc;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

class StockMarketServer {

    private final ArrayList<Thread> threadList = new ArrayList<>();
    private ArrayList<Transaction> transactionHistory;
    private ConcurrentLinkedQueue<Offer> offerSet;
    private ConcurrentLinkedQueue<Demand> demandSet;


    StockMarketServer(int numberOfClients) {
        // here we will add the reporter and the clients
        ArrayList<Client> clientList = new ArrayList<>();
        for (int i = 0; i < numberOfClients; i++) {
            clientList.add(new Client());
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
        // check offerSet and demandSet and if matches are found, make transactions
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
