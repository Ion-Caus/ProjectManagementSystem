package view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;

import model.PMSModel;


public class RequirementListViewController {
    @FXML private TableView<RequirementViewModel> requirementListTable;
    @FXML private TableColumn<RequirementViewModel, String> idRequirementColumn;
    @FXML private TableColumn<RequirementViewModel, String> titleRequirementColumn;
    @FXML private TableColumn<RequirementViewModel, String> statusRequirementColumn;
    @FXML private TableColumn<RequirementViewModel, String> typeRequirementColumn;
    @FXML private TableColumn<RequirementViewModel, String> deadlineRequirementColumn;
    @FXML private Label errorLabel;

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
        requirementListTable.setItems(viewModel.getRequirementList());

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
    private void addRequirementButton() {
        model.setAdding(true);
        viewHandler.openView("RequirementView");
    }

    @FXML
    private void viewRequirementButton() {
        try {
            RequirementViewModel selectItem = requirementListTable.getSelectionModel().getSelectedItem();

            //Setting the focusRequirement by getting the requirement its by ID
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

    }


    @FXML
    private void backButtonPressed() {
        model.setAdding(false);
        viewHandler.openView("ProjectView");
    }

}
