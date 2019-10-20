package seedu.exercise.logic.commands;

import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model) throws CommandException;

    /**
     * Returns the command word that is used to call the command in the command box Ui.
     *
     * @return command word of the command
     */
    public abstract String getCommandWord();
}