package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;

import model.PMSModel;

import model.ProductOwner;
import model.ScrumMaster;
import model.TeamMember;
import org.controlsfx.control.textfield.TextFields;

import java.util.Optional;

public class CreateTeamViewController {
    @FXML private TextField teamMembersInputField;
    @FXML private ComboBox<String> roleComboBox;
    @FXML private TableView<TeamMemberViewModel> teamMembersTable;
    @FXML private TableColumn<TeamMemberViewModel, String> nameColumn;
    @FXML private TableColumn<TeamMemberViewModel, String> roleColumn;
    @FXML private Label errorLabel;

    private ViewHandler viewHandler;
    private PMSModel model;
    private Region root;

    private TeamViewModel viewModel;

    public CreateTeamViewController() {
        // called by FXMLLoader
    }

    public void init(ViewHandler viewHandler, PMSModel model, Region root) {
        this.viewHandler = viewHandler;
        this.model = model;
        this.root = root;

        this.viewModel = new TeamViewModel(model);

        // --- Team List Table ---
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        roleColumn.setCellValueFactory(cellData -> cellData.getValue().getRoleProperty());
        teamMembersTable.setItems(viewModel.getTeamList());

        reset();
    }

    public void reset() {
        errorLabel.setText("");

        teamMembersInputField.setText("");
        TextFields.bindAutoCompletion(teamMembersInputField, model.getEmployeeNameList());

        // Role Combo Box
        roleComboBox.getItems().removeAll(roleComboBox.getItems());
        roleComboBox.getItems().addAll("Team Member", "Scrum Master", "Product Owner");
        roleComboBox.getSelectionModel().selectFirst();

        // update the Team List
        viewModel.update();
    }

    public Region getRoot() {
        return root;
    }

    @FXML
    public void addTeamMemberButton() {
        try {
            // if team member is already in the team throw an error
            if (model.getTeamMemberNameList().contains(teamMembersInputField.getText().strip())) {
                throw new IllegalArgumentException("Team Member is already in the team");
            }

            TeamMember teamMember = model.getEmployee(teamMembersInputField.getText().strip());
            switch (roleComboBox.getValue()) {
                case "Team Member":
                    model.getFocusProject().getTeam().addTeamMember(teamMember.copy());
                    break;
                case "Scrum Master":
                    if (model.isPresent("ScrumMaster")) {
                        throw new IllegalArgumentException("Scrum Master is already present in the team.");
                    }
                    model.getFocusProject().getTeam().addTeamMember(new ScrumMaster(teamMember.getName()));
                    break;
                case "Product Owner":
                    if (model.isPresent("ProductOwner")) {
                        throw new IllegalArgumentException("Product Owner is already present in the team.");
                    }
                    model.getFocusProject().getTeam().addTeamMember(new ProductOwner(teamMember.getName()));
                    break;
            }

            reset();
        }
        catch (IllegalArgumentException | ClassNotFoundException e) {
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML void removeTeamMemberButton() {
        try {
            TeamMemberViewModel selectedItem = teamMembersTable.getSelectionModel().getSelectedItem();

            if (confirmation()) {
                TeamMember teamMember = model.getTeamMember(selectedItem.getNameProperty().get().strip());

                model.getFocusProject().getTeam().removeTeamMember(teamMember);
                viewModel.removeTeamMember(teamMember);
                teamMembersTable.getSelectionModel().clearSelection();
            }
            reset();
        }
        catch (Exception e) {
            errorLabel.setText("Please select an item");
        }
    }

    private boolean confirmation() {
        TeamMemberViewModel selectedItem = teamMembersTable.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Removing the team member \"" + selectedItem.getNameProperty().get() + "\"");

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    @FXML void backButtonPressed() {
        viewHandler.openView("ProjectView");
    }

    @FXML
    private void onEnter(ActionEvent event) {
        if (event.getSource() == teamMembersInputField) {
            addTeamMemberButton();
        }
    }
}
