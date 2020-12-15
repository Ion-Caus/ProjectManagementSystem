package model;

import java.io.*;

public class FileSaver {
    public static void toBinary(String fileName, Object obj) throws IOException {
        String path = "data/" + fileName + ".bin";

        File file = new File(path);
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));

        out.writeObject(obj);
        out.close();
    }

    public static Team fromBinaryEmployeeList(String fileName) throws IOException, ClassNotFoundException {
        String path = "data/" + fileName + ".bin";

        File file = new File(path);
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));

        Team employeeList = (Team) in.readObject();
        in.close();

        return employeeList;
    }

    public static ProjectList fromBinaryProjectList(String fileName) throws IOException, ClassNotFoundException {
        String path = "data/" + fileName + ".bin";

        File file = new File(path);
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));

        ProjectList projectList = (ProjectList) in.readObject();
        in.close();

        return projectList;
    }

    public static void toXml(String fileName, ProjectList projectList) throws FileNotFoundException {
        String path = "../../Desktop/ColorItSite/data/" + fileName + ".xml";
        //TODO (Ion) CHANGE the path

        File file = new File(path);
        PrintWriter out = new PrintWriter(file);

        String xml = "";
        xml += "<?xml version=\"1.0\" encoding=\"UTF-8\""
                + " standalone=\"no\"?>\n";
        xml += "<ProjectList>";

        for(int i = 0; i < projectList.size(); i++) {
            Project project = projectList.getProject(i);
            xml += "\n    <Project>";
            xml += "\n        <idP>" + project.getId() + "</idP>";
            xml += "\n        <nameP>" + project.getName() + "</nameP>";
            xml += "\n        <statusP>" + project.getStatus() + "</statusP>";
            xml += "\n        <deadlineP>" + project.getDeadline().toString() + "</deadlineP>";
            xml += "\n        <estimateP>" + project.getEstimate().toString() + "</estimateP>";

            xml += "\n        <RequirementList>";
            for (int j = 0; j < project.getRequirementList().size(); j++) {
                Requirement requirement = project.getRequirementList().getRequirement(j);
                xml += "\n            <Requirement>";
                xml += "\n                <idR>" + requirement.getId() + "</idR>";
                xml += "\n                <titleR>" + requirement.getTitle() + "</titleR>";
                xml += "\n                <descriptionR>" + requirement.getDescription() + "</descriptionR>";
                xml += "\n                <statusR>" + requirement.getStatus() + "</statusR>";
                xml += "\n                <typeR>" + requirement.getType() + "</typeR>";
                xml += "\n                <deadlineR>" + requirement.getDeadline() + "</deadlineR>";
                xml += "\n                <estimateR>" + requirement.getEstimate() + "</estimateR>";
                xml += "\n            </Requirement>";
            }
            xml += "\n        </RequirementList>";

            xml += "\n    </Project>";
        }
        xml += "\n</ProjectList>";

        out.println(xml);
        out.close();
    }
}

