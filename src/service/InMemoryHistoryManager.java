package service;

import model.Task;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private Map<Integer, Node> historyMap = new HashMap<>();


    private Node first;

    private Node last;

    public List<Task> getTasks() {
        List<Task> getTasks = new LinkedList<>();
        Node currentNode = first;
        while (currentNode != null) {
            getTasks.add(currentNode.getTask());
            currentNode = currentNode.getNext();
        }
        return getTasks;
    }


    @Override
    public void add(Task task) {
        if (task != null) {
            linkLast(task);
            removeNode(historyMap.get(task.getId()));
            historyMap.put(task.getId(), last);
        }
    }

    @Override
    public void remove(int id) {
        removeNode(historyMap.remove(id));
    }

    private void linkLast(Task newTask) {
        Node l = last;
        Node newNode = new Node(l, newTask, null);
        last = newNode;
        if (l == null)
            first = newNode;
        else
            l.setNext(newNode);
    }

    private void removeNode(Node node) {
        if (node == null) {
            return;
        }
        Node previous = node.getPrev();
        Node next = node.getNext();
        if (previous == null && next == null) {
            first = null;
            last = null;
        } else if (previous == null) {
            next.setPrev(null);
            first = next;
        } else if (next == null) {
            previous.setNext(null);
            last = previous;
        } else {
            next.setPrev(previous);
            previous.setNext(next);
        }
    }
}