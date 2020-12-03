package view;

import javafx.collections.*;

import model.PMSModel;
import model.Requirement;

public class RequirementListViewModel {
    private ObservableList<RequirementViewModel> requirementList;
    private PMSModel model;

    public RequirementListViewModel(PMSModel model) {
        this.model = model;
        this.requirementList = FXCollections.observableArrayList();
        update();
    }

    public ObservableList<RequirementViewModel> getRequirementList() {
        return requirementList;
    }

    public void update() {
        requirementList.clear();
        for (int i = 0; i < model.requirementListSize(); i++) {
            requirementList.add(new RequirementViewModel(model.getRequirement(i)));
        }
    }

    public void addRequirement(Requirement requirement) {
        requirementList.add(new RequirementViewModel(requirement));
    }

    public void removeRequirement(Requirement requirement) {
        for (int i = 0; i < requirementList.size(); i++) {
            if (requirementList.get(i).getIdProperty().get().equals(requirement.getId())) {
                requirementList.remove(i);
                break;
            }
        }
    }
}
