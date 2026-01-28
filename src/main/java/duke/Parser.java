package duke;

public class Parser {

    public static CommandType parseCommandType(String input) {
        return CommandType.from(input);
    }

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

    public static String parseTodoDescription(String input) throws SuuException {
        String desc = input.replaceFirst("todo", "").trim();
        if (desc.isEmpty()) {
            throw new SuuException("The description of a todo cannot be empty.");
        }
        return desc;
    }

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
}