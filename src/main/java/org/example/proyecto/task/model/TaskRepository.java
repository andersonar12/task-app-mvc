package org.example.proyecto.task.model;

import org.example.proyecto.task.excepciones.TaskException;
import org.example.proyecto.task.excepciones.TaskValidationException;
import org.example.proyecto.task.persistence.TaskPersistence;

import java.util.ArrayList;
import java.util.List;

public class TaskRepository {
   List<Task> tasks;

   public TaskRepository() {
      this.tasks = TaskPersistence.loadTasks();
   }

   public void save(Task task) throws TaskException {
      if (tasks.contains(task)) {
         throw new TaskException("La tarea ya existe en nuestra base de datos");
      }
      tasks.add(task);
      TaskPersistence.saveTasks(tasks);
   }

   public Task findById(String id) {
      for (Task task : tasks) {
         if (task.getId().equals(id)) {
            return task;
         }
      }
      return null;
   }

   public List<Task> findCompletedTask() throws TaskException {
      List<Task> completedTasks = new ArrayList<>();
      for (Task task : tasks) {
         if (task.getCompleted()) {
            completedTasks.add(task);
         }
      }

      if (completedTasks.isEmpty()) {
         throw new TaskException("No hay tareas completadas");
      }
      return completedTasks;
   }

   public List<Task> findPendingTask() throws TaskException {
      List<Task> pendingTasks = new ArrayList<>();
      for (Task task : tasks) {
         if (!task.getCompleted()) {
            pendingTasks.add(task);
         }
      }

      if (pendingTasks.isEmpty()) {
         throw new TaskException("No hay tareas pendientes");
      }
      return pendingTasks;
   }

   public void remove(String id) throws TaskValidationException {
      Task task = findById(id);
      if (task == null) {
         throw new TaskValidationException("La tarea no existe");
      }
      tasks.remove(task);
      TaskPersistence.saveTasks(tasks);
   }

   public void remove(Task task) throws TaskException {
      if (task == null) {
         throw new TaskException("La tarea no puede ser nula");
      }
      if (!tasks.contains(task)) {
         throw new TaskException("La tarea no existe");
      }
      tasks.remove(task);
      TaskPersistence.saveTasks(tasks);
   }

   public List<Task> findAll() throws TaskException {
      if (tasks.isEmpty()) {
         throw new TaskException("La lista de tareas esta vacia");
      }
      return tasks;
   }

   public int findIndexById(String id) {
      for (int i = 0; i < tasks.size(); i++) {
         if (tasks.get(i).getId().equals(id)) {
            return i;
         }
      }
      return -1;
   }

   public void updateTask(Task updateTask) throws TaskException {
      if (updateTask == null) {
         throw new TaskException("La tarea no puede ser nula");
      }
      int index = findIndexById(updateTask.getId());
      if (index == -1) {
         throw new TaskException("La tarea no existe");
      }
      tasks.set(index, updateTask);
      TaskPersistence.saveTasks(tasks);
   }

   public void updateTaskCompleted(String id, boolean completed) throws TaskException {
      int index = findIndexById(id);
      if (index == -1) {
         throw new TaskException("La tarea no existe o el id es invalido");
      }
      tasks.get(index).setCompleted(completed);
      TaskPersistence.saveTasks(tasks);
   }
}
