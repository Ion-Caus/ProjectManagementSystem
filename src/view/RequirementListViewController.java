package view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;

import model.PMSModel;
import model.Requirement;

import java.util.Optional;


public class RequirementListViewController {
    @FXML private TableView<RequirementViewModel> requirementListTable;
    @FXML private TableColumn<RequirementViewModel, String> idRequirementColumn;
    @FXML private TableColumn<RequirementViewModel, String> titleRequirementColumn;
    @FXML private TableColumn<RequirementViewModel, String> statusRequirementColumn;
    @FXML private TableColumn<RequirementViewModel, String> typeRequirementColumn;
    @FXML private TableColumn<RequirementViewModel, String> deadlineRequirementColumn;
    @FXML private TableColumn<RequirementViewModel, String> estimateRequirementColumn;
    @FXML private TableColumn<RequirementViewModel, String> responsibleTeamMemberReqColumn;
    @FXML private Label errorLabel;
    @FXML private Label pathLabel;

    private ViewHandler viewHandler;
    private PMSModel model;
    private Region root;
    private RequirementListViewModel viewModel;

    public RequirementListViewController() {
        // called by FXMLLoader
    }

    public void init(ViewHandler viewHandler, PMSModel model, Region root) {
        this.viewHandler = viewHandler;
        this.model = model;
        this.root = root;

        this.viewModel = new RequirementListViewModel(model);

        idRequirementColumn.setCellValueFactory(cellDate -> cellDate.getValue().getIdProperty());
        titleRequirementColumn.setCellValueFactory(cellData -> cellData.getValue().getTitleProperty());
        statusRequirementColumn.setCellValueFactory(cellData -> cellData.getValue().getStatusProperty());
        typeRequirementColumn.setCellValueFactory(cellData -> cellData.getValue().getTypeProperty());
        deadlineRequirementColumn.setCellValueFactory(cellData -> cellData.getValue().getDeadlineProperty());
        estimateRequirementColumn.setCellValueFactory(cellData -> cellData.getValue().getEstimateProperty());
        responsibleTeamMemberReqColumn.setCellValueFactory(cellData -> cellData.getValue().getResponsibleTeamMemberProperty());
        requirementListTable.setItems(viewModel.getRequirementList());

        reset();
    }

    public void reset() {
        errorLabel.setText("");
        pathLabel.setText(model.getFocusProject().getName() + "/");

        viewModel.update();
    }

    public Region getRoot() {
        return root;
    }

    @FXML
    private void openTaskList(){
        try {
            RequirementViewModel selectItem = requirementListTable.getSelectionModel().getSelectedItem();

            //Setting the focusRequirement by getting the requirement by its ID
            model.setFocusRequirement(model.getRequirement(selectItem.getIdProperty().get()));
            model.setAdding(false);
            viewHandler.openView("TaskListView");
        }
        catch (Exception e) {
            errorLabel.setText("Please select an item");
        }
    }
    
    @FXML
    private void addRequirementButton() {
        model.setAdding(true);
        viewHandler.openView("RequirementView");
    }

    @FXML
    private void viewRequirementButton() {
        try {
            RequirementViewModel selectItem = requirementListTable.getSelectionModel().getSelectedItem();

            //Setting the focusRequirement by getting the requirement by its ID
            model.setFocusRequirement(model.getRequirement(selectItem.getIdProperty().get()));
            model.setAdding(false);
            viewHandler.openView("RequirementView");
        }
        catch (Exception e) {
            errorLabel.setText("Please select an item");
        }
    }

    @FXML
    private void removeRequirementButton() {
        try {
            RequirementViewModel selectItem = requirementListTable.getSelectionModel().getSelectedItem();

            if (confirmation()) {
                Requirement requirement = model.getRequirement(selectItem.getIdProperty().get());
                model.removeRequirement(requirement);
                viewModel.removeRequirement(requirement);
                requirementListTable.getSelectionModel().clearSelection();
            }
        } catch (Exception e) {
            errorLabel.setText("Please select an item");
        }
    }

    public boolean confirmation() {
        RequirementViewModel selectedItem = requirementListTable.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(
                "Removing the requirement \"" +
                        selectedItem.getTitleProperty().get() + "\"\n" +
                        "with the id: " + selectedItem.getIdProperty().get()
        );

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    @FXML
    private void backButtonPressed() {
        model.setAdding(false);
        viewHandler.openView("ProjectListView");
    }

}
