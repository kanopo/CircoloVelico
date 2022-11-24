package me.ollari.circolovelicogui.controllers.employeeFunctionality;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import me.ollari.circolovelicogui.Ip;
import me.ollari.circolovelicogui.controllers.homeHandlers.EmployeeHome;
import me.ollari.circolovelicogui.rest.Boat;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Objects;

public class DelBoat {
    public Long employeeId;
    public TableView<Boat> boatTable;
    public TableColumn<Boat, Integer> id;
    public TableColumn<Boat, String> name;
    public TableColumn<Boat, Double> length;

    private Stage stage;
    private Scene scene;
    private Parent parent;

    public void setTable() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create("http://" + Ip.getIp() + ":8080/get/boat"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            // the user is there

            // parse JSON
            ObjectMapper mapper = new ObjectMapper();
            List<Boat> boats = mapper.readValue(response.body(), new TypeReference<List<Boat>>() {
            });


            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            length.setCellValueFactory(new PropertyValueFactory<>("length"));

            ObservableList<Boat> boatObservableList = FXCollections.observableArrayList();

            boatObservableList.addAll(boats);

            boatTable.setItems(boatObservableList);


        } else {
            // 404 user not present
            System.out.println("Problema di connessione");
        }

    }

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

    public void delete(ActionEvent actionEvent) {
        Long idToDelete;

        if (!boatTable.getSelectionModel().isEmpty()) {


            try {
                idToDelete = boatTable.getSelectionModel().getSelectedItem().getId();
                System.out.println(idToDelete);

                HttpClient clientDel = HttpClient.newHttpClient();
                HttpRequest requestDel = HttpRequest.newBuilder()
                        .DELETE()
                        .header("accept", "application/json")
                        .uri(URI.create("http://" + Ip.getIp() + ":8080/delete/boat/boat-id/" + idToDelete))
                        .build();

                HttpResponse<String> responseDel = clientDel.send(requestDel, HttpResponse.BodyHandlers.ofString());

                if (responseDel.statusCode() == 200) {
                    // the user is there
                    System.out.println(responseDel.statusCode());

                    setTable();


                } else {
                    // 404 user not present
                    System.out.println("Problema di connessione");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
