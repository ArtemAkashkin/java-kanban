package service;

import model.Task;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {
    Managers managers = new Managers();

    @Test
    void getDefaultTaskManager() {
        TaskManager manager = managers.getDefaultTaskManager();
        ArrayList<Task> listOfTasks  = new ArrayList<>();
        assertEquals(listOfTasks, manager.getTasks(), "Мэпы должны быть равны и пусты");
    }

    @Test
    void getDefaultHistory() {
        HistoryManager manager = managers.getDefaultHistory();
        ArrayList<Task> historyStorage = new ArrayList<>();
        assertEquals(historyStorage, manager.getHistory(), "Мэпы должны быть равны и пусты");
    }
}