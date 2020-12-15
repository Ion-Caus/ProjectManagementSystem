package mediator;

import model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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

        //loading data
        loadData();
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

    // ------ Team -----
    @Override
    public boolean isPresent(String role) throws ClassNotFoundException {
        if (!Arrays.asList("ScrumMaster", "ProductOwner", "TeamMember").contains(role)) {
            throw new IllegalArgumentException("Invalid role given. Roles: [ScrumMaster, ProductOwner, TeamMember]");
        }
        for (TeamMember teamMember: focusProject.getTeam().getTeamMemberList()) {
            if (Class.forName("model." + role).isInstance(teamMember)) {
                return true;
            }
        }
        return false;
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
    public ArrayList<String> getTeamMemberNameList() {
        return focusProject.getTeam().getTeamMemberNameList();
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
    public ArrayList<String> getProjectNameList() {
        ArrayList<String> names = new ArrayList<>();

        for (Project project: projectList.getProjectList()) {
            names.add(project.getName());
        }
        return names;
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
    public ArrayList<String> getRequirementTitleList() {
        ArrayList<String> titles = new ArrayList<>();

        for (Project project: projectList.getProjectList()) {
            for (Requirement requirement: project.getRequirementList().getRequirementList()) {
                titles.add(requirement.getTitle());
            }
        }
        return titles;
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
    public ArrayList<String> getTaskTitleList() {
        ArrayList<String> titles = new ArrayList<>();

        for (Project project: projectList.getProjectList()) {
            for (Requirement requirement: project.getRequirementList().getRequirementList()) {
                for (Task task: requirement.getTaskList().getTaskList()) {
                    titles.add(task.getTitle());
                }
            }
        }
        return titles;
    }

    @Override
    public void setFocusTask(Task task) {
        this.focusTask = task;
    }

    @Override
    public Task getFocusTask() {
        return focusTask;
    }


    //-------FileSaver-------
    @Override
    public void saveData() {
        try {
            FileSaver.toBinary("EmployeeList", this.employeeList);
            FileSaver.toBinary("ProjectList", this.projectList);

            FileSaver.toXml("ProjectListXml", this.projectList);
        }
        catch (IOException e) {
            System.out.println("Data cannot be saved.");
        }
    }

    @Override
    public void loadData() {
        try {
            this.employeeList = FileSaver.fromBinaryEmployeeList("EmployeeList");
            this.projectList = FileSaver.fromBinaryProjectList("ProjectList");
        }
        catch (IOException | ClassNotFoundException e) {
            System.out.println("Data cannot be load.\n" + e.getMessage());
        }
    }
}
