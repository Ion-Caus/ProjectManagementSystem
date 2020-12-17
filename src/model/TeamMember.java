package model;

import java.io.Serializable;

/**
 * A class representing a team member
 */
public class TeamMember implements Serializable {
    /**
     * The name of the team member
     */
    private String name;

    /**
     * A one argument constructor that checks if the parameter name is not empty and initializes TeamMember class with provided name
     * @param name name of the team member in a form of a String
     */
    public TeamMember(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name can not be empty");
        }
        this.name = name;
    }

    /**
     * @return returns the name of team member
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the role of the  Team Member, in this class it can only return a fixed String because if a Team Member is assigned as a Scrum Master or a Product Owner, subclass of class TeamMember will be instantiated and saved
     * @see ProductOwner
     * @see ScrumMaster
     * @return returns role of the Team Member, which in this class can only be "Team Member"
     */
    public String getRole() {
        return "Team Member";
    }

    /**
     * Returns true if the provided object is an instance of this class TeamMember
     * @param obj the object that will be compared with class TeamMember
     * @return true if obj is an instance of TeamMember class and has the same name, otherwise returns false
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TeamMember)) {
            return false;
        }
        TeamMember other = (TeamMember) obj;
        return this.name.equals(other.getName());
    }

    /**
     * Copies the object
     * @return returns a copy of the object TeamMember
     */
    public TeamMember copy() {
        return new TeamMember(name);
    }
}
