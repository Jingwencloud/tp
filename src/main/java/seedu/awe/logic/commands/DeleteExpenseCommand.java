package seedu.awe.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.awe.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.awe.commons.core.Messages;
import seedu.awe.commons.core.index.Index;
import seedu.awe.logic.commands.exceptions.CommandException;
import seedu.awe.model.Model;
import seedu.awe.model.expense.Expense;
import seedu.awe.model.group.Group;
import seedu.awe.model.group.GroupName;

public class DeleteExpenseCommand extends Command {

    public static final String MESSAGE_USAGE = "deleteexpense gn/[GROUPNAME] i/[INDEX OF EXPENSE]";
    public static final String COMMAND_WORD = "deleteexpense";
    private static final String MESSAGE_SUCCESS = "Expense %s successfully deleted!";
    private Index index;
    private GroupName groupName;

    /**
     * Constructor for Delete Expense Command.
     * @param index Position of expense to be deleted in observable list
     * @param groupName Name of group in which expense to be deleted is present
     */
    public DeleteExpenseCommand(Index index, GroupName groupName) {
        requireAllNonNull(index, groupName);
        this.index = index;
        this.groupName = groupName;
    }

    public Index getIndex() {
        return index;
    }

    public GroupName getGroupName() {
        return groupName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Expense> lastShownList = model.getExpenses();
        Group group = model.getGroupByName(groupName);

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);
        }

        Expense expenseToDelete = lastShownList.get(index.getZeroBased());
        Group newGroup = group.deleteExpense(expenseToDelete);
        model.setGroup(group, newGroup);
        String expenseToDeleteDescriptionString = expenseToDelete.getDescription().getFullDescription();
        return new CommandResult(String.format(MESSAGE_SUCCESS, expenseToDeleteDescriptionString), false,
                false, false, false, true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (!(other instanceof DeleteExpenseCommand)) {
            return false;
        } else {
            DeleteExpenseCommand otherCommand = (DeleteExpenseCommand) other;
            return index.equals(otherCommand.getIndex())
                    && groupName.equals(otherCommand.getGroupName());
        }
    }
}
