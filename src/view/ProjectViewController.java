package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;

import model.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ProjectViewController {
    @FXML private TextField nameField;
    @FXML private ComboBox<String> statusBox;
    @FXML private DatePicker deadlinePicker;
    @FXML private DatePicker estimatePicker;
    @FXML private TextField idField;
    @FXML private TextField hoursWorkedField;
    @FXML private Label errorLabel;

    @FXML private Button addTeamMemberButton;
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

            // Deadline Picker
            deadlinePicker.setEditable(false);
                // setting the default deadline in 2 weeks
            deadlinePicker.setValue(LocalDate.now().plusDays(14));

            // Estimate Picker
            estimatePicker.setEditable(false);
            // setting the default deadline in 1 weeks
            estimatePicker.setValue(LocalDate.now().plusDays(7));


            idField.setText("");
            hoursWorkedField.setText("");

            // Open Requirement List Button
            openReqListButton.setVisible(false);

            // Add Team Button
            addTeamMemberButton.setText("Add");
        }
        // View button was pressed
        else {
            nameField.setText(model.getFocusProject().getName());

            // Status ComboBox
            statusBox.getItems().removeAll(statusBox.getItems());
            statusBox.getItems().addAll(Project.STATUS_CREATED, Project.STATUS_IN_PROCESS, Project.STATUS_WAITING_FOR_APPROVAL, Project.STATUS_FINISHED);
            statusBox.getSelectionModel().select(model.getFocusProject().getStatus());

            // Deadline Picker
            deadlinePicker.setValue(model.getFocusProject().getDeadline());

            // Estimate Picker
            estimatePicker.setValue(model.getFocusProject().getEstimate());

            // show id
            idField.setText(model.getFocusProject().getId());

            // update and show time spent
            model.getFocusProject().updateTimeSpent();
            hoursWorkedField.setText(Integer.toString(model.getFocusProject().getTimeSpent()));

            // Open Requirement List Button
            openReqListButton.setVisible(true);

            // Add Team Button
            if (model.getFocusProject().getTeam().size() > 0) {
                addTeamMemberButton.setText("View");
            }
        }
        errorLabel.setText("");

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

    private void createProject() {
        // Add button was pressed
        if (model.isAdding()) {
            if (nameField.getText().isEmpty()) {
                    throw new IllegalArgumentException("Please enter the title of requirement first.");
            }
            model.addProject(
                    new Project(
                            nameField.getText(),
                            statusBox.getSelectionModel().getSelectedItem(),
                            deadlinePicker.getValue(),
                            estimatePicker.getValue(),
                            new Team()
                    ));
        }
        // View button was pressed
        else {
            model.getFocusProject().setName(nameField.getText());
            model.getFocusProject().setStatus(statusBox.getSelectionModel().getSelectedItem());
            model.getFocusProject().setDeadline(deadlinePicker.getValue());
            model.getFocusProject().setEstimate(estimatePicker.getValue());
        }
    }

    @FXML
    private void openRequirementList() {
        submitButtonPressed();
        viewHandler.openView("RequirementListView");
    }

    @FXML
    private void submitButtonPressed() {
        try {
            createProject();
            viewHandler.openView("ProjectListView");
        }
        catch (Exception e) {
            errorLabel.setText("Please enter the name of project first.");
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

    @FXML
    private void addTeamMemberButton() {
        try {
            // set the focus to the last one (just created)
            if (model.isAdding()) {
                createProject();
                model.setFocusProject(model.getProjectList().get(model.projectListSize() - 1));
                model.setAdding(false);
            }
            viewHandler.openView("CreateTeamView");
        }
        catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }
}
