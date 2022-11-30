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
import me.ollari.circolovelicogui.rest.Member;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Objects;

public class DelMember {
    public Long employeeId;
    public TableView<Member> memberTable;
    public TableColumn<Member, String> fiscal_code;
    public TableColumn<Member, String> surname;
    public TableColumn<Member, String> name;
    public TableColumn<Member, Integer> id;

    private Stage stage;
    private Scene scene;
    private Parent parent;

    private HttpFunctions httpFunctions = new HttpFunctions();


    public void setTable() throws IOException, InterruptedException {
        HttpResponse<String> memberResponse = httpFunctions.GET("/members");

        if (memberResponse.statusCode() == 200) {
            // the user is there

            // parse JSON
            ObjectMapper memberMapper = new ObjectMapper();
            List<Member> members = memberMapper.readValue(memberResponse.body(), new TypeReference<List<Member>>() {
            });


            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
            fiscal_code.setCellValueFactory(new PropertyValueFactory<>("fiscalCode"));
            id.setCellValueFactory(new PropertyValueFactory<>("id"));

            ObservableList<Member> membersToDisplay = FXCollections.observableArrayList();

            membersToDisplay.addAll(members);

            memberTable.setItems(membersToDisplay);


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

    public void delete(ActionEvent actionEvent) throws IOException, InterruptedException {
        Long idToDelete;

        try {
            idToDelete = memberTable.getSelectionModel().getSelectedItem().getId();

            HttpResponse<String> responseDel = httpFunctions.DELETE("/members/" + idToDelete);

            if (responseDel.statusCode() == 200) {
                setTable();
            } else {
                // 404 user not present
                System.out.println("Problema di connessione");
            }


        } catch (NullPointerException e) {
            System.err.println("No item was selected before the delete step");
        }

    }
}
