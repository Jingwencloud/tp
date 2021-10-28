package seedu.awe.model;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.awe.commons.core.GuiSettings;
import seedu.awe.logic.commands.exceptions.CommandException;
import seedu.awe.model.expense.Cost;
import seedu.awe.model.expense.Expense;
import seedu.awe.model.group.Group;
import seedu.awe.model.group.GroupName;
import seedu.awe.model.payment.Payment;
import seedu.awe.model.person.Person;
import seedu.awe.model.transactionsummary.TransactionSummary;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Group> PREDICATE_SHOW_ALL_GROUPS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Expense> PREDICATE_SHOW_ALL_EXPENSES = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' awe book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' awe book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces awe book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook. */
    ReadOnlyAddressBook getAddressBook();

    /** Returns the group of the ExpenseList in AddressBook. */
    Group getActiveGroupFromAddressBook() throws CommandException;

    /**
     * Returns true if a person with the same identity as {@code person} exists in the awe book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the awe book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the awe book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the awe contact list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the awe book.
     */
    void setPerson(Person target, Person editedPerson);

    /** Replaces the transactionsummary list with a new list */
    void setTransactionSummary(HashMap<Person, Cost> summary);

    ObservableList<TransactionSummary> getTransactionSummary();

    /**
     * Replaces Person objects with updated Person objects within the {@code group}.
     * {@code group} must exist in the awe group list.
     *
     * @param group Group object containing members to be updated.
     */
    void setAllMembersOfGroup(Group group);


    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Adds the given group.
     * {@code Group} must not already exist in the awe book.
     */
    void addGroup(Group person);

    /**
     * Deletes group based on groupName.
     * {@code Group} must be unique in the awe book.
     */
    void deleteGroup(Group group);

    /** Returns boolean representing if a given group is in the model. */
    boolean hasGroup(Group group);

    void setPayments(List<Payment> payments);

    /**
     * Returns an unmodifiable view of the list of {@code Group} backed by the internal list of
     * {@code versionedAddressBook}
     */
    ObservableList<Group> getFilteredGroupList();

    Group getGroupByName(GroupName groupName);

    /**
     * Replaces the target group with the newGroup.
     *
     * @param group Target group.
     * @param newGroup to replace the target group.
     */
    void setGroup(Group group, Group newGroup);

    /**
     * Updates the filter of the filtered group list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredGroupList(Predicate<Group> predicate);

    /**
     * Returns all expenses in the input group.
     *
     * @return A list of all expenses in the specified group.
     */
    ObservableList<Expense> getExpenses();

    void setExpenses(Group group);

    boolean isCurrentExpenseList(Group group);

    Expense getExpense(int index);

    /**
     * Updates the filter of the filtered expense list to filter by the given {@code predicate}.
     *
     * @param predicate The predicate to filter the list.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredExpenseList(Predicate<Expense> predicate);

    void addExpense(Expense expense, Group group);

    /**
     * Deletes expense if the current expense list in address
     * book belongs to the specified group.
     */
    void deleteExpense(Expense expense, Group group);

    ObservableList<Payment> getPayments();
}
