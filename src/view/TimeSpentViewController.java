package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;

import model.PMSModel;

import model.TeamMember;
import org.controlsfx.control.textfield.TextFields;


public class TimeSpentViewController {
    @FXML private TextField teamMembersInputField;
    @FXML private TextField timeSpentTextField;
    @FXML private TableView<TimeViewModel> timeSpentTable;
    @FXML private TableColumn<TimeViewModel, String > nameColumn;
    @FXML private TableColumn<TimeViewModel, String> timeColumn;
    @FXML private Label errorLabel;

    private ViewHandler viewHandler;
    private PMSModel model;
    private Region root;

    private TimeContainerViewModel viewModel;

    public TimeSpentViewController() {
        // called by FXMLLoader
    }

    public void init(ViewHandler viewHandler, PMSModel model, Region root) {
        this.viewHandler = viewHandler;
        this.model = model;
        this.root = root;

        this.viewModel = new TimeContainerViewModel(model);

        // --- Time Spent List Table ---
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        timeColumn.setCellValueFactory(cellData -> cellData.getValue().getTimeSpentProperty());
        timeSpentTable.setItems(viewModel.getTimeSpentList());

        reset();
    }

    public void reset() {
        errorLabel.setText("");

        teamMembersInputField.setText("");
        TextFields.bindAutoCompletion(teamMembersInputField, model.getTeamMemberNameList());

        timeSpentTextField.setText("");

        // update the Time Spent List
        viewModel.update();
    }

    public Region getRoot() {
        return root;
    }

    @FXML
    private void addTimeSpentButton() {
        try {
            TeamMember teamMember = model.getTeamMember(teamMembersInputField.getText().strip());

            model.getFocusTask().getTimeWorkedList().addTimeWorkedFor(teamMember, timeSpentTextField.getText());
            reset();
        }
        catch (IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML
    private void removeTimeSpentButton() {

    }

    @FXML
    private void backButtonPressed() {
        viewHandler.openView("TaskView");
    }

    @FXML
    private void onEnter(ActionEvent event) {
        if (event.getSource() == teamMembersInputField) {
            timeSpentTextField.requestFocus();
        }
        else if (event.getSource() == timeSpentTextField) {
            addTimeSpentButton();
        }
    }
}
