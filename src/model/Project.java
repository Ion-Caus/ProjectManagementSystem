package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Random;

/**
 * A class representing a project
 */
public class Project implements Serializable {
    private String id;
    private String name;
    private String status;
    private LocalDate deadline;
    private LocalDate estimate;
    private int timeSpent;

    private RequirementList requirementList;
    private Team team;

    public static final String STATUS_CREATED = "Created";
    public static final String STATUS_IN_PROCESS = "In Process";
    public static final String STATUS_WAITING_FOR_APPROVAL = "Waiting For Approval";
    public static final String STATUS_FINISHED = "Finished";

    /**
     * @param name project name
     * @param status  projects status
     * @param deadline date of projects deadline
     * @param estimate estimated date of project completion
     * @param team  team assigned to project
     */
    public Project(String name,String status, LocalDate deadline, LocalDate estimate, Team team) {
        this.id = createProjectID();

        setName(name);
        setStatus(status);
        setDeadline(deadline);
        setEstimate(estimate);
        setTeam(team);
        this.timeSpent = 0;

        this.requirementList = new RequirementList();
    }

    /**
     * @return returns project's id
     */
    public String getId() {
        return id;
    }

    /**
     * @return returns project's names
     */
    public String getName() {
        return name;
    }

    /**
     * Sets project's name after verifying if the user's input is not empty
     * @param name project name
     */
    public void setName(String name) {
        if(name == null || name.strip().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name;
    }

    /**
     * @return returns project's status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets project status after verifying if the user's input is not empty and is valid
     * @param status input status
     */
    public void setStatus(String status) {
        if(status == null || !validStatus(status)) {
            throw new IllegalArgumentException("Illegal status given");
        }
        this.status = status;
    }

    /**
     * Updates project status to "Waiting For Approval" if all the requirements have the status "Approved"
     */
    public void updateStatus() {
        for (Requirement requirement: requirementList.getRequirementList()) {
            if (!requirement.getStatus().equals(Requirement.STATUS_APPROVED)) {
                return;
            }
        }
        this.setStatus(Project.STATUS_WAITING_FOR_APPROVAL);
    }

    /**
     * @return returns the list of all requirements in specific project as a RequirementList object
     */
    public RequirementList getRequirementList() {
        return requirementList;
    }

    /**
     * @return returns project's deadline as a LocalDate object
     */
    public LocalDate getDeadline() {
        return deadline;
    }

    /**
     * @param deadline input project's deadline date
     * Adds deadline after checking if the input is not empty
     */
    public void setDeadline(LocalDate deadline) {
        if (deadline == null) {
            throw new IllegalArgumentException("Null deadline given");
        }
        this.deadline = deadline;
    }

    /**
     * @return returns estimated date of project completion as a LocalDate object
     */
    public LocalDate getEstimate() {
        return this.estimate;
    }

    /**
     * @param estimate input project's estimate date
     * Adds estimate date after checking if the input is not empty
     */
    public void setEstimate(LocalDate estimate) {
        if (estimate == null) {
            throw new IllegalArgumentException("Null estimate given");
        }
        this.estimate = estimate;
    }

    /**
     * @return returns project's team as a Team object
     */
    public Team getTeam() {
        return team;
    }

    /**
     * @param team input project's team
     * Assign the team to the project after checking if the input is not empty
     */
    public void setTeam(Team team) {
        if (team == null) {
            throw new IllegalArgumentException("Null team given");
        }
        this.team = team;
    }

    /**
     * @return returns the total time spent on project
     */
    public int getTimeSpent() {
        return timeSpent;
    }

    /**
     * Updates the total time spent on project.
     * The total time spent on project is calculated as sum of time spent on all tasks in every requirement that the project has
     */
    public void updateTimeSpent() {
        int minutes = 0;
        for (int i = 0; i < requirementList.size(); i++) {
            minutes += requirementList.getRequirement(i).getTimeSpent();
        }
        this.timeSpent = minutes;
    }

    /**
     * Creates projects ID by combining letter P, which stands for projects, with a random 5 digit number
     * @return  letter P combined with a random 5 digit number
     */
    private static String createProjectID() {
        Random random = new Random(System.currentTimeMillis());
        return  "P" + (10000 + random.nextInt(90000));
    }

    /**
     * Checks if status is valid, will compare the input with array of valid statuses
     * @param status input status
     * @return returns true if the array of valid statuses contains the input status, otherwise returns false
     */
    private static boolean validStatus(String status) {
        String[] statuses = {STATUS_CREATED, STATUS_IN_PROCESS, STATUS_WAITING_FOR_APPROVAL, STATUS_FINISHED};
        return Arrays.asList(statuses).contains(status);
    }

    @Override
    public String toString() {
        return "Project{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", status='" + status + '\'' +
                ", deadline=" + deadline.toString() +
                ", estimate=" + estimate.toString() +
                ", timeSpent=" + timeSpent +
                ", requirementList=" + requirementList +
                ", team=" + team.getTeamMemberNameList() +
                '}';
    }
}