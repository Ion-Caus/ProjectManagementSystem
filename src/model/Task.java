package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Random;

/**
 * A class representing a requirement task
 */
public class Task implements Serializable {
    private String id;
    private String title;
    private String status;
    private String description;
    private LocalDate deadline;
    private LocalDate estimate;

    private TimeContainer timeWorkedList;

    private TeamMember responsibleTeamMember;

    public static final String STATUS_NOT_STARTED = "Not Started";
    public static final String STATUS_IN_PROCESS = "In Process";
    public static final String STATUS_COMPLETED = "Completed";

    /**
     * A 6 argument constructor that initializes the class Task
     *
     * @param title the title(name) of the task in a form of String
     * @param status the status of the task in a form of String
     * @param description the description of the task in a form of a String
     * @param deadline the date when task needs to be finished in a form of a LocalDate
     * @param estimate the estimated date of the task completion in a form of a LocalDate
     * @param responsibleTeamMember the TeamMember responsible for the Task, selected from team assigned to Project
     */
    public Task(String title, String status, String description, LocalDate deadline, LocalDate estimate, TeamMember responsibleTeamMember) {
        this.id = createTaskID();

        setTitle(title);
        setStatus(status);
        setDescription(description);
        setDeadline(deadline);
        setEstimate(estimate);
        setResponsibleTeamMember(responsibleTeamMember);

        this.timeWorkedList = new TimeContainer();
    }

    /**
     * Returns the id of task
     * @return returns id of the task in a form of a String
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the title of task
     * @return returns the title of task in a form of a String
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title after checking if the title input is not empty
     * @param title the title of the Task in a form of a String
     * @exception IllegalArgumentException will be thrown in case the title is empty
     */
    public void setTitle(String title) {
        if (title == null || title.strip().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        this.title = title;
    }

    /**
     * @return returns task's status in a form of String
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets task status after verifying if the user's input is not empty and is valid
     * @param status input status
     */
    public void setStatus(String status) {
        if (status == null || !validStatus(status)) {
            throw new IllegalArgumentException("Illegal status given");
        }
        this.status = status;
    }

    /**
     * @return returns the Task description as a String
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
     * @return returns task's deadline as a LocalDate object
     */
    public LocalDate getDeadline() {
        return deadline;
    }

    /**
     * @param deadline input task's deadline date
     * Adds deadline after checking if the input is not empty
     */
    public void setDeadline(LocalDate deadline) {
        if (deadline == null) {
            throw new IllegalArgumentException("Null deadline given");
        }
        this.deadline = deadline;
    }

    /**
     * @return returns estimated date of task completion as a LocalDate object
     */
    public LocalDate getEstimate() {
        return estimate;
    }


    /**
     * @param estimate input task's estimate date
     * Adds estimate date after checking if the input is not empty
     */
    public void setEstimate(LocalDate estimate) {
        if (estimate == null) {
            throw new IllegalArgumentException("Null estimate given");
        }
        this.estimate = estimate;
    }

    /** Returns the team member responsible of this task
     * @return returns the responsible team member as an instance of class TeamMember
     */
    public TeamMember getResponsibleTeamMember() {
        return responsibleTeamMember;
    }

    /**
     * Sets the task team member for this requirement after verifying if parameter responsibleTeamMember is not empty
     * @param responsibleTeamMember the team member that should be assigned as a responsible for this task, in a form of an instance of TeamMember class
     */
    public void setResponsibleTeamMember(TeamMember responsibleTeamMember) {
        if (responsibleTeamMember == null) {
            throw new IllegalArgumentException("Null responsible team member given");
        }
        this.responsibleTeamMember = responsibleTeamMember;
    }

    /**
     * Returns the instance of TimeContainer class, which holds info about time worked on the project and people that worked on the project
     * @see Time
     * @see TeamMember
     * @see TimeContainer
     * @return returns instance of class TimeContainer
     */
    public TimeContainer getTimeWorkedList() {
        return timeWorkedList;
    }

    /**
     * Creates task ID by combining letter T, which stands for tasks, with a random 5 digit number
     * @return  letter T combined with a random 5 digit number
     */
    private static String createTaskID() {
        Random random = new Random(System.currentTimeMillis());
        return  "T" + (10000 + random.nextInt(90000));
    }

    /**
     * Checks if status is valid, will compare the input with array of valid statuses
     * @param status input status
     * @return returns true if the array of valid statuses contains the input status, otherwise returns false
     */
    private static boolean validStatus(String status) {
        String[] statuses = {STATUS_NOT_STARTED,STATUS_IN_PROCESS,STATUS_COMPLETED};
        return Arrays.asList(statuses).contains(status);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", status='" + status + '\'' +
                ", description='" + description + '\'' +
                ", deadline=" + deadline.toString() +
                ", estimate=" + estimate.toString() +
                ", responsibleTeamMember=" + responsibleTeamMember.getName() +
                ", timeWorkedList= " +timeWorkedList +
                '}';
    }
}
