

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.*;
/**
 *
 * @author Administrator
 */
public class FileWriter {
    private RandomAccessFile raf;
    private byte[] buffer;
    private long fileSize;
    private int currentRow;
    private GUI UI;
    private long offSet;
    private long curData;
    private long preData = 0;
    private long startTime = System.currentTimeMillis();
    FileWriter(GUI UI, long fileSize, byte[] buffer, int currentRow, 
                RandomAccessFile f,long offSet){
        this.UI = UI;
        this.fileSize = fileSize;
        this.buffer = buffer;
        this.currentRow = currentRow;
        this.raf = f;
        this.offSet = offSet;
        curData = offSet;
    }
    public void start(int bytesReceived){
        try{
            raf.seek(offSet);
            raf.write(buffer);
            

            //increase offSet
            offSet += bytesReceived;

            //set current data 
            curData += bytesReceived;

            //caculate percent and speed
            int percent = (int)(curData*100/fileSize);

            if(percent + 1> 100) percent = 100;
            UI.setProgress(percent, currentRow);

            long endTime = System.currentTimeMillis();
            if((endTime - startTime) >= 1000){// update speed every second
                int speed = (int)((curData - preData)*1024/( endTime - startTime));
                UI.setSpeed(speed, currentRow);
                startTime = System.currentTimeMillis();
                preData = curData;
            }
        }catch(Exception e){
            UI.showErrorMess(UI, e.toString());
        }
    }
}
