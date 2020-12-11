package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;

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
    @FXML private Label pathLabel;

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
            deadlinePicker.setValue(model.getFocusTask().getDeadline());

            // Estimate Picker
            estimatePicker.setValue(model.getFocusTask().getEstimate());

            // responsible Team Member
            responsibleTeamMemberInputField.setText(model.getFocusTask().getResponsibleTeamMember().getName());

            idField.setText(model.getFocusTask().getId());
            hoursWorkedField.setText(Integer.toString(model.getFocusTask().getTimeWorkedList().getTotalTimeWorked()));
        }
        errorLabel.setText("");
        pathLabel.setText(model.getFocusProject().getName() + "/" + model.getFocusRequirement().getTitle() + "/" + model.getFocusTask().getTitle());

        //add Responsible Team Member from Team List
        TextFields.bindAutoCompletion(responsibleTeamMemberInputField, model.getTeamMemberNameList());

        //formatting the Deadline DatePicker from MM/dd/yyyy to yyyy-MM-dd
        deadlinePicker.getEditor().setText(
                DateTimeFormatter.ofPattern("yyyy-MM-dd").format(deadlinePicker.getValue())
        );
        deadlinePicker.setOnAction(event -> deadlinePicker.getEditor().setText(
                DateTimeFormatter.ofPattern("yyyy-MM-dd").format(deadlinePicker.getValue())
        ));
        //formatting the Estimate DatePicker from MM/dd/yyyy to yyyy-MM-dd
        estimatePicker.getEditor().setText(
                DateTimeFormatter.ofPattern("yyyy-MM-dd").format(estimatePicker.getValue())
        );
        estimatePicker.setOnAction(event -> estimatePicker.getEditor().setText(
                DateTimeFormatter.ofPattern("yyyy-MM-dd").format(estimatePicker.getValue())
        ));
    }

    public Region getRoot() {
        return root;
    }

    private void createTask() {
        // Add button was pressed
        if (model.isAdding()) {
            if (titleField.getText().isEmpty()) {
                throw new IllegalArgumentException("Please enter the title of task first.");
            }

            model.addTask(new Task(
                    titleField.getText(),
                    statusBox.getSelectionModel().getSelectedItem(),
                    descriptionArea.getText(),
                    deadlinePicker.getValue(),
                    estimatePicker.getValue(),
                    model.getTeamMember(responsibleTeamMemberInputField.getText().strip())
            ));
        }
        // View button was pressed
        else {
            model.getFocusTask().setTitle(titleField.getText());
            model.getFocusTask().setDescription(descriptionArea.getText());
            model.getFocusTask().setStatus(statusBox.getSelectionModel().getSelectedItem());
            model.getFocusTask().setDeadline(deadlinePicker.getValue());
            model.getFocusTask().setEstimate(estimatePicker.getValue());
            model.getFocusTask().setResponsibleTeamMember(model.getTeamMember(responsibleTeamMemberInputField.getText().strip()));
        }
    }

    @FXML
    private void submitButtonPressed() {
        try {
            createTask();
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
            submitButtonPressed();
        }
    }

    @FXML
    private void addTimeSpentButton() {
        try {
            // set the focus to the last one (just created)
            if (model.isAdding()) {
                createTask();
                model.setFocusTask(model.getFocusRequirement().getTaskList().getTask(model.taskListSize() - 1));
                model.setAdding(false);
            }
            viewHandler.openView("TimeSpentView");
        }
        catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }

}
