// This file contains material supporting section 3.7 of the textbook:
// "Object-Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package edu.seg2105.client.backend;

import ocsf.client.*;

import java.io.*;

import edu.seg2105.client.common.*;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 */
public class ChatClient extends AbstractClient {
    //Instance variables **********************************************

    /**
     * The interface type variable.  It allows the implementation of
     * the display method in the client.
     */
    ChatIF clientUI;


    //Constructors ****************************************************

    /**
     * Constructs an instance of the chat client.
     *
     * @param host     The server to connect to.
     * @param port     The port number to connect on.
     * @param clientUI The interface type variable.
     */

    public ChatClient(String host, int port, ChatIF clientUI)
            throws IOException {
        super(host, port); //Call the superclass constructor
        this.clientUI = clientUI;
        openConnection();
    }


    //Instance methods ************************************************

    /**
     * This method handles all data that comes in from the server.
     *
     * @param msg The message from the server.
     */
    public void handleMessageFromServer(Object msg) {
        clientUI.display(msg.toString());


    }

    /**
     * This method handles all data coming from the UI
     *
     * @param message The message from the UI.
     */
    public void handleMessageFromClientUI(String message) {
        try {
            if (message.startsWith("#")) {
                handleCommand(message);
            } else {
                sendToServer(message);
            }
        } catch (IOException e) {
            clientUI.display
                    ("Could not send message to server.  Terminating client.");
            quit();
        }
    }

    private void handleCommand(String command) {
        switch (command) {
            // Causes the client to terminate gracefully.
            case "#quit":
                quit();
                break;
            // Causes the client to disconnect from the server, but not quit.
            case "#logoff":
//                try {
//                    if
//                } catch (Exception e) {
//                    throw
//                }
//                closeConnection();
                break;
            // Calls the setHost method in the client.
            // Only allowed if the client is logged off; displays an error message otherwise
            case "sethost":
                break;
            // Calls the setPort method in the client, with the same constraints as #sethost.
            case "setport":
                break;
            // Causes the client to connect to the server. Only allowed if the client is not already
            // connected; displays an error message otherwise.
            case "login":
                break;
            //  Displays the current host name.
            case "gethost":
                System.out.println("Current host is" + this.getHost());
                // Displays the current port number.
            case "getport":
                System.out.println("Current port is" + getPort());
        }
    }

    /**
     * This method terminates the client.
     */
    public void quit() {
        try {
            closeConnection();
        } catch (IOException e) {
            // TODO
        }
        System.exit(0);
    }

    /**
     * Implements the hook method called each time an exception is thrown by the client's
     * thread that is waiting for messages from the server. The method may be
     * overridden by subclasses.
     *
     * @param exception the exception raised.
     */
    @Override
    protected void connectionException(Exception exception) {
        clientUI.display("The server has shut down");
        System.exit(0);
        // quit();
    }

    /**
     * Implemented the hook method called after the connection has been closed. The default
     * implementation does nothing. The method may be overridden by subclasses to
     * perform special processing such as cleaning up and terminating, or
     * attempting to reconnect.
     */
    @Override
    protected void connectionClosed() {
        clientUI.display("Connection closed");
    }
}
//End of ChatClient class
