package service;

import model.Task;

import java.util.ArrayList;

public interface HistoryManager {
    void add(Task task);

    void removeNode(Node node);

    void remove(int id);

    ArrayList<Task> getTasks();
}