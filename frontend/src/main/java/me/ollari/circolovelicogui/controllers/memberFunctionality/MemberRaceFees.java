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
import me.ollari.circolovelicogui.rest.Boat;
import me.ollari.circolovelicogui.rest.Race;
import me.ollari.circolovelicogui.rest.RaceFee;
import me.ollari.circolovelicogui.tableView.RaceFeeVisualizationMember;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.*;

public class MemberRaceFees {
    public Long memberId;
    public TableView<RaceFeeVisualizationMember> raceFeesTable;
    public TableColumn<RaceFeeVisualizationMember, String> raceName;
    public TableColumn<RaceFeeVisualizationMember, String> boatName;
    public TableColumn<RaceFeeVisualizationMember, String> raceDate;
    public TableColumn<RaceFeeVisualizationMember, String> raceFeePaymentDate;
    public TableColumn<RaceFeeVisualizationMember, String> raceFeePrice;

    private Stage stage;
    private Scene scene;
    private Parent parent;

    private final HttpFunctions httpFunctions = new HttpFunctions();

    public void setTable() throws IOException, InterruptedException {
        List<RaceFeeVisualizationMember> raceFeeVisualizations = new ArrayList<>();

        HttpResponse<String> raceFeeResponse = httpFunctions.GET("/raceFees/memberId/" + memberId);

        if (raceFeeResponse.statusCode() == 200) {
            // the user is there

            // parse JSON
            ObjectMapper raceFeeMapper = new ObjectMapper();
            ObjectMapper boatMapper = new ObjectMapper();
            ObjectMapper raceMapper = new ObjectMapper();
            List<RaceFee> raceFees = raceFeeMapper.readValue(raceFeeResponse.body(), new TypeReference<List<RaceFee>>() {
            });

            Map<RaceFee, Boat> raceFeeBoatMap = new HashMap<>();
            Map<RaceFee, Race> raceFeeRaceMap = new HashMap<>();

            for (RaceFee rf : raceFees) {

                Boat boat = new Boat();
                Race race = new Race();

                HttpResponse<String> boatResponse = httpFunctions.GET("/boats/raceFee/" + rf.getId());

                if (boatResponse.statusCode() == 200) {

                    boat = boatMapper.readValue(boatResponse.body(), new TypeReference<Boat>() {
                    });

                }

                HttpResponse<String> raceResponse = httpFunctions.GET("/races/raceFee/" + rf.getId());

                if (raceResponse.statusCode() == 200) {

                    race = raceMapper.readValue(raceResponse.body(), new TypeReference<Race>() {
                    });

                }

                if (boatResponse.statusCode() == 200 && raceResponse.statusCode() == 200) {

                    RaceFeeVisualizationMember rfv = new RaceFeeVisualizationMember(memberId, race, boat, rf);
                    raceFeeVisualizations.add(rfv);
                }


            }


            raceName.setCellValueFactory(new PropertyValueFactory<>("raceName"));
            boatName.setCellValueFactory(new PropertyValueFactory<>("boatName"));
            raceFeePaymentDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
            raceDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            raceFeePrice.setCellValueFactory(new PropertyValueFactory<>("raceFeePrice"));

            ObservableList<RaceFeeVisualizationMember> raceFeeToDisplay = FXCollections.observableArrayList();

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
