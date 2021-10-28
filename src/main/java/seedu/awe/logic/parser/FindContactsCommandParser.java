package seedu.awe.logic.parser;

import static seedu.awe.commons.core.Messages.MESSAGE_FINDCONTACTSCOMMAND_USAGE;
import static seedu.awe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.awe.logic.commands.FindContactsCommand;
import seedu.awe.logic.parser.exceptions.ParseException;
import seedu.awe.model.person.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindContactsCommand object
 */
public class FindContactsCommandParser implements Parser<FindContactsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindContactsCommand
     * and returns a FindContactsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindContactsCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_FINDCONTACTSCOMMAND_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindContactsCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
