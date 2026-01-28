import java.io.*;
import java.util.*;


public class Storage {
    private final File dataFile;

    public Storage(String dirName, String fileName) {
        File dir = new File(dirName);
        this.dataFile = new File(dir, fileName);
    }

    public ArrayList<Task> load() throws SuuException {
        ArrayList<Task> loaded = new ArrayList<>();


        if (!dataFile.exists()) { // if the file don't exist
            return loaded;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(dataFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                Task t = parseLine(line);
                if (t != null) {
                    loaded.add(t);
                }
            }
        } catch (IOException e) {
            throw new SuuException("I had trouble reading the save file.");
        }

        return loaded;
    }

    public void save(List<Task> tasks) throws SuuException {

        File parent = dataFile.getParentFile();
        if (parent != null && !parent.exists()) {
            if (!parent.mkdirs()) {
                throw new SuuException("I couldn't create the data folder to save tasks.");
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(dataFile, false))) {
            for (Task t : tasks) {
                bw.write(encodeTask(t));
                bw.newLine();
            }
        } catch (IOException e) {
            throw new SuuException("I had trouble saving your tasks.");
        }
    }

    // Format for tasks
    // T | 1 | read book
    // D | 0 | return book | Sunday
    // E | 0 | meeting | Mon 2pm | Mon 4pm
    private String encodeTask(Task task) throws SuuException {
        String done = task.isMarked() ? "1" : "0";

        if (task instanceof Todo) {
            return "T | " + done + " | " + task.getDescription();
        } else if (task instanceof Deadline) {
            Deadline d = (Deadline) task;
            return "D | " + done + " | " + task.getDescription() + " | " + d.getDeadline();
        } else if (task instanceof Event) {
            Event e = (Event) task;
            return "E | " + done + " | " + task.getDescription()
                    + " | " + e.getStartDate() + " | " + e.getEndDate();
        }

        throw new SuuException("Unknown task type, cannot save.");
    }

    private Task parseLine(String line) {
        if (line == null) return null;

        String trimmed = line.trim();
        if (trimmed.isEmpty()) return null;

        String[] parts = trimmed.split("\\s*\\|\\s*");
        if (parts.length < 3) return null;

        String type = parts[0].trim();
        String doneStr = parts[1].trim();
        String desc = parts[2].trim();

        Task task;
        switch (type) {
            case "T":
                task = new Todo(desc);
                break;
            case "D":
                if (parts.length < 4) return null;
                task = new Deadline(desc, parts[3].trim());
                break;
            case "E":
                if (parts.length < 5) return null;
                task = new Event(desc, parts[3].trim(), parts[4].trim());
                break;
            default:
                return null;
        }

        if ("1".equals(doneStr)) {
            task.setMarked();
        }
        return task;
    }
}
