package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Random;

/**
 * A class representing a project requirement
 */
public class Requirement implements Serializable {
    private String id;
    private String title;
    private String status;
    private String type;
    private String description;
    private LocalDate deadline;
    private LocalDate estimate;

    private int timeSpent;

    private TaskList taskList;
    private TeamMember responsibleTeamMember;

    public static final String STATUS_NOT_STARTED = "Not Started";
    public static final String STATUS_IN_PROCESS = "In Process";
    public static final String STATUS_WAITING_FOR_APPROVAL = "Waiting For Approval";
    public static final String STATUS_APPROVED = "Approved";
    public static final String STATUS_REJECTED = "Rejected";

    public static final String TYPE_FUNCTIONAL = "Functional";
    public static final String TYPE_NON_FUNCTIONAL = "Non-functional";
    public static final String TYPE_PROJECT_RELATED = "Project Related";


    /**
     * A 7 argument constructor that initializes the class Requirement
     *
     * @param title the title(name) of the requirement in a form of String
     * @param status the status of the requirement in a form of String
     * @param type  the type of the requirement(functional, non functional, project-related) in a form of String
     * @param description  the description of the requirement in a form of a String
     * @param deadline the date when requirement needs to be finished in a form of a LocalDate
     * @param estimate the estimated date of the requirement completion in a form of a LocalDate
     * @param responsibleTeamMember the TeamMember responsible for the Requirement, selected from team assigned to Project
     * @see TeamMember
     * @see Team
     * @see Project
     */
    public Requirement(String title, String status, String type, String description, LocalDate deadline, LocalDate estimate, TeamMember responsibleTeamMember) {
        this.id = createReqID();

        setTitle(title);
        setStatus(status);
        setType(type);
        setDescription(description);
        setDeadline(deadline);
        setEstimate(estimate);
        setResponsibleTeamMember(responsibleTeamMember);
        this.timeSpent = 0;

        this.taskList = new TaskList();
    }

    /**
     * Returns the id of requirement
     * @return returns id of the requirement in a form of a String
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the title of requirement
     * @return returns the title of requirement in a form of a String
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title after checking if the title input is not empty
     * @param title the title of the Requirement in a form of a String
     * @exception IllegalArgumentException will be thrown in case the title is empty
     */
    public void setTitle(String title) {
        if (title == null || title.strip().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        this.title = title;
    }

    /**
     * @return returns requirement's status in a form of String
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets requirement status after verifying if the user's input is not empty and is valid
     * @param status input status
     */
    public void setStatus(String status) {
        if (status == null || !validStatus(status)) {
            throw new IllegalArgumentException("Illegal status given");
        }
        this.status = status;
    }

    /**
     * Updates requirement status to "Waiting For Approval" if all the task have the status "Completed"
     */
    public void updateStatus() {
        for (Task task: taskList.getTaskList()) {
            if (!task.getStatus().equals(Task.STATUS_COMPLETED)) {
                return;
            }
        }
        this.setStatus(Requirement.STATUS_WAITING_FOR_APPROVAL);
    }

    /**
     * @return returns requirement's type(Functional, Non-functional, Project Related)
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type after verifying if the user's input is not empty and is valid
     * @param type input type
     */
    public void setType(String type) {
        if (type == null || !validType(type)) {
            throw new IllegalArgumentException("Illegal type given");
        }
        this.type = type;
    }

    /**
     * @return returns the Requirement description as a String
     */
    public String getDescription() {
        return description;
    }

    /**
     * Assigns description after checking if the description is not empty
     * @param description input description in a form of a String
     */
    public void setDescription(String description) {
        this.description = (description == null) ? "" : description;
    }

    /**
     * @return returns the list of all tasks for this specific requirement
     */
    public TaskList getTaskList() {
        return taskList;
    }

    /**
     * @return returns requirement's deadline as a LocalDate object
     */
    public LocalDate getDeadline() {
        return deadline;
    }

    /**
     * @param deadline input requirement's deadline date
     * Adds deadline after checking if the input is not empty
     */
    public void setDeadline(LocalDate deadline) {
        if (deadline == null) {
            throw new IllegalArgumentException("Null deadline given");
        }
        this.deadline = deadline;
    }

    /**
     * @return returns estimated date of requirement completion as a LocalDate object
     */
    public LocalDate getEstimate() {
        return estimate;
    }

    /**
     * @param estimate input requirement's estimate date
     * Adds estimate date after checking if the input is not empty
     */
    public void setEstimate(LocalDate estimate) {
        if (estimate == null) {
            throw new IllegalArgumentException("Null estimate given");
        }
        this.estimate = estimate;
    }

    /** Returns the team member responsible of this requirement
     * @return returns the responsible team member as an instance of class TeamMember
     */
    public TeamMember getResponsibleTeamMember() {
        return responsibleTeamMember;
    }

    /**
     * Sets the responsible team member for this requirement after verifying if parameter responsibleTeamMember is not empty
     * @param responsibleTeamMember the team member that should be assigned as a responsible for this requirement, in a form of an instance of TeamMember class
     */
    public void setResponsibleTeamMember(TeamMember responsibleTeamMember) {
        if (responsibleTeamMember == null) {
            throw new IllegalArgumentException("Null responsible team member given");
        }
        this.responsibleTeamMember = responsibleTeamMember;
    }

    /**
     * @return returns the total time spent on requirement
     */
    public int getTimeSpent() {
        return timeSpent;
    }

    /**
     * Updates the total time spent on requirement.
     * The total time spent on requirement is calculated as sum of time spent on all tasks that the requirement has
     */
    public void updateTimeSpent() {
        int minutes = 0;
        for (int i = 0; i < taskList.size(); i++) {
            minutes += taskList.getTask(i).getTimeWorkedList().getTotalTimeWorked();
        }
        this.timeSpent = minutes;
    }

    /**
     * Creates requirement ID by combining letter R, which stands for requirements, with a random 5 digit number
     * @return  letter R combined with a random 5 digit number
     */
    private static String createReqID() {
        Random random = new Random(System.currentTimeMillis());
        return  "R" + (10000 + random.nextInt(90000));
    }

    /**
     * Checks if status is valid, will compare the input with array of valid statuses
     * @param status input status
     * @return returns true if the array of valid statuses contains the input status, otherwise returns false
     */
    private static boolean validStatus(String status) {
        String[] statuses = {STATUS_NOT_STARTED, STATUS_IN_PROCESS, STATUS_WAITING_FOR_APPROVAL, STATUS_APPROVED, STATUS_REJECTED};
        return Arrays.asList(statuses).contains(status);
    }

    /**
     * Checks if type is valid, will compare the input with array of valid types
     * @param type input type
     * @return returns true if the array of valid types contains the input type, otherwise returns false
     */
    private static boolean validType(String type) {
        String[] types = {TYPE_FUNCTIONAL, TYPE_NON_FUNCTIONAL, TYPE_PROJECT_RELATED};
        return Arrays.asList(types).contains(type);
    }

    @Override
    public String toString() {
        return "Requirement{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", deadline=" + deadline.toString() +
                ", estimate=" + estimate.toString() +
                ", responsibleTeamMember=" + responsibleTeamMember.getName() +
                ", timeSpent=" + timeSpent +
                ", taskList=" + taskList +
                '}';
    }
}
