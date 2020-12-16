package view;

import javafx.collections.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.layout.Region;

import mediator.PMSModel;
import model.*;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.util.Optional;

public class ProjectListViewController {
    // projects tab
    @FXML private TableView<ProjectViewModel> projectListTable;
    @FXML private TableColumn<ProjectViewModel, String> idProjectColumn;
    @FXML private TableColumn<ProjectViewModel, String> nameProjectColumn;
    @FXML private TableColumn<ProjectViewModel, String> statusProjectColumn;
    @FXML private TableColumn<ProjectViewModel, String> deadlineProjectColumn;
    @FXML private TableColumn<ProjectViewModel, String> estimateProjectColumn;
    @FXML private Label errorLabelProject;

    //employees tab
    @FXML private TableView<TeamMemberViewModel> employeeListTable;
    @FXML private TableColumn<TeamMemberViewModel, String> nameEmployeeColumn;
    @FXML private Label errorLabelEmployee;

    //search tab
    @FXML private TextField searchInputField;
    private AutoCompletionBinding<String> autoCompletionBinding;
    @FXML private ComboBox<String> choiceBox;
    @FXML private ListView<String> resultListView;
    @FXML private Label infoLabelSearch;
    @FXML private Label errorLabelSearch;

    private ViewHandler viewHandler;
    private PMSModel model;
    private Region root;

    private ProjectListViewModel viewModelProject;
    private EmployeesViewModel viewModelEmployee;

    public ProjectListViewController() {
        // called by FXMLLoader
    }

    public void init(ViewHandler viewHandler, PMSModel model, Region root) {
        this.viewHandler = viewHandler;
        this.model = model;
        this.root = root;

        this.viewModelProject = new ProjectListViewModel(model);
        this.viewModelEmployee = new EmployeesViewModel(model);

        //---- Project List ----
        idProjectColumn.setCellValueFactory(cellData -> cellData.getValue().getIdProperty());
        nameProjectColumn.setCellValueFactory(cellDate -> cellDate.getValue().getNameProperty());
        statusProjectColumn.setCellValueFactory(cellDate -> cellDate.getValue().getStatusProperty());
        deadlineProjectColumn.setCellValueFactory(cellDate -> cellDate.getValue().getDeadlineProperty());
        estimateProjectColumn.setCellValueFactory(cellDate -> cellDate.getValue().getEstimateProperty());

        projectListTable.setItems(viewModelProject.getProjectList());
        //---------------------


        //---- Employee List ----
        nameEmployeeColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());

        employeeListTable.setItems(viewModelEmployee.getEmployeeList());
        //-----------------------

        //---- Search Tab ----
        choiceBox.getItems().addAll("Project", "Requirement", "Task", "Employee");
        choiceBox.getSelectionModel().select("Project");
        //--------------------

