package tictactoe.cumputerMove;

import java.util.ArrayList;

public interface ComputerMoveInterface {

    public int randomMove(ArrayList<Integer> cellsList);
    public int bestMove(ArrayList<Integer> cellsList);

}
