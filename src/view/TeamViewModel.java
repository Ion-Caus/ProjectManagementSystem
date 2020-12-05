package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.PMSModel;
import model.TeamMember;

public class TeamViewModel {
    private ObservableList<TeamMemberViewModel> teamMemberList;
    private PMSModel model;

    public TeamViewModel(PMSModel model) {
        this.model = model;
        this.teamMemberList = FXCollections.observableArrayList();
    }

    public ObservableList<TeamMemberViewModel> getTeamMemberList() {
        return teamMemberList;
    }

    public void update() {
        teamMemberList.clear();
        for (int i = 0; i < model.employeeListSize(); i++) {
            teamMemberList.add(new TeamMemberViewModel(model.getTeamMember(i)));
        }
    }

    public void addEmployee(TeamMember teamMember) {
        teamMemberList.add(new TeamMemberViewModel(teamMember));
    }

    public void removeEmployee(TeamMember teamMember) {
        for (int i = 0; i < teamMemberList.size(); i++) {
            if (teamMemberList.get(i).getNameProperty().get().equals(teamMember.getName())) {
                getTeamMemberList().remove(i);
                break;
            }
        }
    }
}
