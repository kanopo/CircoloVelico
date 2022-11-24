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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import me.ollari.circolovelicogui.HttpFunctions;
import me.ollari.circolovelicogui.controllers.homeHandlers.MemberHome;
import me.ollari.circolovelicogui.rest.AnnualFee;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class MemberAnnualFees {
    public TableView<AnnualFee> annualFeeTable;
    public TableColumn<AnnualFee, String> transactionDate;
    public TableColumn<AnnualFee, String> endSubscriptionDate;
    public TableColumn<AnnualFee, Float> price;
    private final HttpFunctions httpFunctions = new HttpFunctions();

    public Long memberId;
    public Button renewButton;

    private Stage stage;
    private Scene scene;
    private Parent parent;

    public void setTable() throws IOException, InterruptedException {
        HttpResponse<String> response = httpFunctions.Get(":8080/annualFees/memberId/" + memberId);

        if (response.statusCode() == 200) {
            // the user is there

            // parse JSON
            ObjectMapper mapper = new ObjectMapper();
            List<AnnualFee> annualFees = mapper.readValue(response.body(), new TypeReference<List<AnnualFee>>() {
            });


            transactionDate.setCellValueFactory(new PropertyValueFactory<>("start"));
            endSubscriptionDate.setCellValueFactory(new PropertyValueFactory<>("end"));
            price.setCellValueFactory(new PropertyValueFactory<>("price"));

            ObservableList<AnnualFee> annualFeesToDisplay = FXCollections.observableArrayList();

            annualFeesToDisplay.addAll(annualFees);

            annualFeeTable.setItems(annualFeesToDisplay);

            LocalDate today = java.time.LocalDate.now();

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
                renewButton.setDisable(true);
                renewButton.setOpacity(0);
            } else {
                renewButton.setDisable(false);
                renewButton.setOpacity(1);
            }

        } else {
            // 404 user not present
            System.out.println(response.statusCode());
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

    public void renew(ActionEvent actionEvent) throws IOException, InterruptedException {

        /*
        Questo bottono e' usabile solo se un abbonamento e' scaduto oppure in scadenza e ne permette il rinnovo
        TODO:
            - creare un popup per far inserire all'utente la carta di credito oppure la ricevuta di bonifico
         */


        HttpResponse<String> response = httpFunctions.Get(":8080/annualFees/memberId/" + memberId);

        if (response.statusCode() == 200) {
            // the user is there

            // parse JSON
            ObjectMapper mapper = new ObjectMapper();
            List<AnnualFee> annualFees = mapper.readValue(response.body(), new TypeReference<List<AnnualFee>>() {
            });

            LocalDate newStartSubscription = LocalDate.parse(annualFees.get(annualFees.size() - 1).getEnd());
            LocalDate newEndSubscription = newStartSubscription.plusYears(1);

            double price = 100.0;

            String body = "{\"price\":\"" + price + "\"" +
                    ",\"start\":\"" + newStartSubscription + "\"" +
                    ",\"end\":\"" + newEndSubscription + "\"}";


            HttpResponse<String> response1 = httpFunctions.Put(":8080/annualFees/memberId/" + memberId, body);

            if (response1.statusCode() == 201) {
                setTable();
            } else {
                System.out.println("errore");
            }

        } else {
            // 404 user not present
            System.out.println(response.statusCode());
        }


    }
}
