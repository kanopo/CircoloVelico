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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import me.ollari.circolovelicogui.HttpFunctions;
import me.ollari.circolovelicogui.controllers.homeHandlers.MemberHome;
import me.ollari.circolovelicogui.rest.RaceFee;
import me.ollari.circolovelicogui.tableView.RaceFeeVisualization;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MemberRaceFees {
    public Long memberId;
    public TableView<RaceFeeVisualization> raceFeesTable;
    public TableColumn<RaceFeeVisualization, String> raceName;
    public TableColumn<RaceFeeVisualization, String> boatName;
    public TableColumn<RaceFeeVisualization, String> raceDate;
    public TableColumn<RaceFeeVisualization, String> raceFeePaymentDate;
    public TableColumn<RaceFeeVisualization, String> raceFeePrice;

    private Stage stage;
    private Scene scene;
    private Parent parent;

    private final HttpFunctions httpFunctions = new HttpFunctions();

    public void setTable() throws IOException, InterruptedException {
        HttpResponse<String> response = httpFunctions.GET("/get/race-fee/member-id/" + memberId);

        if (response.statusCode() == 200) {
            // the user is there

            // parse JSON
            ObjectMapper mapper = new ObjectMapper();
            List<RaceFee> raceFees = mapper.readValue(response.body(), new TypeReference<List<RaceFee>>() {
            });

            List<RaceFeeVisualization> raceFeeVisualizations = new ArrayList<>();

            for (RaceFee rf : raceFees) {
                RaceFeeVisualization rfv = new RaceFeeVisualization(rf);

                raceFeeVisualizations.add(rfv);
            }

            raceName.setCellValueFactory(new PropertyValueFactory<>("raceName"));
            boatName.setCellValueFactory(new PropertyValueFactory<>("boatName"));
            raceDate.setCellValueFactory(new PropertyValueFactory<>("raceDate"));
            raceFeePaymentDate.setCellValueFactory(new PropertyValueFactory<>("raceFeePaymentDate"));
            raceFeePrice.setCellValueFactory(new PropertyValueFactory<>("raceFeePrice"));

            ObservableList<RaceFeeVisualization> raceFeeToDisplay = FXCollections.observableArrayList();

            raceFeeToDisplay.addAll(raceFeeVisualizations);

            raceFeesTable.setItems(raceFeeToDisplay);




        } else {
            // 404 user not present
            System.out.println("Problema di connessione");
        }

    }


    public void go_home(ActionEvent actionEvent) {
        try {
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
