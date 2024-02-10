package service;

import model.Task;

import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    public LinkedList<Task> historyStorage;
    private final int maxAmountOfStorage = 10;

    public InMemoryHistoryManager() {
        this.historyStorage = new LinkedList<>();
    }

    @Override
    public void add(Task task) {
        if (historyStorage.size() >= maxAmountOfStorage) {
            historyStorage.remove(0);
        }
        historyStorage.add(task);
    }

    @Override
    public LinkedList<Task> getHistory() {
        return historyStorage;
    }
}