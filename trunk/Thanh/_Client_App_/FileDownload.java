package test;



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
    FileDownload(int hash, GUI UI){
        super();
        this.hash = hash;
        this.UI = UI;
    }
    
    @Override
    public void run(){
        Socket sk = null;
        String Location = null;
        try{
            Client theClient = new Client();///MT
            Tuple theTuple = theClient.getByHash(hash);///MT
            theClient.finishSocket();

            //Connect server on port 5555
            sk = new Socket(theTuple.ip,5554);///MT
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
                UI.setText("Current row is : " +Integer.toString(UI.getCurrentRow())+UI.countRow());
                outReader.write("READY\n");
                outReader.flush();
                
                Location = "E://";
                
                /* Add row into file list*/
//              fileLst.addFile(filePath, selectedFile.length(), selectedFile.hashCode());
                UI.addRow();
                System.out.println("Current row is : "+UI.getCurrentRow()+UI.countRow());
                UI.setName(fileName,UI.countRow() - 1);
//                UI.setSize(hash);
                UI.setStatus("Downloading",UI.getCurrentRow());
                UI.setHash(hash,UI.getCurrentRow());
                UI.setPath(Location + fileName,UI.getCurrentRow());
//                UI.increaseRow();
                
                /* Create a new file in the tmp directory using the filename */
                FileOutputStream wr = new FileOutputStream(new File(Location + fileName));
        
                byte[] buffer = new byte[sk.getReceiveBufferSize()];

                int bytesReceived = 0;
 
                while((bytesReceived = input.read(buffer))>0){
                    /* Write to the file */
                    wr.write(buffer,0,bytesReceived);

                }
                System.out.println(UI.getCurrentRow());
                UI.setStatus("Done!",UI.getCurrentRow()-1);
               
            }
            inReader.close();
            outReader.close();
            input.close();
                
            
        }catch(Exception e){
            System.out.println(e);
            UI.showMess(UI, e.getMessage());
        }
    }
}
