package model;

import java.io.Serializable;

public class Time implements Serializable {
    private int minute;
    private TeamMember teamMember;

    public Time(int minutes, TeamMember teamMember){
        setTime(minutes);
        setTeamMember(teamMember);
    }

    public void setTeamMember(TeamMember teamMember) {
        if (teamMember == null) {
            throw new IllegalArgumentException("Null teamMember object given.");
        }
        this.teamMember = teamMember;
    }

    public void setTime(int minutes){
        if (minutes < 0) {
            throw new IllegalArgumentException("Minutes can not be negative.");
        }
        this.minute = minutes;
    }

    public void addTime(int minutes) {
        if (minutes < 0) {
            throw new IllegalArgumentException("Minutes can not be negative.");
        }
        this.minute += minutes;
    }

    public int getMinute() {
        return minute;
    }

    public TeamMember getTeamMember() {
        return teamMember;
    }

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