package model;

import java.io.Serializable;
import java.util.ArrayList;

public class TimeContainer implements Serializable {
    private ArrayList<Time> timeContainer;

    public TimeContainer() {
        this.timeContainer = new ArrayList<>();
    }

    public int size() {
        return timeContainer.size();
    }

    public Time get(int index) {
        return timeContainer.get(index);
    }

    public int convertToInteger(String timeString) {
        if (timeString == null || timeString.isEmpty()) {
            throw new IllegalArgumentException("Please enter the time.");
        }

        if ( !timeString.toLowerCase().strip().matches("(\\d+[dhmDHM]\\s?)+") ) {
            throw new IllegalArgumentException("Usage: [3d 1h 42m]");
        }

        int minutes = 0;
        for (String part: timeString.split(" ")){
            if (part.toLowerCase().contains("d")) {
                //get just the number
                part = part.replaceAll("\\D+","");
                minutes += Integer.parseInt(part) * 24 * 60;
            }
            if (part.toLowerCase().contains("h")) {
                //get just the number
                part = part.replaceAll("\\D+","");
                minutes += Integer.parseInt(part) * 60;
            }
            if (part.toLowerCase().contains("m")) {
                //get just the number
                part = part.replaceAll("\\D+","");
                minutes += Integer.parseInt(part);
            }
        }
        return minutes;
    }

    public void addTimeWorkedFor(TeamMember teamMember, String minutes) {
        boolean createNew = true;

        for (Time time: timeContainer) {
            if (time.getTeamMember().equals(teamMember)) {
                time.addTime(convertToInteger(minutes));
                createNew = false;
                break;
            }
        }

        if (createNew) {
            timeContainer.add(new Time(convertToInteger(minutes), teamMember));
        }
    }


    public String getTimeWorkedBy(TeamMember teamMember){
        for (Time time: timeContainer) {
            if (time.getTeamMember().equals(teamMember)) {
                return time.getTimeWorked();
            }
        }
        throw new NullPointerException("No such team member in the team.");
    }

    public int getTotalTimeWorked(){
        int minutes = 0;
        for (Time time : timeContainer) {
            minutes += time.getMinute();
        }
        return minutes;
    }

}