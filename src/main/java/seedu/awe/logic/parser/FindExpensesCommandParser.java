package seedu.awe.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.awe.commons.core.Messages.MESSAGE_FINDEXPENSESCOMMAND_USAGE;
import static seedu.awe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.awe.logic.parser.CliSyntax.PREFIX_GROUP_NAME;

import java.util.Arrays;

import seedu.awe.logic.commands.FindExpensesCommand;
import seedu.awe.logic.parser.exceptions.ParseException;
import seedu.awe.model.expense.DescriptionContainsKeywordsPredicate;
import seedu.awe.model.group.GroupName;

/**
 * Parses input arguments and creates a new FindExpensesCommand object
 */
public class FindExpensesCommandParser implements Parser<FindExpensesCommand> {

    /**
     * Extracts the list of keywords from the input.
     *
     * @param args The user input.
     * @return The list of keywords.
     */
    public String[] extractKeywords(String args) {
        int endIndex = args.indexOf("gn/");
        String keywords = args.substring(0, endIndex).trim();
        String[] keywordsArray = keywords.split("\\s+");
        return keywordsArray;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the FindExpensesCommand
     * and returns a FindExpensesCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindExpensesCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        boolean isKeywordAbsent = trimmedArgs.startsWith("gn/");

        if (trimmedArgs.isEmpty() || isKeywordAbsent) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_FINDEXPENSESCOMMAND_USAGE));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(trimmedArgs, PREFIX_GROUP_NAME);
        if (!args.contains("gn/") || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_FINDEXPENSESCOMMAND_USAGE));
        }

        GroupName groupName = ParserUtil.parseGroupName((argMultimap.getValue(PREFIX_GROUP_NAME)).get());
        String[] descriptionKeywords = extractKeywords(trimmedArgs);
        return new FindExpensesCommand(groupName,
                new DescriptionContainsKeywordsPredicate(Arrays.asList(descriptionKeywords)));
    }

}


