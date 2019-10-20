package seedu.exercise.logic.commands.events;

import seedu.exercise.logic.commands.AddRegimeCommand;
import seedu.exercise.model.Model;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.model.resource.Regime;

/**
 * Represents a particular add regime event that can be redone or undone.
 */
public class AddRegimeEvent implements Event {

    private static final String EVENT_DESCRIPTION = "Add regime: %1$s\n%2$s";

    private final Regime regimeToAdd;

    /**
     * Creates an AddRegimeEvent to store the particular event of a regime being added to
     * the regime book.
     *
     * @param eventPayload a data carrier that stores the essential information for undo and redo
     */
    AddRegimeEvent(EventPayload<Regime> eventPayload) {
        this.regimeToAdd = eventPayload.get(AddRegimeCommand.KEY_REGIME_TO_ADD);
    }

    @Override
    public void undo(Model model) {
        model.deleteRegime(regimeToAdd);
    }

    @Override
    public void redo(Model model) {
        model.addRegime(regimeToAdd);
    }

    @Override
    public String toString() {
        return String.format(EVENT_DESCRIPTION,
                regimeToAdd.getRegimeName(),
                regimeToAdd);
    }
}
