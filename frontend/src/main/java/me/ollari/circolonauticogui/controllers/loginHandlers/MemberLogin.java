package me.ollari.circolonauticogui.controllers.loginHandlers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import me.ollari.circolonauticogui.HttpFunctions;
import me.ollari.circolonauticogui.controllers.homeHandlers.MemberHome;
import me.ollari.circolonauticogui.rest.Member;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Objects;

public class MemberLogin {
    @FXML
    public VBox root;
    @FXML
    public TextField member_username;
    @FXML
    public PasswordField member_password;
    public Label backendIsOff;
    private Stage stage;
    private Scene scene;
    private Parent parent;

    private final HttpFunctions httpFunctions = new HttpFunctions();


    public void member_login(ActionEvent actionEvent) throws IOException, InterruptedException {

        if (httpFunctions.pingHost()) {
            backendIsOff.setOpacity(0);

            String username = "";
            String password = "";

            try {
                username = member_username.getText();
                password = member_password.getText();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!username.isBlank() && !password.isBlank()) {

                HttpResponse<String> response = httpFunctions.Get(":8080/members/username/" + username);

                if (response.statusCode() == 200) {
                    // the user is there
                    ObjectMapper mapper = new ObjectMapper();

                    Member member = mapper.readValue(response.body(), new TypeReference<Member>() {
                    });

                    // check della password
                    if (member.getPassword().equals(password)) {
                        // user accettato

                        try {
                            // 5 righe di codice per passare i dati come l'id alla prossima scena
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/memberHome.fxml"));
                            parent = fxmlLoader.load();
                            MemberHome memberHome = fxmlLoader.getController();
                            memberHome.userId = member.getId();
                            memberHome.initializer();


                            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

                            scene = new Scene(parent);

                            stage.setScene(scene);
                            stage.show();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        member_username.setStyle("-fx-text-box-border: red;");
                        member_password.setStyle("-fx-text-box-border: red;");
                    }
                } else {
                    // 404 user not present
                    member_username.setStyle("-fx-text-box-border: red;");
                    member_password.setStyle("-fx-text-box-border: red;");
                }
            }
        }
        else
        {
            backendIsOff.setOpacity(1);
        }

    }


    public void go_back(ActionEvent actionEvent) {
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

    public void clear_camps(MouseEvent mouseEvent) {
        member_username.setStyle("-fx-text-box-border: black;");
        member_password.setStyle("-fx-text-box-border: black;");
    }
}
