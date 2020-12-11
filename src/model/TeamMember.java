package model;

import java.io.Serializable;
import java.util.Arrays;

public class TeamMember implements Serializable {
    private String name;
    private String role;

    public static final String TEAM_MEMBER = "Team Member";
    public static final String SCRUM_MASTER = "Scrum Master";
    public static final String PRODUCT_OWNER = "Product Owner";

    //TODO divide name to firstName and lastName
    public TeamMember(String name) {
        setName(name);
        this.role = "No role";
    }

    public TeamMember(String name, String role) {
        setName(name);
        setRole(role);
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name can not be empty");
        }
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        if (role == null || !validRole(role)) {
            throw new IllegalArgumentException("Invalid role");
        }
        this.role = role;
    }

    private static boolean validRole(String role) {
        String[] roles = {TEAM_MEMBER, SCRUM_MASTER, PRODUCT_OWNER};
        return Arrays.asList(roles).contains(role);
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
