package org.example.proyecto.task.model;
import org.example.proyecto.task.excepciones.TaskValidationException;

import java.util.ArrayList;
import java.util.List;

public class TaskRepository {
   List<Task> tasks = new ArrayList<>();

   public void save(Task task) throws TaskValidationException {
      if (task == null) {
         throw new TaskValidationException("La tarea no puede ser nula");
      }
      tasks.add(task);
   }

   public Task findById(String id) {
      for (Task task : tasks) {
         if (task.getId().equals(id)) {
            return task;
         }
      }
      return null;
   }

   public void remove(String id) throws TaskValidationException {
      Task task = findById(id);
      if (task == null) {
         throw new TaskValidationException("La tarea no existe");
      }
      tasks.remove(task);
   }

   public void remove(Task task) throws TaskValidationException {
      if (task == null) {
         throw new TaskValidationException("La tarea no puede ser nula");
      }
      if (!tasks.contains(task)) {
         throw new TaskValidationException("La tarea no existe");
      }
      tasks.remove(task);
   }

   public List<Task> findAll() throws TaskValidationException {
      if(tasks.isEmpty()){
         throw new TaskValidationException("La lista de tareas esta vacia");
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

   public void updateTask(Task updateTask) throws TaskValidationException {
      if(updateTask == null){
         throw new TaskValidationException("La tarea no puede ser nula");
      }
      int index = findIndexById(updateTask.getId());
      if(index == -1){
         throw new TaskValidationException("La tarea no existe");
      }
      tasks.set(index, updateTask);
   }
}
