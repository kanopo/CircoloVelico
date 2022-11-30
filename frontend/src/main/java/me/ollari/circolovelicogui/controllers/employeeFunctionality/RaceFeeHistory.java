package me.ollari.circolovelicogui.controllers.employeeFunctionality;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import me.ollari.circolovelicogui.controllers.homeHandlers.EmployeeHome;
import me.ollari.circolovelicogui.tableView.RaceFeeVisualization;

import java.io.IOException;
import java.util.Objects;


public class RaceFeeHistory {
    public Long employeeId;
    public TableView<RaceFeeVisualization> raceFeeHistoryTable;
    public TableColumn<RaceFeeVisualization, Integer> id;
    public TableColumn<RaceFeeVisualization, Integer> memberId;
    public TableColumn<RaceFeeVisualization, Integer> boatId;
    public TableColumn<RaceFeeVisualization, Integer> raceId;
    public TableColumn<RaceFeeVisualization, Float> price;
    public TableColumn<RaceFeeVisualization, String> paymentDate;


    private Stage stage;
    private Scene scene;
    private Parent parent;

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
        /*
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create("http://" + Ip.getIp() + ":8080/get/race-fee"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

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


            for (RaceFeeVisualization rfv : raceFeeVisualizations) {
                System.out.println(rfv.toString());
            }

            id.setCellValueFactory(new PropertyValueFactory<>("raceFeeId"));
            memberId.setCellValueFactory(new PropertyValueFactory<>("memberId"));
            raceId.setCellValueFactory(new PropertyValueFactory<>("raceId"));
            boatId.setCellValueFactory(new PropertyValueFactory<>("boatId"));
            price.setCellValueFactory(new PropertyValueFactory<>("raceFeePrice"));
            paymentDate.setCellValueFactory(new PropertyValueFactory<>("raceFeePaymentDate"));

            ObservableList<RaceFeeVisualization> feeVisualizations = FXCollections.observableArrayList();
            feeVisualizations.addAll(raceFeeVisualizations);

            raceFeeHistoryTable.setItems(feeVisualizations);
            } else

    {
        // 404 user not present
        System.out.println("Problema di connessione");
    }
         */
    }


}
