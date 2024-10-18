package service;

import model.Epic;
import model.SubTask;
import model.Task;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileBackedTaskManager extends InMemoryTaskManager implements TaskManager {
    private final String fileName;

    public FileBackedTaskManager(String fileName) {
        this.fileName = fileName;
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
}
