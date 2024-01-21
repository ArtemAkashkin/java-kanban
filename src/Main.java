import model.Epic;
import model.SubTask;
import model.Task;
import service.TaskManager;

import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        TaskManager taskManager = new TaskManager();

        Task task = new Task("Архитектура", "Создать план архитектуры проекта", "NEW");
        Task task1 = new Task("Бэк", "Написать код на бэк", "NEW");
        Epic epic = new Epic("Фронт", "написать код на фронт", "NEW");
        Epic epic1 = new Epic("Фронт 2", "написать код на фронт2", "NEW");
        SubTask subTask = new SubTask("Сделать стили", "разметка и стили", "NEW");
        SubTask subTask1 = new SubTask("Разобрать с бутстрапом", "изучить документацию", "NEW");
        SubTask subTask2 = new SubTask("Сделать разметку", "прописать первую страницу", "NEW");
        epic.addSubTasks(subTask);
        epic.addSubTasks(subTask);
        epic1.addSubTasks(subTask2);
        subTask.setEpic(epic);
        subTask1.setEpic(epic);
        subTask2.setEpic(epic1);



        taskManager.create(task);
        taskManager.create(task1);
        taskManager.getTasks(); // получение списка задач

        taskManager.create(epic);
        taskManager.create(epic1);
        taskManager.getEpics();

        taskManager.create(subTask1);
        taskManager.create(subTask);
        taskManager.create(subTask2);
        taskManager.getSubtasks();

        System.out.println("Имя задачи под введенным идентификатором: " + taskManager.getEpic(4));
        System.out.println("Имя задачи под введенным идентификатором: " + taskManager.getEpic(3));
        System.out.println("Имя задачи под введенным идентификатором: " + taskManager.getSubTask(7));
        System.out.println("Имя задачи под введенным идентификатором: " + taskManager.getTask(2));

        task1.setName("ООП");
        taskManager.updateTask(task1);
        taskManager.getTasks();

        subTask1.setStatus("DONE");
        subTask1.setName("Аналитика фронта");
        subTask.setStatus("DONE");
        taskManager.updateSubTask(subTask1);
        taskManager.updateSubTask(subTask2);
        taskManager.getSubtasks();
        System.out.println(epic.getStatus());
//
    }
}
