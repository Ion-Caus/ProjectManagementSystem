package view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;

import model.PMSModel;

public class TaskListViewController {
    @FXML
    private TableView<TaskViewModel> taskListTable;
    @FXML private TableColumn<TaskViewModel, String> idTaskColumn;
    @FXML private TableColumn<TaskViewModel, String> titleTaskColumn;
    @FXML private TableColumn<TaskViewModel, String> statusTaskColumn;
    @FXML private TableColumn<TaskViewModel, String> deadlineTaskColumn;
    @FXML private Label errorLabel;

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
        taskListTable.setItems(viewModel.getTaskList());

        errorLabel.setText("");
    }

    public void reset() {
        errorLabel.setText("");
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

    }

    @FXML
    private void backButtonPressed() {
        model.setAdding(false);
        viewHandler.openView("RequirementListView");
    }

}

