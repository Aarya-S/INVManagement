package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import resources.Connect;

import java.io.IOException;
import java.sql.*;

public class registerController extends Connect {
    @FXML
    public Label registeredLabel=null;
    @FXML
    public Button loginReturnButton;
    @FXML
    public Button registerButton2;
    @FXML
    public TextField registerUname;
    @FXML
    public PasswordField registerPassword;
    @FXML
    public TextField registerName;
    @FXML
    public Label registeredLabel2 =null;

    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pst = null;


    public void returnToLogin(ActionEvent event) throws IOException {
        Parent loginParent = FXMLLoader.load(getClass().getResource("/resources/loginPage.fxml"));
        Scene loginOpen = new Scene(loginParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(loginOpen);
        window.show();

    }

    public void registration() throws SQLException {

        String sql = "Insert into login(Name,userid,password) values('" + registerName.getText() + "', '" + registerUname.getText() + "','" +  registerPassword.getText()+"')";
        pst = con.prepareStatement(sql);
        pst.execute();
        registeredLabel.setText("You have successfully registered.");
        registeredLabel2.setText("");

    }

    public void registerPress2(ActionEvent event) throws SQLException {

        con = Connect.ConnectDB();
        if(registerName.getText().equals("")){
            registeredLabel.setText("Please enter name");
            return;
        }

        if(registerUname.getText().equals("")){
            registeredLabel.setText("Please set username");
            return;
        }

        if(registerPassword.getText().equals("")){
            registeredLabel.setText("Please set a password");
            return;
        }

        Statement stmt;
        stmt = con.createStatement();
        String sql1 = "Select userid from login where userid = '" +registerUname.getText()+"'";
        rs = stmt.executeQuery(sql1);
        if(rs.next()){
            registeredLabel2.setText("Username already exists");
            registerUname.setText("");
        }
        registration();

    }

}
