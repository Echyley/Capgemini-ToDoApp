package ToDoApp;

import controller.ProjectController;
import model.Project;

public class App {

    public static void main(String[] args) {

        ProjectController projectController = new ProjectController();
        ;

        Project project = new Project();
        project.setName("test");
        project.setDescription("yey");
        projectController.save(project);

        // project.setName("NewTest");
        // projectController.update(project);
        //
        // List<Project> projects = projectController.getAll();
        //
        // System.out.println("Total de projetos: " + projects.size());
        //
        
    }
}
