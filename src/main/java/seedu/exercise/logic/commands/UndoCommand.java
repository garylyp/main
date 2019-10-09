package seedu.exercise.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_CALORIES;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_MUSCLE;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_UNIT;

import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.model.Model;
import seedu.exercise.model.exercise.Exercise;

/**
 * Undoes the last executed command.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_SUCCESS = "Previous command has been undone";
    public static final String MESSAGE_EMPTY_COMMAND_HISTORY = "There is no command to undo";

//    private final Exercise toAdd;
    private UndoableCommand commandToUndo;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public UndoCommand() {
//        requireNonNull(exercise);
//        toAdd = exercise;
//        commandToUndo = command;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

//        if (model.hasExercise(toAdd)) {
//            throw new CommandException(MESSAGE_DUPLICATE_EXERCISE);
//        }
//
//        if (commandHistory.isEmpty()) {
//            throw new CommandException(MESSAGE_EMPTY_COMMAND_HISTORY);
//        }
//
////        model.addExercise(toAdd);
//        commandHistory.undo()
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UndoCommand // instanceof handles nulls
                && commandToUndo.equals(((UndoCommand) other).commandToUndo));
    }
}
