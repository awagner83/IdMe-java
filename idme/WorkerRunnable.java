package idme;

import java.net.Socket;
import java.io.*;

/**
 * The working thread.
 *
 * @author Adam Wagner
 */
public class WorkerRunnable implements Runnable {

    private Socket socket = null;
    private SynchronizedCounter counter = null;

    public WorkerRunnable(Socket socket, SynchronizedCounter counter) {
        this.socket = socket;
        this.counter = counter;
    }

    public void run() {
        System.err.println("Starting work that worker does.");
        try {
            PrintWriter out = new PrintWriter(
                    socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            String inputLine;
            IdProtocol proto = new IdProtocol(counter);

            while ((inputLine = in.readLine()) != null) {
                out.println(proto.getReply(inputLine));

                if (proto.isQuit(inputLine)) {
                    break;
                }
            }

            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

