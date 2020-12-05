package view;

import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;

import model.PMSModel;

import model.TeamMember;
import org.controlsfx.control.textfield.TextFields;

import java.util.Optional;

public class CreateTeamViewController {
    @FXML private TextField teamMembersInputField;
    @FXML private ListView<String> teamMembersListView;
    @FXML private Label errorLabel;

    private ViewHandler viewHandler;
    private PMSModel model;
    private Region root;

    private ObservableList<String> teamMemberNames;

    public CreateTeamViewController() {
        // called by FXMLLoader
    }

    public void init(ViewHandler viewHandler, PMSModel model, Region root) {
        this.viewHandler = viewHandler;
        this.model = model;
        this.root = root;

        this.teamMemberNames = FXCollections.observableArrayList();
        reset();
    }

    public void reset() {
        errorLabel.setText("");

        teamMembersInputField.setText("");
        TextFields.bindAutoCompletion(teamMembersInputField, model.getEmployeeNameList());

        // adding the teamMembers' name in the ListView
        teamMemberNames.clear();
        teamMemberNames.addAll(model.getFocusProject().getTeam().getTeamMemberNameList());
        teamMembersListView.setItems(teamMemberNames);
    }

    public Region getRoot() {
        return root;
    }

    @FXML
    public void addTeamMemberButton() {
        try {
            model.getFocusProject().getTeam().addTeamMember(model.getEmployee(teamMembersInputField.getText()));

            reset();
        }
        catch (IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML void removeTeamMemberButton() {
        try {
            String name = teamMembersListView.getSelectionModel().getSelectedItem();

            if (confirmation()) {
                TeamMember teamMember = model.getTeamMember(name);
                model.getFocusProject().getTeam().removeTeamMember(teamMember);
                teamMembersListView.getSelectionModel().clearSelection();
            }
            reset();
        }
        catch (Exception e) {
            errorLabel.setText("Please select an item");
        }
    }

    private boolean confirmation() {
        String selectedItem = teamMembersListView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Removing the team member \"" + selectedItem + "\"");

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    @FXML void submitButtonPressed() {
        viewHandler.openView("ProjectView");
    }

    @FXML
    private void onEnter(ActionEvent event) {
        if (event.getSource() == teamMembersInputField) {
            addTeamMemberButton();
        }
    }
}
