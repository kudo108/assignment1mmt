package bitcrazyserver;



import java.io.*;
import java.util.ArrayList;

/**
 *
 * @author T'PHaM
 */
public class TupleList implements Serializable {
    private ArrayList theList;

    public TupleList() {
        theList = new ArrayList(0);
    }

    public boolean add(int _id, String _ip) {
        boolean result = false;
        for (int i = 0; i < theList.size(); i++) {
            if (((Tuple) theList.get(i)).getID() == _id) {
                result = ((Tuple) theList.get(i)).addIP(_ip);
                break;
            }
        }
        return result;
    }

    public int add(String _ip, long _fileSize) {
        int id = theList.size();
        Tuple theTuple = new Tuple(id, _fileSize);
        theTuple.addIP(_ip);
        theList.add(theTuple);
        return id;
    }

    public boolean remove(int _id, String _ip) {
        boolean result = false;
        for (int i = 0; i < theList.size(); i++) {
            if (((Tuple) theList.get(i)).getID() == _id) {
                result = ((Tuple) theList.get(i)).removeIP(_ip);
                break;
            }
        }
        return result;
    }

    public void remove(String _ip) {
        for (int i = 0; i < theList.size(); i++) {
            ((Tuple) theList.get(i)).removeIP(_ip);
        }
    }

    public void removeAll() {
        for (int i = 0; i < theList.size(); i++) {
            ((Tuple) theList.get(i)).removeAllIP();
        }
    }

    public Tuple getByID(int _id) {
        Tuple result = null;
        for (int i = 0; i < theList.size(); i++) {
            if (((Tuple) theList.get(i)).getID() == _id) {
                result = ((Tuple) theList.get(i));
                break;
            }
        }
        return result;
    }

}
