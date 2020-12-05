package model;

import java.util.ArrayList;
import java.util.IllformedLocaleException;

public class Team {
    private ArrayList<TeamMember> teamMemberList;
    //TODO add Scrum master and Product owner, just one dont forget to check
    //private ScrumMaster scrumMaster;
    //private ProductOwner productOwner;

    public Team() {
        this.teamMemberList = new ArrayList<>();
    }

    public int size() {
        return teamMemberList.size();
    }

    public void addTeamMember(TeamMember teamMember) {
        teamMemberList.add(teamMember);
    }

    public void removeTeamMember(TeamMember teamMember) {
        teamMemberList.remove(teamMember);
    }

    public TeamMember getTeamMember(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Add the responsible team member");
        }

        for (TeamMember teamMember: teamMemberList) {
            if (teamMember.getName().equals(name)) {
                return teamMember;
            }
        }
        throw new IllegalArgumentException("No employee with the name " + name);
    }
    public TeamMember getTeamMember(int index) {
        return teamMemberList.get(index);
    }

    public ArrayList<TeamMember> getTeamMemberList() {
        return teamMemberList;
    }

    public ArrayList<String> getTeamMemberNameList() {
        ArrayList<String> names = new ArrayList<>();

        for (TeamMember teamMember: teamMemberList) {
            names.add(teamMember.getName());
        }
        return names;
    }
}
