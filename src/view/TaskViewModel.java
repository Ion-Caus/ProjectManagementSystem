package view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import model.Task;

public class TaskViewModel {
    private StringProperty idProperty;
    private StringProperty titleProperty;
    private StringProperty statusProperty;
    private StringProperty deadlineProperty;

    public TaskViewModel(Task task) {
        this.idProperty = new SimpleStringProperty(task.getId());
        this.titleProperty = new SimpleStringProperty(task.getTitle());
        this.statusProperty = new SimpleStringProperty(task.getStatus());
        this.deadlineProperty = new SimpleStringProperty(task.getDeadline().toString());
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

    public StringProperty getDeadlineProperty() {
        return deadlineProperty;
    }
}
