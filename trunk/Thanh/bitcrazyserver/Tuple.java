package bitcrazyserver;

import java.io.*;
import java.util.ArrayList;

/**
 *
 * @author T'PHaM
 */
public class Tuple implements Serializable {
    private int            key =   -1;
    private ArrayList   ipList = null;
    private long      fileSize =   -1;

    public Tuple(int _key, long _fileSize) {
        key      = _key;
        ipList   = new ArrayList(0);
        fileSize = _fileSize;
    }
    
    public long getFileSize() {
        return fileSize;
    }
    
    public int getID() {
        return key;
    }
    
    public int getIPCount() {
        return ipList.size();
    }

    public void addIP(String _ip) {
        if (!ipList.contains(_ip)) {
            ipList.add(_ip);
        }
    }

    public void removeIP(String _ip) {
        ipList.remove(_ip);
    }

    public void removeAllIP() {
        ipList.clear();
    }

    public String getAnIP() {
        String result = (String) ipList.get(0);
        ipList.remove(0);
        ipList.add(result);
        return result;
    }

}
