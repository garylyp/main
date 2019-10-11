package seedu.exercise.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.exercise.logic.commands.history.EventHistory;
import seedu.exercise.model.ExerciseBook;
import seedu.exercise.model.Model;
import seedu.exercise.model.ReadOnlyExerciseBook;

/**
 * Clears the exercise book.
 */
public class ClearCommand extends Command implements UndoableCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Exercise book has been cleared!";

    private ReadOnlyExerciseBook exerciseBookPrevious;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        exerciseBookPrevious = new ExerciseBook(model.getAllData()); // Create a deep copy
        model.setExerciseBook(new ExerciseBook());
        EventHistory.getInstance().addCommandToUndoStack(this);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    public ReadOnlyExerciseBook getExerciseBookPrevious() {
        return exerciseBookPrevious;
    }
}
