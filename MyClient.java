import java.io.*;  
import java.net.*; 
import java.util.concurrent.TimeUnit;

public class MyClient {  

    public static void main(String[] args) {  
        try {
            Socket s = new Socket("localhost", 4000); 
            // Socket s = new Socket("176.34.64.145", 4000);  
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());  
            DataInputStream din = new DataInputStream(s.getInputStream());
            int looper = 0;
            while(looper < 1000) {
                if(looper % 10 == 0) {
                    if(looper > 100 && looper < 150) {
                        dout.writeInt(1);
                        String identifier = din.readUTF();
                        int colour = din.readInt();
                        System.out.println(identifier + colour);
                    }
                    else {
                        dout.writeInt(0);
                    }
                }
                looper++;
                // Thread.sleep(100);
                TimeUnit.MILLISECONDS.sleep(100);

                if(looper % 10 == 0){
                    System.out.println(looper);
                }
            }
            String identifier = din.readUTF();
            int colour = din.readInt();
            
            System.out.println(identifier + colour); 
            dout.close(); 
            din.close();
            s.close();
        }
        catch(Exception e){
            System.out.println(e);
        }  
    }  
}  