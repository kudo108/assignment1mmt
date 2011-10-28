package bitcrazyclient;




/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author NS
 */


import java.net.*;
import javax.swing.JOptionPane;
import java.util.ArrayList;


public class Server{
    public static ServerSocket server;
    private Socket sk;
    private int port;
    public ArrayList<FileUpload> threadlist = new ArrayList<FileUpload>();
    public boolean stopThread(int hash){
        int index = -1;
        for(int i = 0; i < threadlist.size(); i++){
         if(threadlist.get(i).gethash() == hash){
             index = i;
             break;
         }   
        }
        //System.out.println("line 35/Server.java");
        if(index != -1) threadlist.get(index).stop();//Tim ra tuc la thread da chay ( dang upload)=>stop
        else return false;
        return true;
    }
    Server(int port){
        this.port = port;
        createSocket();
    }
    private void createSocket(){
        try {
            server = new ServerSocket(this.port);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),e.getMessage(),"ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void listen(GUI UI){
        try{
            while(true){
                sk = server.accept();
            
                System.out.println("Server accepted client from "+
                                    sk.getInetAddress().getHostAddress()+sk.getPort());
                FileUpload newFileUpload = new FileUpload(sk,UI);
                Thread newthread = new Thread(newFileUpload);
                newthread.start();
                threadlist.add(newFileUpload);
            }
        }catch(Exception e){
            System.out.println("Listen error : "+ e);
        }
    }
    public void closeServer(){
        try{
            server.close();
        }catch(Exception e){
            
        }
    }

}