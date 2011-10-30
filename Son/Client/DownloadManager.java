



/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author NS
 */
import java.util.ArrayList;

public class DownloadManager {
    private ArrayList<FileDownload> dlList;
    DownloadManager(){
        dlList = new ArrayList<FileDownload>();
    }
    public synchronized boolean stopThead(int id){
        //int index = -1;
        for(int i = 0; i < this.dlList.size(); i++){
            if(this.dlList.get(i).getID() == id && this.dlList.get(i).stillAlive()){
                this.dlList.get(i).stop();
                this.dlList.remove(i);
                this.waiting();
                return true;
            }
            System.out.println(this.dlList.get(i).getID()+" checked" );
         }
        return false;
    }
    public void addThread(FileDownload n){
        this.dlList.add(n);
    }

    private synchronized void waiting(){
        try{
            System.out.println("Waiting for thread FileDownload stopping");
            this.wait(100);
        }catch(Exception e){
            System.out.println(e);
        }
    }
}