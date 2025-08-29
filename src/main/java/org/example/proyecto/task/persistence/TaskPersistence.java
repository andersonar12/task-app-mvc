package org.example.proyecto.task.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.example.proyecto.task.model.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TaskPersistence {
   private static final String FILE_PATH = "tasks.json";
   private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

   public static void saveTasks(List<Task> tasks) {
      try (Writer writer = new FileWriter(FILE_PATH)) {
         gson.toJson(tasks, writer);
      } catch (IOException e) {
         System.out.println("Error al guardar las tareas: " + e.getMessage());
      }
   }

   public static List<Task> loadTasks() {
      File file = new File(FILE_PATH);
      if (!file.exists()) {
         return new ArrayList<>();
      }
      try (Reader reader = new FileReader(file)) {
         return gson.fromJson(reader, new TypeToken<List<Task>>() {
         }.getType());
      } catch (IOException e) {
         System.out.println("Error al cargar las tareas: " + e.getMessage());
         return new ArrayList<>();
      }
   }
}
