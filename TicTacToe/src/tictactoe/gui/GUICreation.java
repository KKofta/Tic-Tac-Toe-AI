package tictactoe.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    
    boolean blockX = false;
    boolean blockO = false;

    @Override
    public void start(Stage stage) throws Exception {
        stage = window;

        BorderPane layout = new BorderPane();

        VBox title = createTitle();
        layout.setTop(title);

        GridPane game = createGameArea();
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

                Text text = new Text();
                GridPane.setRowIndex(text, i);
                GridPane.setColumnIndex(text, j);

                setCellAction(rec, text);

                game.getChildren().addAll(rec, text);
            }
        }
        return game;
    }

    private void setCellAction(Rectangle rec, Text text) {

        text.setFont(Font.font(85));
        text.setFill(Color.WHITESMOKE);

        rec.setOnMouseClicked(event -> {

            if (event.getButton() == MouseButton.PRIMARY && blockX == false) {
                text.setText("  X");
                blockX = true;
                blockO = false;
                
            } else if (event.getButton() == MouseButton.SECONDARY && blockO == false) {
                text.setText("  O");
                blockO = true;
                blockX = false;
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
    
    
    
}
