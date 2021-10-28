package seedu.awe.logic.parser;

import static seedu.awe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.awe.commons.core.Messages.MESSAGE_LISTTRANSACTIONSUMMARYCOMMAND_USAGE;
import static seedu.awe.logic.parser.CliSyntax.PREFIX_GROUP_NAME;

import java.util.ArrayList;
import java.util.stream.Stream;

import seedu.awe.logic.commands.ListTransactionSummaryCommand;
import seedu.awe.logic.parser.exceptions.ParseException;
import seedu.awe.model.group.Group;
import seedu.awe.model.group.GroupName;

public class ListTransactionSummaryCommandParser implements Parser<ListTransactionSummaryCommand> {

    @Override
    public ListTransactionSummaryCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_GROUP_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_GROUP_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MESSAGE_LISTTRANSACTIONSUMMARYCOMMAND_USAGE));
        }

        GroupName groupName = ParserUtil.parseGroupName(argMultimap.getValue(PREFIX_GROUP_NAME).get());
        Group group = new Group(groupName, new ArrayList<>());

        return new ListTransactionSummaryCommand(group);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
