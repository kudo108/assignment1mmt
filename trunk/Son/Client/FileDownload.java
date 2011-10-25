package Client;



/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.*;
import java.io.*;

/**
 *
 * @author NS
 */
public class FileDownload implements Runnable{
    private int hash = 0;
    public GUI UI;
    private String IP;
    private File file;
    FileDownload(int hash, GUI UI,String IP, File file){
        super();
        this.hash = hash;
        this.UI = UI;
        this.IP = IP;
        this.file = file;
    }
    
    @Override
    public void run(){
        Socket sk = null;
        try{
            Client theClient = new Client(IP);///MT
            Tuple theTuple = theClient.getByHash(hash);///MT
            theClient.finishSocket();
            if(theTuple != null){//nếu có người đang seed
                //Connect server on port 5555
                sk = new Socket(theTuple.ip,5555);
                System.out.println("Request to server");
            
            
                InputStream input = sk.getInputStream();
                BufferedReader inReader = new BufferedReader(new InputStreamReader(sk.getInputStream()));
                BufferedWriter outReader = new BufferedWriter(new OutputStreamWriter(sk.getOutputStream()));
            
                //request hash
                outReader.write(hash+"\n");
                outReader.flush();
  
                /* Read the filename */
                String fileName = inReader.readLine();

                if ( fileName.equals("404") ){
                
                    outReader.write("NOT_YET\n");
                    outReader.flush();
                    UI.showMess(UI, "File not found!");
                
                }else{
                    /* Reply back to client with READY status */
                    System.out.println("Current row is : "+UI.getCurrentRow()+UI.countRow());
                    
                    outReader.write("READY\n");
                    outReader.flush();
                
                
                    /* Add row into file list*/
//                  fileLst.addFile(filePath, selectedFile.length(), selectedFile.hashCode());
                    UI.addRow();
                    System.out.println("Current row is : "+UI.getCurrentRow()+UI.countRow());
                    UI.setName(file.getName(),UI.getCurrentRow());
                    UI.setSize(file.length(),UI.getCurrentRow());
                    UI.setStatus("Downloading",UI.getCurrentRow());
                    UI.setHash(file.hashCode(),UI.getCurrentRow());
                    UI.setPath(file.getParent(),UI.getCurrentRow());
//                  UI.increaseRow();
                
                    /* Create a new file in the tmp directory using the filename */
                    FileOutputStream wr = new FileOutputStream(new File(file.getAbsolutePath()));
        
                    byte[] buffer = new byte[sk.getReceiveBufferSize()];

                    int bytesReceived = 0;
 
                    while((bytesReceived = input.read(buffer))>0){
                        /* Write to the file */
                        wr.write(buffer,0,bytesReceived);

                    }
                    System.out.println(UI.getCurrentRow());
                    if(UI.wasAdded()){
                        UI.setStatus("Done!",UI.getCurrentRow() - 1);
                    }else{
                        UI.setStatus("Done!",UI.getCurrentRow());
                    }
                    wr.close();
                    inReader.close();
                    outReader.close();
                    input.close();
                }
                
            } else{
                UI.showMess(UI, "File not found!");
            }
            
        }catch(Exception e){
            System.out.println(e);
            UI.showMess(UI, e.getMessage());
        }
    }
}
