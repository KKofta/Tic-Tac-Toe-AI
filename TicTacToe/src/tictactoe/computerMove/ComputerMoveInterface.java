package tictactoe.computerMove;

import java.util.ArrayList;

public interface ComputerMoveInterface {

    public int [] randomMove(ArrayList<Integer> cellsList);
    public int [] bestMove(char[][] gameLogic, char sign);

}
