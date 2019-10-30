package seedu.exercise.logic.parser;

import static seedu.exercise.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_MUSCLE;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_SUGGEST_TYPE;
import static seedu.exercise.model.property.PropertyBook.getCustomProperties;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import seedu.exercise.logic.commands.ListCommand;
import seedu.exercise.logic.commands.SuggestBasicCommand;
import seedu.exercise.logic.commands.SuggestCommand;
import seedu.exercise.logic.commands.SuggestPossibleCommand;
import seedu.exercise.logic.parser.exceptions.ParseException;
import seedu.exercise.model.property.CustomProperty;
import seedu.exercise.model.property.Muscle;

/**
 * Parses input arguments and creates a new SuggestCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {

    public static final String LIST_TYPE_EXERCISE = "exercise";
    public static final String LIST_TYPE_REGIME = "regime";
    public static final String LIST_TYPE_SCHEDULE = "schedule";
    public static final String LIST_TYPE_SUGGESTION = "suggest";

    /**
     * Parses the given {@code String} of arguments in the context of the SuggestCommand
     * and returns a SuggestCommand object for execution.
     *
     * @throws ParseException if the user does not conform to the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CATEGORY);

        if (!argMultimap.arePrefixesPresent(PREFIX_CATEGORY) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

        String listType = ParserUtil.parseListType(argMultimap.getValue(PREFIX_CATEGORY).get());
        return new ListCommand(listType);
    }

}
