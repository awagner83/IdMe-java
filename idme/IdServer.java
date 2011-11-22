package idme;

import java.net.ServerSocket;
import java.io.*;

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
    public IdServer(int port, String filename) throws IOException {
        CounterDiskSync sync = new CounterDiskSync(filename);
        counter = new SynchronizedCounter(0);
        counter.registerCallback(sync);

        try {
            socket = new ServerSocket(port);
            acceptLoop();

            // After accept-loop ends, save actual id-value to state-file
            sync.save(counter.current());
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
            // TODO: test for something we can change (for proper shutdown)
            new Thread(
                    new WorkerRunnable(socket.accept(), counter)
            ).start();
        }
    }

    /**
     * Callback for writing state of counter to disk.
     */
    private class CounterDiskSync implements Callback {

        private String filename;

        public CounterDiskSync(String filename) {
            this.filename = filename;
        }

        /**
         * Save checkpoint value (higher than actual), when run as callback.
         */
        public void run(int counter, int checkpoint) {
            save(checkpoint);
        }

        public void save(int value) {
            PrintWriter writer = null;

            try {
                writer = new PrintWriter(new FileWriter(filename));
                writer.print(value);
            } catch (IOException e) {
                System.err.println("Cannot save to counter-state file!");
                System.exit(1);
            } finally {
                if (writer != null) {
                    writer.flush();
                    writer.close();
                }
            }
        }

    }

}

