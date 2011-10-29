package bitcrazyserver;

import java.io.*;

/**
 *
 * @author T'PHaM
 */
public class FileSaver implements Runnable {
    private String      path = null;
    private Object    object = null;
    private Thread theThread = null;

    public FileSaver(String _path, Object _object) {
        path   = _path;
        object = _object;
    }

    public void start() {
        if (theThread == null) {
            theThread = new Thread(this);
            theThread.start();
        }
    }

    public void stop() {
        if (theThread != null) {
            theThread.stop();
            theThread = null;
        }
    }

    public void run() {
        try {
            FileOutputStream file  = new   FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(object);
            out.flush();
            out.close();
            file.close();
        } catch (IOException ioe) {
                System.out.println("Error saving: " + ioe.getMessage());
        }
    }

}
