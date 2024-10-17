package service;

import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryTaskManagerTest {

    @Test
    void shouldAddTasks() {
        TaskManager manager = Managers.getDefaultTaskManager();
        Task task = new Task("Архитектура", "Создать план архитектуры проекта", Status.NEW);
        Task task1 = new Task("Архитектура", "Создать план архитектуры проекта", Status.NEW);
        manager.create(task);
        assertEquals(task, manager.getTask(1));
    }

    @Test
    void shouldGetTasksId() {
        TaskManager manager = Managers.getDefaultTaskManager();
        Task task = new Task("Архитектура", "Создать план архитектуры проекта", Status.NEW);
        manager.create(task);
        assertEquals(task, manager.getTask(1));
    }

    @Test
    void shouldGetEpicsId() {
        TaskManager manager = Managers.getDefaultTaskManager();
        Epic epic = new Epic("Фронт", "написать код на фронт", Status.NEW);
        manager.create(epic);
        assertEquals(epic, manager.getEpic(1));
    }

    @Test
    void shouldGetSubTaskId() {
        TaskManager manager = Managers.getDefaultTaskManager();
        Epic epic = new Epic("Фронт", "написать код на фронт", Status.NEW);
        manager.create(epic);
        SubTask subTask = new SubTask("Сделать стили", "разметка и стили", Status.NEW, 1);
        manager.create(subTask);
        assertEquals(subTask, manager.getSubTask(2));
    }

    @Test
    void shouldNotConflictWithId() {
        TaskManager manager = Managers.getDefaultTaskManager();
        Task task = new Task("Архитектура", "Создать план архитектуры проекта", Status.NEW);
        Task task1 = new Task("test", "Создать план архитектуры проекта", Status.NEW);
        manager.create(task);
        manager.create(task1);
        task1.setId(1);
        assertEquals(true, manager.updateTask(task1), "Элемент нельзя добавить, так как он равен нулю");
    }
}