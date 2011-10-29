import java.net.*;
import java.io.*;

/**
 * @author CrazyTeam
 * Assigment 1 Computer Network - BitCrazy Application
 */
public class FileUpload implements Runnable{
    private String fileName = "";
    private String filePath = "";
    private long fileSize = 0;
    private Socket sk;
    public GUI UI;
    private int reqHash;
    private long offSet = -1;
    private boolean running= true;
//    OutputStream output;
//    FileInputStream file;
//    boolean success = false;
    
    public int gethash(){
        return this.reqHash;
    }
    public void stop(){
        running = false;
        Thread.interrupted();
    }
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
            reqHash = Integer.parseInt(inReader.readLine());
            
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
            else{
                //send fileName to leecher
                outputStream.write(fileName + "\n");
                System.out.println("File found : " + fileName);
                
                outputStream.flush();

                /* Get reponse from leecher */

                String leecherStatus = inReader.readLine();
                /* If leecher is ready, send the file */

                if (!leecherStatus.equals("NOT_YET")){//if leecher send offSet of file

                    RandomAccessFile raf = new RandomAccessFile(filePath+"\\"+fileName,"r");
                    
    //              sk.setSendBufferSize(65536);
                    byte[] buffer = new byte[sk.getSendBufferSize()];

                    offSet = Integer.parseInt(leecherStatus);
                    System.out.println("Client want to download from offSet = "+offSet);
                    int bytesRead = 0;
                    raf.seek(offSet);
                    while(running && (bytesRead = raf.read(buffer)) >0)
                    {   
                        /*Write file from offSet to leecher*/
                        output.write(buffer,0,bytesRead);

                    }
                    raf.close();
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
    
}

