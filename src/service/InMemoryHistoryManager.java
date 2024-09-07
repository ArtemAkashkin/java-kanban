package service;

import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private Map<Integer, Node> historyMap = new HashMap<>();


    private Node first;

    private Node last;

    public void linkLast(Task newTask) {
        Node l = last;
        Node newNode = new Node(l, newTask, null);
        last = newNode;
        if (l == null)
            first = newNode;
        else
            l.next = newNode;
//        nodeListTasks.add(newNode);
    }

    public List<Task> getTasks() {
        List<Task> getTasks = new ArrayList<>();
        Node currentNode = first;
        if (currentNode != null) {
            getTasks.add(currentNode.task);
            while (currentNode.next != null) {
                getTasks.add(currentNode.next.task);
                currentNode = currentNode.next;
            }
        }
        return getTasks;
    }

    @Override
    public void removeNode(Node node) {
        if (node.prev == null) {
            node.next.prev = null;
        } else if (node.next == null) {
            node.prev.next = null;
            last = node.prev;
        } else {
            node.next.prev = null;
            node.prev.next = null;
        }
    }

    @Override
    public void add(Task task) {
        if (getTasks().contains(task)) {
            removeNode(historyMap.get(task.getId()));
        }
        linkLast(task);
        historyMap.put(task.getId(), last);
    }

    @Override
    public void remove(int id) {

    }

}