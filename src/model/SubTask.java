package model;

import java.util.Objects;

public class SubTask extends Task {
    private int idEpic;

    public SubTask(String name, String description, String status, int idEpic) {
        super(name, description, status);
        this.idEpic = idEpic;
    }

    public SubTask(String name, String description, String status) {
        super(name, description, status);
    }

    public int getIdEpic() {
        return idEpic;
    }

    public void setIdEpic(int idEpic) {
        this.idEpic = idEpic;
    }


    @Override
    public int hashCode() {
        return Objects.hashCode(idEpic);
    }

    @Override
    public String toString() {
        String a = "SubTask{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status='" + getStatus() + '\'' +
                ' ';
        return a;
    }

}
