package tictactoe.computerAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ComputerAction implements ComputerActionInterface {

    @Override
    public int[] randomMove(ArrayList<Integer> cellsList) {

        Random rand = new Random();
        int randomIndex = rand.nextInt(cellsList.size());
        int randomNumber = cellsList.get(randomIndex);
        int[] rowCol = convertNumToRowCol(randomNumber);

        return rowCol;
    }

    @Override
    public int[] bestMove(char[][] gameLogic, char sign) {

        int[] bestValues = minimaxAlgorithm(gameLogic, true, sign);
        int[] rowCol = new int[]{bestValues[1], bestValues[2]};

        return rowCol;
    }

    private int[] minimaxAlgorithm(char[][] gameLogic, boolean isComputerMove, char sign) {

        char otherSign = generateOtherSign(sign);

        List<int[]> possibleMoves = findPossibleMoves(gameLogic);
        int numberOfPossibleMoves = countPossibleMoves(possibleMoves);

        int currentScore;
        int bestScore;
        int bestRow = -1;
        int bestCol = -1;
        if (isComputerMove) {
            bestScore = Integer.MIN_VALUE; //computer is maximizing
        } else {
            bestScore = Integer.MAX_VALUE; //player is minimizing
        }

        String gameState = checkGameState(gameLogic);
        if (gameState.equals("Player X won!") || gameState.equals("Player O won!")) {
            bestScore = evaluateScore(numberOfPossibleMoves, isComputerMove);
            //System.out.println(bestScore +" evaluation");
        } else if (thereIsNoPossibleMoves(possibleMoves)) {
            bestScore = 0;
            //System.out.println(bestScore);
        } else {
            for (int move = 0; move < numberOfPossibleMoves; move++) {
                int row = possibleMoves.get(move)[0];
                int col = possibleMoves.get(move)[1];
                gameLogic[row][col] = sign;

                currentScore = minimaxAlgorithm(gameLogic, !isComputerMove, otherSign)[0];
                if (isComputerMove) {
                    if (currentScore > bestScore) {
                        bestScore = currentScore;
                        bestRow = row;
                        bestCol = col;
                        //System.out.println(bestScore);
                    }
                } else if (!isComputerMove) {
                    if (currentScore < bestScore) {
                        bestScore = currentScore;
                        bestRow = row;
                        bestCol = col;
                        //System.out.println(bestScore);
                    }
                }
                //undo move
                gameLogic[row][col] = 0;
            }
        }
        return new int[]{bestScore, bestRow, bestCol};
    }

    @Override
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

        return message;
    }

    private List<int[]> findPossibleMoves(char[][] gameLogic) {
        List<int[]> possibleMoves = new ArrayList<int[]>();

        for (int row = 0; row < gameLogic.length; row++) {
            for (int col = 0; col < gameLogic.length; col++) {
                if (gameLogic[row][col] == 0) {
                    possibleMoves.add(new int[]{row, col});
                }
            }
        }
        return possibleMoves;
    }

    private boolean thereIsNoPossibleMoves(List<int[]> possibleMoves) {
        if (possibleMoves.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    private int countPossibleMoves(List<int[]> possibleMoves) {
        int numberOfMoves = possibleMoves.size();
        return numberOfMoves;
    }

    private int evaluateScore(int numberOfPossibleMoves, boolean isComputerMove) {
        boolean wasComputerMove = !isComputerMove; //we are checking previous move
        int score = 1 + numberOfPossibleMoves;
        if (wasComputerMove) {
            return score;
        } else {
            return score * (-1);
        }
    }

    private char generateOtherSign(char sign) {
        char otherSign;
        if (sign == 'X') {
            otherSign = 'O';
        } else {
            otherSign = 'X';
        }
        return otherSign;
    }

    private int[] convertNumToRowCol(int number) {
        int[] rowCol = new int[2];
        int row = 0;
        int col = 0;

        if (number < 3) {
            row = 0;
            if (number == 0) {
                col = 0;
            } else if (number == 1) {
                col = 1;
            } else if (number == 2) {
                col = 2;
            }
        } else if (number >= 3 && number < 6) {
            row = 1;
            if (number == 3) {
                col = 0;
            } else if (number == 4) {
                col = 1;
            } else if (number == 5) {
                col = 2;
            }
        } else if (number >= 6) {
            row = 2;
            if (number == 6) {
                col = 0;
            } else if (number == 7) {
                col = 1;
            } else if (number == 8) {
                col = 2;
            }
        }
        rowCol[0] = row;
        rowCol[1] = col;

        return rowCol;
    }
}
