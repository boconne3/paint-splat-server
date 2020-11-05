package Server;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.logging.Logger;

import static java.lang.Character.getNumericValue;

public class ServerThread extends Thread {
    protected Socket socket;
    public static final String pollingThread = "POLLING_THREAD";
    public static final String actionThread = "ACTION_THREAD";
    public static final String disconnectThread = "STOP";

    int randomInt = 0;                                             // we'll get rid of this when we're sure that multi-threading is working fine

    public ServerThread(Socket clientSocket) {
        this.socket = clientSocket;
    }

    public void run() {
        InputStream inp = null;
        BufferedReader brinp = null;
        DataOutputStream out = null;
        try {
            inp = socket.getInputStream();
            brinp = new BufferedReader(new InputStreamReader(inp));
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            return;
        }
        String line;
        while (true) {                                           //TODO: when to stop this thread ? maybe never ?
            try {
                line = brinp.readLine();
                if (line.equals(pollingThread)) {               //TODO: should decide on some conventions when initiating conversation.
                    pollingThreadExecution(brinp, out);
                }
                else if(line.equals(actionThread)){             // two ways to do this one 1. continuous connection, 2. using "STOP"
                    actionThreadExecution(brinp, out);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    /**
     * TODO: check if need a delay here
     */
    public void pollingThreadExecution(BufferedReader brinp, DataOutputStream out) throws IOException, InterruptedException {
        String line;
        do {
            randomInt++;
            System.out.println("random int increment counter in polling thread at: " + randomInt++);

            out.writeBytes(Arrays.deepToString(ThreadedServer.randomRoom.getBoard()) + "\n\r");
            out.flush();
            line = brinp.readLine();
            Thread.sleep(300);
        } while (!line.equals(disconnectThread)); // maybe we won't need this here
    }

    /** right now this function just fills the board with recieved co-ordinates
     * TODO: check with front-end about condition codes and implement those here
     */
    public void actionThreadExecution(BufferedReader brinp, DataOutputStream out) throws IOException {
        String line;
        while(true) {
            randomInt++;
            System.out.println("random int increment counter in action thread at: " + randomInt++);

            out.writeBytes("give me co-ordinates\n\r");
            out.flush();
            line = brinp.readLine();
            if(line.equals(disconnectThread)) break;

            int[] valuesInLine = getArrayFromMessage(line);
            ThreadedServer.randomRoom.fillSpace(valuesInLine[0], valuesInLine[1], valuesInLine[2]);

            System.out.println(Arrays.deepToString(ThreadedServer.randomRoom.getBoard()));
        }
    }

    public int[] getArrayFromMessage(String message) {
        String[] valuesInLine = message.split("\\[");
        valuesInLine = valuesInLine[1].split("]");
        valuesInLine = valuesInLine[0].split(" ");
        int[] desiredArray = new int[valuesInLine.length];
        int idx = 0;

        for(String val: valuesInLine){
            desiredArray[idx] = getNumericValue(valuesInLine[idx++].toCharArray()[0]);
        }

        return desiredArray;
    }
}