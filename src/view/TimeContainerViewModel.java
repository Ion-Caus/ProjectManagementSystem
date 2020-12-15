package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mediator.PMSModel;
import model.TeamMember;
import model.Time;

public class TimeContainerViewModel {
    private ObservableList<TimeViewModel> timeSpentList;
    private PMSModel model;

    public TimeContainerViewModel(PMSModel model) {
        this.model = model;
        this.timeSpentList = FXCollections.observableArrayList();
    }

    public ObservableList<TimeViewModel> getTimeSpentList() {
        return timeSpentList;
    }

    public void update() {
        timeSpentList.clear();
        for (int i = 0; i < model.getFocusTask().getTimeWorkedList().size(); i++) {
            timeSpentList.add(new TimeViewModel(model.getFocusTask().getTimeWorkedList().get(i)));
        }
    }

    public void addTimeSpent(Time time) {
        timeSpentList.add(new TimeViewModel(time));
    }

    public void removeTeamMember(TeamMember teamMember) {
        for (int i = 0; i < timeSpentList.size(); i++) {
            if (timeSpentList.get(i).getNameProperty().get().equals(teamMember.getName())) {
                getTimeSpentList().remove(i);
                break;
            }
        }
    }
}
