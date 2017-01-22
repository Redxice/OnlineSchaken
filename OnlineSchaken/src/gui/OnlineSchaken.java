/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;


import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import onlineschaken.Game;

/**
 *
 * @author redxice
 */
public class OnlineSchaken extends Application
{
    private static final Logger LOGGER = Logger.getLogger(OnlineSchaken.class.getName());
    private Game game;
    private Label timerBlack;
    private Label timerWhite;
    private Timer timer;
    private VBox vb;
    private Button drawButton;
    
    @Override
    public void start(Stage primaryStage)
    {  
        try
        {   
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = (Parent)fxmlLoader.load();
            LoginController controller= fxmlLoader.<LoginController>getController();
            Scene scene = new Scene(root);

            primaryStage.setTitle("GameLobby");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e)
        {
            Logger.getLogger(OnlineSchaken.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * update the timerlabels
     */
    public void update()
    {
        Platform.runLater(() ->
        {
            timerWhite.setText(game.resterend(1));
            timerBlack.setText(game.resterend(2));
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
