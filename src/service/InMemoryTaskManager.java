package service;

import model.Epic;
import model.SubTask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {

    private Map<Integer, Task> tasks;
    private Map<Integer, Epic> epics;
    private Map<Integer, SubTask> subtasks;
    private HistoryManager historyManager;

    private int seq = 0;

    private int generateId() {
        return ++seq;
    }

    public InMemoryTaskManager() {
        this.historyManager = Managers.getDefaultHistory();
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subtasks = new HashMap<>();
    }

    @Override
    public List<Task> getTasks() {
        ArrayList<Task> listOfTasks = new ArrayList<Task>();
        listOfTasks.addAll(tasks.values());
        return listOfTasks;
    }

    @Override
    public List<Epic> getEpics() {
        ArrayList<Epic> listOfEpics = new ArrayList<Epic>();
        listOfEpics.addAll(epics.values());
        return listOfEpics;
    }

    @Override
    public List<SubTask> getSubtasks() {
        ArrayList<SubTask> listOfSubTasks = new ArrayList<SubTask>();
        listOfSubTasks.addAll(subtasks.values());
        return listOfSubTasks;
    }

    @Override
    public void clearAllTasks() {
        tasks.clear();
    }

    @Override
    public void clearAllEpics() {
        for (Epic epic : epics.values()) {
            epic.deleteIdSubTask();
        }
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void clearAllSubTasks() {
        for (Epic epic : epics.values()) {
            epic.deleteIdSubTask();
            epic.setStatus("NEW");
        }
        subtasks.clear();
    }

    @Override
    public Task getTask(int id) {
        historyManager.add(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public Epic getEpic(int id) {
        historyManager.add(epics.get(id));
        return epics.get(id);
    }

    @Override
    public SubTask getSubTask(int id) {
        historyManager.add(subtasks.get(id));
        return subtasks.get(id);
    }

    @Override
    public void create(Task task) {
        task.setId(generateId());
        tasks.put(task.getId(), task);
    }

    @Override
    public void create(Epic epic) {
        epic.setId(generateId());
        epics.put(epic.getId(), epic);
    }

    @Override
    public void create(SubTask subTask) {
        subTask.setId(generateId());
        subtasks.put(subTask.getId(), subTask);
        epics.get(subTask.getIdEpic()).addIdSubTasks(subTask.getId());
        calculateStatus(epics.get(subTask.getIdEpic()));
    }

    @Override
    public boolean updateTask(Task task) {
        Task newTask = tasks.get(task.getId());
        if (newTask == null) {
            return false;
        }
        tasks.put(task.getId(), task);
        return true;
    }

    @Override
    public boolean updateEpic(Epic epic) {
        Epic newEpic = epics.get(epic.getId());
        if (newEpic == null) {
            return false;
        }
        Epic saved = epics.get(epic.getId());
        saved.setName(epic.getName());
        saved.setDescription(epic.getDescription());
        return true;
    }

    @Override
    public boolean updateSubTask(SubTask subTask) {
        SubTask newSubTask = subtasks.get(subTask.getId());
        if (newSubTask == null) {
            return false;
        }
        SubTask savedSubTask = subtasks.get(subTask.getId());
        savedSubTask.setName(subTask.getName());
        savedSubTask.setDescription(subTask.getDescription());
        savedSubTask.setStatus(subTask.getStatus());
        calculateStatus(epics.get(subTask.getIdEpic()));
        return true;
    }

    @Override
    public void calculateStatus(Epic epic) {
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

    @Override
    public ArrayList<SubTask> giveSubTasks(Epic epic) {
        ArrayList<SubTask> listSubTasks = new ArrayList<>();
        ArrayList<Integer> listIdSubTasks = epic.getIdSubTasks();
        for (int idSubTask : listIdSubTasks) {
            listSubTasks.add(subtasks.get(idSubTask));
        }
        return listSubTasks;
    }

    @Override
    public void delete(Task task) {
        tasks.remove(task.getId());
    }

    @Override
    public void delete(Epic epic) {
        epic.deleteIdSubTask();
        epics.remove(epic.getId());
    }

    @Override
    public void delete(SubTask subTask) {
        Epic savedEpic = epics.get(subTask.getIdEpic());
        savedEpic.getIdSubTasks().remove(subTask.getId());
        subtasks.remove(subTask.getId());
        calculateStatus(epics.get(subTask.getIdEpic()));
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getTasks();
    }
}
