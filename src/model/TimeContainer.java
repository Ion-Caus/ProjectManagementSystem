package model;

import java.util.ArrayList;

public class TimeContainer {

    private ArrayList<Time> timeContainer;

    public TimeContainer() {
        this.timeContainer = new ArrayList<>();
    }


    public double getTimeWorkedBy(TeamMember teamMember){
        for (Time time: timeContainer) {
            if (time.getTeamMember().equals(teamMember)) {
                return time.getTimeWorked();
            }
        }
        throw new NullPointerException("No just team member in the team.");
    }

    public int getTotalTimeWorked(){
        int minutes = 0;
        for (Time time : timeContainer) {
            minutes += time.getTimeWorked();

        }
        return minutes;
    }

}