package service;

import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {

    protected Map<Integer, Task> tasks;
    protected Map<Integer, Epic> epics;
    protected Map<Integer, SubTask> subtasks;
    protected HistoryManager historyManager;

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
        for (int taskId : tasks.keySet()) {
            historyManager.remove(taskId);
        }
        tasks.clear();
    }

    @Override
    public void clearAllEpics() {
        for (Epic epic : epics.values()) {
            epic.deleteIdSubTask();
            historyManager.remove(epic.getId());
        }
        for (int subTaskId : subtasks.keySet()) {
            historyManager.remove(subTaskId);
        }
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void clearAllSubTasks() {
        for (Epic epic : epics.values()) {
            epic.deleteIdSubTask();
            epic.setStatus(Status.NEW);
        }
        for (int subTaskId : subtasks.keySet()) {
            historyManager.remove(subTaskId);
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
        for (int idSubTask : epic.getIdSubTasks()) {
            SubTask savedSubTask = subtasks.get(idSubTask);
            if (savedSubTask.getStatus().equals(Status.NEW)) {
                a += 1;
            } else if (savedSubTask.getStatus().equals(Status.IN_PROGRESS)) {
                b += 1;
            } else {
                c += 1;
            }
        }
        if (a == epic.getIdSubTasks().size()) {
            epic.setStatus(Status.NEW);
        } else if (c == epic.getIdSubTasks().size()) {
            epic.setStatus(Status.DONE);
        } else if (b >= 1) {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }

    @Override
    public List<SubTask> giveSubTasks(Epic epic) {
        List<SubTask> listSubTasks = new ArrayList<>();
        List<Integer> listIdSubTasks = epic.getIdSubTasks();
        for (int idSubTask : listIdSubTasks) {
            listSubTasks.add(subtasks.get(idSubTask));
        }
        return listSubTasks;
    }

    @Override
    public void delete(Task task) {
        tasks.remove(task.getId());
        historyManager.remove(task.getId());
    }

    @Override
    public void delete(Epic epic) {
        historyManager.remove(epic.getId());
        for (int idSubTask : epic.getIdSubTasks()) {
            historyManager.remove(idSubTask);
            subtasks.remove(idSubTask);
        }
        epic.deleteIdSubTask();
        epics.remove(epic.getId());
    }

    @Override
    public void delete(SubTask subTask) {
        Epic savedEpic = epics.get(subTask.getIdEpic());
        savedEpic.getIdSubTasks().remove(subTask.getId());
        subtasks.remove(subTask.getId());
        calculateStatus(epics.get(subTask.getIdEpic()));
        historyManager.remove(subTask.getId());
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getTasks();
    }
}
