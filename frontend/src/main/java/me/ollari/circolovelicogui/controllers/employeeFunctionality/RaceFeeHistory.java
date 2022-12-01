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
import me.ollari.circolovelicogui.rest.RaceFeeVisualizationEmployee;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Objects;


public class RaceFeeHistory {
    public Long employeeId;
    public TableView<RaceFeeVisualizationEmployee> raceFeeHistoryTable;
    public TableColumn<RaceFeeVisualizationEmployee, Integer> id;
    public TableColumn<RaceFeeVisualizationEmployee, Integer> memberId;
    public TableColumn<RaceFeeVisualizationEmployee, Integer> boatId;
    public TableColumn<RaceFeeVisualizationEmployee, Integer> raceId;
    public TableColumn<RaceFeeVisualizationEmployee, Float> price;
    public TableColumn<RaceFeeVisualizationEmployee, String> paymentDate;


    private Stage stage;
    private Scene scene;
    private Parent parent;

    private HttpFunctions httpFunctions = new HttpFunctions();

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

        id.setCellValueFactory(new PropertyValueFactory<>("raceFeeId"));
        memberId.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        raceId.setCellValueFactory(new PropertyValueFactory<>("raceId"));
        boatId.setCellValueFactory(new PropertyValueFactory<>("boatId"));
        price.setCellValueFactory(new PropertyValueFactory<>("raceFeePrice"));
        paymentDate.setCellValueFactory(new PropertyValueFactory<>("raceFeePaymentDate"));

        HttpResponse<String> raceFeesVisualizeResponse = httpFunctions.GET("/raceFees/visualization");

        ObjectMapper raceFeesMapper = new ObjectMapper();


        if (raceFeesVisualizeResponse.statusCode() == 200) {
            List<RaceFeeVisualizationEmployee> raceFeeVisualizations = raceFeesMapper.readValue(raceFeesVisualizeResponse.body(), new TypeReference<List<RaceFeeVisualizationEmployee>>() {
            });

            ObservableList<RaceFeeVisualizationEmployee> feeVisualizations = FXCollections.observableArrayList();
            feeVisualizations.addAll(raceFeeVisualizations);

            raceFeeHistoryTable.setItems(feeVisualizations);
        }
    }
}
