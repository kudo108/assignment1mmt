package test;



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
    Server(){
        try {
            server = new ServerSocket(5555);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),e.getMessage(),"ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void closeServer(){
        try{
            server.close();
        }catch(Exception e){
            
        }
    }

}