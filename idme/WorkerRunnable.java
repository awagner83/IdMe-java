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
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            String inputLine;
            IdProtocol proto = new IdProtocol(counter);

            while ((inputLine = in.readLine()) != null) {
                out.println(proto.getReply(inputLine));

                if (proto.isQuit(inputLine)) {
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) out.close();

            // I'm not sure all of these try/except blocks are
            // are necessary, however, keeping the closes in the
            // 'try' seems like I'd get myself into trouble if an
            // except *is* raised... things won't get close then.
            try {
                if (in != null) in.close();
            } catch (IOException e) {
                // I don't know what I should do.. log an error?
            }

            try {
                if (socket != null) socket.close();
            } catch (IOException e) {
                // Ditto
            }
        }
    }

}

