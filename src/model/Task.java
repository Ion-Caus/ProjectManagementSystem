package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Random;

public class Task implements Serializable {
    private String id;
    private String title;
    private String status;
    private String description;
    private LocalDate deadline;
    private LocalDate estimate;

    private TimeContainer timeWorkedList;

    private TeamMember responsibleTeamMember;

    public static final String STATUS_NOT_STARTED = "Not started";
    public static final String STATUS_IN_PROCESS = "In Process";
    public static final String STATUS_COMPLETED = "Completed";

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = (description == null) ? "" : description;
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

    public TimeContainer getTimeWorkedList() {
        return timeWorkedList;
    }

    public void setResponsibleTeamMember(TeamMember responsibleTeamMember) {
        if (responsibleTeamMember == null) {
            throw new IllegalArgumentException("Null responsible team member given");
        }
        this.responsibleTeamMember = responsibleTeamMember;
    }

    private static String createTaskID() {
        Random random = new Random(System.currentTimeMillis());
        return  "T" + (10000 + random.nextInt(100000));
    }

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
