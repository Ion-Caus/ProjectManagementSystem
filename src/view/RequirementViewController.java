package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;

import model.MyDate;
import model.PMSModel;
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
            statusBox.getItems().addAll(Requirement.STATUS_IN_PROCESS,Requirement.STATUS_WAITING_FOR_APPROVAL, Requirement.STATUS_APPROVED, Requirement.STATUS_REJECTED);
            statusBox.getSelectionModel().select(Requirement.STATUS_IN_PROCESS);

            // Type ComboBox
            typeBox.getItems().removeAll(typeBox.getItems());
            typeBox.getItems().addAll(Requirement.TYPE_FUNCTIONAL, Requirement.TYPE_NON_FUNCTIONAL, Requirement.TYPE_PROJECT_RELATED);
            typeBox.getSelectionModel().select(Requirement.TYPE_FUNCTIONAL);

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

            // Open Task List Button
            openTaskListButton.setVisible(false);
        }
        // View button was pressed
        else {
            titleField.setText(model.getFocusRequirement().getTitle());
            descriptionArea.setText(model.getFocusRequirement().getDescription());

            // Status ComboBox
            statusBox.getItems().removeAll(statusBox.getItems());
            statusBox.getItems().addAll(Requirement.STATUS_IN_PROCESS, Requirement.STATUS_WAITING_FOR_APPROVAL, Requirement.STATUS_APPROVED, Requirement.STATUS_REJECTED);
            statusBox.getSelectionModel().select(model.getFocusRequirement().getStatus());

            // Type ComboBox
            typeBox.getItems().removeAll(typeBox.getItems());
            typeBox.getItems().addAll(Requirement.TYPE_FUNCTIONAL, Requirement.TYPE_NON_FUNCTIONAL, Requirement.TYPE_PROJECT_RELATED);
            typeBox.getSelectionModel().select(model.getFocusRequirement().getType());

            // Deadline Picker
            deadlinePicker.setValue(
                    LocalDate.of(
                            model.getFocusRequirement().getDeadline().getYear(),
                            model.getFocusRequirement().getDeadline().getMonth(),
                            model.getFocusRequirement().getDeadline().getDay()
                    ));

            // Estimate Picker
            estimatePicker.setValue(
                    LocalDate.of(
                            model.getFocusRequirement().getEstimate().getYear(),
                            model.getFocusRequirement().getEstimate().getMonth(),
                            model.getFocusRequirement().getEstimate().getDay()
                    ));

            // responsible Team Member
            responsibleTeamMemberInputField.setText(model.getFocusRequirement().getResponsibleTeamMember().getName());
            System.out.println(model.getFocusRequirement().getResponsibleTeamMember().getName());

            idField.setText(model.getFocusRequirement().getId());
            hoursWorkedField.setText(Integer.toString(model.getFocusRequirement().getTimeSpent()));

            // Open Task List Button
            openTaskListButton.setVisible(true);
        }
        errorLabel.setText("");

       //add Employees
        //TODO change to project team list
        TextFields.bindAutoCompletion(responsibleTeamMemberInputField, model.getEmployeeNameList());

        //formatting the Deadline DatePicker from MM/dd/yyyy to dd/MM/yyyy
        deadlinePicker.getEditor().setText(
                DateTimeFormatter.ofPattern("dd/MM/yyyy").format(deadlinePicker.getValue())
        );
        deadlinePicker.setOnAction(event -> {
            deadlinePicker.getEditor().setText(
                    DateTimeFormatter.ofPattern("dd/MM/yyyy").format(deadlinePicker.getValue())
            );
        });
        //formatting the Estimate DatePicker from MM/dd/yyyy to dd/MM/yyyy
        estimatePicker.getEditor().setText(
                DateTimeFormatter.ofPattern("dd/MM/yyyy").format(estimatePicker.getValue())
        );
        estimatePicker.setOnAction(event -> {
            estimatePicker.getEditor().setText(
                    DateTimeFormatter.ofPattern("dd/MM/yyyy").format(estimatePicker.getValue())
            );
        });
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
                model.addRequirement(new Requirement(
                        titleField.getText(),
                        statusBox.getSelectionModel().getSelectedItem(),
                        typeBox.getSelectionModel().getSelectedItem(),
                        descriptionArea.getText(),
                        deadline,
                        estimate,
                        model.getEmployee(responsibleTeamMemberInputField.getText())
                ));
            }
            // View button was pressed
            else {
                model.getFocusRequirement().setTitle(titleField.getText());
                model.getFocusRequirement().setDescription(descriptionArea.getText());
                model.getFocusRequirement().setStatus(statusBox.getSelectionModel().getSelectedItem());
                model.getFocusRequirement().setType(typeBox.getSelectionModel().getSelectedItem());
                model.getFocusRequirement().setDeadline(deadline);
                model.getFocusRequirement().setEstimate(estimate);
                model.getFocusRequirement().setResponsibleTeamMember(model.getEmployee(responsibleTeamMemberInputField.getText()));
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
