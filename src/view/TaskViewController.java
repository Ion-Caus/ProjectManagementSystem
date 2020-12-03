package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;

import model.MyDate;
import model.PMSModel;
import model.Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TaskViewController {
    @FXML private TextField titleField;
    @FXML private TextArea descriptionArea;
    @FXML private ComboBox<String> statusBox;
    @FXML private DatePicker deadlinePicker;
    @FXML private TextField idField;
    @FXML private TextField hoursWorkedField;
    @FXML private Label errorLabel;

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

            // Date Picker
            deadlinePicker.setEditable(false);
            // setting the default deadline in 2 weeks
            deadlinePicker.setValue(LocalDate.now().plusDays(14));

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

            // Date Picker
            deadlinePicker.setValue(
                    LocalDate.of(
                            model.getFocusTask().getDeadline().getYear(),
                            model.getFocusTask().getDeadline().getMonth(),
                            model.getFocusTask().getDeadline().getDay()
                    ));

            idField.setText(model.getFocusTask().getId());
            hoursWorkedField.setText(Integer.toString(model.getFocusTask().getTimeSpent()));
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
                model.addTask(new Task(
                        titleField.getText(),
                        statusBox.getSelectionModel().getSelectedItem(),
                        descriptionArea.getText(),
                        deadline
                ));
            }
            // View button was pressed
            else {
                model.getFocusTask().setTitle(titleField.getText());
                model.getFocusTask().setDescription(descriptionArea.getText());
                model.getFocusTask().setStatus(statusBox.getSelectionModel().getSelectedItem());
                model.getFocusTask().setDeadline(deadline);
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
            submitButtonPressed();
        }
    }

}
