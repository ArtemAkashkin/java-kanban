package model;

public class SubTask extends Task {
    private Epic epic;

    public SubTask(String name, String description, String status) {
        super(name, description, status);
    }

    public Epic getEpic() {
        return epic;
    }

    public void setEpic(Epic epic) {
        this.epic = epic;
    }
}
