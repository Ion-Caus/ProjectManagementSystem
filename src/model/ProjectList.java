package model;

import java.io.Serializable;

import java.util.ArrayList;

/**
 * A class that is containing the list of the class Project
 */
public class ProjectList implements Serializable {
    private ArrayList<Project> projects;

    /**
     * Zero argument constructor that initializes the Arraylist that is holding Project classes
     */
    public ProjectList() {
        this.projects = new ArrayList<>();
    }

    /**
     * Returning how many objects of type Project are stored in the Arraylist
     *
     * @return returns the number of elements in the Arraylist as an integer
     */
    public int size() {
        return projects.size();
    }

    /**
     * Adds a new instance of the class Project to the containing Arraylist
     * @param project the project as an object of type Project
     */
    public void addProject(Project project){
        projects.add(project);
    }

    /**
     * Removes an instance of the class Project from the containing Arraylist
     * @param project the project as an object of type Project
     */
    public void removeProject(Project project){
        projects.remove(project);
    }

    /**
     * Returns an object of type Project based on the index number
     * @param index the index number of the project as an integer
     * @return returns the project as an object of type Project
     */
    public Project getProject(int index) {
        return projects.get(index);
    }

    /**
     * Loops trough the containing Arraylist and returns the project with matching ID
     * @param id the ID of the project in a form of a string
     * @return will return project as an instance of class Project
     *
     * @exception NullPointerException will be thrown in case there was no project found with matching ID
     */
    public Project getProject(String id){
        for(Project project: projects){
            if(project.getId().equals(id))
                return project;
        }
        throw new NullPointerException("Non-existent project");
    }

    /**
     * Returns an ArrayList containing all projects whose status matches with the status provided
     * @param status status of the project as a String
     * @return returns the Arraylist of all instances of class Project that have a matching status with the status provided
     */
    public ArrayList<Project> getProjectList(String status){
        ArrayList<Project> temp = new ArrayList<>();

        for(Project project: projects){
            if(project.getStatus().equals(status))
                temp.add(project);
        }
        return temp;
    }

    /**
     * Returns all the projects that are stored in the containing element
     * @return returns an ArrayList containing all the instances of class Project
     */
    public ArrayList<Project> getProjectList(){
        return this.projects;
    }
}