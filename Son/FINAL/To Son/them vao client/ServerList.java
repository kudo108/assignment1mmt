

import java.io.*;
import java.util.ArrayList;

/**
 *
 * @author T'PHaM
 */
public class ServerList implements Serializable {
    private ArrayList theList;

    public ServerList() {
        theList = new ArrayList(0);
    }

    public void add(int _id, String _ip) {
        for (int i = 0; i < theList.size(); i++) {
            if (((ServerElement) theList.get(i)).getID() == _id) {
                ((ServerElement) theList.get(i)).addIP(_ip);
                break;
            }
        }
    }

    public int add(String _ip, long _fileSize) {
        int id = theList.size();
        ServerElement theServerElement = new ServerElement(id, _fileSize);
        theServerElement.addIP(_ip);
        theList.add(theServerElement);
        return id;
    }
    
    public void addElement(ServerElement _serverElement) {
        theList.add(_serverElement);
    }

    public void remove(int _id, String _ip) {
        for (int i = 0; i < theList.size(); i++) {
            if (((ServerElement) theList.get(i)).getID() == _id) {
                ((ServerElement) theList.get(i)).removeIP(_ip);
                break;
            }
        }
    }

    public void remove(String _ip) {
        for (int i = 0; i < theList.size(); i++) {
            ((ServerElement) theList.get(i)).removeIP(_ip);
        }
    }

    public void removeAll() {
        for (int i = 0; i < theList.size(); i++) {
            ((ServerElement) theList.get(i)).removeAllIP();
        }
    }

    public ServerElement getByID(int _id) {
        ServerElement result = null;
        for (int i = 0; i < theList.size(); i++) {
            if (((ServerElement) theList.get(i)).getID() == _id) {
                result = ((ServerElement) theList.get(i));
                break;
            }
        }
        return result;
    }
    
    public String getThisList() {
        String result = null;
        result = String.format("%d\n", theList.size());
        for (int i = 0; i < theList.size(); i++) {
            result.concat(String.format("%s", ((ServerElement) theList.get(i)).getThisServerElement()));
        }
        return result;
    }
    
    public void showOut() {
        for (int i = 0; i < theList.size(); i++) {
            System.out.println("\n--" + i + ":");
            ((ServerElement) theList.get(i)).showOut();
        }
    }

}
