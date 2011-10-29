import java.net.*;
import java.io.*;
import javax.swing.JOptionPane;

/**
 *
 * @author CrazyTeam
 */
public class FileDownload implements Runnable{
    private int ID = 0;
    public GUI UI;
    private String IP;
    private long currentSize = 0;
    private boolean running = true;
    private boolean isResume = false;
    FileDownload(int ID, GUI UI,String IP, boolean isResume){
        super();
        this.isResume = isResume;
        this.ID = ID;
        this.UI = UI;
        this.IP = IP;
    }
    public int getID(){
        return this.ID;
    }
    public void stop(){
        running = false;
        Thread.interrupted();
    }
    @Override
    public void run(){
        Socket sk = null;
        try{
            Client theClient = new Client(IP);///MT
            Tuple theTuple = theClient.getByID(ID);///MT
            theClient.finish();
            if(theTuple != null){//if there are at least 1 seeder (actually only 1 :P)
                
                //Connect server on port 5554
                sk = new Socket(theTuple.getIP(),5554);
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
                    
                    //Call the fileSave dialog with default file name
                    UI.setDefaultName(fileName);
                    
                    //Get the file to save
                    File file = UI.getSelectFile();
                    
                    if(file != null && UI.fileExist() == JOptionPane.OK_OPTION){
                    //if the file is exist and user want to overwrite it
                    //or the file isn't exist
                        
                        long offSet = 0;
                        long fileSize = theTuple.getFileSize();
                        
                        /* Add row into file list*/
                          int currentRow = -1;                     
                        if(isResume){
                            for(int i = 0; i < UI.countRow(); i++){
                                if(UI.getIDCol(i) == theTuple.getID()){
                                    currentRow = i;
                                    break;
                                }
                            }
                        }
                        else{
                            UI.addRow();
                            currentRow = UI.getCurrentRow();
                            UI.setName(file.getName(),currentRow);
                            UI.setSize(fileSize,currentRow);
                            UI.setID(theTuple.getID(),currentRow);
                            UI.setPath(file.getParent(),currentRow);
                        }
                        UI.setStatus("Downloading",currentRow);
                         String path = UI.getPathCol(currentRow)+"\\"+UI.getNameCol(currentRow);
                         
                         //read offset from fileSize if it resume
                        if(isResume){
                            //String path = UI.getPathCol(currentRow)+"\\"+UI.getNameCol(currentRow);
                            File a = new File(path);
                            offSet = a.length();
                        }
                        //send offsetto uploader
                        outReader.write(Long.toString(offSet) + "\n");
                        outReader.flush();
                        System.out.println("Leecher send offset : " + offSet);
                        
                        /* Create a new file in the tmp directory using the filename */

                        RandomAccessFile raf = new RandomAccessFile(file.getAbsolutePath(),"rw");
                        byte[] buffer = new byte[sk.getReceiveBufferSize()];

                        int bytesReceived = 0;
                        
                        System.out.println("Starting download with buffer size : "+sk.getReceiveBufferSize());
                        
                        long previous = 0;
                        long current = 0;
                        //if download in the first time, offSet = 0 so current = 0
                        // else if download more than twice, offSet = length of the file has saved on disk
                        current += offSet;
                        long startTime = System.currentTimeMillis();
                        
                        while(running &&(bytesReceived = input.read(buffer))>0){
                            
                            current += bytesReceived;
                            raf.seek(offSet);
                            raf.write(buffer);
                            
                            //increase offSet
                            offSet += bytesReceived;
                            
                            //caculate percent and speed
                            int percent = (int)(current*100/fileSize);
                            
                            if(percent + 1> 100) percent = 100;
                            UI.setProgress(percent, currentRow);
                            long endTime = System.currentTimeMillis();
                            if((endTime - startTime) >= 1000){// update speed every second
                                int speed = (int)((current - previous)*1024/( endTime - startTime));
                                UI.setSpeed(speed, currentRow);
                                startTime = System.currentTimeMillis();
                                previous = current;
                            }
                            
                        }
                        
                        if(running) UI.setStatus("Done!", currentRow);
                        System.out.println(currentRow);
                        raf.close();
                        inReader.close();
                        outReader.close();
                        input.close();
                    } else{ //if user click Cancel
                        //Send message telling seeder that user isn't ready to download
                        outReader.write("NOT_YET\n");
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
}
