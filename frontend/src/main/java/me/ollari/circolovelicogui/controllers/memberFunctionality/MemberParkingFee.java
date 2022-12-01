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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import me.ollari.circolovelicogui.HttpFunctions;
import me.ollari.circolovelicogui.controllers.homeHandlers.MemberHome;
import me.ollari.circolovelicogui.rest.Boat;
import me.ollari.circolovelicogui.rest.Member;
import me.ollari.circolovelicogui.rest.ParkingFee;
import me.ollari.circolovelicogui.tableView.ParkingFeeVisualization;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class MemberParkingFee {
    public Long memberId;
    public TableColumn<ParkingFeeVisualization, String> parkingFeeStart;
    public TableColumn<ParkingFeeVisualization, String> parkingFeeEnd;
    public TableColumn<ParkingFeeVisualization, Double> parkingFeePrice;
    public TableColumn<ParkingFeeVisualization, String> boatName;
    public TableView<ParkingFeeVisualization> parkingFeeTable;
    public Button renewButton;
    public ChoiceBox<String> boatsDropDownSelector;
    public Label warningNotifier;

    private Stage stage;
    private Scene scene;
    private Parent parent;

    private final HttpFunctions httpFunctions = new HttpFunctions();


    private final HashMap<Boat, List<ParkingFee>> parkingFeesMap = new HashMap<>();
    private final HashMap<Boat, ParkingFee> validParkingFee = new HashMap<>();


    public void setTable() throws IOException, InterruptedException {

        /*
        devo richiedere le barche di un utente, dopo di che chiedo le parking fee in base alla barca(id della barca)
         */

        HttpResponse<String> getBoatsResponse = httpFunctions.GET("/boats/memberId/" + memberId);
        ObjectMapper boatsMapper = new ObjectMapper();
        HttpResponse<String> getMemberResponse = httpFunctions.GET("/member/" + memberId);
        ObjectMapper memberMapper = new ObjectMapper();

        Member member = null;

        if (getMemberResponse.statusCode() == 200) {
            member = memberMapper.readValue(getMemberResponse.body(), new TypeReference<Member>() {
            });
        }

        if (getBoatsResponse.statusCode() == 200) {
            // esistono delle barche associate all'id dell'utente
            List<Boat> usersBoats = boatsMapper.readValue(getBoatsResponse.body(), new TypeReference<List<Boat>>() {
            });
            for (Boat b : usersBoats) {
                HttpResponse<String> getParkingFeesResponse = httpFunctions.GET("/parkingFees/boatId/" + b.getId());

                if (getBoatsResponse.statusCode() == 200) {
                    // esistono delle barche associate all'id dell'utente
                    ObjectMapper parkingFeeMapper = new ObjectMapper();

                    List<ParkingFee> boatsParkingFees = parkingFeeMapper.readValue(getParkingFeesResponse.body(), new TypeReference<List<ParkingFee>>() {
                    });

                    parkingFeesMap.clear();
                    parkingFeesMap.put(b, boatsParkingFees);
                }
            }
        }

        boatName.setCellValueFactory(new PropertyValueFactory<>("boatName"));
        parkingFeeStart.setCellValueFactory(new PropertyValueFactory<>("parkingFeeStart"));
        parkingFeeEnd.setCellValueFactory(new PropertyValueFactory<>("parkingFeeEnd"));
        parkingFeePrice.setCellValueFactory(new PropertyValueFactory<>("parkingFeePrice"));

        ObservableList<ParkingFeeVisualization> parkingFeeToDisplay = FXCollections.observableArrayList();


        LocalDate today = LocalDate.now();
        System.out.println(today);
        for (Boat b : parkingFeesMap.keySet()) {
            for (ParkingFee pf : parkingFeesMap.get(b)) {
                parkingFeeToDisplay.add(new ParkingFeeVisualization(member.getId(), b, pf));

                LocalDate start = LocalDate.parse(pf.getStart());
                LocalDate end = LocalDate.parse(pf.getEnd());
                if (
                        (today.isAfter(start) || today.equals(start)) &&
                                (today.isBefore(end.minusWeeks(2)) || today.equals(end.minusWeeks(2)))
                ) {
                    // valid or valid in future(already renewed), all other cases are in expiration or expired, so must need a renovation.
                    validParkingFee.put(b, pf);
                    break;
                } else if (today.isBefore(start) || today.equals(start)) {
                    // already renewed
                    validParkingFee.put(b, pf);
                    break;
                }
            }
        }


        parkingFeeTable.setItems(parkingFeeToDisplay);

        // parkingFeeMap contiene la barca con le relative tasse di rimessaggio


        //System.out.println("Valid parking fees found: " + validParkingFee.keySet().size());
        //System.out.println("Actual boats: " + parkingFeesMap.keySet().size());

        if (parkingFeesMap.keySet().size() == validParkingFee.keySet().size()) {
            // rimessaggi in regola
            renewButton.setOpacity(0);
            renewButton.setDisable(true);

            boatsDropDownSelector.setOpacity(0);
            boatsDropDownSelector.setDisable(true);

            warningNotifier.setOpacity(0);
            warningNotifier.setDisable(true);
        } else {
            renewButton.setOpacity(1);
            renewButton.setDisable(false);

            boatsDropDownSelector.setOpacity(1);
            boatsDropDownSelector.setDisable(false);

            warningNotifier.setOpacity(1);
            warningNotifier.setDisable(false);
        }

        ObservableList<String> boatsToRenew = FXCollections.observableArrayList();

        for (Boat b : parkingFeesMap.keySet()) {
            if (!validParkingFee.containsKey(b)) {
                boatsToRenew.add(b.getName());
            }
        }

        boatsDropDownSelector.setItems(boatsToRenew);
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

    public void renew(ActionEvent actionEvent) throws IOException, InterruptedException {

        String boatName = boatsDropDownSelector.getSelectionModel().getSelectedItem();

        Boat selectedBoat = new Boat();

        if (boatName != null) {
            HttpResponse<String> boatsOfMemberResponse = httpFunctions.GET("/boats/memberId/" + memberId);

            if (boatsOfMemberResponse.statusCode() == 200) {
                // esistono delle barche associate all'id dell'utente
                ObjectMapper boatsMapper = new ObjectMapper();

                List<Boat> usersBoats = boatsMapper.readValue(boatsOfMemberResponse.body(), new TypeReference<List<Boat>>() {
                });

                for (Boat b : usersBoats) {
                    if (b.getName().equals(boatName)) {
                        selectedBoat = b;
                        break;
                    }
                }

                System.out.println(selectedBoat);

                // when the boat object is known, i can procede requesting all the fees for this p[articular id
                // and can submit another parking fee
                // after that I can call the setTable function to refresh the table and check other things


                HttpResponse<String> feesForBoatRequest = httpFunctions.GET("/parkingFees/boatId/" + selectedBoat.getId());

                if (feesForBoatRequest.statusCode() == 200) {
                    // esistono delle barche associate all'id dell'utente
                    ObjectMapper parkingFeesMapper = new ObjectMapper();

                    List<ParkingFee> boatsFees = parkingFeesMapper.readValue(feesForBoatRequest.body(), new TypeReference<List<ParkingFee>>() {
                    });


                    Collections.reverse(boatsFees);

                    System.out.println(boatsFees.get(0));

                    // this is the last tax applied to the boat, I use its ending data to set the new fee

                    LocalDate newStart = LocalDate.parse(boatsFees.get(0).getEnd());
                    LocalDate newEnd = newStart.plusYears(1);


                    String body = "{\"price\":\"" + boatsFees.get(0).getPrice() + "\"" +
                            ",\"start\":\"" + newStart + "\"" +
                            ",\"end\":\"" + newEnd + "\"}";
                    System.out.println(body);

                    HttpResponse<String> putBoatParkingFeeResponse = httpFunctions.PUT("/parkingFees/boatId/" + selectedBoat.getId(), body);

                    System.out.println(putBoatParkingFeeResponse.statusCode());
                    // I use the settable function to reload the table
                    setTable();
                }

            }
        }
    }
}