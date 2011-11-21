package idme;

/**
 * SynchronizedCounter callback interface
 *
 * @author Adam Wagner
 */
public interface Callback {
    public void run(int count, int nextCheckpoint);
}

