package view;

import javafx.beans.property.*;
import model.Project;

public class ProjectViewModel {
    private StringProperty idProperty;
    private StringProperty nameProperty;
    private StringProperty statusProperty;
    private StringProperty deadlineProperty;

    public ProjectViewModel(Project project) {
        this.idProperty = new SimpleStringProperty(project.getId());
        this.nameProperty = new SimpleStringProperty(project.getName());
        this.statusProperty = new SimpleStringProperty(project.getStatus());
        this.deadlineProperty = new SimpleStringProperty(project.getDeadline().toString());
    }

    public StringProperty getIdProperty() {
        return idProperty;
    }

    public StringProperty getNameProperty() {
        return nameProperty;
    }

    public StringProperty getStatusProperty() {
        return statusProperty;
    }

    public StringProperty getDeadlineProperty() {
        return deadlineProperty;
    }
}
