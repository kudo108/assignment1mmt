

import java.io.*;
import java.util.ArrayList;

/**
 *
 * @author T'PHaM
 */
public class ServerElement implements Serializable {
    private int            key =   -1;
    private ArrayList   ipList = null;
    private long      fileSize =   -1;

    public ServerElement(int _key, long _fileSize) {
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
        if (ipList.size() <= 0) return null;
        String result = (String) ipList.get(0);
        ipList.remove(0);
        ipList.add(result);
        return result;
    }
    
    public String getThisServerElement() {
        String result = null;
        result = String.format("%d %d %d\n", key, fileSize, ipList.size());
        for (int i = 0; i < ipList.size(); i++) {
            result.concat(String.format("%s\n", ipList.get(i)));
        }
        return result;
    }
    
    public void showOut() {
        System.out.print("ID: " + key);
        System.out.print(", fileSize: " + fileSize);
        System.out.println(", ipList:");
        for (int i = 0; i < ipList.size(); i++) {
            System.out.println(ipList.get(i));
        }
    }

}
