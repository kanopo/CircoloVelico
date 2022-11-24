package me.ollari.circolonauticogui.controllers.homeHandlers;

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
import me.ollari.circolonauticogui.HttpFunctions;
import me.ollari.circolonauticogui.controllers.memberFunctionality.*;
import me.ollari.circolonauticogui.rest.AnnualFee;
import me.ollari.circolonauticogui.rest.Boat;
import me.ollari.circolonauticogui.rest.Member;
import me.ollari.circolonauticogui.rest.ParkingFee;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class MemberHome {
    public Long userId;

    public Member member;
    public Label notification;
    public Label notificationAnnualFee;
    public Label notificationParkingFee;

    private Stage stage;
    private Scene scene;
    private Parent parent;

    private final HttpFunctions httpFunctions = new HttpFunctions();


    @FXML
    public Label user_logged_in;


    public void initializer() throws IOException, InterruptedException {
        HttpResponse<String> response = httpFunctions.Get(":8080/members/" + userId);

        if (response.statusCode() == 200) {
            // the user is there
            ObjectMapper mapper = new ObjectMapper();

            member = mapper.readValue(response.body(), new TypeReference<Member>() {
            });

            user_logged_in.setText("Salve " + member.getName() + " " + member.getSurname());


        } else {
            // 404 user not present
            System.out.println("Problema di connessione");
        }


        /*
        HttpResponse<String> userHasSomethingToPay = httpFunctions.Get(":8080/get/leftover-fees/member-id/" + member.getId());

        if (userHasSomethingToPay.statusCode() == 200) {
            ObjectMapper mapper = new ObjectMapper();

            Boolean somethingToPayBool = mapper.readValue(userHasSomethingToPay.body(), new TypeReference<Boolean>() {
            });

            if (somethingToPayBool) {
                notification.setOpacity(1.0);
            }

        }
         */

        LocalDate today = java.time.LocalDate.now();
        HttpResponse<String> annualFeesResponse = httpFunctions.Get(":8080/annualFees/memberId/" + member.getId());

        if (annualFeesResponse.statusCode() == 200) {
            ObjectMapper mapper = new ObjectMapper();

            List<AnnualFee> annualFees = mapper.readValue(annualFeesResponse.body(), new TypeReference<List<AnnualFee>>() {
            });


            boolean abbonamentoValido = false;

            for (AnnualFee af : annualFees) {
                LocalDate start = LocalDate.parse(af.getStart());
                LocalDate end = LocalDate.parse(af.getEnd());


                if (
                        (today.isAfter(start) || today.isEqual(start)) && today.isBefore(end)
                                && today.isBefore(end.minusWeeks(2))
                ) {
                    // abbonamento valido
                    //System.out.println("Abbonamento valido");
                    abbonamentoValido = true;


                }

            }

            if (abbonamentoValido) {
                System.out.println("Abbonamento in regola");
                notificationAnnualFee.setOpacity(0.0);
            } else {
                System.out.println("Abbonamento in scadenza o scaduto");
                notificationAnnualFee.setOpacity(1.0);
            }
        }

        HttpResponse<String> response1 = httpFunctions.Get(":8080/boats/expiredParking/" + member.getId());

        if (response1.statusCode() == 200) {
            ObjectMapper mapper1 = new ObjectMapper();
            List<Boat> boatsWithExpiredParkingFee = mapper1.readValue(response1.body(), new TypeReference<List<Boat>>() {});


            if (boatsWithExpiredParkingFee.isEmpty()) {

            }
            else
            {
                notificationParkingFee.setOpacity(1);
            }
            /*
            TODO: sistemare questa parte perchè con la nuova api non va(non ci sono gli endpoint corretti)
             */
            /*
            for (Integer boatid : feesForBoats.keySet()) {
                HttpResponse<String> responseParkingFeeByBoatId = httpFunctions.Get(":8080/get/parking-fee/parking-fee-boat-id/" + boatid);

                if (responseParkingFeeByBoatId.statusCode() == 200) {
                    ObjectMapper mapper2 = new ObjectMapper();

                    List<ParkingFee> parkingFeeByBoatId = mapper2.readValue(responseParkingFeeByBoatId.body(), new TypeReference<List<ParkingFee>>() {
                    });

                    Collections.reverse(parkingFeeByBoatId);

                    boolean renewed = false;

                    for (ParkingFee pf : parkingFeeByBoatId) {
                        LocalDate start = LocalDate.parse(pf.getTransactionDate());
                        LocalDate end = LocalDate.parse(pf.getEndSubscriptionDate());


                        //casi ammessi:
                        //    - abbonamento valido -> break
                        //    - abbonamento futuro(gia rinnovato) -> check successivo abbonamento -> break
                        //    - abbonamento in via di scadenza -> break (segnalare)
                        //    - abbonamento scaduto -> break (segnalare)


            if (
                    (today.isAfter(start) || today.isEqual(start))
                            && (today.isBefore(end.minusWeeks(2)) || today.isEqual(end.minusWeeks(2)))) {

                // abbonamento valido
                System.out.println("Rimessaggio valido per " + pf.getBoat().getName());
                break;
            }

            if (
                    today.isBefore(start)
            ) {
                // Abbonamento rinnovato, procedo a controllare la prossima iterazione
                renewed = true;

            }

            if (
                    (
                            today.isAfter(end.minusWeeks(2))
                    ) && (
                            today.isBefore(end) || today.isEqual(end)
                    )
            ) {


                // abbonamento in scadenza, verificare se è presente il rinnovo
                if (renewed) {
                    // abbonamento rinnovato, non necessita di segnalazione
                    System.out.println("Rimessaggio valido per(abbonamento in scadenza ma già rinnovato) " + pf.getBoat().getName());
                    renewed = false;
                } else {
                    // abbonamento in scadenza ma non ancora rinnovato, segnalare
                    System.out.println("Rimessaggio in scadenza e non ancora rinnovato " + pf.getBoat().getName());
                    notificationParkingFee.setOpacity(1);

                }
                break;
            }

            if (
                    (today.isAfter(start) && today.isAfter(end))
            ) {
                // abbonamento scaduto, segnalare
                System.out.println("Rimessaggio scaduto " + pf.getBoat().getName());
                notificationParkingFee.setOpacity(1);
                break;
            }

        }

    }
}
             */

        }

    }

    public void go_to_boats(ActionEvent actionEvent) {
        try {
            // 5 righe di codice per passare i dati come l'id alla prossima scena
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/member/boats.fxml"));
            parent = fxmlLoader.load();
            MemberBoats memberBoats = fxmlLoader.getController();
            memberBoats.memberId = member.getId();
            memberBoats.setTable();


            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            scene = new Scene(parent);

            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void go_to_races(ActionEvent actionEvent) {

        try {
            // 5 righe di codice per passare i dati come l'id alla prossima scena
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/member/races.fxml"));
            parent = fxmlLoader.load();
            MemberRaces memberRaces = fxmlLoader.getController();
            memberRaces.memberId = member.getId();
            memberRaces.setTable();


            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            scene = new Scene(parent);

            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void go_to_annual_fees(ActionEvent actionEvent) {
        try {
            // 5 righe di codice per passare i dati come l'id alla prossima scena
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/member/annualFees.fxml"));
            parent = fxmlLoader.load();
            MemberAnnualFees annualFee = fxmlLoader.getController();
            annualFee.memberId = member.getId();
            annualFee.setTable();


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

    public void go_to_parking_fees(ActionEvent actionEvent) {
        try {
            // 5 righe di codice per passare i dati come l'id alla prossima scena
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/member/parkingFees.fxml"));
            parent = fxmlLoader.load();
            MemberParkingFee parkingFee = fxmlLoader.getController();
            parkingFee.memberId = member.getId();
            parkingFee.setTable();


            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            scene = new Scene(parent);

            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void go_to_race_fees(ActionEvent actionEvent) {
        try {
            // 5 righe di codice per passare i dati come l'id alla prossima scena
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/member/raceFees.fxml"));
            parent = fxmlLoader.load();
            MemberRaceFees raceFees = fxmlLoader.getController();
            raceFees.memberId = member.getId();
            raceFees.setTable();


            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            scene = new Scene(parent);

            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
