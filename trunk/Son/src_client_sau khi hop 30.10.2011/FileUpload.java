import java.net.*;
import java.io.*;

/**
 *
 * @author Thai Son Dinh Tran
 * Assignment 1 Computer Network - BitCrazy Application
 */
public class FileUpload implements Runnable{
    private String fileName = "";
    private String filePath = "";
    private long fileSize = 0;
    private Socket sk;
    public GUI UI;
    private int reqID;
    private long offSet = -1;
    private boolean running= true;
    private Thread thread;
    private SpeedControler spdctr ;
    
    FileUpload(Socket sk,GUI UI){
        super();
        this.sk = sk;
        this.UI = UI;
        thread = new Thread(this);
        thread.start();
    }
    
    
    @Override
    public void run(){
        try{
            spdctr = new SpeedControler(sk);
            
            
            OutputStream output = sk.getOutputStream();
            BufferedReader inReader = new BufferedReader(new InputStreamReader(sk.getInputStream()));
            
            //Get hash which client want to download
            reqID = Integer.parseInt(inReader.readLine());
            
            System.out.println("Client want to download ID : "+reqID);
            
            /* Check file hash whether it active */
  
            for(int i = 0; i < UI.countRow(); i++){//find file in table
                if( UI.getIDCol(i) == reqID && UI.getStatusCol(i).equals("Seeding")){
                    fileName = UI.getNameCol(i);
                    filePath = UI.getPathCol(i);
                    break;
                }
            }
            
            /* Send filename to leecher */

            OutputStreamWriter outputStream = new OutputStreamWriter(sk.getOutputStream());
            
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

                if (!leecherStatus.equals("NOT_YET")){
                    //if leecher send offSet of file
                    
                    String rate = inReader.readLine();
                    int sndspeed = Integer.parseInt(rate);
                    spdctr.setUpSpeed(sndspeed);
                    
                    RandomAccessFile raf = new RandomAccessFile(filePath+"\\"+fileName,"r");
                    
                    byte[] buffer = new byte[65536];

                    offSet = Integer.parseInt(leecherStatus);
                    System.out.println("Client want to download from offSet = "+offSet);
                    int bytesRead = 0;
                    raf.seek(offSet);
                    while(running && (bytesRead = raf.read(buffer, 0, sk.getSendBufferSize())) >0)
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
    public int gethash(){
        return this.reqID;
    }
    public void stop(){
        running = false;
        this.spdctr.stop();
        thread.interrupt();
    }
    public boolean stillAlive(){
        return thread.isAlive();
    }
}