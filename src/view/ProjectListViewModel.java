package view;

import javafx.collections.*;
import model.Project;
import model.ProjectListModel;

public class ProjectListViewModel {
    private ObservableList<ProjectViewModel> projectList;
    private ProjectListModel model;

    public ProjectListViewModel(ProjectListModel model) {
        this.model = model;
        this.projectList = FXCollections.observableArrayList();
        update();
    }

    public ObservableList<ProjectViewModel> getProjectList() {
        return projectList;
    }

    public void update() {
        projectList.clear();
        for (int i = 0; i < model.projectListSize(); i++) {
            projectList.add(new ProjectViewModel(model.getProject(i)));
        }
    }

    public void addProject(Project project) {
        projectList.add(new ProjectViewModel(project));
    }

    // TODO     maybe try .remove(requirement)
    public void removeProject(Project project) {
        for (int i = 0; i < projectList.size(); i++) {
            if (projectList.get(i).getIdProperty().get().equals(project.getId())) {
                projectList.remove(i);
                break;
            }
        }
    }
}
