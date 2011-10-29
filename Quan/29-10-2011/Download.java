/**
 *
 * @author CrazyTeam
 */
import java.util.ArrayList;

public class Download {
    private ArrayList<FileDownload> dlList;
    Download(){
        dlList = new ArrayList<FileDownload>();
    }
    public synchronized boolean stopThead(int id){
        //int index = -1;
        for(int i = 0; i < this.dlList.size(); i++){
            if(this.dlList.get(i).getID() == id){
                this.dlList.get(i).stop();
                try{
                    this.dlList.wait();
                }catch(Exception e){
                    System.out.println(e);
                }
                return true;
            }
            System.out.println(this.dlList.get(i).getID()+"checked" );
         }
        return false;
    }
    public void addThread(FileDownload n){
        this.dlList.add(n);
        (new Thread(n)).start();
    }
}
