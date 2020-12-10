package view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import model.Time;

public class TimeViewModel {
    private StringProperty nameProperty;
    private StringProperty timeSpentProperty;

    public TimeViewModel(Time time) {
        this.nameProperty = new SimpleStringProperty(time.getTeamMember().getName());
        this.timeSpentProperty = new SimpleStringProperty(time.getTimeWorked());
    }

    public StringProperty getNameProperty() {
        return nameProperty;
    }

    public StringProperty getTimeSpentProperty() {
        return timeSpentProperty;
    }
}
