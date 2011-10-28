package Client;

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
                if( UI.getIDCol(i) == reqHash && UI.getStatusCol(i).equals("Seeding")){
                    fileName = UI.getNameCol(i);
                    filePath = UI.getPathCol(i);
                    break;
                }
            }
            
            /* Send filename to leecher */

            OutputStreamWriter outputStream = new OutputStreamWriter(sk.getOutputStream());
            
//            sk.setSendBufferSize(4096);
            if(fileName.equals("")){//cannot file the specified file
                outputStream.write("404\n");
                System.out.println("File not found!");
            }
            else{//send fileName to leecher
                outputStream.write(fileName + "\n");
                System.out.println("File found : " + fileName);
                
                outputStream.flush();

                /* Get reponse from leecher */

                String leecherStatus = inReader.readLine();


                System.out.println("Client want to download from offSet = "+offSet);

                /* If leecher is ready, send the file */

                if (!leecherStatus.equals("NOT_YET")){//if leecher send offSet of file
                    FileInputStream file = new FileInputStream(filePath+"\\"+fileName);

    //                sk.setSendBufferSize(65536);
                    byte[] buffer = new byte[sk.getSendBufferSize()];

                    offSet = Integer.parseInt(leecherStatus);

                    int bytesRead = 0;

                    while((bytesRead = file.read(buffer))>0)
                    {
    //                    sum += bytesRead;
    //                    System.out.println("Sent " + sum);
                        output.write(buffer,0,bytesRead);

                    }

                    file.close();
                }
            }
            output.close();
            sk.close();
        }
        
        catch (Exception ex){
            /* Catch any errors */
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

