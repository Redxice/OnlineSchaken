/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.SubScene;


import onlineschaken.Game;
import onlineschaken.Player;

/**
 * FXML Controller class
 *
 * @author redxice
 */
public class IngameController implements Initializable
{
    @FXML 
    private SubScene GameBoard;

    /**
     * Initializes the controller class.
     */
    public void DrawBoard(){
        Player p1 = new Player("White", "ww", 0);
        Player p2 = new Player("Black", "ww", 0);
        Group root = new Group();
        Game game = new Game(p1, p2);
        GameBoard.setRoot(root);
        game.getBoard().createContent();
        game.setPieces();
        game.getBoard().createContent2();
        root.getChildren().add(game.getBoard().getRoot());
    }
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        
  
    }    
    
}
