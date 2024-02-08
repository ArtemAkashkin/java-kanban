package service;

import model.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {
    public ArrayList<Task> historyStorage;

    public InMemoryHistoryManager() {
        this.historyStorage = new ArrayList<>();
    }

    @Override
    public void add(Task task) {
        if (historyStorage.size() > 9) {
            historyStorage.remove(0);
        }
        historyStorage.add(task);
    }

    @Override
    public ArrayList<Task> getHistory() {
        return historyStorage;
    }
}