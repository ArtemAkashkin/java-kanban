package service;

import model.Epic;
import model.SubTask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskManager {

    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, Epic> epics;
    private HashMap<Integer, SubTask> subtasks;

    private int seq = 0;

    private int generateId() {
        return ++seq;
    }

    public TaskManager() {
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subtasks = new HashMap<>();
    }

    public ArrayList<Task> getTasks() {
        ArrayList<Task> listOfTasks = new ArrayList<Task>();
        listOfTasks.addAll(tasks.values());
        return listOfTasks;
    }

    public ArrayList<Epic> getEpics() {
        ArrayList<Epic> listOfEpics = new ArrayList<Epic>();
        listOfEpics.addAll(epics.values());
        return listOfEpics;
    }

    public ArrayList<SubTask> getSubtasks() {
        ArrayList<SubTask> listOfSubTasks = new ArrayList<SubTask>();
        listOfSubTasks.addAll(subtasks.values());
        return listOfSubTasks;
    }

    public void clearAllTasks() {
        tasks.clear();
    }

    public void clearAllEpics() {
        for (Epic epic : epics.values()) {
            epic.deleteIdSubTask();
        }
        epics.clear();
        subtasks.clear();
    }

    public void clearAllSubTasks() {
        for (Epic epic : epics.values()) {
            epic.deleteIdSubTask();
            epic.setStatus("NEW");
        }
        subtasks.clear();
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }

    public Epic getEpic(int id) {
        return epics.get(id);
    }

    public SubTask getSubTask(int id) {
        return subtasks.get(id);
    }

    public void create(Task task) {
        task.setId(generateId());
        tasks.put(task.getId(), task);
    }

    public void create(Epic epic) {
        epic.setId(generateId());
        epics.put(epic.getId(), epic);
    }

    public void create(SubTask subTask) {
        subTask.setId(generateId());
        subtasks.put(subTask.getId(), subTask);
        epics.get(subTask.getIdEpic()).addIdSubTasks(subTask.getId());
        calculateStatus(epics.get(subTask.getIdEpic()));
    }

    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void updateEpic(Epic epic) {
        Epic saved = epics.get(epic.getId());
        saved.setName(epic.getName());
        saved.setDescription(epic.getDescription());
    }

    public void updateSubTask(SubTask subTask) {
        SubTask savedSubTask = subtasks.get(subTask.getId());
        savedSubTask.setName(subTask.getName());
        savedSubTask.setDescription(subTask.getDescription());
        savedSubTask.setStatus(subTask.getStatus());
        calculateStatus(epics.get(subTask.getIdEpic()));
    }

    private void calculateStatus(Epic epic) {
        int a = 0;
        int b = 0;
        int c = 0;
        for (int IdSubTask : epic.getIdSubTasks()) {
            SubTask SavedSubTask = subtasks.get(IdSubTask);
            if (SavedSubTask.getStatus().equals("NEW")) {
                a += 1;
            } else if (SavedSubTask.getStatus().equals("IN PROGRESS")) {
                b += 1;
            } else {
                c += 1;
            }
        }
        if (a == epic.getIdSubTasks().size()) {
            epic.setStatus("NEW");
        } else  if (c == epic.getIdSubTasks().size()) {
            epic.setStatus("DONE");
        } else if (b >= 1) {
            epic.setStatus("IN PROGRESS");
        }
    }

    public ArrayList<SubTask> giveSubTasks (Epic epic) {
        ArrayList<SubTask> listSubTasks = new ArrayList<>();
        ArrayList<Integer> listIdSubTasks = epic.getIdSubTasks();
        for (int idSubTask : listIdSubTasks) {
            listSubTasks.add(subtasks.get(idSubTask));
        }
        return listSubTasks;
    }

    public void delete(Task task) {
        tasks.remove(task.getId());
    }

    public void delete(Epic epic) {
        epic.deleteIdSubTask();
        epics.remove(epic.getId());
    }

    public void delete(SubTask subTask) {
        Epic savedEpic = epics.get(subTask.getIdEpic());
        savedEpic.getIdSubTasks().remove(subTask.getId());
        subtasks.remove(subTask.getId());
    }
}
