package org.example.proyecto.task.view;

import org.example.proyecto.task.controller.TaskController;
import org.example.proyecto.task.excepciones.TaskValidationException;
import org.example.proyecto.task.model.Task;

import java.util.Scanner;

public class TaskView {
   private final TaskController taskController;
   private final Scanner scanner;

   public TaskView(TaskController taskController) {
      this.taskController = taskController;
      this.scanner = new Scanner(System.in);
   }

   public void showMenu() {
      while (true) {
         System.out.println("\n Gestion de Tareas:");
         System.out.println("1. Agregar tarea");
         System.out.println("2. Eliminar tareas");
         System.out.println("3. Actualizar tarea");
         System.out.println("4. Mostrar tarea");
         System.out.println("5. Salir");

         int option = Integer.parseInt(scanner.nextLine());

         switch (option) {
            case 1:
               addTaskView();
               break;
            case 2:
               removeTaskView();
               break;
            case 3:
               updateTaskView();
               break;
            case 4:
               showTaskView();
               break;
            case 5:
               System.out.println("Saliendo del programa");
               return;
            default:
               System.out.println("Opcion invalida");
               break;
         }
      }


   }

   public void addTaskView() {
      try {
         Task task = getTaskInput();

         taskController.addTask(task.getId(), task.getTitle(), task.getDescription(), task.getCompleted());
      } catch (TaskValidationException e) {
         System.out.println("Error: " + e.getMessage());
      } catch (Exception e) {
         System.out.println("Error inesperado: " + e.getMessage());
         e.printStackTrace();
      }
   }

   public void removeTaskView() {
      try {
         System.out.println("\n Ingresar ID a eliminar:");
         String id = scanner.nextLine();
         taskController.removeTask(id);
         System.out.println("Tarea eliminada exitosamente");
      } catch (TaskValidationException e) {
         System.out.println("Error: " + e.getMessage());
      } catch (Exception e) {
         System.out.println("Error inesperado: " + e.getMessage());
         e.printStackTrace();
      }
   }


   public void showTaskView() {
      try {
         System.out.println("\n La lista de tareas:");
         taskController.showTasks();
      } catch (TaskValidationException e) {
         System.out.println("Error: " + e.getMessage());
      } catch (Exception e) {
         System.out.println("Error inesperado: " + e.getMessage());
         e.printStackTrace();
      }
   }

   public void updateTaskView() {
      try {
         Task task = getTaskInput();

         taskController.updateTask(task.getId(), task.getTitle(), task.getDescription(), task.getCompleted());
      } catch (TaskValidationException e) {
         System.out.println("Error: " + e.getMessage());
      } catch (Exception e) {
         System.out.println("Error inesperado: " + e.getMessage());
         e.printStackTrace();
      }
   }

   private Task getTaskInput() {
      System.out.println("\n Ingresar ID:");
      String id = scanner.nextLine();

      System.out.println("Ingrese el titulo de la tarea:");
      String title = scanner.nextLine();

      System.out.println("Ingrese la descripcion de la tarea:");
      String description = scanner.nextLine();

      System.out.println("Esta completada? (true/false):");
      boolean completed = Boolean.parseBoolean(scanner.nextLine());
      return new Task(id, title, description, completed);
   }
}
