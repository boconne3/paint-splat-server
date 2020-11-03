import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Room {
    int[][] board;  // Need to decide size of board
    int BOARD_X_SIZE = 10;
    int BOARD_Y_SIZE = 10;
    String id;
    Map<Integer, User> idAndUser;
    int currentUserID;
    ArrayList<Integer> availableColours;

    Room() {
        id = "abcd";
        clearBoard();
        availableColours = new ArrayList<Integer>();
        availableColours.add(1);
        availableColours.add(2);
        availableColours.add(3);
        availableColours.add(4);
        availableColours.add(5);
        availableColours.add(6);
        idAndUser = new HashMap<Integer, User>();
        currentUserID = 1;
    }

    public void printAllUsers(){

        for (Map.Entry<Integer, User> entry : idAndUser.entrySet())  
            System.out.println("Key = " + entry.getKey() + ", Username = " + entry.getValue().username);
    }

    public void clearBoard() {
        board = new int[BOARD_X_SIZE][BOARD_Y_SIZE];
    }

    public boolean freeSpace(int x, int y) {
        if(board[x][y] > 0){
            return false;
        }
        else {
            return true;
        }
    }

    public void fillSpace(int x, int y, int colour) {
        if(freeSpace(x, y)) {
            board[x][y] = colour;
        }
        else {
            return;
        }
    }

    public User addUser(String username) {
        int tempColour = 0;
        if(availableColours.size() > 0) {
            tempColour = availableColours.get(0);
            availableColours.remove(0);
        }
        else {
            return null;
        }
        
        User newUser = new User(currentUserID, username, tempColour);
        idAndUser.put(currentUserID, newUser);
        currentUserID++;
        return newUser;
    }

    public void removeUser(User user) {
        availableColours.add(user.colour);
        idAndUser.remove(user.id);
    }

}