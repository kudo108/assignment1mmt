package test;

import java.io.*;
import java.net.*;

/**
 *
 * @author T'PHaM
 */


/** Usage:
 * new Client(): kết nối tới server tại localhost:33333.
 *
 * new Client(String _serverIP): kết nối tới server tại _serverIP:33333.
 *
 * public Tuple getByHash(int _hash): dùng để lấy địa chỉ ip của người đang giữ
 * file và dung lượng của file đó từ server.
 *
 * public Boolean seedFile(int _hash, long _fileSize): đăng ký seed file
 * với server.
 *
 * public Boolean stopSeed(int _hash): báo cho server việc ngưng seed file.
 *
 * public Boolean finishSocket(): kết thúc kết nối với server.
 */









public class Client {
    private static final int    SERVER_PORT          = 33333;
    private static final String END_OUTCOMMAND       = "END";
    private static final String END_INCOMMAND        = "END";
    private static final String GETFILE_OUTCOMMAND   = "GET";
    private static final String NOTFOUND_INCOMMAND   = "FNF";
    private static final String STARTSEED_OUTCOMMAND = "SSR";
    private static final String STOPSEED_OUTCOMMAND  = "SST";
    private static final String SEEDFILE_INCOMMAND   = "OKL";

    private Socket           theSocket = null;
    private DataInputStream  streamIn  = null;
    private DataOutputStream streamOut = null;

    public Client() {
        Constructor("localhost");
    }

    public Client(String _serverIP) {
        Constructor(_serverIP);
    }

    public Tuple getByHash(int _hash) throws IOException {
        Tuple result = null;
        writeSocket(String.format("%s %d", GETFILE_OUTCOMMAND, _hash));
        String receivedCommand = streamIn.readUTF();
        if ((receivedCommand == null) || (receivedCommand.equals(NOTFOUND_INCOMMAND))) {
            System.out.println(String.format("%d not found.", _hash));
        }
        else {
            String _ip = receivedCommand.substring(0, receivedCommand.indexOf(' ') - 1);
            long _fileSize = Long.parseLong(receivedCommand.substring(receivedCommand.indexOf(' ') + 1));
            result = new Tuple(_hash, _ip, _fileSize);
        }
        return result;
    }

    public Boolean seedFile(int _hash, long _fileSize) throws IOException {
        Boolean result = false;
        writeSocket(String.format("%s %d %d", STARTSEED_OUTCOMMAND, _hash, _fileSize));
        String receivedCommand = streamIn.readUTF();
        if (receivedCommand.equals(SEEDFILE_INCOMMAND)) result = true;
        return result;
    }

    public Boolean stopSeed(int _hash) throws IOException {
        Boolean result = false;
        writeSocket(String.format("%s %d", STOPSEED_OUTCOMMAND, _hash));
        String receivedCommand = streamIn.readUTF();
        if (receivedCommand.equals(SEEDFILE_INCOMMAND)) result = true;
        return result;
    }

    public Boolean finishSocket() throws IOException {
        Boolean result = false;
        writeSocket(END_OUTCOMMAND);
        String receivedCommand = streamIn.readUTF();
        if (receivedCommand.equals(END_INCOMMAND)) result = true;
        close();
        return result;
    }

    private void writeSocket(String msg) throws IOException {
        streamOut.writeUTF(msg);
        streamOut.flush();
    }

    private void close() {
        try {
            if (streamIn  != null)  streamIn.close();
            if (streamOut != null) streamOut.close();
            if (theSocket != null) theSocket.close();
        } catch (IOException ioe) {
            System.out.println("Error closing socket: " + ioe.getMessage());
        }
    }

    private void Constructor(String _serverIP) {
        try {
            theSocket = new Socket(_serverIP, SERVER_PORT);
            streamIn  = new DataInputStream(new
                            BufferedInputStream(theSocket.getInputStream()));
            streamOut = new DataOutputStream(new
                            BufferedOutputStream(theSocket.getOutputStream()));
        } catch (UnknownHostException uhe) {
            System.out.println("Cannot connect to server: " + uhe.getMessage());
        } catch  (IOException ioe) {
            System.out.println("I/O error: " + ioe.getMessage());
        }
    }

}
