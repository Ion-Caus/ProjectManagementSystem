package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A class that is containing the list of the class Requirement
 */
public class RequirementList implements Serializable {
    private ArrayList<Requirement> requirementList;

    /**
     * Zero argument constructor that initializes the Arraylist that is holding Requirement classes
     */
    public RequirementList() {
        this.requirementList = new ArrayList<>();
    }

    /**
     * Returns how many objects of type Requirement are stored in the Arraylist
     *
     * @return  number of elements in the Arraylist as an integer
     */
    public int size() {
        return  requirementList.size();
    }

    /**
     * Adds a new instance of the class Requirement to the containing Arraylist
     * @param requirement the requirement as an object of type Requirement
     */
    public void addRequirement(Requirement requirement) {
        requirementList.add(requirement);
    }

    /**
     * Removes an instance of the class Requirement from the containing Arraylist
     * @param requirement the requirement as an object of type Requirement
     */
    public void removeRequirement(Requirement requirement) {
        requirementList.remove(requirement);
    }

    /**
     * Returns an object of type Requirement based on the index number
     * @param index the index number of the requirements as an integer
     * @return returns the requirement as an object of type Requirement
     */
    public Requirement getRequirement(int index) {
        return requirementList.get(index);
    }

    /**
     * Loops trough the containing Arraylist and returns the requirement with matching ID
     * @param id the ID of the requirement in a form of a string
     * @return returns requirement as an instance of class Requirement
     *
     * @exception NullPointerException will be thrown in case there was no requirement found with matching ID
     */
    public Requirement getRequirement(String id) {
        for (Requirement requirement: requirementList) {
            if (requirement.getId().equals(id)) {
                return requirement;
            }
        }
        throw new NullPointerException("Non-existent requirement");
    }

    /**
     * Returns all the requirements that are stored in the containing element
     * @return returns an ArrayList containing all the instances of class Requirement
     */
    public ArrayList<Requirement> getRequirementList() {
        return requirementList;
    }
}
