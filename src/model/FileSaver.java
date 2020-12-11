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
}

