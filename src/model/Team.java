package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Team implements Serializable {
    private ArrayList<TeamMember> teamMemberList;

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
        for (TeamMember teamMember: teamMemberList) {
            if (teamMember.getName().equals(name)) {
                return teamMember;
            }
        }
        throw new IllegalArgumentException("No person with the name \"" + name +"\"");
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
