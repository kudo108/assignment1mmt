package bitcrazyserver;

import java.io.*;
import java.net.*;

/**
 *
 * @author T'PHaM
 */
public class Main {
    private static final int    WELCOME_PORT = 33333;

    private static ServerSocket theServer    =  null;
    private static TupleList    theTupleList =  null;

    public static void main(String[] args) {
        try {
            theServer = new ServerSocket(WELCOME_PORT);
            System.out.println("Server socket created successful: " + theServer);
        } catch (IOException ioe) {
            System.out.println(String.format("Error creating server socket at port %1$d: %2$s", WELCOME_PORT, ioe.getMessage()));
        }

        while (true) {
            try {
                Socket theSocket = theServer.accept();
                socketHandler theSocketHandler = new socketHandler(theSocket, theTupleList);
                theSocketHandler.start();
            } catch (IOException ioe) {
                System.out.println("Error getting incoming connection: " + ioe.getMessage());
            }
        }
        
    }

}
