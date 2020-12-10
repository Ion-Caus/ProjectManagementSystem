package view;

import javafx.collections.*;

import model.PMSModel;
import model.TeamMember;

import java.util.ArrayList;

public class TeamViewModel {
    private ObservableList<TeamMemberViewModel> teamList;
    private PMSModel model;

    public TeamViewModel(PMSModel model) {
        this.model = model;
        this.teamList = FXCollections.observableArrayList();
    }

    public ObservableList<TeamMemberViewModel> getTeamList() {
        return teamList;
    }

    public void update() {
        teamList.clear();
        for (int i = 0; i < model.getFocusProject().getTeam().size(); i++) {
            teamList.add(new TeamMemberViewModel(model.getTeamMember(i)));
        }
    }

    public void addTeamMember(TeamMember teamMember) {
        teamList.add(new TeamMemberViewModel(teamMember));
    }

    public void removeTeamMember(TeamMember teamMember) {
        for (int i = 0; i < teamList.size(); i++) {
            if (teamList.get(i).getNameProperty().get().equals(teamMember.getName())) {
                getTeamList().remove(i);
                break;
            }
        }
    }
}