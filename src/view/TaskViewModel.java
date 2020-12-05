package view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import model.Task;

public class TaskViewModel {
    private StringProperty idProperty;
    private StringProperty titleProperty;
    private StringProperty statusProperty;
    private StringProperty deadlineProperty;
    private StringProperty estimateProperty;
    private StringProperty responsibleTeamMemberProperty;

    public TaskViewModel(Task task) {
        this.idProperty = new SimpleStringProperty(task.getId());
        this.titleProperty = new SimpleStringProperty(task.getTitle());
        this.statusProperty = new SimpleStringProperty(task.getStatus());
        this.deadlineProperty = new SimpleStringProperty(task.getDeadline().toString());
        this.estimateProperty = new SimpleStringProperty(task.getEstimate().toString());
        this.responsibleTeamMemberProperty = new SimpleStringProperty(task.getResponsibleTeamMember().getName());
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

    public StringProperty getEstimateProperty() {
        return estimateProperty;
    }

    public StringProperty getResponsibleTeamMemberProperty() {
        return responsibleTeamMemberProperty;
    }
}
