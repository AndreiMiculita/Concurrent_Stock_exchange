package com.dead10cc;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@ThreadSafe
class ConcurrentTransactionList<T extends Transaction> {
    @GuardedBy("this")
    private final CopyOnWriteArrayList<T> myList;
    private final List<T> unmodifiableList;

    ConcurrentTransactionList(CopyOnWriteArrayList<T> set) {
        myList = set;
        unmodifiableList = Collections.unmodifiableList(myList);
    }

    synchronized void add(T p) {
        myList.add(p);
    }

    List<T> getProposalList() {
        return unmodifiableList;
    }

    synchronized void remove(Transaction transaction){
        for (T p : myList) {
            if (p.equals(transaction)) myList.remove(p);
        }
    }
}
