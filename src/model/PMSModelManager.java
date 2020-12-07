package model;

import java.util.ArrayList;

public class PMSModelManager implements PMSModel {
    private ProjectList projectList;
    private Team employeeList;

    private Project focusProject;
    private Requirement focusRequirement;
    private Task focusTask;

    private boolean adding;

    public PMSModelManager() {
        this.projectList = new ProjectList();
        this.employeeList = new Team();
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

    //-------Employee-------
    @Override
    public int employeeListSize() {
        return employeeList.size();
    }

    @Override
    public void addEmployee(TeamMember teamMember) {
        employeeList.addTeamMember(teamMember);
    }

    @Override
    public void removeEmployee(TeamMember teamMember) {
        employeeList.removeTeamMember(teamMember);
    }

    @Override
    public TeamMember getTeamMember(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Please enter the team member's name");
        }

        try {
            return focusProject.getTeam().getTeamMember(name);
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("No team member with the name \"" + name + "\" in project's team.");
        }
    }

    @Override
    public TeamMember getTeamMember(int index) {
        return focusProject.getTeam().getTeamMember(index);
    }

    @Override
    public TeamMember getEmployee(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Please enter the employee's name");
        }

        try {
            return employeeList.getTeamMember(name);
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("No employee with the name \"" + name + "\" in your employee list.");
        }
    }

    @Override
    public TeamMember getEmployee(int index) {
        return employeeList.getTeamMember(index);
    }

    @Override
    public ArrayList<TeamMember> getEmployeeList() {
        return employeeList.getTeamMemberList();
    }

    @Override
    public ArrayList<String> getEmployeeNameList() {
        return employeeList.getTeamMemberNameList();
    }


    //-------Project-------
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


    //-------Requirement-------
    @Override
    public int requirementListSize() {
        return focusProject.getRequirementList().size();
    }

    @Override
    public void addRequirement(Requirement requirement) {
        focusProject.getRequirementList().addRequirement(requirement);
    }

    @Override
    public void removeRequirement(Requirement requirement) {
        focusProject.getRequirementList().removeRequirement(requirement);
    }

    @Override
    public Requirement getRequirement(String id) {
        return focusProject.getRequirementList().getRequirement(id);
    }

    @Override
    public Requirement getRequirement(int index) {
        return focusProject.getRequirementList().getRequirement(index);
    }

    @Override
    public ArrayList<Requirement> getRequirementList() {
        return focusProject.getRequirementList().getRequirementList();
    }

    @Override
    public void setFocusRequirement(Requirement requirement) {
        this.focusRequirement = requirement;
    }

    @Override
    public Requirement getFocusRequirement() {
        return focusRequirement;
    }


    //-------Task-------
    @Override
    public int taskListSize() {
        return focusRequirement.getTaskList().size();
    }

    @Override
    public void addTask(Task task) {
        focusRequirement.getTaskList().addTask(task);
    }

    @Override
    public void removeTask(Task task) {
        focusRequirement.getTaskList().removeTask(task);
    }

    @Override
    public Task getTask(String id) {
        return focusRequirement.getTaskList().getTask(id);
    }

    @Override
    public Task getTask(int index) {
        return focusRequirement.getTaskList().getTask(index);
    }

    @Override
    public ArrayList<Task> getTaskList() {
        return focusRequirement.getTaskList().getTaskList();
    }

    @Override
    public void setFocusTask(Task task) {
        this.focusTask = task;
    }

    @Override
    public Task getFocusTask() {
        return focusTask;
    }
}
