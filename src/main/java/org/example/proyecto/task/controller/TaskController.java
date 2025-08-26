package org.example.proyecto.task.controller;

import org.example.proyecto.task.excepciones.TaskValidationException;
import org.example.proyecto.task.model.Task;
import org.example.proyecto.task.model.TaskRepository;

import java.util.List;

public class TaskController {
   private final TaskRepository taskRepository;

   public TaskController(TaskRepository taskRepository) {
      this.taskRepository = taskRepository;
   }

   public void addTask(String id, String title, String description, Boolean completed) throws TaskValidationException {
      validateTaskData(id, title, description, completed);
      Task task = new Task(id, title, description, completed);
      this.taskRepository.save(task);
      System.out.println("Tarea agregada exitosamente");
   }

   public void removeTask(String id) throws TaskValidationException {

      if (id == null || id.trim().isEmpty()) {
         throw new TaskValidationException("El id no puede ser nulo o vacio");
      }
      this.taskRepository.remove(id);
   }

   public void showTasks() throws TaskValidationException {
      List<Task> tasks = this.taskRepository.findAll();

      if (tasks.isEmpty()) {
         throw new TaskValidationException("La lista de tareas esta vacia");
      }
      tasks.forEach(task -> System.out.println(task));
   }

   public void updateTask(String id, String title, String description, Boolean completed) throws TaskValidationException {
      validateTaskData(id, title, description, completed);
      Task task = new Task(id, title, description, completed);
      this.taskRepository.updateTask(task);
      System.out.println("Tarea actualizada exitosamente");
   }

   private void validateTaskData(String id, String title, String description, Boolean completed) throws TaskValidationException {
      if (id == null || id.trim().isEmpty()) {
         throw new TaskValidationException("El id no puede ser nulo o vacio");
      }
      if (title == null || title.trim().isEmpty()) {
         throw new TaskValidationException("El titulo no puede ser nulo o vacio");
      }
      if (description == null || description.trim().isEmpty()) {
         throw new TaskValidationException("La descripcion no puede ser nula o vacia");
      }
      if (completed == null) {
         throw new TaskValidationException("El estado de la tarea no puede ser nulo");
      }
   }
}
