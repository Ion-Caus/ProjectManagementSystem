package model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Random;

public class Requirement {
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

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title can not be empty");
        }
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status == null || !validStatus(status)) {
            throw new IllegalArgumentException("Illegal status given");
        }
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if (type == null || !validType(type)) {
            throw new IllegalArgumentException("Illegal type given");
        }
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = (description == null) ? "" : description;
    }

    public TaskList getTaskList() {
        return taskList;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        if (deadline == null) {
            throw new IllegalArgumentException("Null deadline given");
        }
        this.deadline = deadline;
    }

    public LocalDate getEstimate() {
        return estimate;
    }

    public void setEstimate(LocalDate estimate) {
        if (estimate == null) {
            throw new IllegalArgumentException("Null estimate given");
        }
        this.estimate = estimate;
    }

    public TeamMember getResponsibleTeamMember() {
        return responsibleTeamMember;
    }

    public void setResponsibleTeamMember(TeamMember responsibleTeamMember) {
        if (responsibleTeamMember == null) {
            throw new IllegalArgumentException("Null responsible team member given");
        }
        this.responsibleTeamMember = responsibleTeamMember;
    }

    public int getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(int timeSpent) {
        this.timeSpent = timeSpent;
    }

    private static String createReqID() {
        Random random = new Random(System.currentTimeMillis());
        return  "R" + (10000 + random.nextInt(100000));
    }

    private static boolean validStatus(String status) {
        String[] statuses = {STATUS_NOT_STARTED, STATUS_IN_PROCESS, STATUS_WAITING_FOR_APPROVAL, STATUS_APPROVED, STATUS_REJECTED};
        return Arrays.asList(statuses).contains(status);
    }

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
