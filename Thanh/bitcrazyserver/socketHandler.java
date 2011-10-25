package bitcrazyserver;

import java.io.*;
import java.net.*;

/**
 *
 * @author T'PHaM
 */
public class SocketHandler implements Runnable {
    private static final String END_INCOMMAND       = "END";
    private static final String END_OUTCOMMAND      = "END";
    private static final String GETFILE_INCOMMAND   = "GET";
    private static final String NOTFOUND_OUTCOMMAND = "FNF";
    private static final String STARTSEED_INCOMMAND = "SSR";
    private static final String STOPSEED_INCOMMAND  = "SST";
    private static final String SEEDFILE_OUTCOMMAND = "OKL";

    private Socket           theSocket    = null;
    private TupleList        theTupleList = null;
    private DataInputStream  streamIn     = null;
    private DataOutputStream streamOut    = null;
    private Thread           theThread    = null;

    public SocketHandler(Socket _socket, TupleList _tupleList) {
        theSocket    = _socket;
        theTupleList = _tupleList;
    }

    public void start() {
        if (theThread == null) {
            theThread = new Thread(this);
            theThread.start();
        }
    }

    public void stop() {
        if (theThread != null) {
            theThread.stop();
            theThread = null;
        }
    }

    public void run() {
        try {
            open();
            doMain();
            close();
        } catch (IOException ioe) {
            System.out.println("Error SocketHandler!");
            stop();
        }
    }

    private void doMain() throws IOException {
        String receivedCommand;
        try {
            while (true) {
                receivedCommand = readSocket();
                if (receivedCommand.equals(END_INCOMMAND)) {
                    writeSocket(END_OUTCOMMAND);
                    break;
                }
                else if (receivedCommand.substring(0, 3).equals(GETFILE_INCOMMAND)) {
                    int _hash = Integer.parseInt(receivedCommand.substring(receivedCommand.indexOf(' ') + 1));
                    Tuple theTuple = theTupleList.getByHash(_hash);
                    if (theTuple == null) {
                        writeSocket(NOTFOUND_OUTCOMMAND);
                    } else {
                        writeSocket(String.format("%s %d", theTuple.ip, theTuple.fileSize));
                        theTupleList.remove(theTuple);  /*|Round     | */
                        theTupleList.add(theTuple);    /* |Robin     |*/
                    }
                }
                else if (receivedCommand.substring(0, 3).equals(STARTSEED_INCOMMAND)) {
                    int _hash = Integer.parseInt(receivedCommand.substring(receivedCommand.indexOf(' ') + 1, receivedCommand.lastIndexOf(' ')));
                    String _ip = theSocket.getInetAddress().toString();
                    _ip = _ip.substring(_ip.indexOf('/') + 1);
                    long _fileSize = Long.parseLong(receivedCommand.substring(receivedCommand.lastIndexOf(' ') + 1));
                    Tuple theTuple = new Tuple(_hash, _ip, _fileSize);
                    theTupleList.add(theTuple);
                    writeSocket(SEEDFILE_OUTCOMMAND);
                }
                else if (receivedCommand.substring(0, 3).equals(STOPSEED_INCOMMAND)) {
                    int _hash = Integer.parseInt(receivedCommand.substring(receivedCommand.indexOf(' ') + 1));
                    String _ip = theSocket.getInetAddress().toString();
                    _ip = _ip.substring(_ip.indexOf('/') + 1);
                    theTupleList.remove(_hash, _ip);
                    writeSocket(SEEDFILE_OUTCOMMAND);
                }
                else {
                    System.out.println("Unknown command received.");
                }
            }

        } catch (IOException ioe) {
                System.out.println("Error communicating with client: " + ioe.getMessage());
        }
    }

    private void open() throws IOException {
        try {
            streamIn  = new DataInputStream(new
                            BufferedInputStream(theSocket.getInputStream()));
            streamOut = new DataOutputStream(new
                            BufferedOutputStream(theSocket.getOutputStream()));
        } catch (IOException ioe) {
            System.out.println("Error getting in/out stream: " + ioe.getMessage());
        }
    }

    private void close() throws IOException {
        try {
            if (streamIn  != null)  streamIn.close();
            if (streamOut != null) streamOut.close();
            if (theSocket != null) theSocket.close();
            System.out.println("Socket closed.");
        } catch (IOException ioe) {
            System.out.println("Error closing socket: " + ioe.getMessage());
        }
    }

    private void writeSocket(String msg) throws IOException {
        streamOut.writeUTF(msg);
        System.out.println("Sent: " + msg);
        streamOut.flush();
    }

    private String readSocket() throws IOException {
        String receivedCommand = streamIn.readUTF();
        System.out.println("Received: " + receivedCommand);
        return receivedCommand;
    }

}
