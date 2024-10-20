package model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Integer> idSubTasks = new ArrayList<>();

    public Epic(String name, String description, Status status) {
        super(name, description, status);
    }

    public List<Integer> getIdSubTasks() {
        return idSubTasks;
    }

    public void setIdSubTasks(List<Integer> idSubTasks) {
        this.idSubTasks = idSubTasks;
    }


    public void deleteIdSubTask() {
        this.idSubTasks.clear();
    }

    public void addIdSubTasks(Integer id) {
        this.idSubTasks.add(id);
    }

    @Override
    public String toString() {
        String a = "Epic{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status='" + getStatus() + '\'' +
                ' ';
        return a;
    }
}