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

    public WorkerRunnable(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        System.err.println("Starting work that worker does.");
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                out.println("here's your id!");
            }

            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

