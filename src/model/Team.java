package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A class that represents the containing element of the class TeamMember
 */
public class Team implements Serializable {
    private ArrayList<TeamMember> teamMemberList;

    /**
     * A zero argument constructor that initializes the ArrayList that contains instances of TeamMember class
     */
    public Team() {
        this.teamMemberList = new ArrayList<>();
    }

    /**
     * Returning how many objects of type TeamMember are stored in the Arraylist
     *
     * @return returns number of elements in the Arraylist as an integer
     */
    public int size() {
        return teamMemberList.size();
    }

    /**
     * Adds a new instance of the class teamMember to the containing Arraylist
     * @param teamMember the teamMember as an object of type TeamMember
     */
    public void addTeamMember(TeamMember teamMember) {
        teamMemberList.add(teamMember);
    }

    /**
     * Removes an instance of the class teamMember from the containing Arraylist
     * @param teamMember the teamMember as an object of type TeamMember
     */
    public void removeTeamMember(TeamMember teamMember) {
        teamMemberList.remove(teamMember);
    }

    /**
     * Loops trough the containing Arraylist and return the teamMember with matching name
     * @param name the name of the teamMember in a form of a string
     * @return returns teamMember as an instance of class TeamMember
     * @exception IllegalArgumentException will be thrown in case will be found with matching name
     */
    public TeamMember getTeamMember(String name) {
        for (TeamMember teamMember: teamMemberList) {
            if (teamMember.getName().equals(name)) {
                return teamMember;
            }
        }
        throw new IllegalArgumentException("No person with the name \"" + name +"\"");
    }

    /**
     * Returns an object of type teamMember based on the index number
     * @param index the index number of the teamMembers as an integer
     * @return returns the teamMember as an object of type TeamMember
     */
    public TeamMember getTeamMember(int index) {
        return teamMemberList.get(index);
    }

    /**
     * Returns all the teamMembers that are stored in the containing element
     * @return returns an ArrayList containing all the instances of class TeamMember
     */
    public ArrayList<TeamMember> getTeamMemberList() {
        return teamMemberList;
    }

    /**
     * Creates a new ArrayList called names and loops trough the ArrayList containing instances of class TeamMember, extracting teamMember names and storing them in names ArrayList
     * @return returns an Arraylist containing all employees names in a form of a String
     */
    public ArrayList<String> getTeamMemberNameList() {
        ArrayList<String> names = new ArrayList<>();

        for (TeamMember teamMember: teamMemberList) {
            names.add(teamMember.getName());
        }
        return names;
    }
}
