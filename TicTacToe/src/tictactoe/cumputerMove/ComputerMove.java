package tictactoe.cumputerMove;

import java.util.ArrayList;
import java.util.Random;

public class ComputerMove implements ComputerMoveInterface {

    @Override
    public int randomMove(ArrayList<Integer> cellsList) {
        
        System.out.println("Mam listę:" + cellsList);
        
        Random rand = new Random();
        int randomIndex = rand.nextInt(cellsList.size());
        int randomNumber = cellsList.get(randomIndex);

        System.out.println("Random index: " + randomIndex);
        System.out.println("Random number: " + randomNumber);

        return randomNumber;
    }

    @Override
    public int bestMove(ArrayList<Integer> cellsList) {

        return 0;
    }

}
