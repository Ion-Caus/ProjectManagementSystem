package model;

/**
 * A class representing scrumMaster
 */
public class ScrumMaster extends TeamMember {
    public ScrumMaster(String name) {
        super(name);
    }

    /**
     * This is an overridden method from parent class, returns the role of the employee
     * @return returns the information that this employee is a Scrum Master in a form of a String
     */
    @Override
    public String getRole() {
        return "Scrum Master";
    }

    /**
     * Checks if the provided obj is instance of the ScrumMaster class
     * @param obj object which will be checked
     * @return returns true if is the same, otherwise returns false
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ScrumMaster)) {
            return false;
        }

        return super.equals(obj);
    }

    /**
     * Copies the object
     * @return returns a copy of the object ProductOwner
     */
    @Override
    public ScrumMaster copy() {
        return new ScrumMaster(super.getName());
    }
}
