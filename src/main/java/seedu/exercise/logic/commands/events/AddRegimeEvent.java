package seedu.exercise.logic.commands.events;

import seedu.exercise.model.Model;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.model.resource.Regime;

/**
 * Represents a particular add regime event that can be redone or undone.
 */
public class AddRegimeEvent implements Event {

    private static final String EVENT_DESCRIPTION = "Add regime: %1$s\n%2$s";

    private final boolean isRegimeExisting;
    private final Regime regimeToAdd;
    private final Regime previousRegime;

    /**
     * Creates an AddRegimeEvent to store the particular event of a regime being added to or
     * updated in the exercise book.
     *
     * @param isRegimeExisting Describes if the regime is already existing before the add regime event
     * @param regimeToAdd The regime that has been added during the event
     * @param previousRegime The regime that was existing before this event, if any
     */
    AddRegimeEvent(boolean isRegimeExisting, Regime regimeToAdd, Regime previousRegime) {
        this.isRegimeExisting = isRegimeExisting;
        this.regimeToAdd = regimeToAdd;
        this.previousRegime = previousRegime;
    }

    @Override
    public void undo(Model model) {
        if (isRegimeExisting) {
            model.setRegime(regimeToAdd, previousRegime);
        } else {
            model.deleteRegime(regimeToAdd);
        }
    }

    @Override
    public void redo(Model model) {
        if (isRegimeExisting) {
            model.setRegime(previousRegime, regimeToAdd);
        } else {
            model.addRegime(regimeToAdd);
        }
    }

    @Override
    public String toString() {
        return String.format(EVENT_DESCRIPTION,
                regimeToAdd.getRegimeName(),
                regimeToAdd);
    }
}
