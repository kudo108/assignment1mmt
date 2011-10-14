

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author NS
 */
import java.io.*;
import java.net.*;

class Server
{
    public static void main(String args[])throws Exception
    {
        System.out.println("Server running...");

        /* Listen on port 5555 */

        ServerSocket server = new ServerSocket(5555);

        /* Accept the sk */

        Socket sk = server.accept();
        
        System.out.println("Server accepted client");
        InputStream input = sk.getInputStream();
        BufferedReader inReader = new BufferedReader(new InputStreamReader(sk.getInputStream()));
        BufferedWriter outReader = new BufferedWriter(new OutputStreamWriter(sk.getOutputStream()));

        /* Read the filename */
        String filename = inReader.readLine();

        if ( !filename.equals("") ){

            /* Reply back to client with READY status */

            outReader.write("READY\n");
            outReader.flush();
        }

        /* Create a new file in the tmp directory using the filename */
        FileOutputStream wr = new FileOutputStream(new File("D://" + filename));

        byte[] buffer = new byte[/*sk.getReceiveBufferSize()*/File_Info.BLOCK];

        int bytesReceived = 0;

        while((bytesReceived = input.read(buffer))>0)
        {
            /* Write to the file */
           wr.write(buffer,0,bytesReceived);
        }
    }
}