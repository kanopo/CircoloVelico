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
import me.ollari.circolovelicogui.rest.Race;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Objects;

public class DelRace {
    public TableView<Race> raceTable;
    public TableColumn<Race, Integer> id;
    public TableColumn<Race, String> name;
    public TableColumn<Race, String> date;

    private Stage stage;
    private Scene scene;
    private Parent parent;

    public Long employeeId;

    private HttpFunctions httpFunctions = new HttpFunctions();

    public void setTable() throws IOException, InterruptedException {
        HttpResponse<String> racesResponse = httpFunctions.GET("/races");

        if (racesResponse.statusCode() == 200) {
            // the user is there

            // parse JSON
            ObjectMapper racesMapper = new ObjectMapper();
            List<Race> races = racesMapper.readValue(racesResponse.body(), new TypeReference<List<Race>>() {
            });


            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            date.setCellValueFactory(new PropertyValueFactory<>("date"));

            ObservableList<Race> raceObservableList = FXCollections.observableArrayList();

            raceObservableList.addAll(races);

            raceTable.setItems(raceObservableList);


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

        if (!raceTable.getSelectionModel().isEmpty()) {


            try {
                idToDelete = raceTable.getSelectionModel().getSelectedItem().getId();

                HttpResponse<String> responseDel = httpFunctions.DELETE("/races" + idToDelete);

                if (responseDel.statusCode() == 200) {
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
