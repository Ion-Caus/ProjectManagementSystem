package model;

import java.io.Serializable;

public class TeamMember implements Serializable {
    private String name;

    public TeamMember(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name can not be empty");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return "Team Member";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TeamMember)) {
            return false;
        }
        TeamMember other = (TeamMember) obj;
        return this.name.equals(other.getName());
    }

    public TeamMember copy() {
        return new TeamMember(name);
    }
}
