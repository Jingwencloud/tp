package seedu.awe.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.awe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.awe.logic.parser.CliSyntax.PREFIX_GROUP_NAME;
import static seedu.awe.logic.parser.CliSyntax.PREFIX_INDEX;

import seedu.awe.commons.core.index.Index;
import seedu.awe.commons.exceptions.IllegalValueException;
import seedu.awe.logic.commands.DeleteExpenseCommand;
import seedu.awe.logic.parser.exceptions.ParseException;
import seedu.awe.model.group.GroupName;

public class DeleteExpenseCommandParser implements Parser<DeleteExpenseCommand> {

    @Override
    public DeleteExpenseCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_GROUP_NAME, PREFIX_INDEX);

        try {
            Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).orElse(""));
            GroupName groupName = ParserUtil.parseGroupName((argMultimap.getValue(PREFIX_GROUP_NAME)).orElse(""));
            return new DeleteExpenseCommand(index, groupName);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteExpenseCommand.MESSAGE_USAGE), ive);
        }
    }


}
