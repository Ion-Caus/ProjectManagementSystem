package view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;

import model.PMSModel;
import model.Task;

import java.util.Optional;

public class TaskListViewController {
    @FXML
    private TableView<TaskViewModel> taskListTable;
    @FXML private TableColumn<TaskViewModel, String> idTaskColumn;
    @FXML private TableColumn<TaskViewModel, String> titleTaskColumn;
    @FXML private TableColumn<TaskViewModel, String> statusTaskColumn;
    @FXML private TableColumn<TaskViewModel, String> deadlineTaskColumn;
    @FXML private TableColumn<TaskViewModel, String> estimateTaskColumn;
    @FXML private TableColumn<TaskViewModel, String> responsibleTeamMemberTaskColumn;
    @FXML private Label errorLabel;
    @FXML private Label pathLabel;

    private ViewHandler viewHandler;
    private PMSModel model;
    private Region root;
    private TaskListViewModel viewModel;

    public TaskListViewController() {
        // called by FXMLLoader
    }

    public void init(ViewHandler viewHandler, PMSModel model, Region root) {
        this.viewHandler = viewHandler;
        this.model = model;
        this.root = root;

        this.viewModel = new TaskListViewModel(model);

        idTaskColumn.setCellValueFactory(cellDate -> cellDate.getValue().getIdProperty());
        titleTaskColumn.setCellValueFactory(cellDate -> cellDate.getValue().getTitleProperty());
        statusTaskColumn.setCellValueFactory(cellDate -> cellDate.getValue().getStatusProperty());
        deadlineTaskColumn.setCellValueFactory(cellDate -> cellDate.getValue().getDeadlineProperty());
        estimateTaskColumn.setCellValueFactory(cellData -> cellData.getValue().getEstimateProperty());
        responsibleTeamMemberTaskColumn.setCellValueFactory(cellData -> cellData.getValue().getResponsibleTeamMemberProperty());
        taskListTable.setItems(viewModel.getTaskList());

        reset();
    }

    public void reset() {
        errorLabel.setText("");
        pathLabel.setText(model.getFocusProject().getName() + "/" + model.getFocusRequirement().getTitle() + "/");

        viewModel.update();
    }

    public Region getRoot() {
        return root;
    }

    @FXML
    private void addTaskButton() {
        model.setAdding(true);
        viewHandler.openView("TaskView");
    }

    @FXML
    private void viewTaskButton() {
        try {
            TaskViewModel selectItem = taskListTable.getSelectionModel().getSelectedItem();

            //Setting the focusTask by getting the task by its ID
            model.setFocusTask(model.getTask(selectItem.getIdProperty().get()));
            model.setAdding(false);
            viewHandler.openView("TaskView");
        }
        catch (Exception e) {
            errorLabel.setText("Please select an item");
        }
    }

    @FXML
    private void removeTaskButton() {
        try {
            TaskViewModel selectItem = taskListTable.getSelectionModel().getSelectedItem();

            if (confirmation()) {
                Task task = model.getTask(selectItem.getIdProperty().get());
                model.removeTask(task);
                viewModel.removeTask(task);
                taskListTable.getSelectionModel().clearSelection();
            }
        } catch (Exception e) {
            errorLabel.setText("Please select an item");
        }
    }

    public boolean confirmation() {
        TaskViewModel selectedItem = taskListTable.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(
                "Removing the task \"" +
                        selectedItem.getTitleProperty().get() + "\"\n" +
                        "with the id: " + selectedItem.getIdProperty().get()
        );

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    @FXML
    private void backButtonPressed() {
        model.setAdding(false);
        viewHandler.openView("RequirementListView");
    }

}

