package view;

import javafx.collections.*;

import model.PMSModel;
import model.TeamMember;

public class EmployeesViewModel {
    private ObservableList<TeamMemberViewModel> employeeList;
    private PMSModel model;

    public EmployeesViewModel(PMSModel model) {
        this.model = model;
        this.employeeList = FXCollections.observableArrayList();
    }

    public ObservableList<TeamMemberViewModel> getEmployeeList() {
        return employeeList;
    }

    public void update() {
        employeeList.clear();
        for (int i = 0; i < model.employeeListSize(); i++) {
            employeeList.add(new TeamMemberViewModel(model.getEmployee(i)));
        }
    }

    public void addEmployee(TeamMember teamMember) {
        employeeList.add(new TeamMemberViewModel(teamMember));
    }

    public void removeEmployee(TeamMember teamMember) {
        for (int i = 0; i < employeeList.size(); i++) {
            if (employeeList.get(i).getNameProperty().get().equals(teamMember.getName())) {
                getEmployeeList().remove(i);
                break;
            }
        }
    }
}
