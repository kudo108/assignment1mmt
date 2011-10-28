package Client;



/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.*;
import java.io.*;
import javax.swing.JOptionPane;

/**
 *
 * @author NS
 */
public class FileDownload implements Runnable{
    private int ID = 0;
    public GUI UI;
    private String IP;
    private long currentSize = 0;
    FileDownload(int ID, GUI UI,String IP){
        super();
        this.ID = ID;
        this.UI = UI;
        this.IP = IP;
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
                    UI.showMess(UI, "File not found!");
                
                }else{
                    
                    //Call the fileSave dialog with default file name
                    UI.setDefaultName(fileName);
                    
                    //Get the file to save
                    File file = UI.getSelectFile();
                    
                    if(file != null && UI.fileExist() == JOptionPane.OK_OPTION){
                    //if the file is exist and user want to overwrite it
                    //or the file isn't exist
                        
                        int offSet = 0;
                        long fileSize = theTuple.getFileSize();
                        
                        outReader.write(Integer.toString(offSet) + "\n");
                        outReader.flush();
                        System.out.println("Leecher send offset : " + offSet);
                        
                        /* Add row into file list*/

                        UI.addRow();
                        UI.setName(file.getName(),UI.getCurrentRow());
                        UI.setSize(fileSize,UI.getCurrentRow());
                        UI.setStatus("Downloading",UI.getCurrentRow());
                        UI.setID(theTuple.getID(),UI.getCurrentRow());
                        UI.setPath(file.getParent(),UI.getCurrentRow());

                
                        /* Create a new file in the tmp directory using the filename */
                        FileOutputStream wr = new FileOutputStream(new File(file.getAbsolutePath()));
                        
//                        sk.setReceiveBufferSize(65536);
                        byte[] buffer = new byte[sk.getReceiveBufferSize()];

                        int bytesReceived = 0;
                        
                        System.out.println("Starting download with buffer size : "+sk.getReceiveBufferSize());
                        
                        long previous = 0;
                        long current = 0;
                        long startTime = System.currentTimeMillis();
                        while((bytesReceived = input.read(buffer))>0){
                            
                            current += bytesReceived;
                            
                            /* Write to the file */
                            wr.write(buffer,0,bytesReceived);
                            
                            
                            
                            int percent = (int)(current*100/fileSize);
                            if(percent + 1> 100) percent = 100;
                            UI.setProgress(percent, UI.getCurrentRow());
                            long endTime = System.currentTimeMillis();
                            if((endTime - startTime) >= 1000){// update speed every second
                                int speed = (int)((current - previous)*1024/( endTime - startTime));
                                UI.setSpeed(speed, UI.getCurrentRow());
                                startTime = System.currentTimeMillis();
                                previous = current;
                            }
                            
                        }
                        
                        
                       
//                        UI.showMess(UI, Long.toString((endTime - startTime)));
                        for(int i = 0; i < UI.countRow(); i++){
                            if(UI.getIDCol(i) == file.hashCode() && UI.getStatusCol(i).equals("Downloading")){
                                UI.setStatus("Done!", i);
                                break;
                            }
                        }
                        wr.close();
                        inReader.close();
                        outReader.close();
                        input.close();
                    } else{ //if user click Cancel
                        //Send message telling seeder that user isn't ready to download
                        outReader.write("NOT_YET\n");
                    }
                }//end if (filename.equals("404")
                
            } else{ // if there is no seeders
                UI.showMess(UI, "File not found!!!");
            }// end if (theTuple != null) 
            
        }catch(Exception e){
            //Catch any exception
            System.out.println(e);
            UI.showMess(UI, e.getMessage());
        }
    } // end run()
}
