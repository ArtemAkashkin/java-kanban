package service;

import model.*;

import java.io.*;

public class FileBackedTaskManager extends InMemoryTaskManager implements TaskManager {
    private final String fileName;

    public FileBackedTaskManager(String fileName) {
        this.fileName = fileName;
    }


    @Override
    public void clearAllTasks() {
        super.clearAllTasks();
        save();
    }

    @Override
    public void clearAllEpics() {
        super.clearAllEpics();
        save();
    }

    @Override
    public void clearAllSubTasks() {
        super.clearAllSubTasks();
        save();
    }

    @Override
    public boolean updateTask(Task task) {
        boolean isUpdated = super.updateTask(task);
        save();
        return isUpdated;
    }

    @Override
    public boolean updateEpic(Epic epic) {
        boolean isUpdated = super.updateEpic(epic);
        save();
        return isUpdated;
    }

    @Override
    public boolean updateSubTask(SubTask subTask) {
        boolean isUpdated = super.updateSubTask(subTask);
        save();
        return isUpdated;
    }

    @Override
    public void delete(Task task) {
        super.delete(task);
        save();
    }

    @Override
    public void delete(Epic epic) {
        super.delete(epic);
        save();
    }

    @Override
    public void delete(SubTask subTask) {
        super.delete(subTask);
        save();
    }

    @Override
    public void create(Task task) {
        super.create(task);
        save();
    }

    @Override
    public void create(Epic epic) {
        super.create(epic);
        save();
    }

    @Override
    public void create(SubTask subtask) {
        super.create(subtask);
        save();
    }

    private void save() {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(fileName))) {
            fileWriter.write("id,type,name,status,description,epic");
            fileWriter.newLine();
            for (Task task : tasks.values()) {
                fileWriter.write(toString(task));
                fileWriter.newLine();
            }
            for (Epic epic : epics.values()) {
                fileWriter.write(toString(epic));
                fileWriter.newLine();
            }
            for (SubTask subTask : subtasks.values()) {
                fileWriter.write(toString(subTask));
                fileWriter.newLine();
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Произошла ошибка с вводом выводом файла " + fileName, e);
        }
    }

    private String toString(Task task) {
        String epicId;
        if (task.getIdEpic() == null) {
            epicId = "";
        } else {
            epicId = String.valueOf(task.getIdEpic());
        }
        return String.format("%s,%s,%s,%s,%s,%s", task.getId(), task.getTaskType(), task.getName(),
                task.getStatus(), task.getDescription(), epicId);
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
            FileBackedTaskManager taskManager = new FileBackedTaskManager(file.getName());
            fileReader.readLine();
            int maxId = 0;

            while (fileReader.ready()) {
                String line = fileReader.readLine();
                Task task = fromString(line);
                TaskType taskType = task.getTaskType();
                if (task.getId() > maxId) maxId = task.getId();
                if (taskType.equals(TaskType.TASK)) {
                    taskManager.tasks.put(task.getId(), task);
                } else if (taskType.equals(TaskType.EPIC)) {
                    taskManager.epics.put(task.getId(), (Epic) task);
                } else if (taskType.equals(TaskType.SUBTASK)) {
                    SubTask subTask = (SubTask) task;
                    taskManager.subtasks.put(task.getId(), subTask);
                    taskManager.epics.get(subTask.getIdEpic()).addIdSubTasks(subTask.getId());
                }
            }
            taskManager.setId(maxId);
            return taskManager;
        } catch (IOException e) {
            throw new ManagerSaveException("Произошла ошибка с выводом файла " + file.getName(), e);
        }
    }

    private static Task fromString(String value) {
        String[] values = value.split(",");
        int id = Integer.parseInt(values[0]);
        TaskType taskType = TaskType.valueOf(values[1]);
        String name = values[2];
        Status status = Status.valueOf(values[3]);
        String description = values[4];
        if (taskType.equals(TaskType.TASK)) {
            return new Task(id, name, description, status);
        } else if (taskType.equals(TaskType.EPIC)) {
            return new Epic(id, name, description, status);
        } else if ((taskType.equals(TaskType.SUBTASK))) {
            int epic = Integer.parseInt(values[5]);
            return new SubTask(id, name, description, status, epic);
        } else {
            throw new ManagerSaveException("Неизвестного типа задача!");
        }
    }
}
