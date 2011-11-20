package idme;

import java.net.Socket;
import java.io.*;

/**
 * The working thread.
 *
 * @author Adam Wagner
 */
public class WorkerThread {

    Socket socket = null;

	public WorkerThread(Socket clientSocket) {
		System.err.println("Got socket connection.");
        socket = clientSocket;
	}

	public void start() {
		System.err.println("Starting work that worker does.");
	}

}

