/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author NS
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Server server = new Server(22222);
        GUI UI = new GUI();
        UI.setVisible(true);
        server.listen(UI);
        
    }
    
}
