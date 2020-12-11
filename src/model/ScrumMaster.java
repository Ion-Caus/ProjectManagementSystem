package model;

public class ScrumMaster extends TeamMember {
    public ScrumMaster(String name) {
        super(name);
    }

    @Override
    public String getRole() {
        return "Scrum Master";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ScrumMaster)) {
            return false;
        }

        return super.equals(obj);
    }

    @Override
    public ScrumMaster copy() {
        return new ScrumMaster(super.getName());
    }
}
