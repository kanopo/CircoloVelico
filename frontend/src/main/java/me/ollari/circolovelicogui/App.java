package me.ollari.circolovelicogui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.ollari.circolovelicogui.controllers.DefaultController;

public class App extends Application {


    @Override
    public void start(Stage stage) throws Exception {


        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/default.fxml"));

        Parent root = fxmlLoader.load();
        DefaultController controller = fxmlLoader.getController();
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setTitle("Circolo nautico gui");

        stage.setScene(scene);

        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}
