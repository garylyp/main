package seedu.exercise.logic.commands.history;

import java.util.logging.Logger;

import seedu.exercise.commons.core.LogsCenter;
import seedu.exercise.logic.commands.AddCommand;
import seedu.exercise.logic.commands.ClearCommand;
import seedu.exercise.logic.commands.DeleteCommand;
import seedu.exercise.logic.commands.EditCommand;
import seedu.exercise.logic.commands.UndoableCommand;
import seedu.exercise.model.ModelManager;
import seedu.exercise.model.ReadOnlyExerciseBook;
import seedu.exercise.model.exercise.Exercise;

/**
 * A utility class to generate specific Event objects depending on requirements.
 */
public class EventFactory {

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    /**
     * Generate an Event object that can execute the behaviour of a given Command as well
     * as its opposite behaviour.
     *
     * @param command a command to be represented with using an Event object
     * @return an Event that can be undone or redone
     */
    static Event commandToEvent(UndoableCommand command) {
        if (command instanceof AddCommand) {
            Exercise exercise = ((AddCommand) command).getExercise();
            return new AddEvent(exercise);

        } else if (command instanceof DeleteCommand) {
            Exercise exercise = ((DeleteCommand) command).getExercise();
            return new DeleteEvent(exercise);

        } else if (command instanceof EditCommand) {
            Exercise exerciseOld = ((EditCommand) command).getExerciseToEdit();
            Exercise exerciseNew = ((EditCommand) command).getEditedExercise();
            return new EditEvent(exerciseOld, exerciseNew);

        } else if (command instanceof ClearCommand) {
            ReadOnlyExerciseBook exerciseBook = ((ClearCommand) command).getExerciseBookPrevious();
            logger.info("Exercise book: " + exerciseBook);
            return new ClearEvent(exerciseBook);

        } else {
            return null;
        }
    }

}
