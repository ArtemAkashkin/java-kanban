package service;

import model.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    @Test
    void shouldAddTaskInHistory() {
        Managers managers = new Managers();
//        HistoryManager managerHistory = managers.getDefaultHistory();
        TaskManager manager = managers.getDefaultTaskManager();
        Task task = new Task("Архитектура", "Создать план архитектуры проекта", "NEW");
        manager.create(task);
        manager.getTask(1);
        assertEquals(task, manager.getHistory().get(0));
    }
}