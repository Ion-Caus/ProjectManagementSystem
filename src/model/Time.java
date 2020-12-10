package model;

public class Time {
    private int hour;
    private int minute;

    private TeamMember teamMember;

    public Time(int hour, int minute){
        setTime(hour, minute);
    }

    public void setTime(int hour, int minute){
        if (hour < 0) {
            throw new IllegalArgumentException("Hour can not be negative.");
        }
        this.hour = hour;
        if (minute < 0) {
            throw new IllegalArgumentException("Minutes can not be negative.");
        }
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public TeamMember getTeamMember() {
        return teamMember;
    }

    public int getTimeWorked(){
        return this.hour*60 + this.minute;
    }
}