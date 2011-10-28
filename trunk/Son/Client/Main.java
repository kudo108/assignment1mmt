

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
        Server server = new Server(5555);
        GUI UI = new GUI(server);
        UI.setVisible(true);
        server.listen(UI);
        
    }
    
}
