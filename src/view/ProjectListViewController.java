package view;

import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.layout.Region;

import model.Project;
import model.ProjectListModel;

import java.util.Optional;

public class ProjectListViewController {
    // projects tab
    @FXML private TableView<ProjectViewModel> projectListTable;
    @FXML private TableColumn<ProjectViewModel, String> idProjectColumn;
    @FXML private TableColumn<ProjectViewModel, String> nameProjectColumn;
    @FXML private TableColumn<ProjectViewModel, String> statusProjectColumn;
    @FXML private TableColumn<ProjectViewModel, String> deadlineProjectColumn;
    @FXML private Label errorLabel;

    //employees tab
    //@FXML private TableView<

    private ViewHandler viewHandler;
    private ProjectListModel model;
    private Region root;
    private ProjectListViewModel viewModel;

    public ProjectListViewController() {
        // called by FXMLLoader
    }

    public void init(ViewHandler viewHandler, ProjectListModel model, Region root) {
        this.viewHandler = viewHandler;
        this.model = model;
        this.root = root;

        this.viewModel = new ProjectListViewModel(model);

        idProjectColumn.setCellValueFactory(cellData -> cellData.getValue().getIdProperty());
        nameProjectColumn.setCellValueFactory(cellDate -> cellDate.getValue().getNameProperty());
        statusProjectColumn.setCellValueFactory(cellDate -> cellDate.getValue().getStatusProperty());
        deadlineProjectColumn.setCellValueFactory(cellDate -> cellDate.getValue().getDeadlineProperty());

        projectListTable.setItems(viewModel.getProjectList());

        errorLabel.setText("");
    }

    public void reset() {
        viewModel.update();
    }

    public Region getRoot() {
        return root;
    }

    @FXML
    private void createProjectButton() {
        model.setAdding(true);
        viewHandler.openView("ProjectView");
    }

    @FXML
    private void viewProjectButton() {
        try {
            ProjectViewModel selectItem = projectListTable.getSelectionModel().getSelectedItem();

            //Setting the focusProject by getting the project its by ID
            model.setFocusProject(model.getProject(selectItem.getIdProperty().get()));
            model.setAdding(false);
            viewHandler.openView("ProjectView");
        }
        catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML
    private void removeProjectButton() {
        try {
            ProjectViewModel selectItem = projectListTable.getSelectionModel().getSelectedItem();

            if (confirmation()) {
                Project project = model.getProject(selectItem.getIdProperty().get());
                model.removeProject(project);
                viewModel.removeProject(project);
                projectListTable.getSelectionModel().clearSelection();
            }
        } catch (Exception e) {
            errorLabel.setText("Item not found: " + e.getMessage());
        }
    }

    public boolean confirmation() {
        ProjectViewModel selectedItem = projectListTable.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(
                "Removing project { " +
                        selectedItem.getNameProperty().get() + ": " +
                        selectedItem.getIdProperty().get() + " }");

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    // TODO add methods for employeeList

    @FXML
    private void addMemberButton() {

    }

    @FXML
    private void removeMemberButton() {

    }
}
