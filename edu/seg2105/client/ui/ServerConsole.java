package edu.seg2105.client.ui;

import java.io.*;
import java.util.Scanner;
import java.io.InputStreamReader;

import ocsf.server.*;
import edu.seg2105.client.common.ChatIF;
import edu.seg2105.edu.server.backend.EchoServer;

/**
 * This class constructs the UI for the server.  It implements the
 * chat interface in order to activate the display() method.
 * Warning: Some of the code here is cloned in ClientConsole
 *
 * @author Miranda Deng
 */
public class ServerConsole extends AbstractServer implements ChatIF {
    //Class variables *************************************************

    /**
     * The default port to listen on.
     */
    final public static int DEFAULT_PORT = 5555;

    //Constructors ****************************************************

    /**
     * Constructs an instance of the echo server.
     *
     * @param port The port number to connect on.
     */
    public ServerConsole(int port) {
        super(port);
    }


    //Instance methods ************************************************

    /**
     * This method overrides the method in the ChatIF interface.
     * It displays a message onto the screen.
     *
     * @param message The string to be displayed.
     */
    public void display(String message) {
        System.out.println("SERVER MSG > " + message);
    }

    /**
     * This method handles any messages received from the client.
     *
     * @param msg    The message received from the client.
     * @param client The connection from which the message originated.
     */
    public void handleMessageFromServer
    (Object msg, ConnectionToClient client) {
        System.out.println("Message received: " + msg + " from " + client);
        this.sendToAllClients(msg);
    }

    /**
     * This method handles any messages received from the client.
     *
     * @param msg    The message received from the client.
     * @param client The connection from which the message originated.
     */
    public void handleMessageFromClient
    (Object msg, ConnectionToClient client) {
        System.out.println("Message received: " + msg + " from " + client);
        this.sendToAllClients(msg);
    }

    /**
     * This method handles commands coming from the UI.
     * Commands start with the '#' symbol.
     *
     * @param message The message from the UI.
     */
    private void handleServerCommand(String message) {
        String[] args = message.split(" ");
        String command = args[0];
        switch (command) {
            // i) Causes the server to terminate gracefully.
            case "#quit":
                // TODO
                break;

            // ii)  Causes the server to stop listening for new clients.
            case "#stop":
                stopListening();
                break;

            // (iii)  Causes the server not only to stop listening for new clients,
            // but also to disconnect all existing clients.
            case "#close":
                // TODO
                break;

            // (iv) Calls the setPort method in the server.
            // Only allowed if the server is closed.
            case "#setport":
                // TODO
                break;

            // (v) Causes the server to start listening for new clients.
            // Only valid if the server is stopped.
            case "#start":
                // TODO
                break;

            // (vi) Displays the current port number.
            case "#getport":
                System.out.println("Current port is " + this.getPort());
                break;

            default:
                System.out.println("ERROR - Unknown command:" + command);
                break;
        }
    }

    //Class methods ***************************************************

    /**
     * This method is responsible for the creation of
     * the server instance (there is no UI in this phase).
     *
     * @param args\[0] The port number to listen on.  Defaults to 5555
     *                 if no argument is entered.
     */
//    public static void main(String[] args) {
//        int port = 0; //Port to listen on
//
//        try {
//            port = Integer.parseInt(args[0]); //Get port from command line
//        } catch (Throwable t) {
//            port = DEFAULT_PORT; //Set port to 5555
//        }
//
//        EchoServer sv = new EchoServer(port);
//
//        try {
//            sv.listen(); //Start listening for connections
//        } catch (Exception ex) {
//            System.out.println("ERROR - Could not listen for clients!");
//        }
//    }
}
//End of ServerConsole class
