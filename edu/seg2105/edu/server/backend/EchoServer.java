package edu.seg2105.edu.server.backend;
// This file contains material supporting section 3.7 of the textbook:
// "Object-Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import edu.seg2105.client.backend.ChatClient;
import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 */
public class EchoServer extends AbstractServer {
    //Class variables *************************************************

    /**
     * The default port to listen on.
     */
    final public static int DEFAULT_PORT = 5555;

    /**
     * The login key of the client.
     */
    final private String loginKey = "loginID";

    ChatClient client;

    //Constructors ****************************************************

    /**
     * Constructs an instance of the echo server.
     *
     * @param port The port number to connect on.
     */
    public EchoServer(int port) {
        super(port);
        try {
            listen(); // Start listening for connections
        } catch (Exception e) {
            System.out.println("ERROR - Could not listen for clients!");
        }
    }

    //Instance methods ************************************************

    /**
     * This method handles any messages received from the client.
     *
     * @param msg    The message received from the client.
     * @param client The connection from which the message originated.
     */
    public void handleMessageFromClient(Object msg, ConnectionToClient client) {
        System.out.println("Message received: " + msg + " from " + client);
        String msgStr = (String) msg;
        if (msgStr.startsWith("#login")) {
            String loginID = msgStr.substring(7).trim(); // "#login " is 7 characters
            client.setInfo("loginID", loginID);
            System.out.println("#login " + loginID);
        } else {
            this.sendToAllClients(msg);
        }
    }

    /**
     * This method handles all data coming from the Server UI
     *
     * @param message The message from the UI.
     */
    @Override
    public void handleMessageFromServer(String message) {
        System.out.println("SERVER MSG > " + message);
        this.sendToAllClients(message);
    }

    /**
     * This method overrides the one in the superclass.  Called
     * when the server starts listening for connections.
     */
    protected void serverStarted() {
        System.out.println("Server listening for connections on port " + getPort());
    }

    /**
     * This method overrides the one in the superclass.  Called
     * when the server stops listening for connections.
     */
    protected void serverStopped() {
        System.out.println("Server has stopped listening for connections.");
    }

    /**
     * Implemented the hook method called each time a new client connection is
     * accepted. The default implementation does nothing.
     *
     * @param client the connection connected to the client.
     */
    @Override
    synchronized protected void clientConnected(ConnectionToClient client) {
        System.out.println("A client has connected.");
    }

    /**
     * Implemented the hook method called each time a client disconnects.
     * The default implementation does nothing. The method
     * may be overridden by subclasses but should remain synchronized.
     *
     * @param client the connection with the client.
     */
    @Override
    synchronized protected void clientDisconnected(ConnectionToClient client) {
        System.out.println("A client has disconnected.");
    }

    /**
     * Implemented the hook method called each time an exception is thrown in a
     * ConnectionToClient thread.
     * The method may be overridden by subclasses but should remain synchronized.
     *
     * @param client    the client that raised the exception.
     * @param exception the exception thrown.
     */
    synchronized protected void clientException(
            ConnectionToClient client, Throwable exception) {
        System.out.println("A client has exception: " + exception);
    }

    /**
     * This method handles commands coming from the UI.
     * Commands start with the '#' symbol.
     *
     * @param message The message from the UI.
     */
    private void handleMessageFromServerConsole(String message) {
        if (message.startsWith("#")) {
            String[] args = message.split(" ");
            String command = args[0];

            switch (command) {
                // i) Causes the server to terminate gracefully.
                case "#quit":
                    try {
                        this.close();
                    } catch (Exception e) {
                        System.exit(1);
                    }
                    break;

                // ii)  Causes the server to stop listening for new clients.
                case "#stop":
                    this.stopListening();
                    break;

                // (iii)  Causes the server not only to stop listening for new clients,
                // but also to disconnect all existing clients.
                case "#close":
                    try {
                        this.close();
                    } catch (Exception e) {
                        System.out.println("ERROR - Could not close connection.");
                    }
                    break;

                // (iv) Calls the setPort method in the server.
                // Only allowed if the server is closed.
                case "#setport":
                    if (!this.isListening() && this.getNumberOfClients() < 1) {
                        super.setPort(Integer.parseInt(args[1]));
                        System.out.println("Port is set to " + (args[1]));
                    } else {
                        System.out.println("ERROR - Server is still connected.");
                    }
                    break;

                // (v) Causes the server to start listening for new clients.
                // Only valid if the server is stopped.
                case "#start":
                    if (!this.isListening()) {
                        try {
                            this.listen();
                        } catch (Exception e) {
                            System.out.println("ERROR - Could not start listening for clients.");
                        }
                    } else {
                        System.out.println("ERROR - Already listening for clients.");
                    }
                    break;

                // (vi) Displays the current port number.
                case "#getport":
                    System.out.println("Current port is " + this.getPort());
                    break;

                default:
                    System.out.println("ERROR - Unknown command:" + command);
                    break;
            }
        } else {
            this.sendToAllClients(message);
        }
    }

    //Class methods ***************************************************
}
//End of EchoServer class
