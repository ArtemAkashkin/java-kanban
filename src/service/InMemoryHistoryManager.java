package service;

import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    //    public LinkedList<Task> historyStorage;
    private HashMap<Integer, Node> historyMap = new HashMap<>();
    private List<Node> nodeListTasks;


    private Node first;

    private Node last;

    public void linkLast(Task lastNode) {
        Node l = last;
        Node newNode = new Node(l, lastNode, null);
        last = newNode;
        if (l == null)
            first = newNode;
        else
            l.next = newNode;
        nodeListTasks.add(newNode);
    }

    public ArrayList<Task> getTasks() {
        ArrayList<Task> getTasks = new ArrayList<>();
        for (Node task : nodeListTasks) {
            getTasks.add(task.task);
        }
        return getTasks;
    }

    @Override
    public void removeNode(Node node) {
        nodeListTasks.remove(node);
    }


    public InMemoryHistoryManager() {
        this.nodeListTasks = new LinkedList<>();
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