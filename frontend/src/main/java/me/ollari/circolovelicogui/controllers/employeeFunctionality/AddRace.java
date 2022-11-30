package me.ollari.circolovelicogui.controllers.employeeFunctionality;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import me.ollari.circolovelicogui.HttpFunctions;
import me.ollari.circolovelicogui.Ip;
import me.ollari.circolovelicogui.controllers.homeHandlers.EmployeeHome;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

public class AddRace {
    public TextField name;
    public TextField price;
    public TextField award;
    public TextField date;

    private Stage stage;
    private Scene scene;
    private Parent parent;

    public Long employeeId;

    private HttpFunctions httpFunctions = new HttpFunctions();

    public void go_home(ActionEvent actionEvent) {
        try {
            // 5 righe di codice per passare i dati come l'id alla prossima scena
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/employeeHome.fxml"));
            parent = fxmlLoader.load();
            EmployeeHome employeeHome = fxmlLoader.getController();
            employeeHome.employeeId = employeeId;
            employeeHome.initializer();


            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            scene = new Scene(parent);

            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logout(ActionEvent actionEvent) {
        try {
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/default.fxml")));

            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            scene = new Scene(parent);

            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void add_race(ActionEvent actionEvent) throws IOException, InterruptedException {
        String raceName = "";
        double participationPrice = 0.0;
        double raceAward = 0.0;
        String raceDate = "";

        try {
            raceName = name.getText();
            participationPrice = Float.parseFloat(price.getText());
            raceAward = Float.parseFloat(award.getText());
            raceDate = date.getText();


        } catch (Error e) {
            e.printStackTrace();


            name.setStyle("-fx-text-box-border: red;");
            price.setStyle("-fx-text-box-border: red;");
            award.setStyle("-fx-text-box-border: red;");
            date.setStyle("-fx-text-box-border: red;");
        }


        //System.out.println(raceName);
        //System.out.println(participationPrice);
        //System.out.println(raceAward);
        //System.out.println(raceDate);

        String body = "{\"name\":\"" + raceName + "\",\"price\":\"" + participationPrice + "\",\"award\":\"" + raceAward + "\"," +
                "\"date\":\"" + raceDate + "\"}";

        HttpResponse<String> responsePost = httpFunctions.POST("/races", body);

        System.out.println(responsePost.statusCode());

        if (responsePost.statusCode() == 201) {
            // inserimento corretto
            name.clear();
            price.clear();
            award.clear();
            date.clear();

            name.setStyle("-fx-text-box-border: gray;");
            price.setStyle("-fx-text-box-border: gray;");
            award.setStyle("-fx-text-box-border: gray;");
            date.setStyle("-fx-text-box-border: gray;");
        }
        else
        {
            name.setStyle("-fx-text-box-border: red;");
            price.setStyle("-fx-text-box-border: red;");
            award.setStyle("-fx-text-box-border: red;");
            date.setStyle("-fx-text-box-border: red;");
        }

    }
}
