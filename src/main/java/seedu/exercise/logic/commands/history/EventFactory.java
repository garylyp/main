package seedu.exercise.logic.commands.history;

import java.util.concurrent.CompletionException;

import seedu.exercise.logic.commands.AddCommand;
import seedu.exercise.logic.commands.DeleteCommand;
import seedu.exercise.logic.commands.UndoableCommand;
import seedu.exercise.model.exercise.Exercise;

/**
 * A utility class to generate specific Event objects depending on requirements.
 */
public class EventFactory {

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

        } else {
            return null;
        }
    }

}
