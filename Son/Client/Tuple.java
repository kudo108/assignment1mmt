package mynew;

/**
 *
 * @author T'PHaM
 */
public class Tuple {
    private int          id =   -1;
    private String       ip = null;
    private long   fileSize =   -1;

    public Tuple(int _id, String _ip, long _fileSize) {
        id       = _id;
        ip       = _ip;
        fileSize = _fileSize;
    }
    
    public int getID() {
        return id;
    }

    public String getIP() {
        return ip;
    }

    public long getFileSize() {
        return fileSize;
    }
}
