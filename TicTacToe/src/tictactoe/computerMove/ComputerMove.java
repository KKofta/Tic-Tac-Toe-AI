package tictactoe.computerMove;

import java.util.ArrayList;
import java.util.Random;

public class ComputerMove implements ComputerMoveInterface {

    private boolean localGameFinished = false;
    char[][] localGameLogic = new char[3][3];
    
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

    private int minimaxAlgorithm(char[][] gameLogic, boolean isComputerMove) {

        
        
        int bestScore;
        if (isComputerMove) {
            bestScore = Integer.MIN_VALUE; //computer is maximizing
        } else {
            bestScore = Integer.MAX_VALUE; //player is minimizing
        }

        return 0;
    }

    public String checkGameState(char[][] gameLogic) {

        String message = "Game continues";
        boolean noTie = false;
        //check rows
        if (noTie == false) {
            for (int row = 0; row < gameLogic.length; row++) {
                if (gameLogic[row][0] != 0 && gameLogic[row][0] == gameLogic[row][1] && gameLogic[row][1] == gameLogic[row][2]) {
                    if (gameLogic[row][0] == 'X') {
                        message = "Player X won!";
                    } else {
                        message = "Player O won!";
                    }
                    noTie = true;
                }
            }
        }

        //check columns
        if (noTie == false) {
            for (int col = 0; col < gameLogic.length; col++) {
                if (gameLogic[0][col] != 0 && gameLogic[0][col] == gameLogic[1][col] && gameLogic[1][col] == gameLogic[2][col]) {
                    if (gameLogic[0][col] == 'X') {
                        message = "Player X won!";
                    } else {
                        message = "Player O won!";
                    }
                    noTie = true;
                }
            }
        }

        //check diagonals
        if (noTie == false) {
            if (gameLogic[0][0] != 0 && gameLogic[0][0] == gameLogic[1][1] && gameLogic[1][1] == gameLogic[2][2]) {
                if (gameLogic[0][0] == 'X') {
                    message = "Player X won!";
                } else {
                    message = "Player O won!";
                }
                noTie = true;
            }
        }

        if (noTie == false) {
            if (gameLogic[2][0] != 0 && gameLogic[2][0] == gameLogic[1][1] && gameLogic[1][1] == gameLogic[0][2]) {
                if (gameLogic[2][0] == 'X') {
                    message = "Player X won!";
                } else {
                    message = "Player O won!";
                }
                noTie = true;
            }
        }

        //check if there is a tie
        if (noTie == false) {
            int checkForTie = 0;
            for (int row = 0; row < gameLogic.length; row++) {
                for (int col = 0; col < gameLogic.length; col++) {
                    if (gameLogic[row][col] != 0) {
                        checkForTie++;
                    }
                }
            }
            if (checkForTie == 9) {
                message = "There was a tie!";
                noTie = true;
            }
        }

        if (noTie) {
            localGameFinished = true;
        }

        return message;
    }
    
    private void checkPossibleMoves(){
        
    }
    
}
