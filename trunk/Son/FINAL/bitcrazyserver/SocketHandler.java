package bitcrazyserver;

import java.io.*;
import java.net.*;

/**
 *
 * @author T'PHaM
 */
public class SocketHandler implements Runnable {
    private static final String     HELLO_INCOMMAND = "HEL";
    private static final String       END_INCOMMAND = "END";
    private static final String   GETFILE_INCOMMAND = "GET";
    private static final String    RESEED_INCOMMAND = "SSR";
    private static final String STARTSEED_INCOMMAND = "SSN";
    private static final String  STOPSEED_INCOMMAND = "SST";
    private static final String   STOPALL_INCOMMAND = "SAL";
    private static final String    GETALL_INCOMMAND = "GAL";
    private static final String    HELLO_OUTCOMMAND = "HEL";
    private static final String      END_OUTCOMMAND = "END";
    private static final String NOTFOUND_OUTCOMMAND = "FNF";
    private static final String       ID_OUTCOMMAND = "TID";
    private static final String       OK_OUTCOMMAND = "OKL";
    private static final String     INFO_OUTCOMMAND = "INF";
    private static final String   GETALL_OUTCOMMAND = "HRE";

    private              Socket           theSocket =  null;
    private              TupleList     theTupleList =  null;
    private              DataInputStream   streamIn =  null;
    private              DataOutputStream streamOut =  null;
    private              Thread           theThread =  null;

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
        String receivedCommand = null;
        try {
            while (true) {
                receivedCommand = readSocket();
                String prefix = getStringElem(receivedCommand, 1);
                if (prefix.equals(END_INCOMMAND)) {
                    writeSocket(END_OUTCOMMAND);
                    break;
                } else if (prefix.equals(HELLO_INCOMMAND)) {
                    writeSocket(HELLO_OUTCOMMAND);
                } else if (prefix.equals(GETFILE_INCOMMAND)) {
                    int id = Integer.parseInt(getStringElem(receivedCommand, 2));
                    Tuple theTuple = theTupleList.getByID(id);
                    if (theTuple == null) {
                        writeSocket(NOTFOUND_OUTCOMMAND);
                    } else {
                        writeSocket(String.format("%s %s %d", INFO_OUTCOMMAND, theTuple.getAnIP(), theTuple.getFileSize()));
                    }
                } else if (prefix.equals(STARTSEED_INCOMMAND)) {
                    String ip = getGuestIP();
                    long fileSize = Long.parseLong(getStringElem(receivedCommand, 2));
                    int id = theTupleList.add(ip, fileSize);
                    writeSocket(String.format("%s %d", ID_OUTCOMMAND, id));
                } else if (prefix.equals(RESEED_INCOMMAND)) {
                    String ip = getGuestIP();
                    int id = Integer.parseInt(getStringElem(receivedCommand, 2));
                    theTupleList.add(id, ip);
                    writeSocket(OK_OUTCOMMAND);
                } else if (prefix.equals(STOPSEED_INCOMMAND)) {
                    int id = Integer.parseInt(getStringElem(receivedCommand, 2));
                    String ip = getGuestIP();
                    theTupleList.remove(id, ip);
                    writeSocket(OK_OUTCOMMAND);
                } else if(prefix.equals(STOPALL_INCOMMAND)) {
                    String ip = getGuestIP();
                    theTupleList.remove(ip);
                    writeSocket(OK_OUTCOMMAND);
                } else if(prefix.equals(GETALL_INCOMMAND)) {
                    String returnString = theTupleList.getThisList();
                    writeSocket(GETALL_OUTCOMMAND + " " + returnString);
                } else {
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
        System.out.println(String.format("%s:%d << %s", getGuestIP(), getGuestPort(), msg));
        streamOut.flush();
    }

    private String readSocket() throws IOException {
        String receivedCommand = streamIn.readUTF();
        System.out.println(String.format("%s:%d >> %s", getGuestIP(), getGuestPort(), receivedCommand));
        return receivedCommand;
    }

    private String getStringElem(String _string, int _n) {
        String leftover = _string;
        String result = null;
        for (int i = 0; i < _n; i++) {
            result = leftover;
            if (result.indexOf(' ') >= 0) {
                result = result.substring(0, result.indexOf(' '));
                leftover = leftover.substring(leftover.indexOf(' ') + 1);
            } else {
                leftover = "";
            }
        }
        return result;
    }

    private String getGuestIP() {
        String result = theSocket.getInetAddress().toString();
        result = result.substring(result.indexOf('/') + 1);
        return result;
    }

    private int getGuestPort() {
        return theSocket.getPort();
    }

}
