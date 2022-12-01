package me.ollari.circolovelicogui.controllers.employeeFunctionality;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import me.ollari.circolovelicogui.HttpFunctions;
import me.ollari.circolovelicogui.controllers.homeHandlers.EmployeeHome;
import me.ollari.circolovelicogui.rest.Member;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Objects;

public class AddMember {
    public TextField name;
    public TextField surname;
    public TextField address;
    public TextField fiscal_code;
    public TextField username;
    public TextField password;

    private Stage stage;
    private Scene scene;
    private Parent parent;

    public Long employeeId;

    private final HttpFunctions httpFunctions = new HttpFunctions();

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

    public void add_member(ActionEvent actionEvent) throws IOException, InterruptedException {

        fiscal_code.setStyle("-fx-text-box-border: black;");
        username.setStyle("-fx-text-box-border: black;");

        String memberName = name.getText();
        String memberSurname = surname.getText();
        String memberAddress = address.getText();
        String memberFiscalCode = fiscal_code.getText();
        String memberUsername = username.getText();
        String memberPassword = password.getText();

        boolean canBeAdded = true;

        HttpResponse<String> response = httpFunctions.GET("/members");

        if (response.statusCode() == 200) {
            // the user is there

            // parse JSON
            ObjectMapper mapper = new ObjectMapper();
            List<Member> members = mapper.readValue(response.body(), new TypeReference<List<Member>>() {
            });


            for (Member m : members) {
                if (m.getFiscalCode().equals(memberFiscalCode) || m.getUsername().equals(memberUsername)) {
                    canBeAdded = false;
                    break;
                }
            }

            if (!canBeAdded) {
                fiscal_code.setStyle("-fx-text-box-border: red;");
                username.setStyle("-fx-text-box-border: red;");
            } else {
                fiscal_code.setStyle("-fx-text-box-border: black;");
                username.setStyle("-fx-text-box-border: black;");

                String body = "{\"name\":\"" + memberName + "\",\"surname\":\"" + memberSurname + "\",\"address\":\"" + memberAddress + "\"," +
                        "\"fiscalCode\":\"" + memberFiscalCode + "\"," +
                        "\"username\":\"" + memberUsername + "\"," +
                        "\"password\":\"" + memberPassword + "\"}";

                HttpResponse<String> responsePost = httpFunctions.POST("/members", body);

                if (responsePost.statusCode() == 201) {
                    // added successfully
                    name.clear();
                    surname.clear();
                    address.clear();
                    password.clear();
                    fiscal_code.clear();
                    username.clear();
                    fiscal_code.setStyle("-fx-text-box-border: black;");
                    username.setStyle("-fx-text-box-border: black;");
                    name.setStyle("-fx-text-box-border: black;");
                    surname.setStyle("-fx-text-box-border: black;");
                    address.setStyle("-fx-text-box-border: black;");
                    password.setStyle("-fx-text-box-border: black;");
                    fiscal_code.setStyle("-fx-text-box-border: black;");
                    username.setStyle("-fx-text-box-border: black;");

                    HttpResponse<String> getRes = httpFunctions.GET("/members/username/" + memberUsername);

                    if (getRes.statusCode() == 200) {
                        ObjectMapper mapper1 = new ObjectMapper();
                        Member member = mapper1.readValue(getRes.body(), new TypeReference<Member>() {
                        });


                        String start = java.time.LocalDate.now().toString();
                        String end = java.time.LocalDate.now().plusYears(1).toString();


                        String bodyAnnualFee = "{\"price\":\"" + 100 + "\"" +
                                ",\"start\":\"" + start + "\"" +
                                ",\"end\":\"" + end + "\"}";

                        /*
                        WARNING: ho deciso di mettere il "toPay" di quando viene creato un'utente a falso
                        perché ho immaginato che per creare un'account ci si debba recare da un impiegato del gruppo nautico
                        e che lo si paghi sul momento.
                         */

                        HttpResponse<String> putAnnualFee = httpFunctions.PUT(
                                "/annualFees/memberId/" + member.getId(),
                                bodyAnnualFee
                        );

                        System.out.println(putAnnualFee.statusCode());
                    }

                } else {
                    fiscal_code.setStyle("-fx-text-box-border: red;");
                    username.setStyle("-fx-text-box-border: red;");
                    name.setStyle("-fx-text-box-border: red;");
                    surname.setStyle("-fx-text-box-border: red;");
                    address.setStyle("-fx-text-box-border: red;");
                    password.setStyle("-fx-text-box-border: red;");
                    fiscal_code.setStyle("-fx-text-box-border: red;");
                    username.setStyle("-fx-text-box-border: red;");
                }

            }
        }


    }
}
