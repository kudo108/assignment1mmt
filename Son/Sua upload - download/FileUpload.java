

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
    private String filePath = "";
    private long fileSize = 0;
    private Socket sk;
    public GUI UI;
    private int offSet = -1;
//    OutputStream output;
//    FileInputStream file;
//    boolean success = false;
    
    
    FileUpload(Socket sk,GUI UI){
        super();
        this.sk = sk;
        this.UI = UI;
    }
    
    
    @Override
    public void run(){
        try{
            
            OutputStream output = sk.getOutputStream();
            BufferedReader inReader = new BufferedReader(new InputStreamReader(sk.getInputStream()));
            
            //Get hash which client want to download
            int reqHash = Integer.parseInt(inReader.readLine());
            System.out.println("Client want to download hash : "+reqHash);
            
            /* Check file hash whether it active */
  
            for(int i = 0; i < UI.countRow(); i++){//find file in table
                if( UI.getHashCol(i) == reqHash && UI.getStatusCol(i).equals("Seeding")){
                    fileName = UI.getNameCol(i);
                    filePath = UI.getPathCol(i);
                    fileSize = UI.getSizeCol(i);
                    System.out.println("Row "+i +" "+fileName+" "+filePath);
                    break;
                }
            }
            
            /* Send filename to server */

            OutputStreamWriter outputStream = new OutputStreamWriter(sk.getOutputStream());
            
//            sk.setSendBufferSize(4096);
            if(fileName.equals("")){
                outputStream.write("404\n");
            }
            else{
                outputStream.write(fileName + "\n"+fileSize +"\n");
            }
            
            
            outputStream.flush();
            
            /* Get reponse from server */

           

//          String leecherStatus = inReader.readLine(); // Read the first line
            offSet = Integer.parseInt(inReader.readLine());
            
            System.out.println("Client want to download from offSet = "+offSet);
            
            /* If server is ready, send the file */
           
            if (offSet != -1){//start upload
                FileInputStream file = new FileInputStream(filePath+"\\"+fileName);
                

//                sk.setSendBufferSize(65536);
                byte[] buffer = new byte[sk.getSendBufferSize()];
                
                
                int bytesRead = 0;
               
                
                //start timer
                long startTime = System.currentTimeMillis();
                
                while((bytesRead = file.read(buffer, offSet, sk.getSendBufferSize()))>0)
                {
                    output.write(buffer,0,bytesRead);
                   
                }
//                log.close();
                
                //stop timer
                long endTime = System.currentTimeMillis();
                
//                UI.setStatus("Done!",UI.getSelectedRow());

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

