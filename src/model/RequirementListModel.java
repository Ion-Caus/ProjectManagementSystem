package model;

import java.util.ArrayList;

public interface RequirementListModel {

    void addRequirement(Requirement requirement);
    void removeRequirement(Requirement requirement);

    int requirementListSize();

    Requirement getRequirement(String id);
    Requirement getRequirement(int index);

    ArrayList<Requirement> getRequirementList();

}
