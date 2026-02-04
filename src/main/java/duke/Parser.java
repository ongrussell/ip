package duke;

/**
 * Provides helper methods to parse user input into command types and command arguments.
 * This class extracts information such as task indices, descriptions, and date/time strings
 * used by other parts of the application.
 */
public class Parser {

    /**
     * Parses the user input into a {@link CommandType}.
     *
     * @param input Full user input line.
     * @return The corresponding {@link CommandType} for the input.
     */
    public static CommandType parseCommandType(String input) {
        return CommandType.from(input);
    }

    /**
     * Parses a task number from the user input and converts it into a 0-based index.
     *
     * <p>Example input: {@code "mark 2"} returns index {@code 1}.</p>
     *
     * @param input Full user input line.
     * @param taskCount Current number of tasks in the task list.
     * @param commandWord Command word to show in error messages (e.g. {@code "mark"}, {@code "delete"}).
     * @return 0-based task index.
     * @throws SuuException If the task number is missing, not a number, or out of range.
     */
    public static int parseTaskIndex(String input, int taskCount, String commandWord) throws SuuException {
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            throw new SuuException("Please provide a task number. Example: " + commandWord + " 2");
        }

        int taskNum;
        try {
            taskNum = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            throw new SuuException("duke.Task number must be a number. Example: " + commandWord + " 2");
        }

        int index = taskNum - 1;
        if (index < 0 || index >= taskCount) {
            throw new SuuException("That task number does not exist.");
        }
        return index;
    }

    /**
     * Extracts the description for a {@code todo} command.
     *
     * @param input Full user input line (e.g. {@code "todo read book"}).
     * @return Description of the todo task.
     * @throws SuuException If the description is empty.
     */
    public static String parseTodoDescription(String input) throws SuuException {
        String desc = input.replaceFirst("todo", "").trim();
        if (desc.isEmpty()) {
            throw new SuuException("The description of a todo cannot be empty.");
        }
        return desc;
    }

    /**
     * Parses a {@code deadline} command into description and the raw {@code /by} date string.
     *
     * <p>Expected format: {@code deadline <task> /by <time>}</p>
     *
     * @param input Full user input line.
     * @return A 2-element array: {@code [description, byText]}.
     * @throws SuuException If the description or {@code /by} part is missing/empty or format is invalid.
     */
    public static String[] parseDeadline(String input) throws SuuException {
        String rest = input.replaceFirst("deadline", "").trim();
        if (rest.isEmpty()) {
            throw new SuuException("The description of a deadline cannot be empty.");
        }

        String[] parts = rest.split(" /by ", 2);
        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new SuuException("Use this format: deadline <task> /by <time>");
        }
        return new String[]{parts[0].trim(), parts[1].trim()};
    }

    /**
     * Parses an {@code event} command into description, start time, and end time strings.
     *
     * <p>Expected format: {@code event <task> /from <start> /to <end>}</p>
     *
     * @param input Full user input line.
     * @return A 3-element array: {@code [description, fromText, toText]}.
     * @throws SuuException If any required part is missing/empty or the format is invalid.
     */
    public static String[] parseEvent(String input) throws SuuException {
        String rest = input.replaceFirst("event", "").trim();
        if (rest.isEmpty()) {
            throw new SuuException("The description of an event cannot be empty.");
        }

        String[] firstSplit = rest.split(" /from ", 2);
        if (firstSplit.length < 2 || firstSplit[0].trim().isEmpty()) {
            throw new SuuException("Use this format: event <task> /from <start> /to <end>");
        }

        String desc = firstSplit[0].trim();
        String[] secondSplit = firstSplit[1].split(" /to ", 2);
        if (secondSplit.length < 2 || secondSplit[0].trim().isEmpty() || secondSplit[1].trim().isEmpty()) {
            throw new SuuException("Use this format: event <task> /from <start> /to <end>");
        }

        return new String[]{desc, secondSplit[0].trim(), secondSplit[1].trim()};
    }

    /**
     * Extracts the keyword for a {@code find} command.
     *
     * @param input Full user input line (e.g. {@code "find book"}).
     * @return Keyword to search for.
     * @throws SuuException If the keyword is missing/empty.
     */
    public static String parseFindKeyword(String input) throws SuuException {
        String[] parts = input.trim().split("\\s+", 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new SuuException("Please provide a keyword. Example: find book");
        }
        return parts[1].trim();
    }
}
