package model;

/**
 * A class representing productOwner
 */
public class ProductOwner extends TeamMember {
    public ProductOwner(String name) {
        super(name);
    }

    /**
     * This is an overridden method from parent class, returns the information of the employee
     * @return returns the information that this employee is a scrum master in a form of a String
     */
    @Override
    public String getRole() {
        return "Product Owner";
    }

    /**
     * Checks if the provided obj is instance of the ProductOwner class
     * @param obj object which will be checked
     * @return returns true if is the same, otherwise returns false
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ProductOwner)) {
            return false;
        }

        return super.equals(obj);
    }

    /**
     * Copies the object
     * @return returns a copy of the object ProductOwner
     */
    @Override
    public ProductOwner copy() {
        return new ProductOwner(super.getName());
    }
}
