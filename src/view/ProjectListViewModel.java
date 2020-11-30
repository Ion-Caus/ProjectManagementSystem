package view;

import javafx.collections.*;

import model.Project;
import model.PMSModel;

public class ProjectListViewModel {
    private ObservableList<ProjectViewModel> projectList;
    private PMSModel model;

    public ProjectListViewModel(PMSModel model) {
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

    public void removeProject(Project project) {
        for (int i = 0; i < projectList.size(); i++) {
            if (projectList.get(i).getIdProperty().get().equals(project.getId())) {
                projectList.remove(i);
                break;
            }
        }
    }
}
