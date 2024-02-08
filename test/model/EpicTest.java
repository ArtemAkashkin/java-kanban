package model;

import org.junit.jupiter.api.Test;
import service.InMemoryTaskManager;
import service.Managers;
import service.TaskManager;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    @Test
    public void shouldBeEqualEpic() {
        Epic epic = new Epic("name", "NEW", "desc");
        Epic epic1 = new Epic("name", "NEW", "desc");
        assertEqualsEpicks(epic, epic1, "Эпики должны совпадать");

    }

    private static void assertEqualsEpicks(Task expected, Task actual, String message) {
        assertEquals(expected.getId(), actual.getId(), message + ", id");
        assertEquals(expected.getName(), actual.getName(), message + ", name");
    }

/*    @Test
    public void shouldNotAddThemselfEpic() {
        Epic epic = new Epic("name", "desc", "NEW");
        TaskManager taskManager = new InMemoryTaskManager();
        Managers managers = new Managers();
        TaskManager inMemoryTaskManager = managers.getDefaultTaskManager();
        SubTask epic1 = new SubTask("name", "desc", "NEW");

        inMemoryTaskManager.create(epic);
        inMemoryTaskManager.create(SubTask epic);
    }*/

}