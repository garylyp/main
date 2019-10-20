package seedu.exercise.logic.commands.events;

import seedu.exercise.logic.commands.AddCommand;
import seedu.exercise.logic.commands.AddExerciseCommand;
import seedu.exercise.logic.commands.AddRegimeCommand;
import seedu.exercise.logic.commands.ClearCommand;
import seedu.exercise.logic.commands.Command;
import seedu.exercise.logic.commands.DeleteCommand;
import seedu.exercise.logic.commands.DeleteExerciseCommand;
import seedu.exercise.logic.commands.EditCommand;
import seedu.exercise.logic.commands.UndoableCommand;
import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.model.resource.Regime;

/**
 * A utility class to generate specific Event objects depending on requirements.
 */
public class EventFactory {

    public static final String MESSAGE_COMMAND_NOT_UNDOABLE =
        "The command \'%1$s\' cannot be stored as an undoable event.";

    /**
     * Generates an Event object that can execute the behaviour of a given Command as well
     * as its opposite behaviour.
     *
     * @param undoableCommand a {@code UndoableCommand} to be represented with using an Event object
     * @return an {@code Event} that can be undone or redone
     * @throws CommandException if command provided is not undoable
     */
    static Event commandToEvent(UndoableCommand undoableCommand) throws CommandException {
        Command command = (Command) undoableCommand;
        String commandWord = command.getCommandWord();
        switch (commandWord) {
        case AddCommand.COMMAND_WORD:
            String resourceType = ((AddCommand) command).getResourceType();
            switch (resourceType) {
            case AddExerciseCommand.RESOURCE_TYPE:
                Exercise exerciseAdded = ((AddExerciseCommand) command).getExercise();
                return new AddExerciseEvent(exerciseAdded);

            case AddRegimeCommand.RESOURCE_TYPE:
                AddRegimeCommand addRegimeCommand = (AddRegimeCommand) command;
                return new AddRegimeEvent(addRegimeCommand.isExistingRegime(),
                                            addRegimeCommand.getRegimeToAdd(),
                                            addRegimeCommand.getPreviousRegime());
            }


        case DeleteCommand.COMMAND_WORD:
            Exercise exerciseDeleted = ((DeleteExerciseCommand) command).getExercise();
            return new DeleteExerciseEvent(exerciseDeleted);

        case EditCommand.COMMAND_WORD:
            Exercise exerciseOld = ((EditCommand) command).getExerciseToEdit();
            Exercise exerciseNew = ((EditCommand) command).getEditedExercise();
            return new EditEvent(exerciseOld, exerciseNew);

        case ClearCommand.COMMAND_WORD:
            ReadOnlyResourceBook<Exercise> exerciseBookCleared =
                    new ReadOnlyResourceBook<>(((ClearCommand) command).getExerciseBookCleared());
            return new ClearEvent(exerciseBookCleared);

        default:
            throw new CommandException(
                    String.format(MESSAGE_COMMAND_NOT_UNDOABLE, commandWord));
        }
    }

}
