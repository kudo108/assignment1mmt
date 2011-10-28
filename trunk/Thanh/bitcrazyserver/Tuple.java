package bitcrazyserver;

import java.io.*;

/**
 *
 * @author T'PHaM
 */
public class Tuple implements Serializable {
    private static final int MAX_IP_ALLOWED = 32;

    private int      key      =   -1;
    private String[] ipList   = null;
    private long     fileSize =   -1;
    private int      ipCount  =   -1;

    public Tuple(int _key, long _fileSize) {
        key      = _key;
        ipList   = new String[MAX_IP_ALLOWED];
        fileSize = _fileSize;
        ipCount  = 0;
        for (int i = 0; i < MAX_IP_ALLOWED; i++) {
            ipList[i] = "";
        }
    }
    
    public long getFileSize() {
        return fileSize;
    }
    
    public int getID() {
        return key;
    }
    
    public int getIPCount() {
        return ipCount;
    }

    public boolean addIP(String _ip) {
        boolean result = false;
        for (int i = 0; i < ipCount; i++) {
           if (ipList[i].equals(_ip)) {
               result = true;
               break;
           }
        }
        if ((!result) && (ipCount < MAX_IP_ALLOWED)) {
            ipList[ipCount] = _ip;
            ipCount++;
            result = true;
        }
        return result;
    }

    public boolean removeIP(String _ip) {
        boolean result = false;
        for (int i = 0; i < ipCount; i++) {
            if (ipList[i].equals(_ip)) {
                for (int j = i + 1; j < ipCount; j++) {
                    ipList[j - 1] = ipList[j];
                }
                ipList[ipCount] = "";
                ipCount--;
                result = true;
                break;
            }
        }
        return result;
    }

    public void removeAllIP() {
        for (int i = 0; i < ipCount; i++) {
            ipList[i] = "";
        }
        ipCount = 0;
    }

    public String getAnIP() {
        String result = null;
        if (ipCount > 0) {
            result = ipList[0];
            ipList[ipCount] = ipList[0];
            ipCount++;
            removeIP(ipList[0]);
        }
        return result;
    }

}
