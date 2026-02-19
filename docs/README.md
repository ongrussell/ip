# Suu User Guide

![Suu GUI](Ui.png)

Suu is a simple task manager chatbot with a GUI. You can add tasks, mark them as done, delete them, search with keywords, and view quick statistics.

---

## Quick Start

1. Download and run the application JAR.
2. Type commands into the input box and press **Enter**.
3. Suu will respond in the chat area.

---

## Command Summary

| Command | What it does | Example |
|---|---|---|
| `list` | Shows all tasks | `list` |
| `todo` | Adds a todo task | `todo read book` |
| `deadline` | Adds a deadline task | `deadline return book /by 2026-02-20` |
| `event` | Adds an event task | `event meeting /from 2026-02-20 1400 /to 2026-02-20 1600` |
| `mark` | Marks a task as done | `mark 2` |
| `unmark` | Marks a task as not done | `unmark 2` |
| `delete` | Deletes a task | `delete 3` |
| `find` | Finds tasks containing a keyword | `find book` |
| `stats` | Shows task statistics | `stats` |
| `bye` | Exits the app | `bye` |

---

## Listing tasks: `list`

Shows all tasks currently in your list.

**Format:** `list`

Example:
`list`

Expected outcome:
- Suu prints a numbered list of tasks.
- If there are no tasks, Suu shows `(none)`.

---

## Adding a todo: `todo`

Adds a todo task.

**Format:** `todo DESCRIPTION`

Example:
`todo finish tutorial`

Expected outcome:
- Suu adds the task.
- Suu shows the updated number of tasks.

---

## Adding a deadline: `deadline`

Adds a deadline task with a due date.

**Format:** `deadline DESCRIPTION /by YYYY-MM-DD`

Example:
`deadline submit report /by 2026-02-25`

Expected outcome:
- Suu adds the deadline task with its due date.
- If the date format is invalid, Suu will show an error message.

---

## Adding an event: `event`

Adds an event task with a start and end date/time.

**Format:** `event DESCRIPTION /from YYYY-MM-DD HHmm /to YYYY-MM-DD HHmm`

Example:
`event project meeting /from 2026-02-21 1400 /to 2026-02-21 1600`

Expected outcome:
- Suu adds the event task with its start/end.
- If the date/time format is invalid, Suu will show an error message.

---

## Marking a task as done: `mark`

Marks an existing task as done.

**Format:** `mark INDEX`

Example:
`mark 1`

Expected outcome:
- Task at that index is marked as done.
- If the index is invalid, Suu will show an error message.

---

## Marking a task as not done: `unmark`

Unmarks an existing task (sets it back to not done).

**Format:** `unmark INDEX`

Example:
`unmark 1`

Expected outcome:
- Task at that index is unmarked.
- If the index is invalid, Suu will show an error message.

---

## Deleting a task: `delete`

Deletes a task from the list.

**Format:** `delete INDEX`

Example:
`delete 2`

Expected outcome:
- Suu removes the task.
- Suu shows the updated number of tasks.
- If the index is invalid, Suu will show an error message.

---

## Finding tasks: `find`

Finds tasks whose descriptions contain the given keyword (case-insensitive).

**Format:** `find KEYWORD`

Example:
`find book`

Expected outcome:
- Suu lists matching tasks.
- If there are no matches, Suu reports that none were found.

---

## Viewing statistics: `stats`

Shows a summary of your tasks (total, done/not done, and counts by type).

**Format:** `stats`

Example:
`stats`

Expected outcome:
- Suu prints totals and counts by task type.
- If there are no tasks, Suu will tell you there are no tasks yet.

---

## Exiting: `bye`

Closes the application.

**Format:** `bye`

Example:
`bye`

Expected outcome:
- Suu says goodbye and the app exits.

---

## Notes on Date/Time Formats

- Dates use: `YYYY-MM-DD` (e.g., `2026-02-25`)
- Event times use: `HHmm` in 24-hour format (e.g., `0900`, `1745`)

---

## Common Error Messages

Suu handles common input mistakes gracefully. For example:
- Missing task number: `mark` → Suu asks you to provide a task number.
- Invalid index: `delete 999` → Suu says the task number does not exist.
- Invalid date/time format → Suu prompts the correct format.

---
