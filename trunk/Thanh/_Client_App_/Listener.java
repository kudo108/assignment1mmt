package test;

import java.net.*;
/**
 *
 * @author Thai Son Dinh Tran
 * Assigment 1 Computer Network - BitCrazy Application
 */
public class Listener implements Runnable{
    private Socket sk;
    private ServerSocket sv;
    private GUI gui;
    private int hash;
    Listener(ServerSocket sv,GUI gui,int hash){
        super();
        this.sv = sv;
        this.gui = gui;
        this.hash = hash;
    }
    @Override
    public void run(){
        try{
            
            
            /* Accept the sk */
            while(true){
                sk = sv.accept();
                
                System.out.println("Server accepted client");
                //Start FileUpload Thread
                (new Thread(new FileUpload(sk,gui,hash))).start();
            }
 
        }catch(Exception e){
            gui.showMess(gui, e.getMessage());
            System.out.println(e);
        }
        
    }
    
}

