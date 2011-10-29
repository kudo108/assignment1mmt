import java.io.*;
import java.net.*;

/**
 *
 * @author CrazyTeam
 */
public class Client {
    private static final int    SERVER_PORT          = 33333;
    private static final String HELLO_OUTCOMMAND     = "HEL";
    private static final String END_OUTCOMMAND       = "END";
    private static final String GETFILE_OUTCOMMAND   = "GET";
    private static final String RESEED_OUTCOMMAND    = "SSR";
    private static final String STARTSEED_OUTCOMMAND = "SSN";
    private static final String STOPSEED_OUTCOMMAND  = "SST";
    private static final String STOPALL_OUTCOMMAND   = "SAL";
    private static final String HELLO_INCOMMAND      = "HEL";
    private static final String END_INCOMMAND        = "END";
    private static final String NOTFOUND_INCOMMAND   = "FNF";
//  private static final String CANTSEED_INCOMMAND   = "NMO";
    private static final String ID_INCOMMAND         = "TID";
    private static final String OK_INCOMMAND         = "OKL";
    private static final String INFO_INCOMMAND       = "INF";

    private Socket           theSocket = null;
    private DataInputStream  streamIn  = null;
    private DataOutputStream streamOut = null;

    public Client(String _serverIP) {
        super();
        try {
            theSocket = new Socket(_serverIP, SERVER_PORT);
            streamIn  = new DataInputStream(new
                            BufferedInputStream(theSocket.getInputStream()));
            streamOut = new DataOutputStream(new
                            BufferedOutputStream(theSocket.getOutputStream()));
            System.out.println("Connect successfully to server: " + theSocket);
        } catch (UnknownHostException uhe) {
            System.out.println("Cannot connect to server: " + uhe.getMessage());
        } catch  (IOException ioe) {
            System.out.println("I/O error: " + ioe.getMessage());
        }
    }

    public boolean testServer() throws IOException {
        boolean result = false;
        writeSocket(HELLO_OUTCOMMAND);
        String receivedCommand = readSocket();
        if (receivedCommand.equals(HELLO_INCOMMAND)) {
            result = true;
            System.out.println("Connection to server successfully.");
        } else {
            System.out.println("Failed connecting to server.");
        }
        return result;
    }

    public Tuple getByID(int _id) throws IOException {
        Tuple result = null;
        writeSocket(String.format("%s %d", GETFILE_OUTCOMMAND, _id));
        String receivedCommand = readSocket();
        String prefix = getStringElem(receivedCommand, 1);
        if ((receivedCommand == null) || (prefix.equals(NOTFOUND_INCOMMAND))) {
            System.out.println(String.format("File id: %d not found on server.", _id));
        } else if (prefix.equals(INFO_INCOMMAND)) {
            String ip     =                getStringElem(receivedCommand, 2) ;
            if (!ip.equals("null")) {
                long fileSize = Long.parseLong(getStringElem(receivedCommand, 3));
                result = new Tuple(_id, ip, fileSize);
                System.out.println(String.format("File id: %d with size of %d located on %s.", _id, fileSize, ip));
            }
            else {
                System.out.println(String.format("No seeder found for file id: %d", _id));
            }
        }
        else {
            System.out.println(String.format("File id: %d. Unknown command received.", _id));
        }
        return result;
    }

    public int startSeed(long _fileSize) throws IOException {
        int result = -1;
        writeSocket(String.format("%s %d", STARTSEED_OUTCOMMAND, _fileSize));
        String receivedCommand = readSocket();
        String prefix = getStringElem(receivedCommand, 1);
        if (prefix.equals(ID_INCOMMAND)) {
            result = Integer.parseInt(getStringElem(receivedCommand, 2));
            System.out.println("Seeding accepted.");
        } else {
            System.out.println("Seeding rejected.");
        }
        return result;
    }

    public boolean reSeed(int _id) throws IOException {
        boolean result = false;
        writeSocket(String.format("%s %d", RESEED_OUTCOMMAND, _id));
        String receivedCommand = readSocket();
        String prefix = getStringElem(receivedCommand, 1);
        if (prefix.equals(OK_INCOMMAND)) {
            result = true;
            System.out.println("Seeding accepted.");
        } else {
            System.out.println("Seeding rejected.");
        }
        return result;
    }

    public boolean stopSeed(int _id) throws IOException {
        boolean result = false;
        writeSocket(String.format("%s %d", STOPSEED_OUTCOMMAND, _id));
        String receivedCommand = readSocket();
        String prefix = getStringElem(receivedCommand, 1);
        if (prefix.equals(OK_INCOMMAND)) {
            result = true;
            System.out.println("Stop seeding accepted.");
        } else {
            System.out.println("Stop seeding rejected.");
        }
        return result;
    }

    public boolean stopSeed() throws IOException {
        boolean result = false;
        writeSocket(STOPALL_OUTCOMMAND);
        String receivedCommand = readSocket();
        if (receivedCommand.equals(OK_INCOMMAND)) {
            result = true;
            System.out.println("Stop all seeding successfully.");
        } else {
            System.out.println("Stop all seeding failed.");
        }
        return result;
    }

    public boolean finish() throws IOException {
        boolean result = false;
        writeSocket(END_OUTCOMMAND);
        String receivedCommand = readSocket();
        if (receivedCommand.equals(END_INCOMMAND)) {
            result = true;
            System.out.println("Connection to server closed.");
        } else {
            System.out.println("Failed closing connection to server.");
        }
        close();
        return result;
    }

    private void writeSocket(String msg) throws IOException {
        streamOut.writeUTF(msg);
        System.out.println("Server << " + msg);
        streamOut.flush();
    }

    private String readSocket() throws IOException {
        String receivedCommand = streamIn.readUTF();
        System.out.println("Server >> " + receivedCommand);
        return receivedCommand;
    }

    private String getStringElem(String _string, int _n) {
        String leftover = _string;
        String result   =    null;
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

    private void close() {
        try {
            if (streamIn  != null)  streamIn.close();
            if (streamOut != null) streamOut.close();
            if (theSocket != null) theSocket.close();
        } catch (IOException ioe) {
            System.out.println("Error closing socket to server: " + ioe.getMessage());
        }
    }

}
