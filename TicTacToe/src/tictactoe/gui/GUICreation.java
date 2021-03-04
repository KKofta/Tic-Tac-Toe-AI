package tictactoe.gui;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tictactoe.computerMove.ComputerMove;
import tictactoe.computerMove.ComputerMoveInterface;

public class GUICreation extends Application {

    private final Stage window = new Stage();
    private BorderPane layout;
    private GridPane game;

    private boolean blockX = true;
    private boolean blockO = true;
    private boolean gameFinished = false;

    private boolean randomMode = false;
    private boolean aiMode = false;

    char[][] gameLogic = new char[3][3];
    ArrayList<Integer> cellsList = new ArrayList<>();

    @Override
    public void start(Stage stage) throws Exception {
        stage = window;

        layout = new BorderPane();

        VBox title = createTitle();
        layout.setTop(title);

        game = createGameArea();
        layout.setCenter(game);

        VBox menu = createMenu();
        layout.setRight(menu);

        Scene scene = new Scene(layout, 650, 600);
        scene.getStylesheets().add(getClass().getResource("Styles.css").toExternalForm());

        window.setTitle("TicTacToe with AI");
        window.setScene(scene);
        window.show();
    }

    private VBox createTitle() {
        VBox title = new VBox();
        title.setAlignment(Pos.CENTER);
        title.setPadding(new Insets(10, 0, 0, 0));

        Text titleText = new Text("TicTacToe with AI");
        titleText.setFont(Font.font("Verdana", 30));
        titleText.setFill(Color.WHITESMOKE);

        title.getChildren().addAll(titleText);

        return title;
    }

    private VBox createMenu() {
        VBox menu = new VBox(20);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(10, 15, 15, 15));

        Button infoButton = new Button("Information");

        Button aloneButton = new Button("Play alone");
        menu.setMargin(aloneButton, new Insets(100, 0, 0, 0));
        aloneButton.setId("alone-button");
        setAloneButtonAction(aloneButton);

        Label randomLabel = new Label("Play with random:");
        Button randomFirstButton = new Button("Go first");
        randomFirstButton.setId("randfirst-button");
        setRandomFirstButtonAction(randomFirstButton);

        Button randomSecondButton = new Button("Go second");
        randomSecondButton.setId("randsecond-button");
        setRandomSecondButtonAction(randomSecondButton);

        HBox randomBox = new HBox(10);
        randomBox.getChildren().addAll(randomFirstButton, randomSecondButton);

        Label aiLabel = new Label("Play with AI:");
        Button aiFirstButton = new Button("Go first");
        aiFirstButton.setId("aifirst-button");
        setAiFirstButtonAction(aiFirstButton);

        Button aiSecondButton = new Button("Go second");
        aiSecondButton.setId("aisecond-button");
        setAiSecondButtonAciton(aiSecondButton);

