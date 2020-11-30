package model;

import java.util.ArrayList;

public class ProjectListModelManager implements ProjectListModel {
    private ProjectList projectList;
    private Project focusProject;

    private boolean adding;

    public ProjectListModelManager() {
        this.projectList = new ProjectList();
        this.adding = false;
    }

    //-------for adding or viewing---------
    @Override
    public boolean isAdding() {
        return adding;
    }

    @Override
    public void setAdding(boolean status) {
        this.adding = status;
    }
    //-------------------------------------

    @Override
    public int projectListSize() {
        return projectList.size();
    }

    @Override
    public void addProject(Project project) {
        projectList.addProject(project);
    }

    @Override
    public void removeProject(Project project) {
        projectList.removeProject(project);
    }

    @Override
    public Project getProject(String id) {
        return projectList.getProject(id);
    }

    @Override
    public Project getProject(int index) {
        return projectList.getProject(index);
    }

    @Override
    public ArrayList<Project> getProjectList() {
        return projectList.getProjectList();
    }


    @Override
    public void setFocusProject(Project project) {
        this.focusProject = project;
    }

    @Override
    public Project getFocusProject() {
        return focusProject;
    }


    @Override
    public void addRequirement(Project project, Requirement requirement) {
        project.getRequirementList().addRequirement(requirement);
    }

    @Override
    public void removeRequirement(Project project, Requirement requirement) {
        project.getRequirementList().removeRequirement(requirement);
    }
}
