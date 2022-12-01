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
import me.ollari.circolovelicogui.controllers.homeHandlers.EmployeeHome;
import me.ollari.circolovelicogui.rest.Boat;
import me.ollari.circolovelicogui.rest.Member;

import java.io.IOException;
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
        HttpResponse<String> usersResponse = httpFunctions.GET("/members");

        if (usersResponse.statusCode() == 200) {
            // the user is there

            // parse JSON
            ObjectMapper mapper = new ObjectMapper();
            List<Member> members = mapper.readValue(usersResponse.body(), new TypeReference<List<Member>>() {
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

            HttpResponse<String> memberByUsernameResponse = httpFunctions.GET("/members/username/" + memberUsername);


            if (memberByUsernameResponse.statusCode() == 200) {
                // the user is there
                ObjectMapper mapper = new ObjectMapper();

                Member member = mapper.readValue(memberByUsernameResponse.body(), new TypeReference<Member>() {
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

                        HttpResponse<String> responsePut = httpFunctions.PUT("/boats/memberId/" + memberId, body);


                        if (responsePut.statusCode() == 201) {
                            HttpResponse<String> getRes = httpFunctions.GET("/boats/memberId/" + memberId);

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


                                String bodyParkingFee = "{\"price\":\"" + 10 * boatLenght + "\"" +
                                        ",\"start\":\"" + transactionDate + "\"" +
                                        ",\"end\":\"" + endSubscriptionDate + "\"}";


                                HttpResponse<String> putParkingFee = httpFunctions.PUT(
                                        "/parkingFees/boatId/" + boatId,
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