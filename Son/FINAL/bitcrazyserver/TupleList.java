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

    public void add(int _id, String _ip) {
        for (int i = 0; i < theList.size(); i++) {
            if (((Tuple) theList.get(i)).getID() == _id) {
                ((Tuple) theList.get(i)).addIP(_ip);
                break;
            }
        }
    }

    public int add(String _ip, long _fileSize) {
        int id = theList.size();
        Tuple theTuple = new Tuple(id, _fileSize);
        theTuple.addIP(_ip);
        theList.add(theTuple);
        return id;
    }

    public void remove(int _id, String _ip) {
        for (int i = 0; i < theList.size(); i++) {
            if (((Tuple) theList.get(i)).getID() == _id) {
                ((Tuple) theList.get(i)).removeIP(_ip);
                break;
            }
        }
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
    
    public String getThisList() {
        String result = null;
        result = String.format("%d \n", theList.size());
        for (int i = 0; i < theList.size(); i++) {
            result = result.concat(((Tuple) theList.get(i)).getThisTuple());
        }
        return result;
    }

}
