package service;

import model.Epic;
import model.SubTask;
import model.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    List<Task> getTasks();

    List<Epic> getEpics();

    List<SubTask> getSubtasks();

    void clearAllTasks();

    void clearAllEpics();

    void clearAllSubTasks();

    Task getTask(int id);

    Epic getEpic(int id);

    SubTask getSubTask(int id);

    void create(Task task);

    void create(Epic epic);

    void create(SubTask subTask);

    boolean updateTask(Task task);

    boolean updateEpic(Epic epic);

    boolean updateSubTask(SubTask subTask);

    void calculateStatus(Epic epic);

    ArrayList<SubTask> giveSubTasks(Epic epic);

    void delete(Task task);

    void delete(Epic epic);

    void delete(SubTask subTask);

    List<Task> getHistory();
}
