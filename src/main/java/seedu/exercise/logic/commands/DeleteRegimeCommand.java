package seedu.exercise.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.exercise.commons.core.Messages;
import seedu.exercise.commons.core.index.Index;
import seedu.exercise.logic.commands.events.EventHistory;
import seedu.exercise.logic.commands.events.EventPayload;
import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.model.Model;
import seedu.exercise.model.UniqueResourceList;
import seedu.exercise.model.property.Name;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.model.resource.Regime;

/**
 * Deletes a regime identified using it's name or deletes exercises in regime.
 */
public class DeleteRegimeCommand extends DeleteCommand {

    public static final String MESSAGE_DELETE_REGIME_SUCCESS = "Deleted Regime: %1$s";
    public static final String MESSAGE_REGIME_DOES_NOT_EXIST = "No such regime in regime book.";
    public static final String MESSAGE_DELETE_EXERCISE_IN_REGIME_SUCCESS = "Deleted exercises in regime.";
    public static final String RESOURCE_TYPE = "regime";
    public static final String KEY_REGIME_TO_DELETE = "regimeToDelete";
    public static final String KEY_PREVIOUS_REGIME = "previousRegime";
    public static final String KEY_EDITED_REGIME = "editedRegime";

    private final List<Index> indexes;
    private final Name name;
    private boolean isRegimeEdited;
    private final EventPayload<Regime> eventPayload;

    public DeleteRegimeCommand(Name name, List<Index> indexes) {
        this.name = name;
        this.indexes = indexes;
        this.eventPayload = new EventPayload<>();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Regime> lastShownList = model.getFilteredRegimeList();
        Regime regime = new Regime(name, new UniqueResourceList<>());

        if (!model.hasRegime(regime)) {
            throw new CommandException(MESSAGE_REGIME_DOES_NOT_EXIST);
        }

        int indexOfRegime = model.getRegimeIndex(regime);
        Regime regimeToDelete = lastShownList.get(indexOfRegime);

        //no index provided, delete regime
        if (indexes == null) {
            isRegimeEdited = false;
            model.deleteRegime(regimeToDelete);
            eventPayload.put(KEY_REGIME_TO_DELETE, regimeToDelete);
            EventHistory.getInstance().addCommandToUndoStack(this);
            return new CommandResult(String.format(MESSAGE_DELETE_REGIME_SUCCESS, regimeToDelete));

        } else { //index provided, delete exercise in regime
            isRegimeEdited = true;
            Regime previousRegime = regimeToDelete;
            Regime editedRegime = previousRegime.getDuplicateCopy();
            List<Exercise> currentExerciseList = previousRegime.getRegimeExercises().asUnmodifiableObservableList();

            //check all index valid
            for (Index targetIndex : indexes) {
                if (targetIndex.getZeroBased() >= currentExerciseList.size()) {
                    throw new CommandException(Messages.MESSAGE_INVALID_EXERCISE_DISPLAYED_INDEX);
                }
            }

            // delete exercise identified by index
            for (Index targetIndex : indexes) {
                Exercise exerciseToDelete = currentExerciseList.get(targetIndex.getZeroBased());
                editedRegime.deleteExercise(exerciseToDelete);
            }

            eventPayload.put(KEY_PREVIOUS_REGIME, previousRegime);
            eventPayload.put(KEY_EDITED_REGIME, editedRegime);
            model.setRegime(previousRegime, editedRegime);
            model.updateFilteredRegimeList(Model.PREDICATE_SHOW_ALL_REGIMES);
            EventHistory.getInstance().addCommandToUndoStack(this);
            return new CommandResult(String.format(MESSAGE_DELETE_EXERCISE_IN_REGIME_SUCCESS, editedRegime));
        }
    }

    /**
     * Returns whether the DeleteRegimeCommand deletes a regime completely or edits a previous regime.
     * @return true if a regime is edited, false is a regime is deleted
     */
    public boolean isRegimeEdited() {
        return isRegimeEdited;
    }

    /**
     * Returns the payload that stores the regime that has been deleted or edited in this command.
     *
     * @return payload to store the relevant regime(s) that have been used in this command
     */
    public EventPayload<Regime> getEventPayload() {
        return eventPayload;
    }

    @Override
    public String getResourceType() {
        return RESOURCE_TYPE;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DeleteRegimeCommand // instanceof handles nulls
            && indexes.equals(((DeleteRegimeCommand) other).indexes)); // state check
    }
}
