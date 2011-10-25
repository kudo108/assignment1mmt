package test;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.*;
import java.io.*;

/**
 *
 * @author Thai Son Dinh Tran
 * Assigment 1 Computer Network - BitCrazy Application
 */
public class FileUpload implements Runnable{
    private String fileName = "";
    private String filePath;
    private Socket sk;
    private int hash;
    public GUI UI;
//    OutputStream output;
//    FileInputStream file;
//    boolean success = false;
    
    
    FileUpload(Socket sk,GUI UI, int hash){
        super();
        this.sk = sk;
        this.UI = UI;
        this.hash = hash;
    }
    
    
    @Override
    public void run(){
        try{
            
            OutputStream output = sk.getOutputStream();
            BufferedReader inReader = new BufferedReader(new InputStreamReader(sk.getInputStream()));
            
            //Get hash which client want to download
            int reqHash = Integer.parseInt(inReader.readLine());
            
            
            
            /* Check file hash whether it active */
            if(reqHash == this.hash){
                for(int i = 0; i < UI.countRow(); i++){//find file in table
                    if( UI.getHashCol(i) == hash){
                        fileName = UI.getNameCol(i);
                        filePath = UI.getPathCol(i);
                        break;
                    }
                }
            }
            /* Send filename to server */

            OutputStreamWriter outputStream = new OutputStreamWriter(sk.getOutputStream());
            
//            sk.setSendBufferSize(4096);
            if(fileName.equals("")){
                outputStream.write("404\n");
            }
            else{
                outputStream.write(fileName + "\n");
            }
                
            outputStream.flush();
            
            /* Get reponse from server */

           

            String serverStatus = inReader.readLine(); // Read the first line
           
            /* If server is ready, send the file */
            System.out.println(serverStatus);
            if ( serverStatus.equals("READY") ){
               
                FileInputStream file = new FileInputStream(filePath);
                
//                fstream = new FileWriter("src/log/log.txt",true);
//                log = new BufferedWriter(fstream);
                
                byte[] buffer = new byte[sk.getSendBufferSize()];
                
                int bytesRead = 0;
                
                //start timer
                long startTime = System.currentTimeMillis();
                
                while((bytesRead = file.read(buffer))>0)
                {
                    output.write(buffer,0,bytesRead);
                   
                }
//                log.close();
                
                //stop timer
                long endTime = System.currentTimeMillis();
                
                UI.setStatus("Done!",UI.getSelectRow());

                output.close();
                file.close();
                sk.close();
                
            }
            
        }
        
        catch (Exception ex){
            /* Catch any errors */
//            (new GUI(sk)).showMess(ex.getMessage());
            System.out.println(ex);
            
        }//end try
        
        
        
    }
//    @Override
//    public void finalize() throws Throwable{
//        super.finalize();
//        try{
//            output.close();
//            file.close();
//            sk.close();
//        }catch(Exception e){
//            
//        }
//    }
    
}

