package model;

import java.io.Serializable;

/**
 * A class representing time spent on Task
 * @see Task
 */
public class Time implements Serializable {
    private int minute;
    private TeamMember teamMember;

    /**
     * A two argument constructor that initializes the Time class
     * @param minutes total time worked on task in minutes, as an int
     * @param teamMember the Team Member that worked on the task, as an instance of class TeamMember
     * @see TeamMember
     */
    public Time(int minutes, TeamMember teamMember){
        setTime(minutes);
        setTeamMember(teamMember);
    }

    /**
     * Sets the teamMember after checking if teamMember is not empty
     * @param teamMember the team member that has worked on the task
     * @exception IllegalArgumentException will be thrown in case the teamMember object is not given
     */
    public void setTeamMember(TeamMember teamMember) {
        if (teamMember == null) {
            throw new IllegalArgumentException("Null teamMember object given.");
        }
        this.teamMember = teamMember;
    }

    /**
     * Sets time worked on task after verifying if input is not smaller than 0
     * @param minutes time worked on the task in minutes, as an int
     * @exception IllegalArgumentException will be thrown in case input is smaller than 0
     */
    public void setTime(int minutes){
        if (minutes < 0) {
            throw new IllegalArgumentException("Minutes can not be negative.");
        }
        this.minute = minutes;
    }

    /**
     * Increase the time by amount specified in input on task after verifying if input is not smaller than 0
     * @param minutes additional time worked on task in minutes, as an int
     * @exception IllegalArgumentException will be thrown in case input is smaller than 0
     */
    public void addTime(int minutes) {
        if (minutes < 0) {
            throw new IllegalArgumentException("Minutes can not be negative.");
        }
        this.minute += minutes;
    }

    /**
     * @return returns time worked on task in minutes, as an int
     */
    public int getMinute() {
        return minute;
    }

    /**
     * @return returns the Team Member that has worked on the task or to whom this instance of Time class is linked to
     */
    public TeamMember getTeamMember() {
        return teamMember;
    }

    /**
     * Recalculates minutes (total time worked on task is stored as minutes) into days, hours and minutes and return those values as a String
     * @return returns minutes recalculated into days, hours and minutes in a form of a String
     */
    public String getTimeWorked(){
        int days = this.minute / 24 / 60;
        int hours = this.minute / 60 % 24;
        int minutes = this.minute % 60;

        String timeString = "";

        if (days != 0){
            timeString += days + "d ";
        }

        if (hours != 0){
            timeString += hours + "h ";
        }

        if (minutes != 0) {
            timeString += minutes + "m";
        }

        return timeString;
    }
}