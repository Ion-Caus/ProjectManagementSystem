package model;

import java.util.Arrays;
import java.util.Random;

public class Project {
    private String id;
    private String name;
    private String status;
    private MyDate deadline;
    private MyDate estimate;
    private int timeSpent;

    private RequirementList requirementList;
    //TODO private Team team;

    public static final String STATUS_CREATED = "Created";
    public static final String STATUS_IN_PROCESS = "In process";
    public static final String STATUS_WAITING_FOR_APPROVAL = "Waiting for approval";
    public static final String STATUS_FINISHED = "Finished";

    public Project(String name,String status, MyDate deadline) {//, MyDate estimate) {
        this.id = createProjectID();

        setName(name);
        setStatus(status);
        setDeadline(deadline);
        //setEstimate(estimate);
        this.timeSpent = 0;

        this.requirementList = new RequirementList();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if(status == null || !validStatus(status)) {
            throw new IllegalArgumentException("Illegal status given");
        }
        this.status = status;
    }

    public RequirementList getRequirementList() {
        return requirementList;
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

    private MyDate getEstimate() {
        return this.estimate.copy();
    }

    private void setEstimate(MyDate estimate) {
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

    //TODO
    //public void setTeam(){}
    //public Team getTeam(){}

    //TODO public ProductOwner getProductOwner(){}
    //TODO public ScrumMaster getScrumMaster(){}

    private static String createProjectID() {
        Random random = new Random(System.currentTimeMillis());
        return  "P" + (10000 + random.nextInt(100000));
    }

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
                ", deadline=" + deadline +
                ", estimate=" + estimate +
                ", timeSpent=" + timeSpent +
                ", requirementList=" + requirementList +
                '}';
    }
}