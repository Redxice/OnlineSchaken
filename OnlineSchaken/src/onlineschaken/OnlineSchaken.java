/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineschaken;

import database.Database;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
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
        game = new Game(p1,p2);
        game.board.createContent();               
        game.setPieces();
        game.board.createContent2();
        game.board.getSections(1, 1).getPiece().move(game.board.getSections(1, 2));
        game.board.drawSpecificPieces(game.board.getSections(1, 1), game.board.getSections(1, 2));
        Scene scene = new Scene(game.board.getRoot()); 
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
