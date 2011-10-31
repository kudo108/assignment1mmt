/*
 * Author: BitCrazy Team
 */
import java.net.*;
import java.io.*;

public class FileDownload implements Runnable{
    private int ID = 0;
    public GUI UI;
    private String IP;
    private boolean running = true;
    private boolean isResume = false;
    private Thread thread;
    FileDownload(int ID, GUI UI,String IP, boolean isResume){
        super();
        this.isResume = isResume;
        this.ID = ID;
        this.UI = UI;
        this.IP = IP;
        thread = (new Thread(this));
        thread.start();
    }
    
    @Override
    public void run(){
        Socket sk;
        try{
            Client theClient = new Client(IP);///MT
            Tuple theTuple = theClient.getByID(ID);///MT
            theClient.finish();
            if(theTuple != null){//if there are at least 1 seeder (actually only 1 :P)
                
                //Connect server on port 5554
                sk = new Socket(theTuple.getIP(),22221);
               
                
                //SpeedControler sc = new SpeedControler(sk);
                //sc.setDownSpeed(1);
                //sk.setReceiveBufferSize(2);
                
                System.out.println("Request to server");
            
                InputStream input = sk.getInputStream();
                BufferedReader inReader = new BufferedReader(new InputStreamReader(sk.getInputStream()));
                BufferedWriter outReader = new BufferedWriter(new OutputStreamWriter(sk.getOutputStream()));
            
                //request hash
                outReader.write(ID+"\n");
                outReader.flush();
  
                /* Read the filename */
                String fileName = inReader.readLine();
                
                System.out.println("File : " + fileName + " Size : " + theTuple.getFileSize());

                if ( fileName.equals("404") ){// if seeder has removed that file
                
                    outReader.write("NOT_YET\n");
                    outReader.flush();
                    UI.showInfMess(UI, "File not found!");
                
                }else{
                    
                    //Set default name from fileName
                    UI.setDefaultName(fileName);
                    //if this file going to downloading the first time
                    //show the save dialog otherwise do nothing
                    if(!isResume){
                        UI.showSaveDlg();
                    }
                    //Get the file to save
                    File file = UI.getSelectFile();
                    
                    if(file != null && (isResume ||UI.fileExist() == GUI.OK_CLICK)){
                    //if the file is exist and user want to overwrite it
                    //or the file isn't exist
                        
                        long offSet = 0;
                        long fileSize = theTuple.getFileSize();
                        
                        /* Add row into file list*/
                          int currentRow = -1;                     
                        if(isResume){
                            currentRow = UI.checkExist(ID);
                        }
                        else{
                            UI.addRow(0);
                            currentRow = UI.getCurrentRow();
                            UI.setName(file.getName(),currentRow);
                            UI.setSize(fileSize,currentRow);
                            UI.setID(theTuple.getID(),currentRow);
                            UI.setPath(file.getParent(),currentRow);
                        }
                        UI.setStatus("Downloading",currentRow);
                        String path = UI.getPathCol(currentRow)+"\\"+UI.getNameCol(currentRow);
                         
                         //read the last byte from fileSize if it resume
                        if(isResume){
                            File interruptedFile = new File(path);
                            offSet = interruptedFile.length();
                        }
                        //send offset to seeder
                        int rate = UI.getLimitCol(ID);
                        outReader.write(Long.toString(offSet) + "\n"+Integer.toString(rate) +"\n");
                        outReader.flush();
                        System.out.println("Leecher send offset : " + offSet);
                        
                        
                        /* Create a new file in the tmp directory using the filename */

                        RandomAccessFile raf = new RandomAccessFile(file.getAbsolutePath(),"rw");

                        byte[] buffer = new byte[65536];
                        
                        int bytesReceived = 0;
                        
                        System.out.println("Starting download with buffer size : "+sk.getReceiveBufferSize());
                        
                        long preData = 0;
                        long curData = 0;
                        //if download in the first time, offSet = 0 so current = 0
                        // else if download more than twice, offSet = length of the file has saved on disk
                        curData += offSet;
                        //int speedrate = 4;
                        
                        long startTime = System.currentTimeMillis();
                        raf.seek(offSet);
//                        FileWriter writer = new FileWriter(UI,fileSize,buffer,currentRow,raf,offSet);
                        //int rtt = SpeedControler.getRTT(theTuple.getIP());////
                        //System.out.println("RTT: " +  rtt);/////
                        //sk.setTcpNoDelay(true); /////
                        
                        while(running &&(bytesReceived = input.read(buffer))>0){
//                            int dlspeed = UI.getLimitCol(ID);
//                            spdctr.setDownSpeed(dlspeed);
//                            outReader.write(dlspeed+"\n");
//                            outReader.flush();
                            /*Start writing to file*/
                            raf.write(buffer,0,bytesReceived);

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
                        }
                        if(!running){
                            notice();
                            raf.close();
                        }
                        if(running){ 
                            if(curData==fileSize){
                                UI.setStatus("Done!", currentRow);}
                            else {
                                UI.setStatus("StoppedDownloading", currentRow);
                            }
                        }
                        raf.close();
                        inReader.close();
                        outReader.close();
                        input.close();
                    } else{ //if user click Cancel
                        //Send message telling seeder that user isn't ready to download
                        outReader.write("NOT_YET\n");
                        System.out.println("Send message to seeder : NOT_YET");
                    }
                }
                
            } else{ // if there is no seeders
                UI.showInfMess(UI, "File not found!!!");
            }// end if (theTuple != null) 
            
        }catch(Exception e){
            //Catch any exception
            System.out.println(e);
            UI.showInfMess(UI, e.toString());
        }
    } // end run()
    private synchronized void notice(){
        try{
            System.out.println("Wake the DownloadManger up!");
            this.notifyAll();
        }catch(Exception e){
            System.out.println("Cannot wake up DownloadManager");
        }
    }
    public boolean stillAlive(){
        return thread.isAlive();
    }
    public int getID(){
        return this.ID;
    }
    public void stop(){
        running = false;
        thread.interrupt();
        
    }
}
