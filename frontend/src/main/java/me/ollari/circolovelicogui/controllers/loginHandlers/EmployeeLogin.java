package me.ollari.circolovelicogui.controllers.loginHandlers;

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
import me.ollari.circolovelicogui.HttpFunctions;
import me.ollari.circolovelicogui.Ip;
import me.ollari.circolovelicogui.controllers.homeHandlers.EmployeeHome;
import me.ollari.circolovelicogui.rest.Employee;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

public class EmployeeLogin {
    @FXML
    public VBox root;
    @FXML
    public TextField employee_username;
    @FXML
    public PasswordField employee_password;
    public Label backendisOffline;
    private Stage stage;
    private Scene scene;
    private Parent parent;

    private HttpFunctions httpFunctions = new HttpFunctions();

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
        employee_username.setStyle("-fx-text-box-border: black;");
        employee_password.setStyle("-fx-text-box-border: black;");
    }

    public void employee_login(ActionEvent actionEvent) throws IOException, InterruptedException {

        if (httpFunctions.pingHost()) {
            backendisOffline.setOpacity(0);

            String username = "";
            String password = "";

            try {
                username = employee_username.getText();
                password = employee_password.getText();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!username.isBlank() && !password.isBlank()) {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .GET()
                        .header("accept", "application/json")
                        .uri(URI.create("http://" + Ip.getIp() + ":8080/employee/username/" + username + ""))
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


                if (response.statusCode() == 200) {
                    // the user is there
                    ObjectMapper mapper = new ObjectMapper();

                    Employee employee = mapper.readValue(response.body(), new TypeReference<Employee>() {
                    });

                    // check della password
                    if (employee.getPassword().equals(password)) {
                        // user accettato

                        try {
                            // 5 righe di codice per passare i dati come l'id alla prossima scena
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/employeeHome.fxml"));
                            parent = fxmlLoader.load();
                            EmployeeHome employeeHome = fxmlLoader.getController();
                            employeeHome.employeeId = employee.getId();
                            employeeHome.initializer();


                            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

                            scene = new Scene(parent);

                            stage.setScene(scene);
                            stage.show();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        employee_username.setStyle("-fx-text-box-border: red;");
                        employee_password.setStyle("-fx-text-box-border: red;");
                    }
                } else {
                    // 404 user not present
                    employee_username.setStyle("-fx-text-box-border: red;");
                    employee_password.setStyle("-fx-text-box-border: red;");
                }
            }
        }
        else
        {
            backendisOffline.setOpacity(1);
        };
    }
}
