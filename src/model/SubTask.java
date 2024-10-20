package model;

import java.util.Objects;

public class SubTask extends Task {
    private Integer idEpic;

    public SubTask(String name, String description, Status status, int idEpic) {
        super(name, description, status);
        this.idEpic = idEpic;
    }

    public SubTask(int id, String name, String description, Status status, int idEpic) {
        super(id, name, description, status);
        this.idEpic = idEpic;
    }

    public SubTask(String name, String description, Status status) {
        super(name, description, status);
    }

    @Override
    public Integer getIdEpic() {
        return idEpic;
    }

    public void setIdEpic(int idEpic) {
        this.idEpic = idEpic;
    }

    @Override
    public TaskType getTaskType() {
        return TaskType.SUBTASK;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idEpic);
    }

    @Override
    public String toString() {
        String a = "SubTask{" + "name='" + getName() + '\'' + ", description='" + getDescription() + '\'' + ", id=" + getId() + ", status='" + getStatus() + '\'' + ' ';
        return a;
    }

}
