package duke;

/**
 * Represents the supported command types of the chatbot.
 * Each command type is mapped to a command word (e.g. {@code "list"}, {@code "todo"}).
 */
public enum CommandType {
    BYE("bye"),
    LIST("list"),
    MARK("mark"),
    UNMARK("unmark"),
    TODO("todo"),
    DEADLINE("deadline"),
    EVENT("event"),
    DELETE("delete"),
    FIND("find"),
    STATS("stats"),
    UNKNOWN("");

    private final String word;

    /**
     * Creates a {@code CommandType} with the associated command word.
     *
     * @param word Command word that identifies this command type.
     */
    CommandType(String word) {
        this.word = word;
    }

    /**
     * Returns the command word associated with this command type.
     *
     * @return Command word.
     */
    public String getWord() {
        return word;
    }

    /**
     * Determines the {@code CommandType} from the given user input.
     * The first word of the input is used to decide the command type.
     *
     * @param input Full user input line.
     * @return Matching {@code CommandType}, or {@link #UNKNOWN} if no match is found.
     */
    public static CommandType from(String input) {
        if (input == null) {
            return UNKNOWN;
        }

        String trimmed = input.trim();
        if (trimmed.isEmpty()) {
            return UNKNOWN;
        }

        // first word determines command type
        String firstWord = trimmed.split("\\s+", 2)[0].toLowerCase();

        for (CommandType type : CommandType.values()) {
            if (type.word.equals(firstWord)) {
                return type;
            }
        }
        return UNKNOWN;
    }
}
