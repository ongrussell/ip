package duke;

import java.io.*;
import java.util.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Handles loading tasks from disk and saving tasks back to disk.
 * Tasks are stored in a simple line-based text format (one task per line).
 */
public class Storage {
    private final File dataFile;

    /**
     * Creates a {@code Storage} that reads/writes to a data file located in the given directory.
     *
     * @param dirName Directory name (relative or absolute) that contains the data file.
     * @param fileName Name of the data file.
     */
    public Storage(String dirName, String fileName) {
        File dir = new File(dirName);
        this.dataFile = new File(dir, fileName);
    }

    /**
     * Loads tasks from the data file.
     *
     * <p>If the data file does not exist, this returns an empty list.</p>
     *
     * @return An {@link ArrayList} of tasks loaded from disk.
     * @throws SuuException If there is an I/O error while reading or the file contents are corrupted.
     */
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

    /**
     * Saves the given list of tasks to the data file, overwriting any existing content.
     *
     * <p>If the parent directory does not exist, it will be created.</p>
     *
     * @param tasks List of tasks to save.
     * @throws SuuException If the data folder cannot be created or an I/O error occurs when writing.
     */
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

    /**
     * Encodes a task into the storage line format.
     *
     * <p>Example formats:</p>
     * <ul>
     *   <li>{@code T | 1 | read book}</li>
     *   <li>{@code D | 0 | return book | 2019-10-15}</li>
     *   <li>{@code E | 0 | meeting | 2019-12-02T14:00 | 2019-12-02T16:00}</li>
     * </ul>
     *
     * @param task Task to encode.
     * @return Encoded line representation of the task.
     * @throws SuuException If the task type is not recognized.
     */
    private String encodeTask(Task task) throws SuuException {
        String done = task.isMarked() ? "1" : "0";

        if (task instanceof Todo) {
            return "T | " + done + " | " + task.getDescription();
        } else if (task instanceof Deadline) {
            Deadline d = (Deadline) task;
            return "D | " + done + " | " + task.getDescription() + " | " + d.getBy().toString();
        } else if (task instanceof Event) {
            Event e = (Event) task;
            return "E | " + done + " | " + task.getDescription()
                    + " | " + e.getFrom().toString()
                    + " | " + e.getTo().toString();
        }

        throw new SuuException("Unknown task type, cannot save.");
    }

    /**
     * Parses a single line from the data file into a {@link Task}.
     *
     * @param line A line from the save file.
     * @return The parsed task.
     * @throws SuuException If the line is empty, malformed, or contains an unknown task type.
     */
    private Task parseLine(String line) throws SuuException {
        String trimmed = line.trim();
        if (trimmed.isEmpty()) {
            throw new SuuException("Save file has an empty line.");
        }

        String[] parts = trimmed.split("\\s*\\|\\s*");
        if (parts.length < 3) {
            throw new SuuException("Save file is corrupted: " + line);
        }

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String desc = parts[2];

        Task task;
        switch (type) {
            case "T":
                task = new Todo(desc);
                break;
            case "D":
                if (parts.length != 4) throw new SuuException("Save file is corrupted: " + line);
                task = new Deadline(desc, LocalDate.parse(parts[3]));
                break;
            case "E":
                if (parts.length != 5) throw new SuuException("Save file is corrupted: " + line);
                task = new Event(desc, LocalDateTime.parse(parts[3]), LocalDateTime.parse(parts[4]));
                break;
            default:
                throw new SuuException("Save file has unknown task type: " + type);
        }

        if (isDone) task.setMarked();
        return task;
    }
}
