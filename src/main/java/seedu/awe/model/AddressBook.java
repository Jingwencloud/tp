package seedu.awe.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.awe.model.expense.Cost;
import seedu.awe.model.expense.Expense;
import seedu.awe.model.expense.ExpenseList;
import seedu.awe.model.group.Group;
import seedu.awe.model.group.GroupName;
import seedu.awe.model.group.UniqueGroupList;
import seedu.awe.model.payment.Payment;
import seedu.awe.model.payment.PaymentList;
import seedu.awe.model.person.Person;
import seedu.awe.model.person.UniquePersonList;
import seedu.awe.model.transactionsummary.TransactionSummary;
import seedu.awe.model.transactionsummary.TransactionSummaryList;

/**
 * Wraps all data at the awe-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueGroupList groups;
    private final ExpenseList expenses;
    private final PaymentList payments;
    private final TransactionSummaryList transactionSummary;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        groups = new UniqueGroupList();
        expenses = new ExpenseList();
        payments = new PaymentList();
        transactionSummary = new TransactionSummaryList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setGroups(List<Group> groups) {
        this.groups.setGroups(groups);
    }

    public void setGroup(Group group, Group newGroup) {
        groups.setGroup(group, newGroup);
    }

    /**
     * Replaces the contents of the expenses list with the given group's
     * expenses.
     *
     * @param group Group that contains all new expenses.
     */
    public void setExpenses(Group group) {
        expenses.clear();
        expenses.addAll(group.getExpenses());
        expenses.setGroup(group);
    }

    public ObservableList<Expense> getExpenseList() {
        return expenses.asUnmodifiableObservableList();
    }

    @Override
    public Optional<Group> getGroupFromExpenseList() {
        return expenses.getGroup();
    }

    @Override
    public ObservableList<Payment> getPaymentList() {
        return payments.asUnmodifiableObservableList();
    }

    public UniqueGroupList getGroups() {
        return this.groups;
    }

    public UniquePersonList getPersons() {
        return this.persons;
    }

    public ObservableList<TransactionSummary> getTransactionSummaryList() {
        return this.transactionSummary.asUnmodifiableObservableList();
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setGroups(newData.getGroupList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the awe book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the awe book.
     * The person must not already exist in the awe book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the awe book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the awe book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
        groups.updatePerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the awe book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    /**
     * Adds group to unique group list.
     * @param group new Group object to be added.
     */
    public void addGroup(Group group) {
        groups.add(group);
    }

    /**
     * Removes group from unique group list.
     * @param group new Group object to be removed.
     */
    public void removeGroup(Group group) {
        groups.remove(group);
    }

    /**
     * Checks that group is present based on group name.
     * @param group Group object
     * @return boolean
     */
    public boolean hasGroup(Group group) {
        requireNonNull(group);
        return groups.contains(group);
    }

    @Override
    public ObservableList<Group> getGroupList() {
        return groups.asUnmodifiableObservableList();
    }

    @Override
    public Group getGroupByName(GroupName groupName) {
        requireNonNull(groupName);
        return groups.getGroupByName(groupName);
    }

    @Override
    public void setPayments(List<Payment> payments) {
        this.payments.clear();
        this.payments.addAll(payments);
    }

    /**
     * Checks if the current expense list in address book
     * belongs to the specified group.
     *
     * @param group The group to check if the expense list belongs to.
     * @return true if the expense list belongs to the group.
     */
    public boolean isCurrentExpenseList(Group group) {
        Optional<Group> current = expenses.getGroup();
        boolean isCurrentGroup = current.isPresent() && current.get().equals(group);
        return isCurrentGroup;
    }

    public void setTransactionSummary(HashMap<Person, Cost> summary) {
        List<TransactionSummary> toSet = new ArrayList<>();
        for (Map.Entry<Person, Cost> e: summary.entrySet()) {
            toSet.add(new TransactionSummary(e.getKey(), e.getValue()));
        }

        this.transactionSummary.set(toSet);
    }

    /**
     * Adds expense to the current expense list if it belongs to the
     * specified group.
     *
     * @param expense The expense to add.
     * @param group The group which the expense belongs to.
     */
    public void addExpense(Expense expense, Group group) {
        boolean isCurrentGroup = isCurrentExpenseList(group);
        if (isCurrentGroup) {
            expenses.add(expense);
        }
    }

    /**
     * Deletes the specified expense in the current list if it belongs to the
     * specified group.
     *
     * @param expense The expense to remove.
     * @param group The group which the expense belongs to.
     */
    public void deleteExpense(Expense expense, Group group) {
        boolean isCurrentGroup = isCurrentExpenseList(group);
        if (isCurrentGroup) {
            expenses.remove(expense);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddressBook)) { // instanceof handles nulls
            return false;
        }

        AddressBook toBeChecked = (AddressBook) other;
        return persons.equals(toBeChecked.persons)
                && groups.equals(toBeChecked.groups);
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
