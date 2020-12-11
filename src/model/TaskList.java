package model;

import java.io.Serializable;
import java.util.ArrayList;

public class TaskList implements Serializable {
  private ArrayList<Task> taskList;

  public TaskList() {
    this.taskList = new ArrayList<>();
  }

  public int size() {
    return  taskList.size();
  }

  public void addTask(Task task) {
    taskList.add(task);
  }

  public void removeTask(Task task) {
    taskList.remove(task);
  }

  public Task getTask(int index) {
    return taskList.get(index);
  }

  public Task getTask(String id) {
    for (Task task: taskList) {
      if (task.getId().equals(id)) {
        return task;
      }
    }
    return null;
  }

  public ArrayList<Task> getTaskList() {
    return taskList;
  }

}