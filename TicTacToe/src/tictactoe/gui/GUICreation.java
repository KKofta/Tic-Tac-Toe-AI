package tictactoe.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUICreation extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        BorderPane layout = new BorderPane();

        VBox title = createTitle();
        layout.setTop(title);

        GridPane game = createGameArea();
        layout.setCenter(game);

        VBox menu = createMenu();
        layout.setRight(menu);

        Scene scene = new Scene(layout, 600, 600);
        scene.getStylesheets().add(getClass().getResource("Styles.css").toExternalForm());

        stage.setTitle("TicTacToe with AI");
        stage.setScene(scene);
        stage.show();
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
        GridPane game = new GridPane();
        game.setGridLinesVisible(true);
        game.setPadding(new Insets(10, 15, 15, 15));

        final int numCols = 3;
        final int numRows = 3;
        for (int i = 0; i < numCols; i++) {
            ColumnConstraints colConst = new ColumnConstraints(100);
            colConst.setPercentWidth(100.0 / numCols);
            game.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / numRows);
            game.getRowConstraints().add(rowConst);
        }

        return game;
    }

    private VBox createMenu() {
        VBox menu = new VBox(40);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(10, 15, 15, 15));

        Button aloneButton = new Button("Play alone");
        Button randomButton = new Button("Play with random");
        Button aiButton = new Button("Play and lose");
        Button exitButton = new Button("Exit");

        menu.setMargin(exitButton, new Insets(200, 0, 0, 0));
        menu.getChildren().addAll(aloneButton, randomButton, aiButton, exitButton);

        return menu;
    }

}
