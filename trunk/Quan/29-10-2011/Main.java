/**
 *
 * @author CrazyTeam
 */
public class Main {
    public final static int LISTENING_PORT = 5555;
    public static void main(String[] args) {
        Server server = new Server(LISTENING_PORT);
        Download dl = new Download();
        GUI UI = new GUI(server, dl);
        UI.setVisible(true);
        server.listen(UI);
        
    }
}
