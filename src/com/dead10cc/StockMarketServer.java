package com.dead10cc;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class StockMarketServer {

    Monitor monitor;

    ArrayList<Transaction> transactionHistory;

    ArrayList <Client> clientList;

    ConcurrentLinkedQueue<Offer> offerSet;
    ConcurrentLinkedQueue<Demand> demandSet;



    public StockMarketServer() {
        // here we will add the clients
    }

    public void startSimulation() {
        // TODO: run monitor thread, then run client threads and wait for them to finish
    }
}
