package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class ProjectList {
    private ArrayList<Project> projects;

    public ProjectList() {
        this.projects = new ArrayList<>();
    }

    public int size() {
        return projects.size();
    }

    public void addProject(Project project){
        projects.add(project);
    }

    public void removeProject(Project project){
        projects.remove(project);
    }

    public Project getProject(int index) {
        return projects.get(index);
    }

    public Project getProject(String id){
        for(Project project: projects){
            if(project.getId().equals(id))
                return project;
        }
        throw new NullPointerException("Non-existent project");
    }


    public Project getProject(LocalDate deadline){
        for(Project project: projects){
            if(project.getDeadline().equals(deadline))
                return project;
        }
        throw new NullPointerException("Non-existent project");
    }

    public ArrayList<Project> getProjectList(String status){
        ArrayList<Project> temp = new ArrayList<>();
        for(Project project: projects){
            if(project.getStatus().equals(status))
                temp.add(project);
        }
        return temp;
    }

    public ArrayList<Project> getProjectList(){
        return this.projects;
    }


}