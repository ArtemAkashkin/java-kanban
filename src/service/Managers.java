package service;

public class Managers {
    private Managers() {
    }

    public static TaskManager getDefaultTaskManager() {
        return new FileBackedTaskManager("Artem.csv");
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}