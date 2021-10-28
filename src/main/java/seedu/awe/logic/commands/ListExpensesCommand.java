package seedu.awe.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.awe.commons.core.Messages.MESSAGE_LISTEXPENSESCOMMAND_GROUP_NOT_FOUND;
import static seedu.awe.commons.core.Messages.MESSAGE_LISTEXPENSESCOMMAND_SUCCESS;
import static seedu.awe.model.Model.PREDICATE_SHOW_ALL_EXPENSES;

import java.util.Objects;

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
            if (Objects.isNull(group)) {
                throw new CommandException(MESSAGE_LISTEXPENSESCOMMAND_GROUP_NOT_FOUND);
            }
            model.setExpenses(group);
            model.updateFilteredExpenseList(PREDICATE_SHOW_ALL_EXPENSES);
            return new CommandResult(MESSAGE_LISTEXPENSESCOMMAND_SUCCESS, false, false,
                    false, false, true,
                    false, false);
        } catch (GroupNotFoundException e) {
            throw new CommandException(MESSAGE_LISTEXPENSESCOMMAND_GROUP_NOT_FOUND);
        }
    }
}


