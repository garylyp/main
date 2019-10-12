package seedu.exercise.logic.commands.history;

import seedu.exercise.logic.commands.AddExerciseCommand;
import seedu.exercise.logic.commands.DeleteExerciseCommand;
import java.util.logging.Logger;

import seedu.exercise.commons.core.LogsCenter;
import seedu.exercise.logic.commands.ClearCommand;
import seedu.exercise.logic.commands.EditCommand;
import seedu.exercise.logic.commands.UndoableCommand;
import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.model.ModelManager;
import seedu.exercise.model.ReadOnlyExerciseBook;
import seedu.exercise.model.exercise.Exercise;

/**
 * A utility class to generate specific Event objects depending on requirements.
 */
public class EventFactory {

    public static final String MESSAGE_COMMAND_NOT_UNDOABLE =
            "The command \'%1$s\' cannot be stored as an undoable event.";

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    /**
     * Generates an Event object that can execute the behaviour of a given Command as well
     * as its opposite behaviour.
     *
     * @param command a command to be represented with using an Event object
     * @return an Event that can be undone or redone
     */
    static Event commandToEvent(UndoableCommand command) throws CommandException {
        if (command instanceof AddExerciseCommand) {
            Exercise exercise = ((AddExerciseCommand) command).getExercise();
            return new AddExerciseEvent(exercise);

        } else if (command instanceof DeleteExerciseCommand) {
            Exercise exercise = ((DeleteExerciseCommand) command).getExercise();
            return new DeleteExerciseEvent(exercise);

        } else if (command instanceof EditCommand) {
            Exercise exerciseOld = ((EditCommand) command).getExerciseToEdit();
            Exercise exerciseNew = ((EditCommand) command).getEditedExercise();
            return new EditEvent(exerciseOld, exerciseNew);

        } else if (command instanceof ClearCommand) {
            ReadOnlyExerciseBook exerciseBook = ((ClearCommand) command).getExerciseBookPrevious();
            logger.info("Exercise book: " + exerciseBook);
            return new ClearEvent(exerciseBook);

        } else {
            throw new CommandException(
                    String.format(MESSAGE_COMMAND_NOT_UNDOABLE, command.getClass().getName()));
        }
    }

}
