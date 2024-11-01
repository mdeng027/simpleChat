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
     * This method handles all data coming from the client UI
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
                    ("Could not send message to server. Terminating client.");
            quit();
        }
    }

    /**
     * This method handles commands coming from the UI.
     * Commands start with the '#' symbol.
     *
     * @param message The message from the UI.
     */
    private void handleCommand(String message) {
        String[] args = message.split(" ");
        String command = args[0];
        switch (command) {
            // i) Causes the client to terminate gracefully.
            case "#quit":
                System.out.println("Quitting...");
                quit();
                break;

            // ii) Causes the client to disconnect from the server, but not quit.
            case "#logoff":
                try {
                    if (this.isConnected()) {
                        this.closeConnection();
                    } else {
                        System.out.println("ERROR - The client is already disconnected.");
                    }
                } catch (IOException e) {
                    System.out.println("ERROR - Could not close connection.");
                }
                break;

            // (iii) Calls the setHost method in the client.
            // Only allowed if the client is logged off; displays an error message otherwise
            case "#sethost":
                if (this.isConnected()) {
                    System.out.println("ERROR - Client is already connected.");
                } else {
                    super.setHost(args[1]);
                    System.out.println("Host is set to " + (args[1]));
                }
                break;

            // (iv) Calls the setPort method in the client, with the same constraints as #sethost.
            case "#setport":
                if (this.isConnected()) {
                    System.out.println("ERROR - Client is still connected.");
                } else {
                    super.setPort(Integer.parseInt(args[1]));
                    System.out.println("Port is set to " + (args[1]));
                }
                break;

            // (v) Causes the client to connect to the server. Only allowed if the client is not already
            // connected; displays an error message otherwise.
            case "#login":
                try {
                    if (!(this.isConnected())) {
                        System.out.println("Connection opened");
                        this.openConnection();
                    } else {
                        System.out.println("ERROR - The client is already connected.");
                    }
                } catch (IOException e) {
                    System.out.println("ERROR - Could not open connection.");
                }
                break;

            //  (vi) Displays the current host name.
            case "#gethost":
                System.out.println("Current host is " + this.getHost());
                break;

            // (vii) Displays the current port number.
            case "#getport":
                System.out.println("Current port is " + this.getPort());
                break;

            default:
                System.out.println("ERROR - Unknown command:" + command);
                break;
        }
    }

    /**
     * This method terminates the client.
     */
    public void quit() {
        try {
            closeConnection();
        } catch (IOException e) {
            System.out.println("ERROR - Could not close connection.");
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
        System.out.println("The server has shut down");
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
        System.out.println("Connection closed");
    }
}
//End of ChatClient class
