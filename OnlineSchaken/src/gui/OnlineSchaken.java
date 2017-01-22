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
    private static int count = 1;
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
        {   count++;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = (Parent)fxmlLoader.load();
            LoginController controller= fxmlLoader.<LoginController>getController();
            //System.out.println(getClass().getResource("gui/Login.fxml"));
            Scene scene = new Scene(root);

            primaryStage.setTitle("GameLobby");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e)
        {
            Logger.getLogger(OnlineSchaken.class.getName()).log(Level.SEVERE, null, e);
            //System.out.println(e.getMessage());
        }

        /*
        Player p1 = new Player("White", "ww", 0);
        Player p2 = new Player("Black", "ww", 0);
        Group root = new Group();
        game = new Game(p1, p2, this);
        game.getBoard().createContent();
        game.setPieces();
        game.getBoard().createContent2();
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
                game.getTimer().cancel();
                int exit = JOptionPane.showOptionDialog(null, "The game is a draw", "Draw", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
                if (exit == 0)
                {
                    Platform.exit();
                }
            }
        });
        vb = new VBox();
        vb.getChildren().addAll(timerWhite, timerBlack, drawButton);
        hb.getChildren().addAll(game.getBoard().getRoot(), vb);
        hb.setAlignment(Pos.CENTER);
        vb.setAlignment(Pos.CENTER);
        root.getChildren().add(hb);
        primaryStage.setTitle("OnlineSchaken");
        primaryStage.setScene(scene);
        primaryStage.show();*/
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
        try
        {/*
            Database db = new Database();
            db.init();
            //db.insertPlayer("Sander", "WW123", "sander@test.com");
            db.selectPlayer("Sander");
            db.closeConnection();*/
        } catch (Exception e)
        {
            LOGGER.log(Level.FINE, e.getMessage(), e);
        }
        launch(args);
    }
}
