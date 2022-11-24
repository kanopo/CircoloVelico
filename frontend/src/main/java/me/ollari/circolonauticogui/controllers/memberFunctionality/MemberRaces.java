package me.ollari.circolonauticogui.controllers.memberFunctionality;

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
import me.ollari.circolonauticogui.HttpFunctions;
import me.ollari.circolonauticogui.controllers.homeHandlers.MemberHome;
import me.ollari.circolonauticogui.rest.Boat;
import me.ollari.circolonauticogui.rest.Race;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MemberRaces {
    public TableView<Race> raceTable;
    public TableColumn<Race, String> name;
    public TableColumn<Race, String> date;
    public TableColumn<Race, Float> participationPrice;

    public TableColumn<Race, Float> award;

    public Long memberId;
    public ComboBox<String> boatSelector;
    public ComboBox<String> paymentSelector;

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
        HttpResponse<String> response = httpFunctions.Get(":8080/get/race");

        if (response.statusCode() == 200) {
            // the user is there

            // parse JSON
            ObjectMapper mapper = new ObjectMapper();
            List<Race> races = mapper.readValue(response.body(), new TypeReference<List<Race>>() {
            });


            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            participationPrice.setCellValueFactory(new PropertyValueFactory<>("participationPrice"));
            award.setCellValueFactory(new PropertyValueFactory<>("award"));

            ObservableList<Race> racesToDisplay = FXCollections.observableArrayList();

            racesToDisplay.addAll(races);

            raceTable.setItems(racesToDisplay);

            List<String> pay = new ArrayList<>();
            pay.add("Carta di credito");
            pay.add("Ricevuta di bonifico");

            paymentSelector.setItems(FXCollections.observableList(pay));


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

        /*
        try {
            boatId = boats.get(boatSelector.getSelectionModel().getSelectedIndex()).getId();
            System.out.println(boatId);

            // ora creo la race fee per far si che la barca risulti iscritta


            double price = raceTable.getSelectionModel().getSelectedItem().getParticipationPrice();
            LocalDate localDate = LocalDate.now();
            String paymentDate = localDate.toString();
            String paymentMethod = "";

            String body = "{\"price\":\"" + price + "\",\"paymentDate\":\"" + paymentDate + "\",\"paymentMethod\":\"" + paymentMethod + "\"}";

            HttpResponse<String> responsePut = httpFunctions.Put(
                    ":8080/put/race-fee/add-race-fee/race-id/" + raceId + "/member-id/" + memberId + "/boat-id/" + boatId,
                    body
            );

            System.out.println(responsePut);


            ObservableList<String> empty = FXCollections.observableArrayList();


            boatSelector.setItems(empty);
            raceTable.getSelectionModel().clearSelection();


        } catch (Error e) {
            System.out.println("Barca e/o gara non selezionata");
        }
         */

    }

    public void selectBoat(MouseEvent mouseEvent) {

        try {
            Race race = raceTable.getSelectionModel().getSelectedItem();
            raceId = race.getId();

            HttpResponse<String> response = httpFunctions.Get(":8080/get/boat-not-in-race/race-id/" + raceId + "/member-id/" + memberId);

            if (response.statusCode() == 200) {
                // the user is there

                // parse JSON
                ObjectMapper mapper = new ObjectMapper();
                boats = mapper.readValue(response.body(), new TypeReference<>() {
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
