package seedu.exercise.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.exercise.commons.core.Messages;
import seedu.exercise.commons.core.index.Index;
import seedu.exercise.logic.commands.events.EventHistory;
import seedu.exercise.logic.commands.events.EventPayload;
import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.model.Model;
import seedu.exercise.model.resource.Exercise;

/**
 * Deletes an exercise identified using it's displayed index from the exercise book.
 */
public class DeleteExerciseCommand extends DeleteCommand {

    public static final String MESSAGE_DELETE_EXERCISE_SUCCESS = "Deleted Exercise: %1$s";
    public static final String RESOURCE_TYPE = "exercise";
    public static final String KEY_EXERCISE_TO_DELETE = "exerciseToDelete";

    private final Index targetIndex;
    private EventPayload<Exercise> eventPayload;

    public DeleteExerciseCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.eventPayload = new EventPayload<>();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Exercise> lastShownList = model.getFilteredExerciseList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EXERCISE_DISPLAYED_INDEX);
        }

        Exercise exerciseToDelete = lastShownList.get(targetIndex.getZeroBased());
        eventPayload.put(KEY_EXERCISE_TO_DELETE, exerciseToDelete);
        model.deleteExercise(exerciseToDelete);
        EventHistory.getInstance().addCommandToUndoStack(this);
        return new CommandResult(String.format(MESSAGE_DELETE_EXERCISE_SUCCESS, exerciseToDelete));
    }

    @Override
    public String getResourceType() {
        return RESOURCE_TYPE;
    }

    /**
     * Returns the payload that stores the exercise that has been deleted in this command.
     *
     * @return payload to store the exercise that have been used in this command
     */
    public EventPayload<Exercise> getEventPayload() {
        return eventPayload;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DeleteExerciseCommand // instanceof handles nulls
            && targetIndex.equals(((DeleteExerciseCommand) other).targetIndex)); // state check
    }
}
