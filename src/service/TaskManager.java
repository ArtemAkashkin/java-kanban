package service;

import model.Epic;
import model.SubTask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;

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

    public boolean getTasks() {
        for (Task task : tasks.values()){
            System.out.println(task.getName() + ". Id:" + task.getId());
        }
        return false;
    }

    public void getSubtasks() {
        for (SubTask subTask : subtasks.values()){
            System.out.println(subTask.getName() + ". Id:" + subTask.getId());
        }
    }

    public void getEpics() {
        for (Task epic : epics.values()){
            System.out.println("Имя задачи: " + epic.getName() + ". Id:" + epic.getId());
        }
    }

    public void clearAllTasks() {
        tasks.clear();
    }

    public void clearAllEpics() {
        epics.clear();
    }

    public void clearAllSubTasks() {
        subtasks.clear();
    }

    public String getTask(int id) {
        return (tasks.get(id)).getName();
    }

    public String getEpic(int id) {
        return (epics.get(id)).getName();
    }

    public String getSubTask(int id) {
        return (subtasks.get(id)).getName();
    }

    public Task create(Task task) {
        task.setId(generateId());
        tasks.put(task.getId(), task);
        return task;
    }

    public Epic create(Epic epic) {
        epic.setId(generateId());
        epics.put(epic.getId(), epic);
        return epic;
    }

    public SubTask create(SubTask subTask) {
        subTask.setId(generateId());
        subtasks.put(subTask.getId(), subTask);
        return subTask;
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
        Epic epic = subTask.getEpic();
        Epic savedEpic = epics.get(epic.getId());
        subTask.setName(subTask.getName());
        subTask.setDescription(subTask.getDescription());
        subTask.setStatus(subTask.getStatus());
        calculateStatus(savedEpic);
    }

    private void calculateStatus(Epic epic) {
        for (SubTask subTask : epic.getSubTasks()) {
            if (subTask.equals("NEW") || subTask.equals("IN PROGRESS")) {
                return;
            }
        }
        epic.setStatus("DONE");
    }

    public ArrayList<SubTask> giveSubTasks (Epic epic) {
        ArrayList<SubTask> listSubTasks = epic.getSubTasks();
        return listSubTasks;
    }

    public void delete(Task task) {
        tasks.remove(tasks.get(task.getId()));
    }

    public void delete(Epic epic) {
        epics.remove(epics.get(epic.getId()));
    }

    public void delete(SubTask subTask) {
        subtasks.remove(subtasks.get(subTask.getId()));
    }
}
