package org.example.proyecto;

import org.example.proyecto.task.controller.TaskController;
import org.example.proyecto.task.model.TaskRepository;
import org.example.proyecto.task.view.TaskView;

public class Main {
   public static void main(String[] args) {
      TaskRepository taskRepository = new TaskRepository();
      TaskController taskController = new TaskController(taskRepository);
      TaskView taskView = new TaskView(taskController );

      taskView.showMenu();
   }
}