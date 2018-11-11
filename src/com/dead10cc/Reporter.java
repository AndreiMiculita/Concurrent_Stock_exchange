package com.dead10cc;

import java.util.ArrayList;

/**
 * The Reporter is one thread on the server that is used to monitor the transaction history, write to files and generate graphs
 */
class Reporter implements Runnable {

    private boolean reporterActive;
    private final ArrayList<CompletedTransaction> transactionHistory;

    Reporter(ArrayList<CompletedTransaction> transactionHistory) {
        this.transactionHistory = transactionHistory;
    }

    private void writeToFile() {
        //todo: implement this
    }

    private void generateGraph() {
        //todo: implement this also
    }

    @Override
    public void run() {
        //TODO: this will check the market history every n seconds and write it to a file and generate graphs
        while (reporterActive) {
            writeToFile();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
