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


        taskManager.create(task);
        taskManager.create(task1);
        System.out.println("Список задач: " + taskManager.getTasks()); // получение списка задач

        taskManager.create(epic);
        taskManager.create(epic1);
        System.out.println("Список епиков: " + taskManager.getEpics());

        SubTask subTask = new SubTask("Сделать стили", "разметка и стили", "NEW", 3);
        SubTask subTask1 = new SubTask("Разобрать с бутстрапом", "изучить документацию", "NEW", 3);
        SubTask subTask2 = new SubTask("Сделать разметку", "прописать первую страницу", "NEW", 4);

        taskManager.create(subTask1);
        taskManager.create(subTask);
        taskManager.create(subTask2);
        System.out.println("Список сабтасков: " + taskManager.getSubtasks());


        System.out.println("Имя задачи под введенным идентификатором: " + taskManager.getEpic(4));
        System.out.println("Имя задачи под введенным идентификатором: " + taskManager.getEpic(3));
        System.out.println("Имя задачи под введенным идентификатором: " + taskManager.getSubTask(5));
        System.out.println("Имя задачи под введенным идентификатором: " + taskManager.getTask(2));
//
        task1.setName("ООП");
        taskManager.updateTask(task1);
        taskManager.getTasks();

        System.out.println(taskManager.giveSubTasks(epic1));

        System.out.println("   ");

        subTask1.setStatus("DONE");
        subTask1.setName("Аналитика");
        subTask.setStatus("DONE");
        taskManager.updateSubTask(subTask1);
        taskManager.updateSubTask(subTask);
        System.out.println(taskManager.getSubtasks());
        System.out.println(epic.getStatus());



    }
}
