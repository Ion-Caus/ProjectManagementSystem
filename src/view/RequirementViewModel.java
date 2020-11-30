package view;

import javafx.beans.property.*;
import model.Requirement;

public class RequirementViewModel {
    private StringProperty idProperty;
    private StringProperty titleProperty;
    private StringProperty statusProperty;
    private StringProperty typeProperty;
    private StringProperty deadlineProperty;

    public RequirementViewModel(Requirement requirement) {
        this.idProperty = new SimpleStringProperty(requirement.getId());
        this.titleProperty = new SimpleStringProperty(requirement.getTitle());
        this.statusProperty = new SimpleStringProperty(requirement.getStatus());
        this.typeProperty = new SimpleStringProperty(requirement.getType());
        this.deadlineProperty = new SimpleStringProperty(requirement.getDeadline().toString());
    }

    public StringProperty getIdProperty() {
        return idProperty;
    }

    public StringProperty getTitleProperty() {
        return titleProperty;
    }

    public StringProperty getStatusProperty() {
        return statusProperty;
    }

    public StringProperty getTypeProperty() {
        return typeProperty;
    }

    public StringProperty getDeadlineProperty() {
        return deadlineProperty;
    }
}
