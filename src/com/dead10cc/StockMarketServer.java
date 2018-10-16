package com.dead10cc;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

class StockMarketServer {

    private Monitor monitor = new Monitor();

    private ArrayList<Transaction> transactionHistory;

    private ArrayList <Thread> threadList = new ArrayList<>();

    private ConcurrentLinkedQueue<Offer> offerSet;
    private ConcurrentLinkedQueue<Demand> demandSet;



    StockMarketServer() {
        // here we will add the clients
        ArrayList<Client> clientList = new ArrayList<>();
        clientList.add(new Client());
        clientList.add(new Client());
        clientList.add(new Client());
        threadList.add(new Thread(monitor));
        for (Client c : clientList){
            threadList.add(new Thread(c));
        }
    }

    void startSimulation() {
        // TODO: run monitor thread, then run client threads
        // check offerSet and demandSet and if matches are found, make transactions
        // wait for them to finish
        for (Thread t: threadList) {
            t.start();
        }
    }
}
