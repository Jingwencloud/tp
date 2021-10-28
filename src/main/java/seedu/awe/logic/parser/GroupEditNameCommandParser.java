package seedu.awe.logic.parser;

import static seedu.awe.commons.core.Messages.MESSAGE_GROUPEDITNAMECOMMAND_USAGE;
import static seedu.awe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.awe.logic.parser.CliSyntax.PREFIX_GROUP_NAME;

import java.util.List;

import seedu.awe.logic.commands.GroupEditNameCommand;
import seedu.awe.logic.parser.exceptions.ParseException;
import seedu.awe.model.group.GroupName;

public class GroupEditNameCommandParser implements Parser<GroupEditNameCommand> {
    private static final String BAD_FORMATTING = "\"groupeditname command\" is not properly formatted";

    /**
     * Returns GroupEditNameCommand based on user input.
     *
     * @param args User input into contact list.
     * @return GroupEditNameCommand object to represent command to be executed.
     * @throws ParseException If user input is incorrectly formatted.
     */
    public GroupEditNameCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_GROUP_NAME);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_GROUP_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MESSAGE_GROUPEDITNAMECOMMAND_USAGE));
        }

        List<GroupName> groupNamesList = ParserUtil.parseGroupNames(argMultimap.getAllValues(PREFIX_GROUP_NAME));

        GroupName oldGroupName;
        GroupName newGroupName;

        if (groupNamesList.size() > 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MESSAGE_GROUPEDITNAMECOMMAND_USAGE));
        }

        try {
            oldGroupName = groupNamesList.get(0);
            newGroupName = groupNamesList.get(1);
        } catch (IndexOutOfBoundsException exception) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MESSAGE_GROUPEDITNAMECOMMAND_USAGE));
        }

        boolean isValidCommand = true;
        if (newGroupName.getName().equals(BAD_FORMATTING)) {
            isValidCommand = false;
        }

        return new GroupEditNameCommand(oldGroupName, newGroupName, isValidCommand);
    }
}
