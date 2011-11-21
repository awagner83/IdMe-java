package idme;

import java.net.ServerSocket;
import java.io.IOException;

/**
 * ID serving service
 *
 * @author Adam Wagner
 */
public class IdServer {

    private final SynchronizedCounter counter;
    private ServerSocket socket = null;

    /**
     * Init a new IdServer and begin listening on new port.
     *
     * @throws IOException (failed socket close)
     */
    public IdServer(int port) throws IOException {
        counter = new SynchronizedCounter(0);
        counter.registerCallback(new CounterSyncCallback("counter.state"));

        try {
            socket = new ServerSocket(port);
            acceptLoop();
        } catch (IOException e) {
            System.err.println("Cannot listen on port 8888");
            System.exit(1);
        } finally {
            socket.close();
        }
    }

    public void acceptLoop() throws IOException {
        System.err.println("Beginning to accept connections.");
        while (true) {
            new Thread(
                    new WorkerRunnable(socket.accept(), counter)
            ).start();
        }
    }

    /**
     * Callback for writing state of counter to disk.
     */
    private class CounterSyncCallback implements Callback {

        private String filename;

        public CounterSyncCallback(String filename) {
            this.filename = filename;
        }

        public void run(int counter, int checkpoint) {
            System.out.println(String.format("Saving to %s", filename));
        }

    }

}

