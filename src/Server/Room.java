package Server;

import java.util.*;

public class Room {
    int[][] board;  // Need to decide size of board
    int BOARD_X_SIZE = 10;
    int BOARD_Y_SIZE = 10;
    String id;
    Map<Integer, User> idAndUser = new HashMap<>();
    int currentUserID;
    List<Integer> availableColours;

    Room() {
        id = "abcd";
        clearBoard();
        availableColours = Arrays.asList(1, 2, 3, 4, 5, 6);
        currentUserID = 1;
    }

    public void printAllUsers(){
        for (Map.Entry<Integer, User> entry : idAndUser.entrySet())
            System.out.println("Key = " + entry.getKey() + ", Username = " + entry.getValue().username);
    }

    public void clearBoard() {
        board = new int[BOARD_X_SIZE][BOARD_Y_SIZE];
        for(int[] row: board){              //initialized with all elements 0
            Arrays.fill(row, 0);
        }
    }

    public boolean freeSpace(int x, int y) {
        return board[x][y] <= 0;
    }

    public synchronized void fillSpace(int x, int y, int colour) {
        if(freeSpace(x, y)) {
            board[x][y] = colour;
        }
    }

    public synchronized int[][] getBoard(){
        return board;
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