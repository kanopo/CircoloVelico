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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import me.ollari.circolovelicogui.HttpFunctions;
import me.ollari.circolovelicogui.controllers.homeHandlers.MemberHome;
import me.ollari.circolovelicogui.rest.Boat;
import me.ollari.circolovelicogui.rest.Race;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Objects;

public class MemberRaces {
    public TableView<Race> raceTable;
    public TableColumn<Race, String> date;
    public TableColumn<Race, Double> price;

    public TableColumn<Race, Double> award;

    public Long memberId;
    public ComboBox<String> boatSelector;
    public TableColumn<Race, String> name;

    private Stage stage;
    private Scene scene;
    private Parent parent;

    private boolean raceIsSelected = false;
    private final boolean boatIsSelected = false;
    private Long raceId;
    private Long boatId;
    private List<Boat> boats;

    private final HttpFunctions httpFunctions = new HttpFunctions();


    public void setTable() throws IOException, InterruptedException {
        HttpResponse<String> response = httpFunctions.GET("/races");

        if (response.statusCode() == 200) {
            // the user is there

            // parse JSON
            ObjectMapper mapper = new ObjectMapper();
            List<Race> races = mapper.readValue(response.body(), new TypeReference<List<Race>>() {
            });


            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            price.setCellValueFactory(new PropertyValueFactory<>("price"));
            award.setCellValueFactory(new PropertyValueFactory<>("award"));

            ObservableList<Race> racesToDisplay = FXCollections.observableArrayList();

            racesToDisplay.addAll(races);

            raceTable.setItems(racesToDisplay);


        } else {
            // 404 user not present
            System.out.println("Problema di connessione");
        }

    }

    public void go_home(ActionEvent actionEvent) {
        try {
            // 5 righe di codice per passare i dati come l'id alla prossima scena
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


    public void IscriviBarca(ActionEvent actionEvent) throws IOException, InterruptedException {
/*
        try {
            boatId = boats.get(boatSelector.getSelectionModel().getSelectedIndex()).getId();

            Integer paymentMethod = paymentSelector.getSelectionModel().getSelectedIndex();
            System.out.println(paymentMethod);

            switch (paymentMethod) {
                case -1:
                    System.out.println("Pagamento non selezionato");
                    break;
                case 0:
                    //TODO: effettuare il pagamento con la carta
                    break;
                case 1:
                    //TODO: effettuare il pagamento con bonifico
                    break;
            }


        } catch (Error e) {
            System.out.println("Barca e/o gara non selezionata");
        }

 */

        try {
            boatId = boats.get(boatSelector.getSelectionModel().getSelectedIndex()).getId();
            System.out.println(boatId);

            // ora creo la race fee per far si che la barca risulti iscritta


            // TODO: modificare l'api per prendere il memberID dalla barca
            HttpResponse<String> responsePost = httpFunctions.POSTNoBody("/raceFees/raceId/" + raceId + "/boatId/" + boatId);


            System.out.println(responsePost);


            ObservableList<String> empty = FXCollections.observableArrayList();


            boatSelector.setItems(empty);
            raceTable.getSelectionModel().clearSelection();


        } catch (Error e) {
            System.out.println("Barca e/o gara non selezionata");
        }

    }

    public void selectBoat(MouseEvent mouseEvent) {

        try {
            Race race = raceTable.getSelectionModel().getSelectedItem();
            raceId = race.getId();

            HttpResponse<String> response = httpFunctions.GET("/boats/raceId/" + raceId + "/memberId/" + memberId);

            if (response.statusCode() == 200) {
                // the user is there

                // parse JSON
                ObjectMapper mapper = new ObjectMapper();
                boats = mapper.readValue(response.body(), new TypeReference<List<Boat>>() {
                });


                // ora devo settare il dropdown per far vedere solo le barche che non sono iscritte alla corsa selezionata
                // sono tutte le barche che vengono restituite dalla variabile boats

                ObservableList<String> boatNames = FXCollections.observableArrayList();

                for (Boat b : boats) {
                    boatNames.add(b.getName());
                    System.out.println(b.getName());
                }

                boatSelector.setItems(boatNames);


            } else {
                // 404 user not present
                System.out.println("Problema di connessione");
            }


            raceIsSelected = true;
        } catch (Exception e) {
            raceIsSelected = false;
        }


    }
}
