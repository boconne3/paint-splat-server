import java.io.*;  
import java.net.*;

public class MyServer {  
    public static void main(String[] args){  
        try{
            int connectionNum = 1;
            ServerSocket ss = new ServerSocket(4000);
            Room room = new Room();
            while(true && connectionNum < 10) {
                Socket s = ss.accept();
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dout = new DataOutputStream(s.getOutputStream());
    
                int command = dis.readInt();
                while(command > -1) {
                    if(command == 0) {  // Keep alive
                        System.out.println("Keep alive");
                    }
                    if(command == 1){ // Add user to only room
                        String username = "USER " + connectionNum;
                        User newUser = room.addUser(username);
                        if(newUser != null) {
                            connectionNum++;
                            dout.writeUTF("Colour = ");
                            dout.writeInt(newUser.colour);
                        }
                        else {
                            dout.writeUTF("Error, room is full: ");
                            dout.writeInt(0);
                        }
                    }
                    command = dis.readInt();
                }
                // room.printAllUsers();
                dis.close();
                dout.close();
            }
            ss.close();
             
        } catch(Exception e) {
            System.out.println(e);
        }  
    }  
}  