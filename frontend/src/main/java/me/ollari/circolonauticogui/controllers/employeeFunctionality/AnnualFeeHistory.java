package me.ollari.circolonauticogui.controllers.employeeFunctionality;

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
import me.ollari.circolonauticogui.Ip;
import me.ollari.circolonauticogui.controllers.homeHandlers.EmployeeHome;
import me.ollari.circolonauticogui.rest.AnnualFee;
import me.ollari.circolonauticogui.tableView.AnnualFeeVisualization;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AnnualFeeHistory {
    public TableView<AnnualFeeVisualization> annualFeeHistoryTable;
    public TableColumn<AnnualFeeVisualization, Integer> id;
    public TableColumn<AnnualFeeVisualization, Float> price;
    public TableColumn<AnnualFeeVisualization, String> transactionDate;
    public TableColumn<AnnualFeeVisualization, String> endSubscriptionDate;
    public TableColumn<AnnualFeeVisualization, Boolean> toPay;

    public Long employeeId;
    public TableColumn<AnnualFeeVisualization, Integer> memberId;

    private Stage stage;
    private Scene scene;
    private Parent parent;

    public void setTable() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create("http://" + Ip.getIp() + ":8080/get/annual-fee"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            // the user is there

            // parse JSON
            ObjectMapper mapper = new ObjectMapper();
            List<AnnualFee> annualFees = mapper.readValue(response.body(), new TypeReference<List<AnnualFee>>() {
            });

            List<AnnualFeeVisualization> annualFeeVisualizations = new ArrayList<>();

            for (AnnualFee af : annualFees) {
                AnnualFeeVisualization afv = new AnnualFeeVisualization(af);
                annualFeeVisualizations.add(afv);
            }


            id.setCellValueFactory(new PropertyValueFactory<>("annualFeeId"));
            memberId.setCellValueFactory(new PropertyValueFactory<>("memberId"));
            transactionDate.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));
            endSubscriptionDate.setCellValueFactory(new PropertyValueFactory<>("endSubscriptionDate"));
            price.setCellValueFactory(new PropertyValueFactory<>("price"));
            toPay.setCellValueFactory(new PropertyValueFactory<>("toPay"));


            ObservableList<AnnualFeeVisualization> feeVisualizations = FXCollections.observableArrayList();
            feeVisualizations.addAll(annualFeeVisualizations);

            annualFeeHistoryTable.setItems(feeVisualizations);


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
}
