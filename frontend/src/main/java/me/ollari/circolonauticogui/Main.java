package me.ollari.circolonauticogui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.ollari.circolonauticogui.controllers.DefaultController;

public class Main extends Application {
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
}
