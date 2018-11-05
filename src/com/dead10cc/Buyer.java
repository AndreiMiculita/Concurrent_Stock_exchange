package com.dead10cc;

public interface Buyer {
    void addSharesToInventory(Share shareType, int amount);
    String getId();
}
