package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import model.ProjectListModel;

public class ViewHandler {
    private Scene currentScene;
    private Stage primaryStage;
    private ProjectListModel model;

    private ProjectListViewController projectListViewController;
    private ProjectViewController projectViewController;
    private RequirementViewController requirementViewController;
    private RequirementListViewController requirementListViewController;

    public ViewHandler(ProjectListModel model) {
        this.model = model;
        this.currentScene = new Scene(new Region());
    }

    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        openView("ProjectListView");
    }

    public void openView(String id) {
        Region root = null;
        switch (id) {
            case "ProjectListView":
                root = loadProjectListViewGUI("ProjectListView.fxml");
                break;
            case "ProjectView":
                root = loadProjectViewGUI("ProjectView.fxml");
                break;
            case  "RequirementListView":
                root = loadRequirementListViewGUI("RequirementListView.fxml");
                break;
            case "RequirementView":
                root = loadRequirementViewGUI("RequirementView.fxml");
                break;
        }
        currentScene.setRoot(root);

        String title = "";
        assert root != null;
        if (root.getUserData() != null) {
            title += root.getUserData();
        }
        primaryStage.setTitle(title);
        primaryStage.setScene(currentScene);
        primaryStage.setWidth(root.getPrefWidth());
        primaryStage.setHeight(root.getPrefHeight());
        primaryStage.show();
    }

    public void closeView() {
        primaryStage.close();
    }

    public Region loadProjectListViewGUI(String fxmlFile) {
        if (projectListViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                projectListViewController = loader.getController();
                projectListViewController.init(this, model, root);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            projectListViewController.reset();
        }
        return projectListViewController.getRoot();
    }

    public Region loadProjectViewGUI(String fxmlFile) {
        if (projectViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                projectViewController = loader.getController();
                projectViewController.init(this, model, root);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            projectViewController.reset();
        }
        return projectViewController.getRoot();
    }

    public Region loadRequirementListViewGUI(String fxmlFile) {
        if (requirementListViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                requirementListViewController = loader.getController();
                requirementListViewController.init(this, model, root);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            requirementListViewController.reset();
        }
        return requirementListViewController.getRoot();
    }

    public Region loadRequirementViewGUI(String fxmlFile) {
        if (requirementViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                requirementViewController = loader.getController();
                requirementViewController.init(this, model, root);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            requirementViewController.reset();
        }
        return requirementViewController.getRoot();
    }
}
