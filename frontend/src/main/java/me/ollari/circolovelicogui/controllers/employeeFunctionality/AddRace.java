package me.ollari.circolovelicogui.controllers.employeeFunctionality;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
            /*
            TODO:
                - gestire il float con un segnale nel client grafico
                - per il momento il programma continua solo se i due duble vengono parsati, non c'è un check sulla data
             */
        }


        System.out.println(raceName);
        System.out.println(participationPrice);
        System.out.println(raceAward);
        System.out.println(raceDate);

        String body = "{\"name\":\"" + raceName + "\",\"participationPrice\":\"" + participationPrice + "\",\"award\":\"" + raceAward + "\"," +
                "\"date\":\"" + raceDate + "\"}";

        System.out.println(body);

        HttpClient clientPost = HttpClient.newHttpClient();
        HttpRequest requestPost = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .header("Content-Type", "application/json")
                .uri(URI.create("http://" + Ip.getIp() + ":8080/post/race"))
                .build();

        HttpResponse<String> responsePost = clientPost.send(requestPost, HttpResponse.BodyHandlers.ofString());

        System.out.println(responsePost.statusCode());

    }
}
