package idme;

/**
 * Id Server Protocol
 *
 * @author Adam Wagner
 */
class IdProtocol {

    private SynchronizedCounter counter = null;

    public IdProtocol(SynchronizedCounter counter) {
        this.counter = counter;
    }

    /**
     * Given a request (input string), return the appropriate reply-string.
     */
    public String getReply(String input) {
        String reply = null;

        if (input.equals("GET")) {
            reply = Integer.toString(counter.next());
        } else if (isQuit(input)) {
            reply = "Goodbye!";
        } else {
            reply = "Error: Bad Request!";
        }

        return reply;
    }

    public boolean isQuit(String input) {
        return input.equals("QUIT");
    }

}

