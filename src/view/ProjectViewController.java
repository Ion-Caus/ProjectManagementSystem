package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;

import model.MyDate;
import model.Project;
import model.PMSModel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ProjectViewController {
    @FXML private TextField nameField;
    @FXML private ComboBox<String> statusBox;
    @FXML private DatePicker deadlinePicker;
    @FXML private TextField idField;
    @FXML private TextField hoursWorkedField;
    @FXML private Label errorLabel;

    @FXML private Button openReqListButton;

    private ViewHandler viewHandler;
    private PMSModel model;
    private Region root;

    public ProjectViewController() {
        //called by FXMLLoader
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
            nameField.setText("");

            // Status ComboBox
            statusBox.getItems().removeAll(statusBox.getItems());
            statusBox.getItems().addAll(Project.STATUS_CREATED, Project.STATUS_IN_PROCESS, Project.STATUS_WAITING_FOR_APPROVAL, Project.STATUS_FINISHED);
            statusBox.getSelectionModel().select(Project.STATUS_CREATED);

            // Date Picker
            deadlinePicker.setEditable(false);
                // setting the default deadline in 2 weeks
            deadlinePicker.setValue(LocalDate.now().plusDays(14));

            idField.setText("");
            hoursWorkedField.setText("");

            // Open Requirement List Button
            openReqListButton.setDisable(true);
        }
        // View button was pressed
        else {
            nameField.setText(model.getFocusProject().getName());

            // Status ComboBox
            statusBox.getItems().removeAll(statusBox.getItems());
            statusBox.getItems().addAll(Project.STATUS_CREATED, Project.STATUS_IN_PROCESS, Project.STATUS_WAITING_FOR_APPROVAL, Project.STATUS_FINISHED);
            statusBox.getSelectionModel().select(model.getFocusProject().getStatus());

            // Date Picker
            deadlinePicker.setValue(
                    LocalDate.of(
                            model.getFocusProject().getDeadline().getYear(),
                            model.getFocusProject().getDeadline().getMonth(),
                            model.getFocusProject().getDeadline().getDay()
                    ));

            idField.setText(model.getFocusProject().getId());
            hoursWorkedField.setText(Integer.toString(model.getFocusProject().getTimeSpent()));

            // Open Requirement List Button
            openReqListButton.setDisable(false);
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
    private  void openRequirementList() {
        submitButtonPressed();
        viewHandler.openView("RequirementListView");
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
                model.addProject(
                        new Project(
                                nameField.getText(),
                                statusBox.getSelectionModel().getSelectedItem(),
                                deadline
                        ));
            }
            // View button was pressed
            else {
                model.getFocusProject().setName(nameField.getText());
                model.getFocusProject().setStatus(statusBox.getSelectionModel().getSelectedItem());
                model.getFocusProject().setDeadline(deadline);
            }

            viewHandler.openView("ProjectListView");
        }
        catch (IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML
    private void cancelButtonPressed() {
        viewHandler.openView("ProjectListView");
    }

    @FXML
    private void onEnter(ActionEvent event) {
        if (event.getSource() == nameField) {
            submitButtonPressed();
        }
    }
}
