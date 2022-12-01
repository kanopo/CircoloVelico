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
import me.ollari.circolovelicogui.HttpFunctions;
import me.ollari.circolovelicogui.controllers.homeHandlers.EmployeeHome;
import me.ollari.circolovelicogui.rest.Boat;
import me.ollari.circolovelicogui.rest.ParkingFee;
import me.ollari.circolovelicogui.tableView.ParkingFeeVisualization;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class ParkingFeeHistory {
    public Long employeeId;


    public TableView<ParkingFeeVisualization> parkingFeeHistoryTable;
    public TableColumn<ParkingFeeVisualization, Integer> boatId;
    public TableColumn<ParkingFeeVisualization, Float> price;
    public TableColumn<ParkingFeeVisualization, String> start;
    public TableColumn<ParkingFeeVisualization, String> end;
    public TableColumn<ParkingFeeVisualization, Integer> memberId;


    private Stage stage;
    private Scene scene;
    private Parent parent;

    private HttpFunctions httpFunctions = new HttpFunctions();

    private final HashMap<Boat, List<ParkingFee>> parkingFeesMap = new HashMap<>();

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

    public void setTable() throws IOException, InterruptedException {

        boatId.setCellValueFactory(new PropertyValueFactory<>("boatId"));
        memberId.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        price.setCellValueFactory(new PropertyValueFactory<>("parkingFeePrice"));
        start.setCellValueFactory(new PropertyValueFactory<>("parkingFeeStart"));
        end.setCellValueFactory(new PropertyValueFactory<>("parkingFeeEnd"));

        ObservableList<ParkingFeeVisualization> parkingFeeToDisplay = FXCollections.observableArrayList();

        ObjectMapper parkingFeeMapper = new ObjectMapper();
        ObjectMapper boatMapper = new ObjectMapper();

        HashMap<Long, Set<Boat>> memberIdBoatMap = new HashMap<>();
        HashMap<Long, Set<ParkingFee>> boatIdParkignFeeMap = new HashMap<>();

        HttpResponse<String> boatResponse = httpFunctions.GET("/boats/memberBoatSet");

        if (boatResponse.statusCode() == 200) {
            // ci sono utenti nel db
            memberIdBoatMap = boatMapper.readValue(boatResponse.body(), new TypeReference<HashMap<Long, Set<Boat>>>() {
            });
        }

        HttpResponse<String> parkingFeeResponse = httpFunctions.GET("/parkingFee/boatParkingFeeSet");

        if (parkingFeeResponse.statusCode() == 200) {
            // ci sono utenti nel db
            boatIdParkignFeeMap = parkingFeeMapper.readValue(parkingFeeResponse.body(), new TypeReference<HashMap<Long, Set<ParkingFee>>>() {
            });
        }


        if (boatResponse.statusCode() == 200 && parkingFeeResponse.statusCode() == 200) {
            for (Long memberId : memberIdBoatMap.keySet()) {

                for (Boat b : memberIdBoatMap.get(memberId)) {

                    for (ParkingFee pf : boatIdParkignFeeMap.get(b.getId())) {
                        parkingFeeToDisplay.add(new ParkingFeeVisualization(memberId, b, pf));
                    }
                }
            }

            parkingFeeHistoryTable.setItems(parkingFeeToDisplay);

        }
    }
}