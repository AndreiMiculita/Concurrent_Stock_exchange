package com.dead10cc;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@ThreadSafe
class ConcurrentProposalList<T extends Proposal> {
    @GuardedBy("this")
    private final CopyOnWriteArrayList<T> myList;
    private final List<T> unmodifiableList;

    ConcurrentProposalList(CopyOnWriteArrayList<T> set) {
        myList = set;
        unmodifiableList = Collections.unmodifiableList(myList);
    }

    synchronized void add(T p) {
        myList.add(p);
    }

    List<T> getProposalList() {
        return unmodifiableList;
    }

    synchronized void remove(Proposal proposal){
        for (T p : myList) {
            if (p.equals(proposal)) myList.remove(p);
        }
    }
}
