package seedu.exercise.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_NAME;

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
 * Adds a regime to the regime book.
 */
public class AddRegimeCommand extends AddCommand {

    public static final String MESSAGE_USAGE_REGIME = "Parameters: "
        + PREFIX_CATEGORY + "CATEGORY "
        + PREFIX_NAME + "REGIME NAME "
        + PREFIX_INDEX + "INDEX\n"
        + "\t\tExample: " + COMMAND_WORD + " "
        + PREFIX_CATEGORY + "regime "
        + PREFIX_NAME + "power set "
        + PREFIX_INDEX + "1 "
        + PREFIX_INDEX + "2";

    public static final String MESSAGE_SUCCESS_NEW_REGIME = "Added new regime to regime list.";
    public static final String MESSAGE_SUCCESS_ADD_EXERCISE_TO_REGIME = "Added exercises to regime.";
    public static final String MESSAGE_DUPLICATE_EXERCISE_IN_REGIME = "Exercise already in regime.";
    public static final String MESSAGE_NO_EXERCISES_ADDED = "No index provided, nothing changes.";
    public static final String MESSAGE_DUPLICATE_INDEX_WHEN_CREATING_REGIME = "There is duplicate index.";
    public static final String RESOURCE_TYPE = "regime";
    public static final String KEY_REGIME_TO_ADD = "regimeToAdd";
    public static final String KEY_PREVIOUS_REGIME = "previousRegime";
    public static final String KEY_EDITED_REGIME = "editedRegime";

    private List<Index> toAddIndexes;
    private Name name;
    private boolean isRegimeEdited;
    private EventPayload<Regime> eventPayload;

    public AddRegimeCommand(List<Index> indexes, Name name) {
        requireAllNonNull(indexes, name);
        this.name = name;
        this.toAddIndexes = indexes;
        this.eventPayload = new EventPayload<>();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Exercise> lastShownList = model.getFilteredExerciseList();

        //check all index valid
        for (Index targetIndex : toAddIndexes) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_EXERCISE_DISPLAYED_INDEX);
            }
        }

        //create new regime
        Regime regime = new Regime(name, new UniqueResourceList<Exercise>());
        if (!model.hasRegime(regime)) {
            isRegimeEdited = false;
            for (Index index : toAddIndexes) {
                if (regime.getRegimeExercises().contains(lastShownList.get(index.getZeroBased()))) {
                    throw new CommandException(MESSAGE_DUPLICATE_INDEX_WHEN_CREATING_REGIME);
                }
                regime.addExercise(lastShownList.get(index.getZeroBased()));
            }

            model.addRegime(regime);
            eventPayload.put(KEY_REGIME_TO_ADD, regime);
            EventHistory.getInstance().addCommandToUndoStack(this);
            return new CommandResult(MESSAGE_SUCCESS_NEW_REGIME);
        } else {
            //add exercise to existing regime
            isRegimeEdited = true;
            if (toAddIndexes.size() == 0) {
                throw new CommandException(MESSAGE_NO_EXERCISES_ADDED);
            }

            int indexOfRegime = model.getRegimeIndex(regime);
            List<Regime> regimes = model.getFilteredRegimeList();
            Regime previousRegime = regimes.get(indexOfRegime);

            Regime editedRegime = previousRegime.getDuplicateCopy();
            UniqueResourceList<Exercise> currentExerciseList = editedRegime.getRegimeExercises();

            //check whether exercise is in current exercise list in regime
            for (Index index : toAddIndexes) {
                if (currentExerciseList.contains(lastShownList.get(index.getZeroBased()))) {
                    throw new CommandException(MESSAGE_DUPLICATE_EXERCISE_IN_REGIME);
                }
            }

            //add exercise to regime
            for (Index index : toAddIndexes) {
                editedRegime.addExercise(lastShownList.get(index.getZeroBased()));
            }

            eventPayload.put(KEY_PREVIOUS_REGIME, previousRegime);
            eventPayload.put(KEY_EDITED_REGIME, editedRegime);
            model.setRegime(previousRegime, editedRegime);
            model.updateFilteredRegimeList(Model.PREDICATE_SHOW_ALL_REGIMES);
            EventHistory.getInstance().addCommandToUndoStack(this);
            return new CommandResult(MESSAGE_SUCCESS_ADD_EXERCISE_TO_REGIME);
        }
    }

    /**
     * Returns whether the AddRegimeCommand adds a new regime or edits a previous regime.
     * @return true if a regime is edited, false is a new regime is added
     */
    public boolean isRegimeEdited() {
        return isRegimeEdited;
    }

    /**
     * Returns the payload that stores the regime that has been added or edited in this command.
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
            || (other instanceof AddRegimeCommand // instanceof handles nulls
            && toAddIndexes.equals(((AddRegimeCommand) other).toAddIndexes));
    }


}
