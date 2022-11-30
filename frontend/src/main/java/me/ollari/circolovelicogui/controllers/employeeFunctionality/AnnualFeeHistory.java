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
import me.ollari.circolovelicogui.rest.AnnualFee;
import me.ollari.circolovelicogui.rest.Member;
import me.ollari.circolovelicogui.tableView.AnnualFeeVisualization;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.*;

public class AnnualFeeHistory {
    public TableView<AnnualFeeVisualization> annualFeeHistoryTable;
    public TableColumn<AnnualFeeVisualization, Integer> id;
    public TableColumn<AnnualFeeVisualization, Float> price;
    public TableColumn<AnnualFeeVisualization, String> start;
    public TableColumn<AnnualFeeVisualization, String> end;

    public Long employeeId;
    public TableColumn<AnnualFeeVisualization, Integer> memberId;

    private Stage stage;
    private Scene scene;
    private Parent parent;

    private HttpFunctions httpFunctions = new HttpFunctions();

    public void setTable() throws IOException, InterruptedException {
        //TODO: chiedo una lista di members e poi chiedo le annual fees per utente

        HttpResponse<String> membersResponse = httpFunctions.GET("/members");
        List<AnnualFeeVisualization> annualFeeVisualizations = new ArrayList<>();

        if (membersResponse.statusCode() == 200) {
            // esistono degli utenti
            ObjectMapper memberMapper = new ObjectMapper();
            ObjectMapper annualFeeMapper = new ObjectMapper();

            List<Member> members = memberMapper.readValue(membersResponse.body(), new TypeReference<List<Member>>() {
            });

            Map<Member, List<AnnualFee>> memberAnnualFeeMap = new HashMap<>();

            for (Member m : members) {
                // richiedo le tasse di ogni utente
                HttpResponse<String> annualFeeByMemberResponse = httpFunctions.GET("/annualFees/memberId/" + m.getId());
                List<AnnualFee> annualFees = annualFeeMapper.readValue(annualFeeByMemberResponse.body(), new TypeReference<List<AnnualFee>>() {
                });


                for (AnnualFee af : annualFees) {
                    AnnualFeeVisualization afv = new AnnualFeeVisualization(m, af);
                    annualFeeVisualizations.add(afv);
                }

            }

            id.setCellValueFactory(new PropertyValueFactory<>("annualFeeId"));
            memberId.setCellValueFactory(new PropertyValueFactory<>("memberId"));
            start.setCellValueFactory(new PropertyValueFactory<>("start"));
            end.setCellValueFactory(new PropertyValueFactory<>("end"));
            price.setCellValueFactory(new PropertyValueFactory<>("price"));

            ObservableList<AnnualFeeVisualization> feeVisualizations = FXCollections.observableArrayList();
            feeVisualizations.addAll(annualFeeVisualizations);

            annualFeeHistoryTable.setItems(feeVisualizations);


        } else {
            System.out.println("conn");
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
