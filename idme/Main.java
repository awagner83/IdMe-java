package idme;

import java.io.IOException;

/**
 * CLI entry-point.
 *
 * @author Adam Wagner
 */
public class Main {

    public static void main(String[] args) {
        try {
            IdServer server = new IdServer(8888, "counter.txt");
        } catch (IOException e) {
            System.err.println(
                    "Uh oh, things may not have shut down properly"
            );
            System.exit(1);
        }
    }

}

