package com.dead10cc;

public class Monitor implements Runnable {

    private void writeToFile() {

    }

    private void generateGraph() {

    }
    @Override
    public void run() {
        //TODO: this will check the market history every n seconds and write it to a file and generate graphs
        while (true) {
            writeToFile();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
