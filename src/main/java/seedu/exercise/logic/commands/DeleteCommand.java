package seedu.exercise.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.exercise.commons.core.Messages;
import seedu.exercise.commons.core.index.Index;
import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.logic.commands.history.EventHistory;
import seedu.exercise.model.Model;
import seedu.exercise.model.exercise.Exercise;

/**
 * Deletes a exercise identified using it's displayed index from the exercise book.
 */
public class DeleteCommand extends Command implements UndoableCommand {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the exercise identified by the index number used in the displayed exercise list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_EXERCISE_SUCCESS = "Deleted Exercise: %1$s";

    private final Index targetIndex;
    private Exercise exerciseToDelete;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Exercise> lastShownList = model.getFilteredExerciseList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EXERCISE_DISPLAYED_INDEX);
        }

        exerciseToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteExercise(exerciseToDelete);
        EventHistory.getInstance().addCommandToUndoStack(this);
        return new CommandResult(String.format(MESSAGE_DELETE_EXERCISE_SUCCESS, exerciseToDelete));
    }

    /**
     * Returns the exercise to be deleted from Exercise Book.
     *
     * @return exercise referred to by targetIndex
     */
    public Exercise getExercise() {
        return exerciseToDelete;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
