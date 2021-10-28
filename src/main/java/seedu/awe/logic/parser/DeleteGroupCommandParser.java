package seedu.awe.logic.parser;

import static seedu.awe.commons.core.Messages.MESSAGE_DELETEGROUPCOMMAND_USAGE;
import static seedu.awe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.awe.logic.parser.CliSyntax.PREFIX_GROUP_NAME;

import java.util.ArrayList;
import java.util.stream.Stream;

import seedu.awe.logic.commands.DeleteGroupCommand;
import seedu.awe.logic.parser.exceptions.ParseException;
import seedu.awe.model.Model;
import seedu.awe.model.ReadOnlyAddressBook;
import seedu.awe.model.group.Group;
import seedu.awe.model.group.GroupName;

public class DeleteGroupCommandParser implements Parser<DeleteGroupCommand> {

    /**
     * Creates new CreateGroupParser object.
     *
     * @param model Model object passed into constructor to provide list of contacts.
     */
    public DeleteGroupCommandParser(Model model) {
        ReadOnlyAddressBook addressBook = model.getAddressBook();
    }
    //assume command is of the following form
    //deletegroup gn/Berlin

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteGroupCommand
     * and returns an DeleteGroupCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteGroupCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_GROUP_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_GROUP_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_DELETEGROUPCOMMAND_USAGE));
        }

        GroupName groupName = ParserUtil.parseGroupName(argMultimap.getValue(PREFIX_GROUP_NAME).get());
        Group group = new Group(groupName, new ArrayList<>());

        return new DeleteGroupCommand(group);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
