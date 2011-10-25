package bitcrazyserver;

import java.util.ArrayList;

/**
 *
 * @author T'PHaM
 */
public class TupleList {
    private ArrayList theList;

    public TupleList() {
        theList = new ArrayList(0);
    }

    public void add(Tuple _tuple) {
        theList.add(_tuple);
    }

    public void remove(int _hash, String _ip) {
        for (int i = 0; i < theList.size(); i++) {
            if ((((Tuple)theList.get(i)).hash == _hash) && (((Tuple)theList.get(i)).ip.equals(_ip))) {
                theList.remove(i);
                break;
            }
        }
    }

    public void remove(Tuple _tuple) {
        theList.remove(_tuple);
    }

    public Tuple getByHash(int _hash) {
        Tuple result = null;
        for (int i = 0; i < theList.size(); i++) {
            if (((Tuple)theList.get(i)).hash == _hash) {
                result = ((Tuple)theList.get(i));
                break;
            }
        }
        return result;
    }

}
