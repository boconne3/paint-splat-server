import Client.Client;
import Server.ServerThread;
import org.junit.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;


public class ClientTest {

    @Test
    public void testsContinuousPollingToServer_WhenOnlyOneClient() {
        Client client = new Client();
        String response;
        try {
            client.startConnection("127.0.0.1", 4000);
            while(true) {
                response = client.sendMessage(Client.pollingThread);
                System.out.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testsActionThread_WhenOnlyOneClient() {
        Client client = new Client();
        String response;
        try {
            client.startConnection("127.0.0.1", 4000);
            response = client.sendMessage(Client.actionThread);
            System.out.println(response);

            if(response.equals("give me co-ordinates")) {
                int _x = 0;
                int _y = 0;
                while(true){
                    client.sendMessage(Arrays.deepToString(new Integer[]{_x++, _y++, 1}));
                    if(_x > 9 || _y > 9) {
                        client.sendMessage(Client.disconnectThread);
                        break;
                    }
                    Thread.sleep(1000);
                }
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetArrayFromMessage() {
        ServerThread thread = new ServerThread(new Socket());
        assertArrayEquals(thread.getArrayFromMessage("[3, 3, 1]"), new int[] {3,3,1});
    }
}