        HBox aiBox = new HBox(10);
        aiBox.getChildren().addAll(aiFirstButton, aiSecondButton);

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> window.close());

        menu.setMargin(exitButton, new Insets(100, 0, 0, 0));
        menu.getChildren().addAll(infoButton, aloneButton, randomLabel, randomBox, aiLabel, aiBox, exitButton);

        return menu;
    }

    private GridPane createGameArea() {

        final int numCols = 3;
        final int numRows = 3;
        final int gameSize = 450;
        final int cellSize = gameSize / 3;

        GridPane game = new GridPane();

        game.setMinSize(gameSize, gameSize);
        game.setPadding(new Insets(10, 15, 15, 15));

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                Rectangle rec = new Rectangle(cellSize, cellSize);
                GridPane.setRowIndex(rec, i);
                GridPane.setColumnIndex(rec, j);
                rec.setFill(Color.TRANSPARENT);
                rec.setStroke(Color.WHITESMOKE);

                Text recText = new Text();
                GridPane.setRowIndex(recText, i);
                GridPane.setColumnIndex(recText, j);

                setCellAction(rec, recText);

                game.getChildren().addAll(rec, recText);
            }
        }

        return game;
    }

    private void setCellAction(Rectangle rec, Text recText) {

        recText.setFont(Font.font(85));
        recText.setFill(Color.WHITESMOKE);

        rec.setOnMouseClicked(event -> {

            if (event.getButton() == MouseButton.PRIMARY && blockX == false && recText.getText().isEmpty() && !gameFinished) {
                recText.setText("  X");
                int row = GridPane.getRowIndex(rec);
                int col = GridPane.getColumnIndex(rec);

                updateGameState(row, col, 'X');
                checkGameState();

                if (randomMode && !gameFinished) {
                    System.out.println(cellsList);

                    findRandomPlace("  O");

                    checkGameState();
                    blockO = true;

                } else if (aiMode && !gameFinished) {
                    System.out.println(cellsList);

                    findBestPlace("  O");

                    checkGameState();
                    blockO = true;

                } else if (!gameFinished) {
                    blockX = true;
                    blockO = false;
                }

            } else if (event.getButton() == MouseButton.SECONDARY && blockO == false && recText.getText().isEmpty() && !gameFinished) {
                recText.setText("  O");
                int row = GridPane.getRowIndex(rec);
                int col = GridPane.getColumnIndex(rec);

                updateGameState(row, col, 'O');
                checkGameState();

                if (randomMode && !gameFinished) {
                    System.out.println(cellsList);

                    findRandomPlace("  X");

                    checkGameState();
                    blockX = true;

                } else if (aiMode && !gameFinished) {
                    System.out.println(cellsList);

                    checkGameState();
                    blockX = true;

                } else {
                    blockX = false;
                    blockO = true;
                }
                //System.out.println(i + "," + j);
                //System.out.println(Arrays.deepToString(gameLogic));
            }
        });
    }

    private void displayMessage(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText("Game over!");
        alert.setContentText(message);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("Styles.css").toExternalForm());

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                alert.close();
            }
        });
    }

    private void displayMove(int rowIndex, int colIndex, String text) {

        Text recText = new Text();
        recText.setFont(Font.font(85));
        recText.setFill(Color.WHITESMOKE);
        recText.setText(text);
        GridPane.setRowIndex(recText, rowIndex);
        GridPane.setColumnIndex(recText, colIndex);

        game.getChildren().add(recText);
    }

    /*private void checkGameState1() {

        boolean noTie = false;
        //check rows
        if (noTie == false) {
            for (int row = 0; row < gameLogic.length; row++) {
                if (gameLogic[row][0] != 0 && gameLogic[row][0] == gameLogic[row][1] && gameLogic[row][1] == gameLogic[row][2]) {
                    if (gameLogic[row][0] == 'X') {
                        displayMessage("Player X won!");
                    } else {
                        displayMessage("Player O won!");
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
                        displayMessage("Player X won!");
                    } else {
                        displayMessage("Player O won!");
                    }
                    noTie = true;
                }
            }
        }

        //check diagonals
        if (noTie == false) {
            if (gameLogic[0][0] != 0 && gameLogic[0][0] == gameLogic[1][1] && gameLogic[1][1] == gameLogic[2][2]) {
                if (gameLogic[0][0] == 'X') {
                    displayMessage("Player X won!");
                } else {
                    displayMessage("Player O won!");
                }
                noTie = true;
            }
        }

        if (noTie == false) {
            if (gameLogic[2][0] != 0 && gameLogic[2][0] == gameLogic[1][1] && gameLogic[1][1] == gameLogic[0][2]) {
                if (gameLogic[2][0] == 'X') {
                    displayMessage("Player X won!");
                } else {
                    displayMessage("Player O won!");
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
                displayMessage("There was a tie!");
                noTie = true;
            }
        }

        if (noTie) {
            gameFinished = true;
        }
    }*/
    private void checkGameState() {
        ComputerMove gameState = new ComputerMove();
        String message = gameState.checkGameState(gameLogic);

        if (message != "Game continues") {
            displayMessage(message);
            gameFinished = true;
        }
    }

    private void updateGameState(int row, int col, char value) {
        gameLogic[row][col] = value;

        if (row == 0) {
            cellsList.remove(new Integer(col));
        } else if (row == 1) {
            cellsList.remove(new Integer(col + 3));
        } else if (row == 2) {
            cellsList.remove(new Integer(col + 6));
        }
    }

    private int[] convertToRowCol(int number) {
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

    private void findRandomPlace(String text) {
        if (!cellsList.isEmpty()) {

            ComputerMoveInterface computerMove = new ComputerMove();
            int randomNumber = computerMove.randomMove(cellsList);

            int[] rowCol = convertToRowCol(randomNumber);
            int row = rowCol[0];
            int col = rowCol[1];

            System.out.println("Row: " + row + " Col: " + col);

            updateGameState(row, col, text.charAt(text.length() - 1));
            displayMove(row, col, text);
        }
    }

    private void findBestPlace(String text) {

        if (cellsList.isEmpty()) {
            ComputerMoveInterface computerMove = new ComputerMove();
            computerMove.bestMove(cellsList);

        }

    }

    private void clearOldGame() {

        blockX = false;
        blockO = false;
        gameFinished = false;
        randomMode = false;
        aiMode = false;
        game = createGameArea();
        layout.setCenter(game);

        for (int row = 0; row < gameLogic.length; row++) {
            for (int col = 0; col < gameLogic.length; col++) {
                gameLogic[row][col] = 0;
            }
        }

        final int numberOfCells = gameLogic.length * gameLogic.length;
        cellsList.clear();
        for (int i = 0; i < numberOfCells; i++) {
            cellsList.add(i);
        }
        System.out.println(cellsList);
    }

    private void setAloneButtonAction(Button button) {

        button.setOnAction(event -> {
            clearOldGame();
        });
    }

    private void setRandomFirstButtonAction(Button randomFirstButton) {

        randomFirstButton.setOnAction(event -> {
            clearOldGame();
            randomMode = true;
        });

    }

    private void setRandomSecondButtonAction(Button randomSecondButton) {

        randomSecondButton.setOnAction(event -> {
            clearOldGame();
            randomMode = true;

            System.out.println(cellsList);

            findRandomPlace("  O");

            blockO = true;
        });
    }

    private void setAiFirstButtonAction(Button aiFirstButton) {

        aiFirstButton.setOnAction(event -> {
            clearOldGame();
            aiMode = true;
        });

    }

    private void setAiSecondButtonAciton(Button aiSecondButton) {

        aiSecondButton.setOnAction(event -> {
            clearOldGame();
            aiMode = true;

            System.out.println(cellsList);

            findRandomPlace("  O");

            blockO = true;
        });

    }
}
