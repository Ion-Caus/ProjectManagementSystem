package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A class that represents the list  of the class Task
 */
public class TaskList implements Serializable {
    private ArrayList<Task> taskList;

    /**
     * A zero argument constructor that initializes the Arraylist that is holding Task classes
     */
    public TaskList() {
        this.taskList = new ArrayList<>();
    }

    /**
     * Returning how many objects of type Task are stored in the Arraylist
     *
     * @return returns number of elements in the Arraylist as an integer
     */
    public int size() {
        return  taskList.size();
    }

    /**
     * Adds a new instance of the class Task to the containing Arraylist
     * @param task the task as an object of type Task
     */
    public void addTask(Task task) {
        taskList.add(task);
    }

    /**
     * Removes an instance of the class task from the containing Arraylist
     * @param task the task as an object of type Task
     */

    public void removeTask(Task task) {
        taskList.remove(task);
    }

    /**
     * Returns an object of type task based on the index number
     * @param index the index number of the tasks as an integer
     * @return returns the task as an object of type Task
     */
    public Task getTask(int index) {
        return taskList.get(index);
    }

    /**
     * Loops trough the containing Arraylist and returns the task with matching ID
     * @param id the ID of the task in a form of a string
     * @return returns task as an instance of class Task
     *
     * @exception NullPointerException will be thrown in case there was no task found with matching ID
     */
    public Task getTask(String id) {
        for (Task task: taskList) {
            if (task.getId().equals(id)) {
                return task;
            }
        }
        throw new NullPointerException("Non-existent task");
    }

    /**
     * Returns all the tasks that are stored in the containing element
     * @return returns an ArrayList containing all the instances of class Task
     */
    public ArrayList<Task> getTaskList() {
        return taskList;
    }
}