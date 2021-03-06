/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Server.ClientApp;
import Shared.IrmiClient;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import onlineschaken.Player;

/**
 * FXML Controller class
 *
 * @author redxice
 */
public class RegisterController implements Initializable
{

    private ClientApp client;
    private IrmiClient iClient;
    private static final Logger LOGGER = Logger.getLogger(RegisterController.class.getName());
    /**
     * Initializes the controller class.
     */
    @FXML
    private Button Btn_Register;
    @FXML
    private TextField TxtField_Username;
    @FXML
    private TextField TxtField_Email;
    @FXML
    private PasswordField TxtField_Password;
    @FXML
    private PasswordField TxtField_RePassword;
    @FXML
    private Label WarningLabel_Username;
    @FXML
    private Label WarningLabel_Email;
    @FXML
    private Label WarningLabel_Password;
    @FXML
    private Label WarningLabel_RePassword;

    @FXML
    private void RegisterAccount(ActionEvent event)
    {
        WarningLabel_Username.setText(null);
        WarningLabel_Email.setText(null);
        WarningLabel_Password.setText(null);
        WarningLabel_RePassword.setText(null);
        if (CheckTextFields())
        {  
           
            try
            {
                iClient.insertPlayer(TxtField_Username.getText(), TxtField_Password.getText(), TxtField_Email.getText());
            } catch (RemoteException ex)
            {
                Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
            }

            try
            {
                Stage CurrentStage = (Stage) Btn_Register.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                LoginController controller = fxmlLoader.<LoginController>getController();
                controller.setClient(client);
                controller.setiClient(iClient);
                CurrentStage.close();
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex)
            {
                Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Checked of allen fields correct in zijn gevult.
     *
     * @return true wanneer alles correct is in gevult.
     */
    private boolean CheckTextFields()
    {
        boolean FieldsAreCorrect = true;
        if (TxtField_Username.getText().isEmpty())
        {
            WarningLabel_Username.setText("Plz insert a username");

            FieldsAreCorrect = false;
        } else if (!CheckTxtFieldUsername())
        {
            TxtField_Username.setText("");
            WarningLabel_Username.setText("Username already in use");
            FieldsAreCorrect = false;
        }
        if (!CheckTxtFieldEmail())
        {
            WarningLabel_Email.setText("Please insert a valid e-Mail");
            FieldsAreCorrect = false;
        }
        if (TxtField_Password.getText().isEmpty())
        {
            WarningLabel_Password.setText("Please insert a password");
            FieldsAreCorrect = false;

        }
        if (!CheckTxtFieldRePassword() && !TxtField_Password.getText().isEmpty())
        {
            FieldsAreCorrect = false;
        }

        return FieldsAreCorrect;
    }

    private boolean CheckTxtFieldUsername()
    { 
        Player player= null;
        try
        {
            player = iClient.selectPlayer(TxtField_Username.getText());
        } catch (RemoteException ex)
        {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (player != null)
        {
            if (player.getUsername().equals(TxtField_Username.getText()))
            {

                return false;
            }
        }

        return true;
    }

    /**
     *
     * @return
     */
    private boolean CheckTxtFieldRePassword()
    {
        if (TxtField_RePassword.getText().isEmpty())
        {
            WarningLabel_RePassword.setText("Please re enter your password");
            return false;
        } else if (!TxtField_RePassword.getText().matches(TxtField_Password.getText()))
        {
            WarningLabel_RePassword.setText("Passwords don't match");
            return false;
        }
        return true;
    }

    /**
     * Checked of het Email text field niet leeg is. Als hij niet leeg is wordt
     * er gekeken of het een valide e-mail adress is.Als dit valide is wordt er
     * true gereturned anders altijd false;
     *
     * @return
     */
    private boolean CheckTxtFieldEmail()
    {
        if (TxtField_Email.getText().isEmpty())
        {

            return false;
        } else
        {
            String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
            java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
            java.util.regex.Matcher m = p.matcher(TxtField_Email.getText());
            return m.matches();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }

    /**
     *
     * @param client
     */
    public void setClient(ClientApp client)
    {
        this.client = client;
    }

    void setiClient(IrmiClient Client)
    {
       this.iClient = Client;
    }

}
