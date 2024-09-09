package service;

import model.Task;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ManagersTest {


    @Test
    void getDefaultTaskManager() {
        TaskManager manager = Managers.getDefaultTaskManager();
        ArrayList<Task> listOfTasks  = new ArrayList<>();
        assertEquals(listOfTasks, manager.getTasks(), "Мэпы должны быть равны и пусты");
    }

    @Test
    void getDefaultHistory() {
        HistoryManager manager = Managers.getDefaultHistory();
        ArrayList<Task> historyStorage = new ArrayList<>();
        assertEquals(historyStorage, manager.getTasks(), "Мэпы должны быть равны и пусты");
    }
}