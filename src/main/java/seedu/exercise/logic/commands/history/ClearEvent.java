package seedu.exercise.logic.commands.history;

import seedu.exercise.model.ExerciseBook;
import seedu.exercise.model.Model;
import seedu.exercise.model.ReadOnlyExerciseBook;

/**
 * Represents a particular add event that can be redone or or undone.
 */
public class ClearEvent implements Event {

    private static final String EVENT_DESCRIPTION = "Clear Exercise Book: %1$s";

    /**
     * The exercise book that exists before the clear event.
     */
    private final ReadOnlyExerciseBook exerciseBook;

    /**
     * Creates a ClearEvent to store the particular event of the exercise book being cleared.
     *
     * @param exerciseBookPrevious an exercise book in the state before the ClearEvent.
     */
    ClearEvent(ReadOnlyExerciseBook exerciseBookPrevious) {
        this.exerciseBook = exerciseBookPrevious;
    }

    @Override
    public void undo(Model model) {
        model.setExerciseBook(exerciseBook);
    }

    @Override
    public void redo(Model model) {
        model.setExerciseBook(new ExerciseBook());
    }

    /**
     * Returns the exercise book that exists before the clear event.
     *
     * @return an exercise book in the state before the ClearEvent.
     */
    public ReadOnlyExerciseBook getExerciseBook() {
        return exerciseBook;
    }

    @Override
    public String toString() {
        return String.format(EVENT_DESCRIPTION, exerciseBook);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ClearEvent // instanceof handles nulls
                && exerciseBook.equals(((ClearEvent) other).getExerciseBook()));
    }

}
