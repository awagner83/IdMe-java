package idme;

public class SynchronizedCounter {

    private int count = 0;

    public SynchronizedCounter(int startAt) {
        count = startAt;
    }

    public synchronized int next() {
        return ++count;
    }

    public synchronized int current() {
        return count;
    }

}

