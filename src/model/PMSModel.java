package model;

import java.util.ArrayList;

public interface PMSModel {

    boolean isAdding();
    void setAdding(boolean status);

    //----Project----
    int projectListSize();

    void addProject(Project project);
    void removeProject(Project project);

    Project getProject(String id);
    Project getProject(int index);

    ArrayList<Project> getProjectList();

    void setFocusProject(Project project);
    Project getFocusProject();

    //----Requirement----
    int requirementListSize();

    void addRequirement(Requirement requirement);
    void removeRequirement(Requirement requirement);

    Requirement getRequirement(String id);
    Requirement getRequirement(int index);

    ArrayList<Requirement> getRequirementList();

    void setFocusRequirement(Requirement requirement);
    Requirement getFocusRequirement();

    //----Task----
}
