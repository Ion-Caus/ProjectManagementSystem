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
        String path = "data/" + fileName + ".xml";

        File file = new File(path);
        PrintWriter out = new PrintWriter(file);

        String xml = "";
        xml += "<?xml version=\"1.0\" encoding=\"UTF-8\""
                + "standalone=\"no\"?>\n";
        xml += "<ProjectList>";

        for(int i = 0; i < projectList.size(); i++) {
            Project project = projectList.getProject(i);
            xml += "\n    <Project>";
            xml += "\n        <id>" + project.getId() + "</id>";
            xml += "\n        <name>" + project.getName() + "</name>";
            xml += "\n        <status>" + project.getStatus() + "</status>";
            xml += "\n        <deadline>" + project.getDeadline().toString() + "</deadline>";
            xml += "\n        <estimate>" + project.getEstimate().toString() + "</estimate>";

            xml += "\n        <RequirementList>";
            for (int j = 0; j < project.getRequirementList().size(); j++) {
                Requirement requirement = project.getRequirementList().getRequirement(j);
                xml += "\n            <Requirement>";
                xml += "\n                <id>" + requirement.getId() + "</id>";
                xml += "\n                <title>" + requirement.getTitle() + "</title>";
                xml += "\n                <description>" + requirement.getDescription() + "</description>";
                xml += "\n                <status>" + requirement.getStatus() + "</status>";
                xml += "\n                <type>" + requirement.getType() + "</type>";
                xml += "\n                <deadline>" + requirement.getDeadline() + "</deadline>";
                xml += "\n                <estimate>" + requirement.getEstimate() + "</estimate>";
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

