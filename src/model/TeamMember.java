package model;

public class TeamMember {
    private String name;

    //TODO divide name to firstName and lastName
    public TeamMember(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name can not be empty");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }


}
