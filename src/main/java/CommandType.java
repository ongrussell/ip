public enum CommandType {
    BYE("bye"),
    LIST("list"),
    MARK("mark"),
    UNMARK("unmark"),
    TODO("todo"),
    DEADLINE("deadline"),
    EVENT("event"),
    DELETE("delete"),
    UNKNOWN("");

    private final String word;

    CommandType(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public static CommandType from(String input) {
        if (input == null) {
            return UNKNOWN;
        }

        String trimmed = input.trim();
        if (trimmed.isEmpty()) {
            return UNKNOWN;
        }

        // first word determines command type
        String firstWord = trimmed.split(" ")[0].toLowerCase();

        for (CommandType type : CommandType.values()) {
            if (type.word.equals(firstWord)) {
                return type;
            }
        }
        return UNKNOWN;
    }
}
