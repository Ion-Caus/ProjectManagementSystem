package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import model.PMSModel;

public class ViewHandler {
    private Scene currentScene;
    private Stage primaryStage;
    private PMSModel model;

    private ProjectListViewController projectListViewController;
    private ProjectViewController projectViewController;
    private RequirementListViewController requirementListViewController;
    private RequirementViewController requirementViewController;
    private TaskListViewController taskListViewController;
    private TaskViewController taskViewController;

    private CreateTeamViewController createTeamViewController;
    private TimeSpentViewController timeSpentViewController;

    public ViewHandler(PMSModel model) {
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
            case "CreateTeamView":
                root = loadCreateTeamViewGUI("CreateTeamView.fxml");
                break;
            case  "RequirementListView":
                root = loadRequirementListViewGUI("RequirementListView.fxml");
                break;
            case "RequirementView":
                root = loadRequirementViewGUI("RequirementView.fxml");
                break;
            case  "TaskListView":
                root = loadTaskListViewGUI("TaskListView.fxml");
                break;
            case "TaskView":
                root = loadTaskViewGUI("TaskView.fxml");
                break;
            case "TimeSpentView":
                root = loadTimeSpentViewGUI("TimeSpentView.fxml");

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

        //saveDataOnClosing
        primaryStage.setOnCloseRequest(windowEvent -> model.saveData());
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

    public Region loadTaskListViewGUI(String fxmlFile) {
        if (taskListViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                taskListViewController = loader.getController();
                taskListViewController.init(this, model, root);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            taskListViewController.reset();
        }
        return taskListViewController.getRoot();
    }

    public Region loadTaskViewGUI(String fxmlFile) {
        if (taskViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                taskViewController = loader.getController();
                taskViewController.init(this, model, root);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            taskViewController.reset();
        }
        return taskViewController.getRoot();
    }

    public Region loadCreateTeamViewGUI(String fxmlFile) {
        if (createTeamViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                createTeamViewController = loader.getController();
                createTeamViewController.init(this, model, root);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            createTeamViewController.reset();
        }
        return createTeamViewController.getRoot();
    }

    public Region loadTimeSpentViewGUI(String fxmlFile) {
        if (timeSpentViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                timeSpentViewController = loader.getController();
                timeSpentViewController.init(this, model, root);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            timeSpentViewController.reset();
        }
        return timeSpentViewController.getRoot();
    }
}
