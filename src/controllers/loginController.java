package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import resources.Connect;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


public class loginController implements Initializable {
    public Button loginButton;
    public PasswordField passwordField;
    public TextField usernameField;
    public ComboBox comboBox;
    public Label loginPageLabel;

    @FXML
    Button registerButton;

    @FXML
    private void registerClicked() throws IOException {

        Parent registerParent = FXMLLoader.load(getClass().getResource("/resources/registration.fxml"));
        Scene registerOpen = new Scene(registerParent);

        Stage window = (Stage) registerButton.getScene().getWindow();

        window.setScene(registerOpen);
        window.show();

    }


    public void loginButtonClick(ActionEvent event) throws SQLException {
    try{
        Connection connection = Connect.ConnectDB();
        String query = "Select * from login where userid=? and password=? and status=?";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setString(1,usernameField.getText());
        pst.setString(2,passwordField.getText());
        pst.setString(3,String.valueOf(comboBox.getSelectionModel().getSelectedItem()));
        ResultSet rs = pst.executeQuery();
        if(rs.next()) {
            if (comboBox.getSelectionModel().getSelectedIndex() == 0) {
                Parent workViewParent;
                workViewParent = FXMLLoader.load(getClass().getResource("/resources/viewPageUser.fxml"));
                Scene workScene = new Scene(workViewParent);

                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(workScene);
                window.show();
            }
            if(comboBox.getSelectionModel().getSelectedIndex()==1){
                Parent workViewParent;
                workViewParent = FXMLLoader.load(getClass().getResource("/resources/viewPageAdmin.fxml"));
                Scene workScene = new Scene(workViewParent);

                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(workScene);
                window.show();
            }

        }
        else {
            loginPageLabel.setText("Invalid Login Credentials!");
        }
    }
    catch (Exception e){

    }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> comboList = FXCollections.observableArrayList("user","admin");
        comboBox.setItems(comboList);


    }
}
