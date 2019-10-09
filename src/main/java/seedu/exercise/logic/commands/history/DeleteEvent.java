package seedu.exercise.logic.commands.history;

import seedu.exercise.model.Model;
import seedu.exercise.model.exercise.Exercise;

/**
 * Represents a particular delete event that can be redone or or undone.
 */
public class DeleteEvent implements Event {

    private static final String EVENT_DESCRIPTION = "Delete exercise: %1$s";

    /**
     * The exercise that has been deleted during the event.
     */
    private final Exercise exercise;

    /**
     * Creates a DeleteEvent to store the particular event of an exercise being deleted from the exercise book.
     *
     * @param exercise the exercise that has been deleted in this instance of DeleteEvent.
     */
    DeleteEvent(Exercise exercise) {
        this.exercise = exercise;
    }

    @Override
    public void undo(Model model) {
        model.addExercise(exercise);
    }

    @Override
    public void redo(Model model) {
        model.deleteExercise(exercise);
    }

    /**
     * Returns the exercise that was deleted.
     *
     * @return exercise that is passed into constructor of DeleteEvent
     */
    public Exercise getExercise() {
        return exercise;
    }

    @Override
    public String toString() {
        return String.format(EVENT_DESCRIPTION, exercise);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteEvent // instanceof handles nulls
                && exercise.equals(((DeleteEvent) other).getExercise()));
    }

}
