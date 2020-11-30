package model;

import java.util.ArrayList;

public interface ProjectListModel {

    boolean isAdding();
    void setAdding(boolean status);

    int projectListSize();

    void addProject(Project project);
    void removeProject(Project project);

    Project getProject(String id);
    Project getProject(int index);

    ArrayList<Project> getProjectList();

    void setFocusProject(Project project);
    Project getFocusProject();

    void addRequirement(Project project, Requirement requirement);
    void removeRequirement(Project project, Requirement requirement);
}
