package model;

import java.util.ArrayList;

public class RequirementModelManager implements RequirementListModel {
    private RequirementList requirementList;

    public RequirementModelManager() {
        requirementList = new RequirementList();
    }

    @Override
    public int requirementListSize() {
        return requirementList.size();
    }

    @Override
    public void addRequirement(Requirement requirement) {
        requirementList.addRequirement(requirement);
    }

    @Override
    public void removeRequirement(Requirement requirement) {
        requirementList.removeRequirement(requirement);
    }

    @Override
    public Requirement getRequirement(String title) {
        return requirementList.getRequirement(title);
    }

    @Override
    public Requirement getRequirement(int index) {
        return requirementList.getRequirement(index);
    }

    @Override
    public ArrayList<Requirement> getRequirementList() {
        return requirementList.getRequirementList();
    }
}
