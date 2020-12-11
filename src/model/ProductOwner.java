package model;

public class ProductOwner extends TeamMember {
    public ProductOwner(String name) {
        super(name);
    }

    @Override
    public String getRole() {
        return "Product Owner";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ProductOwner)) {
            return false;
        }

        return super.equals(obj);
    }

    @Override
    public ProductOwner copy() {
        return new ProductOwner(super.getName());
    }
}
