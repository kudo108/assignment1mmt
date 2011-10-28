package bitcrazyserver;

import java.io.*;
import java.net.*;

/**
 *
 * @author T'PHaM
 */
public class Main {
    private static final String SAVE         = "lazycat";
    private static final int    WELCOME_PORT =         33333;

    private static ServerSocket theServer    =          null;
    private static TupleList    theTupleList =          null;

    public static void main(String[] args) {
        try {
            FileInputStream file = new FileInputStream(SAVE);
            ObjectInputStream input = new ObjectInputStream(file);
            theTupleList = (TupleList) input.readObject();
            theTupleList.removeAll();
            input.close();
            file.close();
            System.out.println("Save loaded successfully.");
        } catch (IOException ioe) {
            theTupleList = new TupleList();
            System.out.println("WELCOME!");
        } catch (ClassNotFoundException cnfe) {
            theTupleList = new TupleList();
            System.out.println("Loading failed: " + cnfe.getMessage());
        }
        
        try {
            theServer = new ServerSocket(WELCOME_PORT);
            System.out.println("Server socket created successfully: " + theServer);
        } catch (IOException ioe) {
            System.out.println(String.format("Error creating server socket at port %d: %s", WELCOME_PORT, ioe.getMessage()));
        }

        try {
            while (true) {
                Socket theSocket = theServer.accept();
                System.out.println("Accepted new incoming socket: " + theSocket);
                SocketHandler theSocketHandler = new SocketHandler(theSocket, theTupleList);
                theSocketHandler.start();
                FileSaver theFileSaver = new FileSaver(SAVE, theTupleList);
                theFileSaver.start();
            }
        } catch (IOException ioe) {
                System.out.println("Error getting incoming connection: " + ioe.getMessage());
        }
    }

}
