package service;

import model.Task;

import java.util.ArrayList;
import java.util.List;

public interface HistoryManager {
    void add(Task task);

    void removeNode(Node node);

    void remove(int id);

    ArrayList<Task> getTasks();
}