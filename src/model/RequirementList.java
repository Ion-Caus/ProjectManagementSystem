package model;

import java.io.Serializable;
import java.util.ArrayList;

public class RequirementList implements Serializable {
    private ArrayList<Requirement> requirementList;

    public RequirementList() {
        this.requirementList = new ArrayList<>();
    }

    public int size() {
        return  requirementList.size();
    }

    public void addRequirement(Requirement requirement) {
        requirementList.add(requirement);
    }

    public void removeRequirement(Requirement requirement) {
        requirementList.remove(requirement);
    }

    public Requirement getRequirement(int index) {
        return requirementList.get(index);
    }

    public Requirement getRequirement(String id) {
        for (Requirement requirement: requirementList) {
            if (requirement.getId().equals(id)) {
                return requirement;
            }
        }
        return null;
    }

    public ArrayList<Requirement> getRequirementList() {
        return requirementList;
    }
}
