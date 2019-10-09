package seedu.exercise.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.logic.commands.history.Event;
import seedu.exercise.logic.commands.history.EventHistory;
import seedu.exercise.model.Model;

/**
 * Undoes the last executed command.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_SUCCESS = "Action redone: \n%1$s";
    public static final String MESSAGE_EMPTY_REDO_STACK = "There is no command to redo";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        EventHistory actionHistory = EventHistory.getInstance();

        if (actionHistory.isRedoStackEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_REDO_STACK);
        }

        Event eventToRedo = actionHistory.redo();
        eventToRedo.redo(model);
        return new CommandResult(
                String.format(MESSAGE_SUCCESS, eventToRedo));
    }

//    @Override
//    public boolean equals(Object other) {
//        return other == this // short circuit if same object
//                || (other instanceof RedoCommand // instanceof handles nulls
//                && eventToRedo.equals(((RedoCommand) other).eventToRedo));
//    }
}
