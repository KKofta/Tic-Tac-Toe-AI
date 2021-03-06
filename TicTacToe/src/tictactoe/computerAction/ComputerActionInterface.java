package tictactoe.computerAction;

import java.util.ArrayList;

public interface ComputerActionInterface {

    public int[] randomMove(ArrayList<Integer> cellsList);

    public int[] bestMove(char[][] gameLogic, char sign);

    public String checkGameState(char[][] gameLogic);

}
