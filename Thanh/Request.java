/** this file belongs to bitcrazy project
 * 2011-10-11
 * @author T'PHaM
 * Request.class
 */

//TODO: update int[] when finish/failed download
//consider removing getStatus method & all its relate const

import java.net.*;
import  java.io.*;

public class Request implements Runnable {
    private static final int        SEEDER_PORT =           33333;
    private static final String REQUEST_COMMAND = "GET %1$s %2$d";
    private static final String   START_COMMAND =           "STR";
    private static final String NOT_FOUND_REPLY =           "NFD";
    private static final String        OK_REPLY =           "OKL";
    public  static final int        BUFFER_SIZE =            1024;
    public  static final int  PROCESSING_STATUS =              -1;
    public  static final int   SUCCESSED_STATUS =               0;
    public  static final int      FAILED_STATUS =               1;

    private Socket           theSocket =              null;
    private Thread           theThread =              null;
    private DataInputStream   streamIn =              null;
    private DataOutputStream streamOut =              null;
    private int             intPieceNo =                -1;
    private String             strHash =                "";
    private int                 status = PROCESSING_STATUS;

    public Request(String _strDesIP, String _strHash, int _intPieceNo) {
        try {
            theSocket  = new Socket(_strDesIP, SEEDER_PORT);
            //System.out.println("Connected: " + theSocket);
            strHash    =    _strHash;
            intPieceNo = _intPieceNo;
        } catch(UnknownHostException uhe) {
            System.out.println("Host unknown: " + uhe.getMessage());
        } catch(IOException ioe) {
            System.out.println("Unexpected exception: " + ioe.getMessage());
        }
    }

    public void start() {
        if (theThread == null) {
            theThread = new Thread(this);
            theThread.start();
        }
    }

    public int getStatus() {
        return status;
    }

    /**NOTE**
     * This method is not necessary :P
     */
    public void stop() {
        if (theThread != null) {
            theThread.stop();
            theThread = null;
        }
    }

    /**WARNING**
     *Should not call this method directly.
     *Use start() instead.
     */
    public void run() {
        open();
        doMain();
        close();
    }

//**Private Methods*************************************************************

    private void doMain() {
        String strReply = "";
        int    dataRead = -1;
        try {
            streamOut.writeUTF(
                    String.format(REQUEST_COMMAND, strHash, intPieceNo));
            streamOut.flush();
            strReply = streamIn.readUTF();
            if (strReply.equals(OK_REPLY)) {
                streamOut.writeUTF(START_COMMAND);
                streamOut.flush();
                byte[] dataBuffer = new byte[BUFFER_SIZE];
                dataRead = getData(dataBuffer, BUFFER_SIZE);
                while (dataRead > 0) {
                    //TODO: call fileWriter to write block to file
                    dataRead = getData(dataBuffer, BUFFER_SIZE);
                }
                status = SUCCESSED_STATUS;
            }
            else if (strReply.equals(NOT_FOUND_REPLY)) {
                status = FAILED_STATUS;
                System.out.println("Block does not exists on partner side.");
            }
        } catch (IOException ioe) {
            System.out.println("Error interacting with partner: " + ioe.getMessage());
        }
    }

    private int getData(byte[] _dataBuffer, int _intMaxLength) {
        int intReadAmount = -1;
        try {
            intReadAmount = streamIn.read(_dataBuffer, 0, _intMaxLength);
        } catch (IOException ioe) {
            System.out.println("Error reading data: " + ioe.getMessage());
        }
        return intReadAmount;
    }

    private void open() {
        try {
             streamIn = new DataInputStream(new
                            BufferedInputStream(theSocket.getInputStream()));
            streamOut = new DataOutputStream(new
                            BufferedOutputStream(theSocket.getOutputStream()));
        } catch(IOException ioe) {
            System.out.println("Error getting socket streams: " + ioe.getMessage());
        }
    }

    private void close() {
        try {
            if ( streamIn != null)  streamIn.close();
            if (streamOut != null) streamOut.close();
            if (theSocket != null) theSocket.close();
        } catch (IOException ioe) {
            System.out.println("Error closing socket streams: " + ioe.getMessage());
        }
    }

}
