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
public class ServerConsole implements ChatIF {
    //Class variables *************************************************

    /**
     * The default port to listen on.
     */
    final public static int DEFAULT_PORT = 5555;

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
     * Anything typed on the server’s console by an end-user of the server will be echoed to
     * the server’s console and to all the clients.
     *
     * @param message
     */
//    @Override
//    public void handleMessageFromServerUI(Object message) {
//        // TODO
//    }

    /**
     * This method handles commands coming from the server UI.
     * Commands start with the '#' symbol.
     *
     * @param message The message from the UI.
     */
    public void handleCommandsFromServer(String message) {
        // TODO
    }

    //Class methods ***************************************************

    /**
     * This method is responsible for the creation of
     * the server instance (there is no UI in this phase).
     *
     * @param args\[0] The port number to listen on.  Defaults to 5555
     *                 if no argument is entered.
     */
    public static void main(String[] args) {
        int port = 0; //Port to listen on

        try {
            port = Integer.parseInt(args[0]); //Get port from command line
        } catch (Throwable t) {
            port = DEFAULT_PORT; //Set port to 5555
        }

        EchoServer sv = new EchoServer(port);

        try {
            sv.listen(); //Start listening for connections
        } catch (Exception ex) {
            System.out.println("ERROR - Could not listen for clients!");
        }
    }
}
//End of ServerConsole class
