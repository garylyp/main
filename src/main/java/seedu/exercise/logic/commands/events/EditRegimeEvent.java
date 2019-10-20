package seedu.exercise.logic.commands.events;

import seedu.exercise.logic.commands.AddRegimeCommand;
import seedu.exercise.model.Model;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.model.resource.Regime;

/**
 * Represents a particular edit regime event that can be redone or undone. Edit regime events
 * are induced using AddRegimeCommand or DeleteRegimeCommand on an existing command.
 */
public class EditRegimeEvent implements Event {

    private static final String EVENT_DESCRIPTION = "Edit regime: %1$s\n%2$s";

    private final Regime regimeToAdd;
    private final Regime previousRegime;

    /**
     * Creates an EditRegimeEvent to store the particular event of a regime being edited in the regime book.
     *
     * @param eventPayload a data carrier that stores the essential information for undo and redo
     */
    EditRegimeEvent(EventPayload<Regime> eventPayload) {
        this.regimeToAdd = eventPayload.get(AddRegimeCommand.KEY_EDITED_REGIME);
        this.previousRegime = eventPayload.get(AddRegimeCommand.KEY_PREVIOUS_REGIME);
    }

    @Override
    public void undo(Model model) {
        model.setRegime(regimeToAdd, previousRegime);
    }

    @Override
    public void redo(Model model) {
        model.setRegime(previousRegime, regimeToAdd);
    }

    @Override
    public String toString() {
        return String.format(EVENT_DESCRIPTION,
                regimeToAdd.getRegimeName(),
                regimeToAdd);
    }
}
