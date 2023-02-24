package me.ollari.circolovelicogui.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import me.ollari.circolovelicogui.HttpFunctions;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Objects;



public class DefaultController {
    private HttpFunctions httpFunctions = new HttpFunctions();

    public VBox root;
    public Button login_member_btn;
    public Button login_employee_btn;

    private Stage stage;
    private Scene scene;


    public void login_member(ActionEvent actionEvent) {
        try {
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/memberLogin.fxml")));

            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            scene = new Scene(parent);

            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void login_employee(ActionEvent actionEvent) {

        HttpResponse<String> response = null;
        try {
            response = httpFunctions.GET("/employee/username/admin");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (response.statusCode() == 200) {
            // admin già registrato nel db
        }else {
            // admin assente dal db
            // Aggiungo admin

            String body = "{\"username\":\"" + "admin" + "\",\"password\":\"" + "password"+ "\"}";
            System.out.println(body);
            HttpResponse<String> responsePost = null;
            try {
                 responsePost = httpFunctions.POST("/employee", body);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(responsePost);
        }




        try {
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/employeeLogin.fxml")));

            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            scene = new Scene(parent);

            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
