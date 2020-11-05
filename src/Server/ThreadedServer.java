package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadedServer {
    static final int PORT = 4000;

    /* keeping only one room for now, in future we can make this a list to do multiple rooms if we want..
    TODO: do we need a lock here ?
    get and fill are using synchronized keyword, maybe that does the trick.. not completely sure !
    */
    static Room randomRoom = new Room();

    public static void main(String[] args) {

        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();

        }

        try {
            while (true) {
                assert serverSocket != null;
                socket = serverSocket.accept();
                // new thread for a client
                new ServerThread(socket).start();
            }
        } catch (IOException e) {
            System.out.println("I/O error: " + e);
        }

    }
}