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

    public ConcurrentProposalList(CopyOnWriteArrayList<T> set) {
        myList = set;
        unmodifiableList = Collections.unmodifiableList(myList);
    }

    public synchronized void add(T p) {
        myList.add(p);
    }

    public List<T> getProposalList() {
        return unmodifiableList;
    }

    public synchronized T remove(Proposal proposal){
        for (T p : myList) {
            if (p.equals(proposal)) myList.remove(p);
        }
        return null;
    }
}
