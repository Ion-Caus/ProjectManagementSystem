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
            deadlinePicker.setValue(
                    LocalDate.of(
                            model.getFocusProject().getDeadline().getYear(),
                            model.getFocusProject().getDeadline().getMonth(),
                            model.getFocusProject().getDeadline().getDay()
                    ));

            // Estimate Picker
            estimatePicker.setValue(
                    LocalDate.of(
                            model.getFocusProject().getEstimate().getYear(),
                            model.getFocusProject().getEstimate().getMonth(),
                            model.getFocusProject().getEstimate().getDay()
                    ));

            idField.setText(model.getFocusProject().getId());
            hoursWorkedField.setText(Integer.toString(model.getFocusProject().getTimeSpent()));

            // Open Requirement List Button
            openReqListButton.setVisible(true);

            // Add Team Button
            if (model.getFocusProject().getTeam().size() > 0) {
                addTeamMemberButton.setText("View");
            }
        }
        errorLabel.setText("");

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

    private void createProject() {
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
            model.addProject(
                    new Project(
                            nameField.getText(),
                            statusBox.getSelectionModel().getSelectedItem(),
                            deadline,
                            estimate,
                            new Team()
                    ));
        }
        // View button was pressed
        else {
            model.getFocusProject().setName(nameField.getText());
            model.getFocusProject().setStatus(statusBox.getSelectionModel().getSelectedItem());
            model.getFocusProject().setDeadline(deadline);
            model.getFocusProject().setEstimate(estimate);
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
            createProject();
            // setting the last created project to focus
            model.setFocusProject(model.getProjectList().get(model.projectListSize()-1));
            model.setAdding(false);
            viewHandler.openView("CreateTeamView");
        }
        catch (Exception e) {
            errorLabel.setText("Please enter the name of project first");
        }
    }
}
