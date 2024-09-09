package service;

import model.Task;

import java.util.List;

public interface HistoryManager {
    void add(Task task);

    private void removeNode(Node node) {

    }

    void remove(int id);

    List<Task> getTasks();
}