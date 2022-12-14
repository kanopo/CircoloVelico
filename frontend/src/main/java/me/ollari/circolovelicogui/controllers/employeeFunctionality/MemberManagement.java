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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import me.ollari.circolovelicogui.HttpFunctions;
import me.ollari.circolovelicogui.controllers.homeHandlers.EmployeeHome;
import me.ollari.circolovelicogui.rest.Member;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Objects;

public class MemberManagement {
    public Long employeeId;
    public TableView<Member> userTable;

    public TableColumn<Member, String> name;
    public TableColumn<Member, String> surname;
    public TableColumn<Member, String> address;
    public TextField surnameMod;
    public TextField nameMod;
    public TextField addressMod;
    private Stage stage;
    private Scene scene;
    private Parent parent;

    HttpFunctions httpFunctions = new HttpFunctions();


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
            address.setCellValueFactory(new PropertyValueFactory<>("address"));

            ObservableList<Member> membersToDisplay = FXCollections.observableArrayList();

            membersToDisplay.addAll(members);

            userTable.setItems(membersToDisplay);


        } else {
            // 404 user not present
            System.out.println("Problema di connessione");
        }

    }

    public void requestDataToMod(MouseEvent mouseEvent) {

        String userName = userTable.getSelectionModel().getSelectedItem().getName();
        String userSurname = userTable.getSelectionModel().getSelectedItem().getSurname();
        String userAddress = userTable.getSelectionModel().getSelectedItem().getAddress();
        nameMod.setText(userName);
        surnameMod.setText(userSurname);
        addressMod.setText(userAddress);


    }

    public void goHome(ActionEvent actionEvent) {
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

    public void modifySelected(ActionEvent actionEvent) throws IOException, InterruptedException {
        Long memberId;
        String newName = "";
        String newSurname = "";
        String newAddress = "";
        String userName;
        String userSurname;
        String userAddress;


        // check se i valori sono diversi da nullo e se sono stati alterati


        String nameToModify = nameMod.getText();
        String surnameToModify = surnameMod.getText();
        String addressToModify = addressMod.getText();

        if (userTable.getSelectionModel().getSelectedItem().getId() != null) {
            memberId = userTable.getSelectionModel().getSelectedItem().getId();
            userName = userTable.getSelectionModel().getSelectedItem().getName();
            userSurname = userTable.getSelectionModel().getSelectedItem().getSurname();
            userAddress = userTable.getSelectionModel().getSelectedItem().getAddress();

            if (!nameToModify.isBlank() && !surnameToModify.isBlank() && !addressToModify.isBlank()) {
                if (!userName.equals(nameToModify)) {
                    // add name mod to http body
                    newName = nameToModify;
                } else {
                    newName = userName;
                }

                if (!userSurname.equals(surnameToModify)) {
                    // add surname mod to http body
                    newSurname = surnameToModify;
                } else {
                    newSurname = userSurname;
                }

                if (!userAddress.equals(addressToModify)) {
                    // add address mod to http body
                    newAddress = addressToModify;
                } else {
                    newAddress = userAddress;
                }


                if (userName.equals(nameToModify) && userSurname.equals(surnameToModify) && userAddress.equals(addressToModify)) {
                    // do nothing
                } else {
                    String body = "{\"name\":\"" + newName + "\",\"surname\":\"" + newSurname + "\",\"address\":\"" + newAddress + "\"}";

                    HttpResponse<String> responsePut = httpFunctions.PUT("/members/" + memberId, body);

                    System.out.println(responsePut);

                    setTable();
                    nameMod.clear();
                    surnameMod.clear();
                    addressMod.clear();
                }
            }
        } else {
            System.out.println("selezionare prima l'utente da modificare");
        }


    }
}
