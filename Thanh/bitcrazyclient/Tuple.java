package javaapplication7;

/**
 *
 * @author T'PHaM
 */
public class Tuple {
    public int    hash     =   -1;
    public String ip       = null;
    public long   fileSize =   -1;

    public Tuple(int _hash, String _ip, long _fileSize) {
        hash     = _hash;
        ip       = _ip;
        fileSize = _fileSize;
    }
}
