package seedu.exercise.logic.commands.events;

import seedu.exercise.logic.commands.AddCommand;
import seedu.exercise.logic.commands.AddExerciseCommand;
import seedu.exercise.logic.commands.AddRegimeCommand;
import seedu.exercise.logic.commands.ClearCommand;
import seedu.exercise.logic.commands.Command;
import seedu.exercise.logic.commands.DeleteCommand;
import seedu.exercise.logic.commands.DeleteExerciseCommand;
import seedu.exercise.logic.commands.DeleteRegimeCommand;
import seedu.exercise.logic.commands.EditCommand;
import seedu.exercise.logic.commands.UndoableCommand;
import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.resource.Exercise;

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
            return addCommandToEvent((AddCommand) command);

        case DeleteCommand.COMMAND_WORD:
            return deleteCommandToEvent((DeleteCommand) command);


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

    /**
     * Generates a add exercise or add regime event based on the command type.
     *
     * @param command a {@code AddCommand} to be represented with using an Event object
     * @return an {@code AddExerciseEvent}, {@code AddRegimeEvent} or {@code EditRegimeEvent}
     * that can be undone or redone
     */
    private static Event addCommandToEvent(AddCommand command) throws CommandException {
        String resourceType = command.getResourceType();
        switch (resourceType) {
        case DeleteExerciseCommand.RESOURCE_TYPE:
            Exercise exerciseAdded = ((AddExerciseCommand) command).getExercise();
            return new AddExerciseEvent(exerciseAdded);

        case AddRegimeCommand.RESOURCE_TYPE:
            AddRegimeCommand addRegimeCommand = (AddRegimeCommand) command;
            if (addRegimeCommand.isRegimeEdited()) {
                return new EditRegimeEvent(addRegimeCommand.getEventPayload());
            } else {
                return new AddRegimeEvent(addRegimeCommand.getEventPayload());
            }

        default:
            throw new CommandException(
                    String.format(MESSAGE_COMMAND_NOT_UNDOABLE, command.getCommandWord()));
        }
    }

    /**
     * Generates a delete exercise or delete regime event based on the command type.
     *
     * @param command a {@code DeleteCommand} to be represented with using an Event object
     * @return an {@code DeleteExerciseEvent}, {@code DeleteRegimeEvent} or {@code EditRegimeEvent}
     * that can be undone or redone
     */
    private static Event deleteCommandToEvent(DeleteCommand command) throws CommandException {
        String resourceType = command.getResourceType();
        switch (resourceType) {
        case DeleteExerciseCommand.RESOURCE_TYPE:
            Exercise exerciseDeleted = ((DeleteExerciseCommand) command).getExercise();
            return new DeleteExerciseEvent(exerciseDeleted);

        case DeleteRegimeCommand.RESOURCE_TYPE:
            DeleteRegimeCommand deleteRegimeCommand = (DeleteRegimeCommand) command;
            if (deleteRegimeCommand.isRegimeEdited()) {
                return new EditRegimeEvent(deleteRegimeCommand.getEventPayload());
            } else {
                return new DeleteRegimeEvent(deleteRegimeCommand.getEventPayload());
            }

        default:
            throw new CommandException(
                    String.format(MESSAGE_COMMAND_NOT_UNDOABLE, command.getCommandWord()));
        }
    }

}
