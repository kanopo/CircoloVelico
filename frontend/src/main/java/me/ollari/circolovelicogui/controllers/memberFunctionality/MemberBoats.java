package me.ollari.circolovelicogui.controllers.memberFunctionality;

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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import me.ollari.circolovelicogui.HttpFunctions;
import me.ollari.circolovelicogui.Ip;
import me.ollari.circolovelicogui.controllers.homeHandlers.MemberHome;
import me.ollari.circolovelicogui.rest.Boat;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Objects;

public class MemberBoats {
    public TableView<Boat> boats_table;
    public TableColumn<Boat, String> name;
    public TableColumn<Boat, Double> length;

    public Long memberId;
    public TextField new_boat_length;
    public TextField new_boat_name;

    private Stage stage;
    private Scene scene;
    private Parent parent;

    private final HttpFunctions httpFunctions = new HttpFunctions();

    public void setTable() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create("http://" + Ip.getIp() + ":8080/boats/memberId/" + memberId + ""))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            // the user is there

            // parse JSON
            ObjectMapper mapper = new ObjectMapper();
            List<Boat> boats = mapper.readValue(response.body(), new TypeReference<List<Boat>>() {
            });


            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            length.setCellValueFactory(new PropertyValueFactory<>("length"));

            ObservableList<Boat> boatsToDisplay = FXCollections.observableArrayList();

            boats.forEach(boat -> {
                boatsToDisplay.add(boat);
                System.out.println(boat.getName());
            });

            boats_table.setItems(boatsToDisplay);


        } else {
            // 404 user not present
            System.out.println("Problema di connessione");
        }

    }

    public void delete_boat(ActionEvent actionEvent) throws IOException, InterruptedException {
        Long idToDelete = boats_table.getSelectionModel().getSelectedItem().getId();

        HttpClient clientDel = HttpClient.newHttpClient();
        HttpRequest requestDel = HttpRequest.newBuilder()
                .DELETE()
                .header("accept", "application/json")
                .uri(URI.create("http://" + Ip.getIp() + ":8080/boats/" + idToDelete + ""))
                .build();

        HttpResponse<String> responseDel = clientDel.send(requestDel, HttpResponse.BodyHandlers.ofString());
        System.out.println(responseDel);


        HttpClient clientGet = HttpClient.newHttpClient();
        HttpRequest requestGet = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create("http://" + Ip.getIp() + ":8080/boats/memberId/" + memberId + ""))
                .build();

        HttpResponse<String> responseGet = clientGet.send(requestGet, HttpResponse.BodyHandlers.ofString());

        if (responseGet.statusCode() == 200) {
            // the user is there

            // parse JSON
            ObjectMapper mapper = new ObjectMapper();
            List<Boat> boats = mapper.readValue(responseGet.body(), new TypeReference<List<Boat>>() {
            });


            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            length.setCellValueFactory(new PropertyValueFactory<>("length"));

            ObservableList<Boat> boatsToDisplay = FXCollections.observableArrayList();

            boats.forEach(boat -> {
                boatsToDisplay.add(boat);
                System.out.println(boat.getName());
            });

            boats_table.setItems(boatsToDisplay);


        } else {
            // 404 user not present
            System.out.println("Boat array is empty");
            boats_table.getItems().clear();
        }


    }

    //TODO: da gestire l'input dei dati
    public void add_boat(ActionEvent actionEvent) throws IOException, InterruptedException {
        String nameToAdd = new_boat_name.getText();
        Double lengthToAdd = Double.valueOf(new_boat_length.getText());

        String body = "{\"name\":\"" + nameToAdd + "\",\"length\":\"" + lengthToAdd + "\"}";

        HttpResponse<String> responsePut = httpFunctions.Put(":8080/boats/memberId/" + memberId, body);


        if (responsePut.statusCode() == 201) {
            HttpResponse<String> getRes = httpFunctions.Get(":8080/boats/memberId/" + memberId);

            if (getRes.statusCode() == 200) {
                ObjectMapper mapper1 = new ObjectMapper();
                List<Boat> boats = mapper1.readValue(getRes.body(), new TypeReference<List<Boat>>() {
                });

                Long boatId = 0L;

                for (Boat b : boats) {
                    if (b.getName().equals(nameToAdd) && b.getLength().equals(lengthToAdd)) {
                        boatId = b.getId();
                        break;
                    }
                }


                String transactionDate = java.time.LocalDate.now().toString();
                String endSubscriptionDate = java.time.LocalDate.now().plusYears(1).toString();


                String bodyParkingFee = "{\"price\":\"" + 10 * lengthToAdd + "\"" +
                        ",\"start\":\"" + transactionDate + "\"" +
                        ",\"end\":\"" + endSubscriptionDate + "\"}";


                HttpResponse<String> putParkingFee = httpFunctions.Put(
                        ":8080/parkingFees/boatId/" + boatId,
                        bodyParkingFee
                );
                System.out.println(putParkingFee.statusCode());
            }
        }


        HttpResponse<String> responseGet = httpFunctions.Get(":8080/boats/memberId/" + memberId);

        if (responseGet.statusCode() == 200) {
            // the user is there

            // parse JSON
            ObjectMapper mapper = new ObjectMapper();
            List<Boat> boats = mapper.readValue(responseGet.body(), new TypeReference<List<Boat>>() {
            });


            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            length.setCellValueFactory(new PropertyValueFactory<>("length"));

            ObservableList<Boat> boatsToDisplay = FXCollections.observableArrayList();

            boats.forEach(boat -> {
                boatsToDisplay.add(boat);
                System.out.println(boat.getName());
            });

            boats_table.setItems(boatsToDisplay);


        } else {
            // 404 user not present
            System.out.println("Problema di connessione");
        }
    }

    public void go_home(ActionEvent actionEvent) {


        try {
            // 5 righe di codice per passare i dati come l'id alla prossima scena
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/memberHome.fxml"));
            parent = fxmlLoader.load();
            MemberHome memberHome = fxmlLoader.getController();
            memberHome.userId = memberId;
            memberHome.initializer();


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
}
