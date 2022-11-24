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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import me.ollari.circolovelicogui.HttpFunctions;
import me.ollari.circolovelicogui.Ip;
import me.ollari.circolovelicogui.controllers.homeHandlers.EmployeeHome;
import me.ollari.circolovelicogui.rest.Boat;
import me.ollari.circolovelicogui.rest.Member;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Objects;

public class AddBoat {
    public ChoiceBox<String> member_selector;
    public TextField name;

    private Stage stage;
    private Scene scene;
    private Parent parent;

    public Long employeeId;
    public TextField length;

    private final HttpFunctions httpFunctions = new HttpFunctions();

    public void setDropdown() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create("http://" + Ip.getIp() + ":8080/get/member"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            // the user is there

            // parse JSON
            ObjectMapper mapper = new ObjectMapper();
            List<Member> members = mapper.readValue(response.body(), new TypeReference<List<Member>>() {
            });

            ObservableList<String> usernames = FXCollections.observableArrayList();

            for (Member m : members) {
                usernames.add(m.getUsername());
            }

            member_selector.setItems(usernames);
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

    public void add_boat(ActionEvent actionEvent) throws IOException, InterruptedException {
        Long memberId;
        String boatName = "";
        double boatLenght = 0;

        int memberIndex = member_selector.getSelectionModel().getSelectedIndex();

        if (memberIndex != -1) {
            String memberUsername = member_selector.getSelectionModel().getSelectedItem().toString();

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .header("accept", "application/json")
                    .uri(URI.create("http://" + Ip.getIp() + ":8080/get/member/username/" + memberUsername + ""))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


            if (response.statusCode() == 200) {
                // the user is there
                ObjectMapper mapper = new ObjectMapper();

                Member member = mapper.readValue(response.body(), new TypeReference<Member>() {
                });

                memberId = member.getId();

                if (!name.getText().isBlank() && !name.getText().isEmpty() && !length.getText().isBlank() && !length.getText().isEmpty()) {
                    boatName = name.getText();

                    try {
                        boatLenght = Float.parseFloat(length.getText());
                    } catch (NumberFormatException e) {
                        //e.printStackTrace();
                    }

                    if (!boatName.equals("") && boatLenght > 0) {
                        String body = "{\"name\":\"" + boatName + "\",\"length\":\"" + boatLenght + "\"}";

                        HttpClient clientPut = HttpClient.newHttpClient();
                        HttpRequest requestPut = HttpRequest.newBuilder()
                                .PUT(HttpRequest.BodyPublishers.ofString(body))
                                .header("Content-Type", "application/json")
                                .uri(URI.create("http://" + Ip.getIp() + ":8080/put/boat/add-boat/member-id/" + memberId + ""))
                                .build();

                        HttpResponse<String> responsePut = clientPut.send(requestPut, HttpResponse.BodyHandlers.ofString());

                        System.out.println(responsePut.statusCode());

                        if (responsePut.statusCode() == 200) {
                            HttpResponse<String> getRes = httpFunctions.Get(":8080/get/boat/member-id/" + memberId);

                            if (getRes.statusCode() == 200) {
                                ObjectMapper mapper1 = new ObjectMapper();
                                List<Boat> boats = mapper1.readValue(getRes.body(), new TypeReference<List<Boat>>() {
                                });

                                Long boatId = 0L;

                                for (Boat b : boats) {
                                    if (b.getName().equals(boatName) && b.getLength().equals(boatLenght)) {
                                        boatId = b.getId();
                                        break;
                                    }
                                }


                                String transactionDate = java.time.LocalDate.now().toString();
                                String endSubscriptionDate = java.time.LocalDate.now().plusYears(1).toString();


                                String bodyParkingFee = "{\"price\":\"" + Math.round(10 * boatLenght) + "\"" +
                                        ",\"transactionDate\":\"" + transactionDate + "\"" +
                                        ",\"endSubscriptionDate\":\"" + endSubscriptionDate + "\"" +
                                        ",\"toPay\":\"" + false + "\"}";

                        /*
                        WARNING: ho deciso di mettere il "toPay" di quando viene creato una barca a falso
                        perché ho immaginato che se lo deve un impiegato il pagamento avvenga sul posto
                         */

                                HttpResponse<String> putParkingFee = httpFunctions.Put(
                                        ":8080/put/parking-fee/add-parking-fee/member-id/" + memberId + "/boat-id/" + boatId,
                                        bodyParkingFee
                                );
                                System.out.println(putParkingFee.statusCode());
                            }


                            name.clear();
                            length.clear();
                            member_selector.getSelectionModel().clearSelection();
                        }

                    }


                }


            }
        }
    }
}