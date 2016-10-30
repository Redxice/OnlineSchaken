/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineschaken;

import java.util.Timer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author redxice
 */
public class OnlineSchaken extends Application
{

    public Game game;
    Label timerBlack;
    Label timerWhite;
    Timer timer;
    VBox vb;
    Button drawButton;
    
    @Override
    public void start(Stage primaryStage)
    {
        Player p1 = new Player("White", "ww", 0);
        Player p2 = new Player("Black", "ww", 0);
        Group root = new Group();
        game = new Game(p1, p2, this);
        game.board.createContent();
        game.setPieces();
        game.board.createContent2();
        Scene scene = new Scene(root);
        HBox hb = new HBox(20);
        timerWhite = new Label("30:00");
        timerBlack = new Label("30:00");
        drawButton = new Button("draw");
        drawButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e)
            {
                game.setPlayer1Draw(true);
                game.setPlayer2Draw(true);
                game.timer.cancel();
                JOptionPane.showMessageDialog(null, "The game is a draw");
            }
        });
        vb = new VBox();
        vb.getChildren().addAll(timerWhite, timerBlack, drawButton);
        hb.getChildren().addAll(game.board.getRoot(), vb);
        hb.setAlignment(Pos.CENTER);
        vb.setAlignment(Pos.CENTER);
        root.getChildren().add(hb);
        primaryStage.setTitle("OnlineSchaken");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void update()
    {
        Platform.runLater(new Runnable() {
        @Override public void run() {
        timerWhite.setText(game.resterend(1));
        timerBlack.setText(game.resterend(2));
      }
    });
    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }

}
