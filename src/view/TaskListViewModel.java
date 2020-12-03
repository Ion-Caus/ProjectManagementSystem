package view;

import javafx.collections.*;

import model.PMSModel;
import model.Task;

public class TaskListViewModel {
    private ObservableList<TaskViewModel> taskList;
    private PMSModel model;

    public TaskListViewModel(PMSModel model) {
        this.model = model;
        this.taskList = FXCollections.observableArrayList();

        update();
    }

    public ObservableList<TaskViewModel> getTaskList() {
        return taskList;
    }

    public void update() {
        taskList.clear();
        for (int i = 0; i < model.taskListSize(); i++) {
            taskList.add(new TaskViewModel(model.getTask(i)));
        }
    }

    public void addTask(Task task) {
        taskList.add(new TaskViewModel(task));
    }

    public void removeTask(Task task) {
        for (int i = 0; i < taskList.size(); i++) {
            if (taskList.get(i).getIdProperty().get().equals(task.getId())) {
                taskList.remove(i);
                break;
            }
        }
    }
}
