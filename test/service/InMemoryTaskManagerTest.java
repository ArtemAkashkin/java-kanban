package service;

import model.Epic;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    Managers managers = new Managers();

    @Test
    void shouldAddTasks() {
        TaskManager manager = managers.getDefaultTaskManager();
        Task task = new Task("Архитектура", "Создать план архитектуры проекта", "NEW");
        Task task1 = new Task("Архитектура", "Создать план архитектуры проекта", "NEW");
        manager.create(task);
        assertEquals(task, manager.getTask(1));
    }

    @Test
    void shouldGetTasksId() {
        TaskManager manager = managers.getDefaultTaskManager();
        Task task = new Task("Архитектура", "Создать план архитектуры проекта", "NEW");
        manager.create(task);
        assertEquals(task, manager.getTask(1));
    }

    @Test
    void shouldGetEpicsId() {
        TaskManager manager = managers.getDefaultTaskManager();
        Epic epic = new Epic("Фронт", "написать код на фронт", "NEW");
        manager.create(epic);
        assertEquals(epic, manager.getEpic(1));
    }

    @Test
    void shouldGetSubTaskId() {
        TaskManager manager = managers.getDefaultTaskManager();
        Epic epic = new Epic("Фронт", "написать код на фронт", "NEW");
        manager.create(epic);
        SubTask subTask = new SubTask("Сделать стили", "разметка и стили", "NEW", 1);
        manager.create(subTask);
        assertEquals(subTask, manager.getSubTask(2));
    }

    @Test
    void shouldNotConflictWithId() {
        TaskManager manager = managers.getDefaultTaskManager();
        Task task = new Task("Архитектура", "Создать план архитектуры проекта", "NEW");
        Task task1 = new Task("test", "Создать план архитектуры проекта", "NEW");
        manager.create(task);
        manager.create(task1);
        task1.setId(1);
        assertEquals(true, manager.updateTask(task1), "Элемент нельзя добавить, так как он равен нулю");
    }
}