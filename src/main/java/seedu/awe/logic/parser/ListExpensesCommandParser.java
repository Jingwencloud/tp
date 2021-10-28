package seedu.awe.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.awe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.awe.commons.core.Messages.MESSAGE_LISTEXPENSESCOMMAND_USAGE;
import static seedu.awe.logic.parser.CliSyntax.PREFIX_GROUP_NAME;

import java.util.stream.Stream;

import seedu.awe.commons.exceptions.IllegalValueException;
import seedu.awe.logic.commands.ListExpensesCommand;
import seedu.awe.logic.parser.exceptions.ParseException;
import seedu.awe.model.group.GroupName;

/**
 * Parses input arguments and creates a new ListExpensesCommand object
 */
public class ListExpensesCommandParser implements Parser<ListExpensesCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListExpensesCommand
     * and returns a ListExpensesCommand object for execution.
     *
     * @param args user input
     * @return ListExpensesCommand object to represent command to be executed.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListExpensesCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_GROUP_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_GROUP_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_LISTEXPENSESCOMMAND_USAGE));
        }

        try {
            GroupName groupName = ParserUtil.parseGroupName((argMultimap.getValue(PREFIX_GROUP_NAME)).get());
            return new ListExpensesCommand(groupName);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MESSAGE_LISTEXPENSESCOMMAND_USAGE), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
