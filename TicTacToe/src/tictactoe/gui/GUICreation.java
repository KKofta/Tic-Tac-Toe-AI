package tictactoe.gui;

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

public class GUICreation extends Application {

    private final Stage window = new Stage();
    private BorderPane layout;
    private GridPane game;

    private boolean blockX = false;
    private boolean blockO = false;

    char[][] gameLogic = new char[3][3];

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

            if (event.getButton() == MouseButton.PRIMARY && blockX == false && recText.getText().isEmpty()) {
                recText.setText("  X");
                int i = GridPane.getRowIndex(rec);
                int j = GridPane.getColumnIndex(rec);
                gameLogic[i][j] = 'X';
                //System.out.println(i + "," + j);
                //System.out.println(Arrays.deepToString(gameLogic));
                blockX = true;
                blockO = false;

                checkGameState();

            } else if (event.getButton() == MouseButton.SECONDARY && blockO == false && recText.getText().isEmpty()) {
                recText.setText("  O");
                int i = GridPane.getRowIndex(rec);
                int j = GridPane.getColumnIndex(rec);
                gameLogic[i][j] = 'O';
                //System.out.println(i + "," + j);
                //System.out.println(Arrays.deepToString(gameLogic));
                blockO = true;
                blockX = false;

                checkGameState();

            }
        });
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
        Button randomSecondButton = new Button("Go second");
        randomSecondButton.setId("randsecond-button");
        HBox randomBox = new HBox(10);
        randomBox.getChildren().addAll(randomFirstButton, randomSecondButton);

        Label aiLabel = new Label("Play with AI:");
        Button aiFirstButton = new Button("Go first");
        aiFirstButton.setId("aifirst-button");
        Button aiSecondButton = new Button("Go second");
        aiSecondButton.setId("aisecond-button");
        HBox aiBox = new HBox(10);
        aiBox.getChildren().addAll(aiFirstButton, aiSecondButton);

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> window.close());

        menu.setMargin(exitButton, new Insets(100, 0, 0, 0));
        menu.getChildren().addAll(infoButton, aloneButton, randomLabel, randomBox, aiLabel, aiBox, exitButton);

        return menu;
    }

    private void setAloneButtonAction(Button button) {

        button.setOnAction(event -> {
            clearOldGame();
        });
    }

    private void clearOldGame() {

        blockX = false;
        blockO = false;
        game = createGameArea();
        layout.setCenter(game);

        for (int row = 0; row < gameLogic.length; row++) {
            for (int col = 0; col < gameLogic.length; col++) {
                gameLogic[row][col] = 0;
            }
        }
    }

    private void checkGameState() {

        //check rows
        for (int row = 0; row < gameLogic.length; row++) {
            if (gameLogic[row][0] != 0 && gameLogic[row][0] == gameLogic[row][1] && gameLogic[row][1] == gameLogic[row][2]) {
                if (gameLogic[row][0] == 'X') {
                    displayMessage("Player X won!");
                    blockX = true;
                    blockO = true;
                } else {
                    displayMessage("Player O won!");
                    blockX = true;
                    blockO = true;
                }
            }
        }

        //check columns
        for (int col = 0; col < gameLogic.length; col++) {
            if (gameLogic[0][col] != 0 && gameLogic[0][col] == gameLogic[1][col] && gameLogic[1][col] == gameLogic[2][col]) {
                if (gameLogic[0][col] == 'X') {
                    displayMessage("Player X won!");
                    blockX = true;
                    blockO = true;
                } else {
                    displayMessage("Player O won!");
                    blockX = true;
                    blockO = true;
                }
            }
        }

        //check diagonals
        if (gameLogic[0][0] != 0 && gameLogic[0][0] == gameLogic[1][1] && gameLogic[1][1] == gameLogic[2][2]) {
            if (gameLogic[0][0] == 'X') {
                displayMessage("Player X won!");
                blockX = true;
                blockO = true;
            } else {
                displayMessage("Player O won!");
                blockX = true;
                blockO = true;
            }
        }

        if (gameLogic[2][0] != 0 && gameLogic[2][0] == gameLogic[1][1] && gameLogic[1][1] == gameLogic[0][2]) {
            if (gameLogic[2][0] == 'X') {
                displayMessage("Player X won!");
                blockX = true;
                blockO = true;
            } else {
                displayMessage("Player O won!");
                blockX = true;
                blockO = true;
            }
        }

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

}