        reset();
    }

    public void reset() {
        errorLabelProject.setText("");
        viewModelProject.update();

        errorLabelEmployee.setText("");
        viewModelEmployee.update();

        //---- Search Tab ----
            //autocompletion set default to projects name
        searchInputField.setText("");
        autoCompletionBinding = TextFields.bindAutoCompletion(searchInputField, model.getProjectNameList());

            //info label
        infoLabelSearch.setText("");


            //error label
        errorLabelSearch.setText("");
        //--------------------
    }

    public Region getRoot() {
        return root;
    }

    //---------- ProjectList Methods ----------
    @FXML
    private void createProjectButton() {
        model.setAdding(true);
        viewHandler.openView("ProjectView");
    }

    @FXML
    private void viewProjectButton() {
        try {
            ProjectViewModel selectItem = projectListTable.getSelectionModel().getSelectedItem();

            //Setting the focusProject by getting the project by its ID
            model.setFocusProject(model.getProject(selectItem.getIdProperty().get()));
            model.setAdding(false);
            viewHandler.openView("ProjectView");
        }
        catch (Exception e) {
            errorLabelProject.setText("Please select an item");
        }
    }

    @FXML
    private void removeProjectButton() {
        try {
            ProjectViewModel selectItem = projectListTable.getSelectionModel().getSelectedItem();

            if (confirmationProject()) {
                Project project = model.getProject(selectItem.getIdProperty().get());
                model.removeProject(project);
                viewModelProject.removeProject(project);
                projectListTable.getSelectionModel().clearSelection();
            }
        }
        catch (Exception e) {
            errorLabelProject.setText("Please select an item");
        }
    }

    private boolean confirmationProject() {
        ProjectViewModel selectedItem = projectListTable.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(
                "Removing the project \"" +
                        selectedItem.getNameProperty().get() + "\"\n" +
                        "with the id: " + selectedItem.getIdProperty().get()
        );

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    @FXML
    private void openRequirementList(){
        try {
            ProjectViewModel selectItem = projectListTable.getSelectionModel().getSelectedItem();

            //Setting the focusProject by getting the project by its ID
            model.setFocusProject(model.getProject(selectItem.getIdProperty().get()));
            model.setAdding(false);
            viewHandler.openView("RequirementListView");
        }
        catch (Exception e) {
            errorLabelEmployee.setText("Please select an item");
        }
    }
    //-----------------------------------------

    //---------- EmployeeList Methods ----------
    @FXML
    private void addMemberButton() {
        errorLabelEmployee.setText("");
        try {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Employee");
            dialog.setHeaderText("What is the name of employee");

            Optional<String> result = dialog.showAndWait();

            if (result.isPresent()) {
                // check if the employee is not already in the list
                if (model.getEmployeeNameList().contains(result.get().strip())) {
                    errorLabelEmployee.setText("Employee is already in the list.");
                }
                else {
                    model.addEmployee(new TeamMember(result.get().strip()));
                    viewModelEmployee.update();
                }
            }
        }
        catch (IllegalArgumentException e) {
            errorLabelEmployee.setText(e.getMessage());
        }
    }

    @FXML
    private void removeMemberButton() {
        try {
            TeamMemberViewModel selectItem = employeeListTable.getSelectionModel().getSelectedItem();

            if (confirmationEmployee()) {
                TeamMember employee = model.getEmployee(selectItem.getNameProperty().get());
                model.removeEmployee(employee);
                viewModelEmployee.removeEmployee(employee);
                employeeListTable.getSelectionModel().clearSelection();
            }
        }
        catch (Exception e) {
            errorLabelEmployee.setText("Please select an item");
        }
    }
    private boolean confirmationEmployee() {
        TeamMemberViewModel selectedItem = employeeListTable.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Removing the employee \"" + selectedItem.getNameProperty().get() + "\"");

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
    //------------------------------------------

    //------------- Search Methods -------------
    @FXML
    private void searchButtonPressed() {
        ObservableList<String > items = FXCollections.observableArrayList();

        searchInputField.setText(searchInputField.getText().strip());
        // if user entered the id or the name
        String identifier = searchInputField.getText().matches("[PRTprt]\\d+") ? "the project with the ID" : "all the projects with the name";


        switch (choiceBox.getValue()) {
            // search by project name
            case "Project":
                infoLabelSearch.setText("The list contains " + identifier + " '" + searchInputField.getText() + "'");
                for (Project project : model.getProjectList()) {
                    if ( project.getName().equals(searchInputField.getText()) ||
                            project.getId().equals(searchInputField.getText()) ) {
                        items.add(project.getId() + "  " + project.getName());
                    }
                }
                break;
            // search by requirement name
            case "Requirement":
                infoLabelSearch.setText("The list contains " + identifier + " '" + searchInputField.getText() + "'");
                for (Project project : model.getProjectList()) {
                    for (Requirement requirement : project.getRequirementList().getRequirementList()) {
                        if ( requirement.getTitle().equals(searchInputField.getText()) ||
                                requirement.getId().equals(searchInputField.getText()) ) {
                            items.add(String.format(
                                    "%s  %s  (%s/%s)",
                                    requirement.getId(),
                                    requirement.getTitle(),
                                    project.getName(),
                                    requirement.getTitle()
                            ));
                        }
                    }
                }
                break;
            // search by task name
            case "Task":
                infoLabelSearch.setText("The list contains " + identifier + " '" + searchInputField.getText() + "'");
                for (Project project : model.getProjectList()) {
                    for (Requirement requirement : project.getRequirementList().getRequirementList()) {
                        for (Task task : requirement.getTaskList().getTaskList()) {
                            if ( task.getTitle().equals(searchInputField.getText()) ||
                                    task.getId().equals(searchInputField.getText()) ) {
                                items.add(String.format(
                                        "%s  %s (%s/%s/%s)",
                                        task.getId(),
                                        task.getTitle(),
                                        project.getName(),
                                        requirement.getTitle(),
                                        task.getTitle()
                                ));
                            }
                        }
                    }
                }
                break;
            // search task by task employee
            case "Employee":
                infoLabelSearch.setText("The list contains all the tasks '" + searchInputField.getText() + "' is responsible for.");

                for (Project project : model.getProjectList()) {
                    for (Requirement requirement : project.getRequirementList().getRequirementList()) {
                        for (Task task : requirement.getTaskList().getTaskList()) {
                            if (task.getResponsibleTeamMember().getName().equals(searchInputField.getText())) {
                                items.add(String.format(
                                        "%s  %s (%s/%s/%s)",
                                        task.getId(),
                                        task.getTitle(),
                                        project.getName(),
                                        requirement.getTitle(),
                                        task.getTitle()
                                ));
                            }
                        }
                    }
                }
                break;
        }
        resultListView.setItems(items);
    }

    @FXML
    private void goToButtonPressed() {
        try {
            String selectedItem = resultListView.getSelectionModel().getSelectedItem();

            model.setAdding(false);
            switch (selectedItem.split(" ")[0].charAt(0)) {
                case 'P':
                    for (Project project : model.getProjectList()) {
                        if (project.getId().equals(selectedItem.split(" ")[0])) {
                            model.setFocusProject(project);
                            viewHandler.openView("ProjectView");
                        }
                    }
                    break;
                case 'R':
                    for (Project project : model.getProjectList()) {
                        for (Requirement requirement : project.getRequirementList().getRequirementList()) {
                            if (requirement.getId().equals(selectedItem.split(" ")[0])) {
                                model.setFocusProject(project);
                                model.setFocusRequirement(requirement);
                                viewHandler.openView("RequirementView");
                            }
                        }
                    }
                    break;
                case 'T':
                    for (Project project : model.getProjectList()) {
                        for (Requirement requirement : project.getRequirementList().getRequirementList()) {
                            for (Task task : requirement.getTaskList().getTaskList()) {
                                if (task.getId().equals(selectedItem.split(" ")[0])) {
                                    model.setFocusProject(project);
                                    model.setFocusRequirement(requirement);
                                    model.setFocusTask(task);
                                    viewHandler.openView("TaskView");
                                }
                            }
                        }
                    }
                    break;
            }
        }
        catch (Exception e) {
            errorLabelSearch.setText("Please select an item");
        }
    }

    @FXML
    private void updateSearchAutocompletion() {
        infoLabelSearch.setText("");

        // clear the auto completion list
        autoCompletionBinding.dispose();

        switch (choiceBox.getValue()) {
            case "Project" :
                TextFields.bindAutoCompletion(searchInputField, model.getProjectNameList());
                break;
            case "Requirement" :
                TextFields.bindAutoCompletion(searchInputField, model.getRequirementTitleList());
                break;
            case "Task" :
                TextFields.bindAutoCompletion(searchInputField, model.getTaskTitleList());
                break;
            case "Employee" :
                TextFields.bindAutoCompletion(searchInputField, model.getEmployeeNameList());
                break;
        }
    }

    @FXML
    private void onEnterSearch(ActionEvent event) {
        if (event.getSource() == searchInputField) {
            searchButtonPressed();
        }
    }
    //------------------------------------------
}
