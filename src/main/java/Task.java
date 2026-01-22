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

    @Override
    public String toString() {
        String status = isMarked ? "X" : " ";
        return "[" + status + "] " + description;
    }
}
