package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;

import model.MyDate;
import model.PMSModel;
import model.Task;
import org.controlsfx.control.textfield.TextFields;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TaskViewController {
    @FXML private TextField titleField;
    @FXML private TextArea descriptionArea;
    @FXML private ComboBox<String> statusBox;
    @FXML private DatePicker deadlinePicker;
    @FXML private DatePicker estimatePicker;
    @FXML private TextField idField;
    @FXML private TextField hoursWorkedField;
    @FXML private Label errorLabel;

    @FXML private TextField responsibleTeamMemberInputField;

    private ViewHandler viewHandler;
    private PMSModel model;
    private Region root;

    public TaskViewController() {
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
            statusBox.getItems().addAll(Task.STATUS_NOT_STARTED, Task.STATUS_IN_PROCESS, Task.STATUS_COMPLETED);
            statusBox.getSelectionModel().select(Task.STATUS_NOT_STARTED);

            // Deadline Picker
            deadlinePicker.setEditable(false);
            // setting the default deadline in 2 weeks
            deadlinePicker.setValue(LocalDate.now().plusDays(14));

            // Estimate Picker
            estimatePicker.setEditable(false);
            // setting the default deadline in 1 weeks
            estimatePicker.setValue(LocalDate.now().plusDays(7));

            // responsible Team Member
            responsibleTeamMemberInputField.setText("");

            idField.setText("");
            hoursWorkedField.setText("");
        }
        // View button was pressed
        else {
            titleField.setText(model.getFocusTask().getTitle());
            descriptionArea.setText(model.getFocusTask().getDescription());

            // Status ComboBox
            statusBox.getItems().removeAll(statusBox.getItems());
            statusBox.getItems().addAll(Task.STATUS_NOT_STARTED, Task.STATUS_IN_PROCESS, Task.STATUS_COMPLETED);
            statusBox.getSelectionModel().select(model.getFocusTask().getStatus());

            // Deadline Picker
            deadlinePicker.setValue(
                    LocalDate.of(
                            model.getFocusTask().getDeadline().getYear(),
                            model.getFocusTask().getDeadline().getMonth(),
                            model.getFocusTask().getDeadline().getDay()
                    ));

            // Estimate Picker
            estimatePicker.setValue(
                    LocalDate.of(
                            model.getFocusTask().getEstimate().getYear(),
                            model.getFocusTask().getEstimate().getMonth(),
                            model.getFocusTask().getEstimate().getDay()
                    ));

            idField.setText(model.getFocusTask().getId());
            hoursWorkedField.setText(Integer.toString(model.getFocusTask().getTimeSpent()));
        }
        errorLabel.setText("");

        //add Responsible Team Member from Team List
        TextFields.bindAutoCompletion(responsibleTeamMemberInputField, model.getFocusProject().getTeam().getTeamMemberNameList());

        //formatting the Deadline DatePicker from MM/dd/yyyy to dd/MM/yyyy
        deadlinePicker.getEditor().setText(
                DateTimeFormatter.ofPattern("dd/MM/yyyy").format(deadlinePicker.getValue())
        );
        deadlinePicker.setOnAction(event -> deadlinePicker.getEditor().setText(
                DateTimeFormatter.ofPattern("dd/MM/yyyy").format(deadlinePicker.getValue())
        ));
        //formatting the Estimate DatePicker from MM/dd/yyyy to dd/MM/yyyy
        estimatePicker.getEditor().setText(
                DateTimeFormatter.ofPattern("dd/MM/yyyy").format(estimatePicker.getValue())
        );
        estimatePicker.setOnAction(event -> estimatePicker.getEditor().setText(
                DateTimeFormatter.ofPattern("dd/MM/yyyy").format(estimatePicker.getValue())
        ));
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

            MyDate estimate = new MyDate(
                    estimatePicker.getValue().getDayOfMonth(),
                    estimatePicker.getValue().getMonthValue(),
                    estimatePicker.getValue().getYear()
            );

            // Add button was pressed
            if (model.isAdding()) {
                if (titleField.getText().isEmpty()) {
                    throw new IllegalArgumentException("Please enter the title of task first.");
                }

                model.addTask(new Task(
                        titleField.getText(),
                        statusBox.getSelectionModel().getSelectedItem(),
                        descriptionArea.getText(),
                        deadline,
                        estimate,
                        model.getTeamMember(responsibleTeamMemberInputField.getText())
                ));
            }
            // View button was pressed
            else {
                model.getFocusTask().setTitle(titleField.getText());
                model.getFocusTask().setDescription(descriptionArea.getText());
                model.getFocusTask().setStatus(statusBox.getSelectionModel().getSelectedItem());
                model.getFocusTask().setDeadline(deadline);
                model.getFocusTask().setEstimate(estimate);
                model.getFocusTask().setResponsibleTeamMember(model.getTeamMember(responsibleTeamMemberInputField.getText()));
            }
            viewHandler.openView("TaskListView");
        }
        catch (IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML
    private void cancelButtonPressed() {
        viewHandler.openView("TaskListView");
    }

    @FXML
    private void onEnter(ActionEvent event) {
        if (event.getSource() == titleField) {
            responsibleTeamMemberInputField.requestFocus();
        }
        else if (event.getSource() == responsibleTeamMemberInputField) {
            submitButtonPressed();
        }
    }

}
