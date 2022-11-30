package me.ollari.circolovelicogui.controllers.homeHandlers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import me.ollari.circolovelicogui.HttpFunctions;
import me.ollari.circolovelicogui.Ip;
import me.ollari.circolovelicogui.controllers.employeeFunctionality.*;
import me.ollari.circolovelicogui.rest.Employee;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

public class EmployeeHome {
    public Employee employee;
    public Long employeeId;

    private Stage stage;
    private Scene scene;
    private Parent parent;
    private HttpFunctions httpFunctions = new HttpFunctions();


    @FXML
    public Label user_logged_in;

    public void initializer() throws IOException, InterruptedException {

        HttpResponse<String> response = httpFunctions.GET("/employee/" + employeeId);

        if (response.statusCode() == 200) {
            // the user is there
            ObjectMapper mapper = new ObjectMapper();

            employee = mapper.readValue(response.body(), new TypeReference<Employee>() {
            });

            user_logged_in.setText("Salve " + employee.getUsername());


        } else {
            // 404 user not present
            System.out.println("Problema di connessione");
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

    public void member_management(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Employee/membersManagement.fxml"));
            parent = fxmlLoader.load();
            MemberManagement memberManagement = fxmlLoader.getController();
            memberManagement.employeeId = employeeId;
            memberManagement.setTable();


            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            scene = new Scene(parent);

            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void add_member(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Employee/addMember.fxml"));
            parent = fxmlLoader.load();
            AddMember addMember = fxmlLoader.getController();
            addMember.employeeId = employeeId;


            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            scene = new Scene(parent);

            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void del_member(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Employee/delMember.fxml"));
            parent = fxmlLoader.load();
            DelMember delMember = fxmlLoader.getController();
            delMember.employeeId = employeeId;
            delMember.setTable();


            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            scene = new Scene(parent);

            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void add_boat(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Employee/addBoat.fxml"));
            parent = fxmlLoader.load();
            AddBoat addBoat = fxmlLoader.getController();
            addBoat.employeeId = employeeId;
            addBoat.setDropdown();


            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            scene = new Scene(parent);

            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void add_race(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Employee/addRace.fxml"));
            parent = fxmlLoader.load();
            AddRace addRace = fxmlLoader.getController();
            addRace.employeeId = employeeId;


            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            scene = new Scene(parent);

            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void go_notification_system(ActionEvent actionEvent) {
    }

    public void go_race_fee_history(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Employee/raceFeesHistory.fxml"));
            parent = fxmlLoader.load();
            RaceFeeHistory raceFeeHistory = fxmlLoader.getController();
            raceFeeHistory.employeeId = employeeId;
            raceFeeHistory.setTable();


            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            scene = new Scene(parent);

            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void go_annual_fee_history(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Employee/annualFeeHistory.fxml"));
            parent = fxmlLoader.load();
            AnnualFeeHistory annualFeeHistory = fxmlLoader.getController();
            annualFeeHistory.employeeId = employeeId;
            annualFeeHistory.setTable();


            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            scene = new Scene(parent);

            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void go_parking_fee_history(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Employee/parkingFeesHistory.fxml"));
            parent = fxmlLoader.load();
            ParkingFeeHistory parkingFeeHistory = fxmlLoader.getController();
            parkingFeeHistory.employeeId = employeeId;
            parkingFeeHistory.setTable();


            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            scene = new Scene(parent);

            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void del_race(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Employee/delRace.fxml"));
            parent = fxmlLoader.load();
            DelRace delRace = fxmlLoader.getController();
            delRace.employeeId = employeeId;
            delRace.setTable();


            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            scene = new Scene(parent);

            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void del_boat(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Employee/delBoat.fxml"));
            parent = fxmlLoader.load();
            DelBoat delBoat = fxmlLoader.getController();
            delBoat.employeeId = employeeId;
            delBoat.setTable();


            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            scene = new Scene(parent);

            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
