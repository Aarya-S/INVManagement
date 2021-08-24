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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import resources.Connect;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class viewControllerAdmin implements Initializable{


    public Button addButton;
    public Button deleteButton;
    public Button logoutButton;
    public TableColumn<ModelTable, Integer> itemIDcol;
    public TableColumn<ModelTable, String> itemNameCol;
    public TableColumn<ModelTable, Integer> quantityCol;
    public TableColumn<ModelTable, String> supplierCol;
    public TableView<ModelTable> tableMain;
    public TextField txt_name;
    public TextField txt_quantity;
    public TextField txt_supplier;
    public TextField txt_id;
    public Button refreshButton;
    public Label labelSuccess;
    public Button updateButton;

    ObservableList<ModelTable> oblist = FXCollections.observableArrayList();
    int index = -1;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        Connection con2 = Connect.ConnectDB();
        try {
            ResultSet rs1 = con2.createStatement().executeQuery("select * from inventory");

            while (rs1.next()) {
                oblist.add(new ModelTable((rs1.getString("id")), (rs1.getString("name")), (rs1.getString("quantity")), (rs1.getString("supplier"))));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        itemIDcol.setCellValueFactory(new PropertyValueFactory<ModelTable, Integer>("id"));
        itemNameCol.setCellValueFactory(new PropertyValueFactory<ModelTable, String>("name"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<ModelTable, Integer>("quantity"));
        supplierCol.setCellValueFactory(new PropertyValueFactory<ModelTable, String>("supplier"));

        tableMain.setItems(oblist);

    }



    public void logoutButtonPress(ActionEvent event) throws IOException {
        Parent logoutParent = FXMLLoader.load(getClass().getResource("/resources/loginPage.fxml"));
        Scene logout = new Scene(logoutParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(logout);
        window.show();

    }



    public void addButtonPress(ActionEvent event) {
        Connection conn = Connect.ConnectDB();
        String sql = "Insert into inventory (id,name,quantity,supplier)values(?,?,?,?)";

        try{
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, txt_id.getText());
            pst.setString(2, txt_name.getText());
            pst.setString(3, txt_quantity.getText());
            pst.setString(4,txt_supplier.getText());

            pst.execute();
            labelSuccess.setText("Record Added!!");

        }
        catch (Exception e){

        }

        }

    public void deleteButtonPress(ActionEvent event) {
        Connection conn = Connect.ConnectDB();
        String sqlDelete = "Delete from inventory where id = ?";


        try{
            PreparedStatement pst = conn.prepareStatement(sqlDelete);
            pst.setString(1, txt_id.getText());
            pst.execute();
            labelSuccess.setText("Record Deleted");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void getSelected(javafx.scene.input.MouseEvent mouseEvent) {
        index = tableMain.getSelectionModel().getSelectedIndex();
        if(index <= -1){

            return;
        }

        txt_id.setText(String.valueOf(itemIDcol.getCellData(index)));
        txt_name.setText(itemNameCol.getCellData(index));
        txt_quantity.setText(String.valueOf(quantityCol.getCellData(index)));
        txt_supplier.setText(supplierCol.getCellData(index));

    }

    public void updateButtonPress(ActionEvent event) {
        try {
            Connection con = Connect.ConnectDB();
            String value1 = txt_id.getText();
            String value2 = txt_name.getText();
            String value3 = txt_quantity.getText();
            String value4 = txt_supplier.getText();

            String sql1 = "Update inventory set id='"+value1+"', name='"+value2+"', quantity='"+value3+"', supplier='"+value4+"' where id='"+value1+"'";
            PreparedStatement pst = con.prepareStatement(sql1);
            pst.execute();
            labelSuccess.setText("Update Successful!!");

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void refreshButtonPress(ActionEvent event) {
       oblist.clear();
        Connection con2 = Connect.ConnectDB();
        try {
            ResultSet rs1 = con2.createStatement().executeQuery("select * from inventory");

                while (rs1.next()) {
                    oblist.add(new ModelTable((rs1.getString("id")), (rs1.getString("name")), (rs1.getString("quantity")), (rs1.getString("supplier"))));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        itemIDcol.setCellValueFactory(new PropertyValueFactory<ModelTable, Integer>("id"));
        itemNameCol.setCellValueFactory(new PropertyValueFactory<ModelTable, String>("name"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<ModelTable, Integer>("quantity"));
        supplierCol.setCellValueFactory(new PropertyValueFactory<ModelTable, String>("supplier"));

        tableMain.setItems(oblist);
        labelSuccess.setText("Data Refreshed.");

    }

}
