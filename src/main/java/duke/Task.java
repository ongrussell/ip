package duke;

public class Task {
    private String description;
    private boolean isMarked;

    public Task(String description) {
        this.description = description;
        this.isMarked = false;
    }

    public void setMarked() {
        isMarked = true;
    }

    public void unmark() {
        isMarked = false;
    }

    public String getDescription() {
        return description;
    }

    public boolean isMarked() {
        return isMarked;
    }

    @Override
    public String toString() {
        String status = isMarked ? "X" : " ";
        return "[" + status + "] " + description;
    }
}
