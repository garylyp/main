package seedu.exercise.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.exercise.logic.commands.events.EventHistory;
import seedu.exercise.logic.commands.events.EventPayload;
import seedu.exercise.model.Model;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.resource.Exercise;

/**
 * Clears the exercise book.
 */
public class ClearCommand extends Command implements UndoableCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Exercise book has been cleared!";
    public static final String KEY_EXERCISE_BOOK_CLEARED = "exerciseBookCleared";

    private EventPayload<ReadOnlyResourceBook<Exercise>> eventPayload;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        ReadOnlyResourceBook<Exercise> exerciseBookCleared = new ReadOnlyResourceBook<>(model.getExerciseBookData());
        eventPayload = new EventPayload<>();
        eventPayload.put(KEY_EXERCISE_BOOK_CLEARED, exerciseBookCleared);
        EventHistory.getInstance().addCommandToUndoStack(this);
        model.setExerciseBook(new ReadOnlyResourceBook<>());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    /**
     * Returns the payload that stores the exercise book that has been cleared in this command.
     *
     * @return payload to store the exercise book that has been cleared in this command
     */
    public EventPayload<ReadOnlyResourceBook<Exercise>> getEventPayload() {
        return eventPayload;
    }

}
