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

    public void linkLast(Task newTask) {
        Node l = last;
        Node newNode = new Node(l, newTask, null);
        last = newNode;
        if (l == null)
            first = newNode;
        else
            l.setNext(newNode);
    }

    public List<Task> getTasks() {
        List<Task> getTasks = new LinkedList<>();
        Node currentNode = first;
        while (currentNode != null) {
            getTasks.add(currentNode.getTask());
            currentNode = currentNode.getNext();
        }
        return getTasks;
    }


    private void removeNode(Node node) {
        Node previous = node.getPrev();
        Node next = node.getNext();
        if (previous == null) {
            next.setPrev(null);
            first = next;
        } else if (next == null) {
            previous.setNext(null);
            last = previous;
        } else {
            next.setPrev(null);
            previous.setNext(null);
        }
    }

    @Override
    public void add(Task task) {
        if (historyMap.containsKey(task.getId())) {
            removeNode(historyMap.get(task.getId()));
        }
        linkLast(task);
        historyMap.put(task.getId(), last);
    }

    @Override
    public void remove(int id) {

    }

}