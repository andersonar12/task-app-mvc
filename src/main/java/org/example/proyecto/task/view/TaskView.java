package org.example.proyecto.task.view;

import org.example.proyecto.task.controller.TaskController;
import org.example.proyecto.task.excepciones.TaskException;
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
         System.out.println("4. Mostrar todas las tareas");
         System.out.println("5. Actualizar estado de tarea");
         System.out.println("6. Mostrar tareas completadas");
         System.out.println("7. Mostrar tareas pendientes");
         System.out.println("8. Salir");

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
               updateTaskCompletedView();
               break;
            case 6:
               showCompletedTasksView();
               break;
            case 7:
               showPendingTasksView();
               break;
            case 8:
               System.out.println("Saliendo del programa");
               return;
            default:
               System.out.println("Opcion invalida");
               break;
         }
      }

   }

   public void addTaskView() {
      boolean success = false;
      do {
         try {
            Task task = getTaskInput();

            taskController.addTask(task.getId(), task.getTitle(), task.getDescription(), task.getCompleted());
            success = true;
         } catch (TaskValidationException e) {
            System.out.println("Error: " + e.getMessage());
         } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
            success = true; // Exit on unexpected error
         }
      } while (!success);
   }

   public void removeTaskView() {
      boolean success = false;
      do {
         try {
            System.out.println("\n Ingresar ID a eliminar:");
            String id = scanner.nextLine();
            taskController.removeTask(id);
            System.out.println("Tarea eliminada exitosamente");
            success = true;
         } catch (TaskValidationException e) {
            System.out.println("Error: " + e.getMessage());
         } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
            success = true; // Exit on unexpected error
         }
      } while (!success);
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

   public void updateTaskCompletedView() {
      try {
         System.out.println("\n Ingresar ID de la tarea a actualizar:");
         String id = scanner.nextLine();

         Boolean completed = null;
         while (completed == null) {
            System.out.println("\n Ingresar estado de la tarea (true/false):");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("true")) {
               completed = true;
            } else if (input.equals("false")) {
               completed = false;
            } else {
               System.out.println("Entrada invalida. Por favor, ingrese 'true' o 'false'.");
            }
         }

         taskController.updateTaskCompleted(id, completed);
         System.out.println("Tarea actualizada exitosamente");
      } catch (TaskValidationException e) {
         System.out.println("Error: " + e.getMessage());
      } catch (Exception e) {
         System.out.println("Error inesperado: " + e.getMessage());
         e.printStackTrace();
      }
   }

   public void showCompletedTasksView() {
      try {
         System.out.println("\n La lista de tareas completadas:");
         taskController.showCompletedTasks();
      } catch (TaskException e) {
         throw new RuntimeException(e);
      }
   }

   public void showPendingTasksView() {
      try {
         System.out.println("\n La lista de tareas pendientes:");
         taskController.showTasksPending();
      } catch (TaskException e) {
         throw new RuntimeException(e);
      }
   }

   public void showTaskView() {
      try {
         System.out.println("\n La lista de tareas:");
         taskController.showTasks();
      } catch (Exception e) {
         System.out.println("Error inesperado: " + e.getMessage());
         e.printStackTrace();
      }
   }

   private String getNonBlankInput(String prompt) {
      String input;
      do {
         System.out.println(prompt);
         input = scanner.nextLine().trim();
         if (input.isEmpty()) {
            System.out.println("La entrada no puede estar vacia. Por favor, intente de nuevo.");
         }
      } while (input.isEmpty());
      return input;
   }

   private boolean getBooleanInput(String prompt) {
      String input;
      while (true) {
         System.out.println(prompt);
         input = scanner.nextLine().trim().toLowerCase();
         if (input.equals("true") || input.equals("false")) {
            return Boolean.parseBoolean(input);
         } else {
            System.out.println("Entrada invalida. Por favor, ingrese 'true' o 'false'.");
         }
      }
   }

   private Task getTaskInput() {
      String id = getNonBlankInput("\n Ingresar ID:");
      String title = getNonBlankInput("Ingrese el titulo de la tarea:");
      String description = getNonBlankInput("Ingrese la descripcion de la tarea:");
      boolean completed = getBooleanInput("Esta completada? (true/false):");
      return new Task(id, title, description, completed);
   }
}
