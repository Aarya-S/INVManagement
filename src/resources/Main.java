package resources;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent homeParent = FXMLLoader.load(getClass().getResource("loginPage.fxml"));
        Scene homeScene = new Scene(homeParent);

        primaryStage.setTitle("Inventory Management System");

        primaryStage.setScene(homeScene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        Connect con = new Connect();
        Connect.ConnectDB();



        launch(args);

    }
}
