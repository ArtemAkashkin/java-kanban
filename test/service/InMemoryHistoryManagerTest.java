package service;

import model.Epic;
import model.Task;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryHistoryManagerTest {
    TaskManager manager = Managers.getDefaultTaskManager();
    Task task = new Task("Архитектура", "Создать план архитектуры проекта", "NEW");
    Epic epic = new Epic("name", "NEW", "desc");
    Epic epic1 = new Epic("name", "NEW", "desc");

    @Test
    void shouldAddTaskInHistory() {
//        HistoryManager managerHistory = managers.getDefaultHistory();
        manager.create(task);
        manager.getTask(1);
        assertEquals(task, manager.getHistory().get(0));
    }

    @Test
    void shouldDeleteDuplicate() {
        manager.create(task);
        manager.create(epic);
        manager.create(epic1);
        ArrayList<Task> newList = new ArrayList<>();
        newList.add(task);
        newList.add(epic1);
        manager.getTask(1);
        manager.getEpic(3);
        manager.getEpic(3);
        assertEquals(newList, manager.getHistory());
    }
}