package model;

import java.util.Arrays;
import java.util.Random;

public class Task {
    private String id;
    private String title;
    private String status;
    private String description;
    private MyDate deadline;
    private MyDate estimate;
    private int timeSpent;

    //TODO private TeamMember teamMember;

    public static final String STATUS_NOT_STARTED = "Not started";
    public static final String STATUS_IN_PROCESS = "In Process";
    public static final String STATUS_COMPLETED = "Completed";

    //TODO implement MyDate estimate
    public Task(String title, String status, String description, MyDate deadline) {//, MyDate estimate) {
        this.id = createTaskID();

        setTitle(title);
        setStatus(status);
        setDescription(description);
        setDeadline(deadline);
        //setEstimate(estimate);
        this.timeSpent = 0;

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

    public MyDate getDeadline() {
        return deadline.copy();
    }

    public void setDeadline(MyDate deadline) {
        if (deadline == null) {
            throw new IllegalArgumentException("Null deadline given");
        }
        this.deadline = deadline.copy();
    }

    public MyDate getEstimate() {
        return estimate.copy();
    }

    public void setEstimate(MyDate estimate) {
        if (estimate == null) {
            throw new IllegalArgumentException("Null estimate given");
        }
        this.estimate = estimate.copy();
    }

    public int getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(int timeSpent) {
        this.timeSpent = timeSpent;
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
                ", deadline=" + deadline +
                ", estimate=" + estimate +
                ", timeSpent=" + timeSpent +
                '}';
    }
}
