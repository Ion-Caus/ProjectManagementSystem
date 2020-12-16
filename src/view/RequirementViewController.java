package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;

import mediator.PMSModel;
import model.Requirement;

import org.controlsfx.control.textfield.TextFields;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RequirementViewController {
    @FXML private TextField titleField;
    @FXML private TextArea descriptionArea;
    @FXML private ComboBox<String> statusBox;
    @FXML private ComboBox<String> typeBox;
    @FXML private DatePicker deadlinePicker;
    @FXML private DatePicker estimatePicker;
    @FXML private TextField idField;
    @FXML private TextField hoursWorkedField;
    @FXML private Label errorLabel;
    @FXML private Label pathLabel;

    @FXML private Button openTaskListButton;

    @FXML private TextField responsibleTeamMemberInputField;

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
            statusBox.getItems().addAll(Requirement.STATUS_NOT_STARTED, Requirement.STATUS_IN_PROCESS,Requirement.STATUS_WAITING_FOR_APPROVAL, Requirement.STATUS_APPROVED, Requirement.STATUS_REJECTED);
            statusBox.getSelectionModel().select(Requirement.STATUS_NOT_STARTED);

            // Type ComboBox
            typeBox.getItems().removeAll(typeBox.getItems());
            typeBox.getItems().addAll(Requirement.TYPE_FUNCTIONAL, Requirement.TYPE_NON_FUNCTIONAL, Requirement.TYPE_PROJECT_RELATED);
            typeBox.getSelectionModel().select(Requirement.TYPE_FUNCTIONAL);

            // Deadline Picker
            deadlinePicker.setEditable(false);
            deadlinePicker.setValue(LocalDate.now());

            // Estimate Picker
            estimatePicker.setEditable(false);
            estimatePicker.setValue(LocalDate.now());

            // responsible Team Member
            responsibleTeamMemberInputField.setText("");

            idField.setText("");
            hoursWorkedField.setText("");

            // Open Task List Button
            openTaskListButton.setVisible(false);

            // path
            pathLabel.setText(model.getFocusProject().getName() + "/");
        }
        // View button was pressed
        else {
            titleField.setText(model.getFocusRequirement().getTitle());
            descriptionArea.setText(model.getFocusRequirement().getDescription());

            // Status ComboBox
            statusBox.getItems().removeAll(statusBox.getItems());
            statusBox.getItems().addAll(Requirement.STATUS_NOT_STARTED, Requirement.STATUS_IN_PROCESS, Requirement.STATUS_WAITING_FOR_APPROVAL, Requirement.STATUS_APPROVED, Requirement.STATUS_REJECTED);
            statusBox.getSelectionModel().select(model.getFocusRequirement().getStatus());

            // Type ComboBox
            typeBox.getItems().removeAll(typeBox.getItems());
            typeBox.getItems().addAll(Requirement.TYPE_FUNCTIONAL, Requirement.TYPE_NON_FUNCTIONAL, Requirement.TYPE_PROJECT_RELATED);
            typeBox.getSelectionModel().select(model.getFocusRequirement().getType());

            // Deadline Picker
            deadlinePicker.setValue(model.getFocusRequirement().getDeadline());

            // Estimate Picker
            estimatePicker.setValue(model.getFocusRequirement().getEstimate());

            // responsible Team Member
            responsibleTeamMemberInputField.setText(model.getFocusRequirement().getResponsibleTeamMember().getName());

            // show id
            idField.setText(model.getFocusRequirement().getId());

            // update and show time spent
            model.getFocusRequirement().updateTimeSpent();
            hoursWorkedField.setText( String.format("%.2f", (double)model.getFocusRequirement().getTimeSpent() / 60 ));

            // Open Task List Button
            openTaskListButton.setVisible(true);

            // path
            pathLabel.setText(model.getFocusProject().getName() + "/" + model.getFocusRequirement().getTitle());
        }
        errorLabel.setText("");

        //add Responsible Team Member from Team List
        TextFields.bindAutoCompletion(responsibleTeamMemberInputField, model.getTeamMemberNameList());

        //formatting the Deadline DatePicker from MM/dd/yyyy to yyyy-MM-dd
        deadlinePicker.getEditor().setText(
                DateTimeFormatter.ofPattern("yyyy-MM-dd").format(deadlinePicker.getValue())
        );
        deadlinePicker.setOnAction(event -> deadlinePicker.getEditor().setText(
                DateTimeFormatter.ofPattern("yyyy-MM-dd").format(deadlinePicker.getValue())
        ));

        // setting limits to deadline
        deadlinePicker.setDayCellFactory(dateCell -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                // from current date until project's deadline
                setDisable(item.isBefore(LocalDate.now()) || item.isAfter(model.getFocusProject().getDeadline()));
            }});

        //formatting the Estimate DatePicker from MM/dd/yyyy to yyyy-MM-dd
        estimatePicker.getEditor().setText(
                DateTimeFormatter.ofPattern("yyyy-MM-dd").format(estimatePicker.getValue())
        );
        estimatePicker.setOnAction(event -> estimatePicker.getEditor().setText(
                DateTimeFormatter.ofPattern("yyyy-MM-dd").format(estimatePicker.getValue())
        ));

        // setting limits to estimate
        estimatePicker.setDayCellFactory(dateCell -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                // from current date until project's estimate
                setDisable(item.isBefore(LocalDate.now()) || item.isAfter(model.getFocusProject().getEstimate()));
            }});
    }

    public Region getRoot() {
        return root;
    }

    @FXML
    private void openTaskList() {
        submitButtonPressed();
        viewHandler.openView("TaskListView");
    }

    @FXML
    private void submitButtonPressed() {
        try {
            // Add button was pressed
            if (model.isAdding()) {
                if (titleField.getText().isEmpty()) {
                    throw new IllegalArgumentException("Please enter the title of requirement first.");
                }

                model.addRequirement(new Requirement(
                        titleField.getText(),
                        statusBox.getSelectionModel().getSelectedItem(),
                        typeBox.getSelectionModel().getSelectedItem(),
                        descriptionArea.getText(),
                        deadlinePicker.getValue(),
                        estimatePicker.getValue(),
                        model.getTeamMember(responsibleTeamMemberInputField.getText().strip())
                ));
            }
            // View button was pressed
            else {
                model.getFocusRequirement().setTitle(titleField.getText());
                model.getFocusRequirement().setDescription(descriptionArea.getText());
                model.getFocusRequirement().setStatus(statusBox.getSelectionModel().getSelectedItem());
                model.getFocusRequirement().setType(typeBox.getSelectionModel().getSelectedItem());
                model.getFocusRequirement().setDeadline(deadlinePicker.getValue());
                model.getFocusRequirement().setEstimate(estimatePicker.getValue());
                model.getFocusRequirement().setResponsibleTeamMember(model.getTeamMember(responsibleTeamMemberInputField.getText().strip()));
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

    @FXML
    private void onEnter(ActionEvent event) {
        if (event.getSource() == titleField) {
            submitButtonPressed();
        }
    }
}
