package seedu.awe.model;

import java.util.List;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.awe.logic.commands.exceptions.CommandException;
import seedu.awe.model.expense.Expense;
import seedu.awe.model.group.Group;
import seedu.awe.model.group.GroupName;
import seedu.awe.model.payment.Payment;
import seedu.awe.model.person.Person;

/**
 * Unmodifiable view of an awe book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    ObservableList<Group> getGroupList();

    /**
     * Returns an unmodifiable view of the expenses list.
     */
    ObservableList<Expense> getExpenseList();

    /**
     * Get group from ExpenseList.
     */
    Optional<Group> getGroupFromExpenseList() throws CommandException;

    /**
     * Returns an unmodifiable view of the payments list.
     */
    ObservableList<Payment> getPaymentList();

    Group getGroupByName(GroupName groupName);

    void setPayments(List<Payment> payments);
}
