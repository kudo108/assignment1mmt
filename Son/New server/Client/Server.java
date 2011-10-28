package Client;



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



public class Server{
    public static ServerSocket server;
    private Socket sk;
    private int port;
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
                (new Thread(new FileUpload(sk,UI))).start();
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