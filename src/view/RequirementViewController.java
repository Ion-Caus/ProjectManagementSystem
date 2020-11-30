package view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import model.MyDate;
import model.PMSModel;

import model.Requirement;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    private PMSModel model;
    private Region root;


    public RequirementViewController() {
        // called by FXMLLoader
    }

    public void init(ViewHandler viewHandler, PMSModel model, Region root) {
        this.viewHandler = viewHandler;
        this.model = model;
        this.root = root;

        reset();
    }

    public void reset() {
        // Add button was pressed
        if (model.isAdding()) {
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
            deadlinePicker.setEditable(false);
            // setting the default deadline in 2 weeks
            deadlinePicker.setValue(LocalDate.now().plusDays(14));

            idField.setText("");
            hoursWorkedField.setText("");

            // Open Requirement List Button
            //TODO openTaskListButton
            //openTaskListButton.setDisable(true);
        }
        // View button was pressed
        else {
            titleField.setText(model.getFocusRequirement().getTitle());
            descriptionArea.setText(model.getFocusRequirement().getDescription());

            // Status ComboBox
            statusBox.getItems().removeAll(statusBox.getItems());
            statusBox.getItems().addAll(Requirement.STATUS_UNASSIGNED, Requirement.STATUS_IN_PROCESS, Requirement.STATUS_WAITING_FOR_APPROVAL, Requirement.STATUS_APPROVED, Requirement.STATUS_REJECTED);
            statusBox.getSelectionModel().select(model.getFocusRequirement().getStatus());

            // Type ComboBox
            typeBox.getItems().removeAll(typeBox.getItems());
            typeBox.getItems().addAll(Requirement.TYPE_FUNCTIONAL, Requirement.TYPE_NON_FUNCTIONAL, Requirement.TYPE_PROJECT_RELATED);
            typeBox.getSelectionModel().select(model.getFocusRequirement().getType());

            // Date Picker
            deadlinePicker.setValue(
                    LocalDate.of(
                            model.getFocusRequirement().getDeadline().getYear(),
                            model.getFocusRequirement().getDeadline().getMonth(),
                            model.getFocusRequirement().getDeadline().getDay()
                    ));

            idField.setText(model.getFocusRequirement().getId());
            hoursWorkedField.setText(Integer.toString(model.getFocusRequirement().getTimeSpent()));

            // Open Requirement List Button
            //TODO openTaskListButton
            //openReqListButton.setDisable(false);
        }
        errorLabel.setText("");

        //formatting the DatePicker from MM/dd/yyyy to dd/MM/yyyy
        deadlinePicker.getEditor().setText(
                DateTimeFormatter.ofPattern("dd/MM/yyyy").format(deadlinePicker.getValue())
        );
        deadlinePicker.setOnAction(event -> {
            deadlinePicker.getEditor().setText(
                    DateTimeFormatter.ofPattern("dd/MM/yyyy").format(deadlinePicker.getValue())
            );
        });
    }

    public Region getRoot() {
        return root;
    }

    @FXML
    private void submitButtonPressed() {
        try {
            MyDate deadline = new MyDate(
                    deadlinePicker.getValue().getDayOfMonth(),
                    deadlinePicker.getValue().getMonthValue(),
                    deadlinePicker.getValue().getYear()
            );

            // Add button was pressed
            if (model.isAdding()) {
                model.addRequirement(new Requirement(
                        titleField.getText(),
                        statusBox.getSelectionModel().getSelectedItem(),
                        typeBox.getSelectionModel().getSelectedItem(),
                        descriptionArea.getText(),
                        deadline
                ));
            }
            // View button was pressed
            else {
                model.getFocusRequirement().setTitle(titleField.getText());
                model.getFocusRequirement().setDescription(descriptionArea.getText());
                model.getFocusRequirement().setStatus(statusBox.getSelectionModel().getSelectedItem());
                model.getFocusRequirement().setType(typeBox.getSelectionModel().getSelectedItem());
                model.getFocusRequirement().setDeadline(deadline);
            }
            viewHandler.openView("RequirementListView");
        }
        catch (IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML
    private void cancelButtonPressed() {
        viewHandler.openView("RequirementListView");
    }

}
