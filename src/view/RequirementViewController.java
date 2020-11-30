package view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import model.MyDate;
import model.ProjectListModel;

import model.Requirement;


import java.time.LocalDate;

public class RequirementViewController {
    @FXML private TextField titleField;
    @FXML private TextArea descriptionArea;
    @FXML private ComboBox<String> statusBox;
    @FXML private ComboBox<String> typeBox;
    @FXML private DatePicker deadlinePicker;
    @FXML private TextField idField;
    @FXML private TextField hoursWorkedField;
    @FXML private Label errorLabel;

    private ViewHandler viewHandler;
    private ProjectListModel model;
    private Region root;


    public RequirementViewController() {
        // called by FXMLLoader
    }

    public void init(ViewHandler viewHandler, ProjectListModel model, Region root) {
        this.viewHandler = viewHandler;
        this.model = model;
        this.root = root;

        reset();
    }

    public void reset() {
        titleField.setText("");
        descriptionArea.setText("");

        // Status ComboBox
        statusBox.getItems().removeAll(statusBox.getItems());
        statusBox.getItems().addAll(Requirement.STATUS_UNASSIGNED, Requirement.STATUS_IN_PROCESS,Requirement.STATUS_WAITING_FOR_APPROVAL, Requirement.STATUS_APPROVED, Requirement.STATUS_REJECTED);
        statusBox.getSelectionModel().select(Requirement.STATUS_UNASSIGNED);

        // Type ComboBox
        typeBox.getItems().removeAll(typeBox.getItems());
        typeBox.getItems().addAll(Requirement.TYPE_FUNCTIONAL, Requirement.TYPE_NON_FUNCTIONAL, Requirement.TYPE_PROJECT_RELATED);
        typeBox.getSelectionModel().select(Requirement.TYPE_FUNCTIONAL);

        // Date Picker
        deadlinePicker.setValue(LocalDate.now());

        idField.setText("");
        hoursWorkedField.setText("");


        errorLabel.setText("");
    }

    public Region getRoot() {
        return root;
    }

    @FXML
    private void submitButtonPressed() {
        /*try {
            int day = deadlinePicker.getValue().getDayOfMonth();
            int month = deadlinePicker.getValue().getMonthValue();
            int year = deadlinePicker.getValue().getYear();
            model.addRequirement(new Requirement(
                    titleField.getText(),
                    statusBox.getSelectionModel().getSelectedItem(),
                    typeBox.getSelectionModel().getSelectedItem(),
                    descriptionArea.getText(),
                    new MyDate(day, month, year)
                    ));
            reset();
        }
        catch (IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
        } */
    }

    @FXML
    private void cancelButtonPressed() {
        viewHandler.openView("RequirementListView");
    }

}
