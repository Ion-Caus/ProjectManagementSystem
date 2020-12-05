package view;

import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.layout.Region;

import model.Project;
import model.PMSModel;
import model.TeamMember;

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

        errorLabelProject.setText("");
        //---------------------


        //---- Employee List ----
        nameEmployeeColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());

        employeeListTable.setItems(viewModelEmployee.getEmployeeList());

        errorLabelEmployee.setText("");
        //-----------------------
    }

    public void reset() {
        errorLabelProject.setText("");
        viewModelProject.update();

        errorLabelEmployee.setText("");
        viewModelEmployee.update();
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


    //---------- EmployeeList Methods ----------
    @FXML
    private void addMemberButton() {
        errorLabelEmployee.setText("");

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Employee");
        dialog.setHeaderText("What is the name of employee");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            model.addEmployee(new TeamMember(result.get()));
            viewModelEmployee.update();
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
        alert.setHeaderText(
                "Removing the employee \"" + selectedItem.getNameProperty().get()
        );

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}
