/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineschaken;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 *
 * @author redxice
 */
public class OnlineSchaken extends Application {
    public Game game;
    
    
   
    @Override
    public void start(Stage primaryStage) {
        Player p1 = new Player("p1","ww",0);
        Player p2 = new Player("p2","ww",0); 
        Group root = new Group();
        game = new Game(p1,p2);
        game.board.createContent();               
        game.setPieces();
        game.board.createContent2();
        Scene scene = new Scene(root);
        HBox hb = new HBox(20);
        Label timerwhite = new Label("TIMER");
        Label timerblack = new Label("TIMER");
        VBox vb = new VBox();
        vb.getChildren().addAll(timerwhite, timerblack);
        hb.getChildren().addAll(game.board.getRoot(), vb);
        hb.setAlignment(Pos.CENTER);
        vb.setAlignment(Pos.CENTER);
        root.getChildren().add(hb);
        primaryStage.setTitle("OnlineSchaken");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
        //Database db = new Database();
        //db.init();
        //db.closeConnection();
    }
    
}
