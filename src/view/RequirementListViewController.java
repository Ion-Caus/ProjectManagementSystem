package view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;

import model.Project;
import model.ProjectListModel;


public class RequirementListViewController {
    @FXML private ComboBox<String> idsList;

    private ViewHandler viewHandler;
    private ProjectListModel model;
    private Region root;

    public RequirementListViewController() {
        // called by FXMLLoader
    }

    public void init(ViewHandler viewHandler, ProjectListModel model, Region root) {
        this.viewHandler = viewHandler;
        this.model = model;
        this.root = root;
        reset();
    }

    public void reset() {
        idsList.getItems().removeAll(idsList.getItems());
        for (Project project: model.getProjectList()) {
            idsList.getItems().add(project.getId());
        }
        idsList.getSelectionModel().selectFirst();
    }

    public Region getRoot() {
        return root;
    }
    
    @FXML
    private void addButtonPressed() {
        viewHandler.openView("RequirementView");
    }

    @FXML
    private void viewButtonPressed() {
        viewHandler.openView("RequirementView");
    }

    @FXML
    private void backButtonPressed() {
        viewHandler.openView("ProjectListView");
    }

}
