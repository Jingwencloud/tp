package seedu.awe.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.awe.commons.core.Messages.MESSAGE_ADDEXPENSECOMMAND_ALL_MEMBERS_EXCLUDED;
import static seedu.awe.commons.core.Messages.MESSAGE_ADDEXPENSECOMMAND_CANNOT_ADD_EXCLUDED_MEMBER;
import static seedu.awe.commons.core.Messages.MESSAGE_ADDEXPENSECOMMAND_COST_ZERO_OR_LESS;
import static seedu.awe.commons.core.Messages.MESSAGE_ADDEXPENSECOMMAND_NOT_PART_OF_GROUP;
import static seedu.awe.commons.core.Messages.MESSAGE_ADDEXPENSECOMMAND_SUCCESS;
import static seedu.awe.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import seedu.awe.logic.commands.exceptions.CommandException;
import seedu.awe.model.Model;
import seedu.awe.model.expense.Cost;
import seedu.awe.model.expense.Description;
import seedu.awe.model.expense.Expense;
import seedu.awe.model.group.Group;
import seedu.awe.model.group.GroupName;
import seedu.awe.model.group.exceptions.DuplicateGroupException;
import seedu.awe.model.person.Person;

/**
 * Adds an expense to a group.
 */
public class AddExpenseCommand extends Command {

    public static final String COMMAND_WORD = "addexpense";

    private final Person payer;
    private final Cost totalCost;
    private final Description description;
    private final GroupName groupName;
    private final List<Person> selfPayees;
    private final List<Cost> selfCosts;
    private final List<Person> excluded;

    /**
     * Creates a AddExpenseCommand from the specified Person {@code Index} for the specified {@code Group}
     *
     * @param payer Person paying for the expense.
     * @param totalCost Total amount of money paid for the expense.
     * @param description Description for the expense
     * @param groupName Name of group to add expense to.
     * @param selfPayees List of persons to exclude from the expense.
     * @param selfCosts List of costs to exclude from the expense.
     * @param excluded Person excluded from the expense.
     */
    public AddExpenseCommand(Person payer, Cost totalCost, Description description, GroupName groupName,
                             List<Person> selfPayees, List<Cost> selfCosts, List<Person> excluded) {
        requireAllNonNull(payer, totalCost, description, groupName, selfPayees, selfCosts, excluded);

        this.payer = payer;
        this.totalCost = totalCost;
        this.description = description;
        this.groupName = groupName;
        this.selfPayees = selfPayees;
        this.selfCosts = selfCosts;
        this.excluded = excluded;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException, DuplicateGroupException {
        requireNonNull(model);

        Group group = model.getGroupByName(groupName);

        if (!group.isPartOfGroup(payer)) {
            throw new CommandException(MESSAGE_ADDEXPENSECOMMAND_NOT_PART_OF_GROUP);
        }

        for (Person exclude : excluded) {
            if (!group.isPartOfGroup(exclude)) {
                throw new CommandException(MESSAGE_ADDEXPENSECOMMAND_NOT_PART_OF_GROUP);
            }
        }

        if (group.getMembers().size() == excluded.size()) {
            throw new CommandException(MESSAGE_ADDEXPENSECOMMAND_ALL_MEMBERS_EXCLUDED);
        }

        return calculateExpense(group, model);
    }

    /**
     * Process data from user and checks for errors. Data is then passed into createExpense method.
     *
     * @param group Group for the expense.
     * @param model Model for AWE.
     * @return Return the result for creating an expense.
     * @throws CommandException if any of the person input by the user does not belong to the group
     */
    private CommandResult calculateExpense(Group group, Model model) throws CommandException {
        HashMap<Person, Cost> individualPayment = new HashMap<>();

        Cost leftoverExpenseAfterIndividualExpense = totalCost;
        for (int i = 0; i < selfPayees.size(); i++) {
            Person currentPayer = selfPayees.get(i);
            if (excluded.contains(currentPayer)) {
                throw new CommandException(MESSAGE_ADDEXPENSECOMMAND_CANNOT_ADD_EXCLUDED_MEMBER);
            }
            if (currentPayer == null || !group.isPartOfGroup(currentPayer)) {
                throw new CommandException(MESSAGE_ADDEXPENSECOMMAND_NOT_PART_OF_GROUP);
            }
            individualPayment.merge(currentPayer, selfCosts.get(i), (original, toAdd) -> original.add(toAdd));
            leftoverExpenseAfterIndividualExpense = leftoverExpenseAfterIndividualExpense.subtract(selfCosts.get(i));
        }

        if (leftoverExpenseAfterIndividualExpense.cost <= 0) {
            throw new CommandException(MESSAGE_ADDEXPENSECOMMAND_COST_ZERO_OR_LESS);
        }

        ArrayList<Person> groupMembers = removeExcludedFromGroup(group.getMembers());

        return createExpense(model, group, groupMembers, individualPayment);
    }

    /**
     * Creates an expense class based on data passed in. Created expense is then added into the model.
     *
     * @param model Model for AWE
     * @param group Group for the expense.
     * @param groupMembers List of person that is included in the expense.
     * @param individualPayement Individual expenses of the user (if any)
     * @return
     */
    private CommandResult createExpense(Model model, Group group, List<Person> groupMembers,
                                        HashMap<Person, Cost> individualPayement) {
        Expense newExpense = new Expense(payer, totalCost, description, groupMembers, individualPayement);

        Group newGroup = group.addExpense(newExpense);
        model.setGroup(group, newGroup);

        model.addExpense(newExpense, newGroup);

        return new CommandResult(String.format(MESSAGE_ADDEXPENSECOMMAND_SUCCESS, newExpense));
    }

    /**
     * Gets a list of person that is included in the expense. Excluded person is not included in the list.
     *
     * @param members All members of the group.
     * @return A list of Person in the group that is involved in the expense.
     */
    private ArrayList<Person> removeExcludedFromGroup(ArrayList<Person> members) {
        ArrayList<Person> groupMembers = new ArrayList<>(members);
        for (Person toExclude : excluded) {
            groupMembers.remove(toExclude);
        }
        return groupMembers;
    }

    public Cost getTotalCost() {
        return totalCost;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (!(other instanceof AddExpenseCommand)) {
            return false;
        } else {
            AddExpenseCommand otherCommand = (AddExpenseCommand) other;
            return payer.equals(otherCommand.payer)
                    && totalCost.equals(otherCommand.totalCost)
                    && description.equals(otherCommand.description)
                    && groupName.equals(otherCommand.groupName);
        }
    }
}
