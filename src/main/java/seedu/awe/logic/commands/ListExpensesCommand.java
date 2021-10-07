package seedu.awe.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.awe.logic.parser.CliSyntax.PREFIX_GROUP_NAME;

import seedu.awe.logic.commands.exceptions.CommandException;
import seedu.awe.model.Model;
import seedu.awe.model.group.Group;
import seedu.awe.model.group.GroupName;
import seedu.awe.model.group.exceptions.GroupNotFoundException;

/**
 * Lists all expenses of the group to the user.
 */
public class ListExpensesCommand extends Command {

    public static final String COMMAND_WORD = "expenses";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists expenses of a group. "
            + "Parameters: "
            + PREFIX_GROUP_NAME + "GROUP NAME ";

    public static final String MESSAGE_SUCCESS = "Listed all expenses";
    public static final String MESSAGE_GROUP_NOT_FOUND = "The specified group does not exists.";

    private final GroupName groupName;

    /**
     * A public constructor to initialise the group name
     * to the given one.
     *
     * @param groupName The name of the group.
     */
    public ListExpensesCommand(GroupName groupName) {
        requireNonNull(groupName);
        this.groupName = groupName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        try {
            Group group = model.getGroupByName(groupName);
            model.setExpenses(group);
            return new CommandResult(MESSAGE_SUCCESS, false, false, false, false, true);
        } catch (GroupNotFoundException e) {
            throw new CommandException(MESSAGE_GROUP_NOT_FOUND);
        }
    }
}


