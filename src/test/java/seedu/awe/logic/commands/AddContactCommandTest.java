package seedu.awe.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.awe.commons.core.Messages.MESSAGE_ADDCONTACTCOMMAND_DUPLICATE;
import static seedu.awe.commons.core.Messages.MESSAGE_ADDCONTACTCOMMAND_SUCCESS;
import static seedu.awe.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.awe.commons.core.GuiSettings;
import seedu.awe.logic.commands.exceptions.CommandException;
import seedu.awe.model.AddressBook;
import seedu.awe.model.Model;
import seedu.awe.model.ReadOnlyAddressBook;
import seedu.awe.model.ReadOnlyUserPrefs;
import seedu.awe.model.expense.Cost;
import seedu.awe.model.expense.Expense;
import seedu.awe.model.group.Group;
import seedu.awe.model.group.GroupName;
import seedu.awe.model.group.exceptions.DuplicateGroupException;
import seedu.awe.model.payment.Payment;
import seedu.awe.model.person.Person;
import seedu.awe.model.transactionsummary.TransactionSummary;
import seedu.awe.testutil.PersonBuilder;

public class AddContactCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddContactCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person validPerson = new PersonBuilder().build();

        CommandResult commandResult = new AddContactCommand(validPerson).execute(modelStub);

        assertEquals(String.format(MESSAGE_ADDCONTACTCOMMAND_SUCCESS, validPerson), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person validPerson = new PersonBuilder().build();
        AddContactCommand addContactCommand = new AddContactCommand(validPerson);
        ModelStub modelStub = new ModelStubWithPerson(validPerson);

        assertThrows(CommandException.class,
                MESSAGE_ADDCONTACTCOMMAND_DUPLICATE, () -> addContactCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        AddContactCommand addAliceCommand = new AddContactCommand(alice);
        AddContactCommand addBobCommand = new AddContactCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddContactCommand addAliceCommandCopy = new AddContactCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Group getActiveGroupFromAddressBook() throws CommandException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTransactionSummary(HashMap<Person, Cost> summary) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAllMembersOfGroup(Group group) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteGroup(Group group) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasGroup(Group group) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPayments(List<Payment> payments) {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * Returns an unmodifiable view of the list of {@code Group} backed by the internal list of
         * {@code versionedAddressBook}
         */
        @Override
        public ObservableList<Group> getFilteredGroupList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredGroupList(Predicate<Group> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Group getGroupByName(GroupName groupName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addGroup(Group group) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGroup(Group group, Group newGroup) throws DuplicateGroupException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addExpense(Expense expense, Group group) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteExpense(Expense expense, Group group) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Payment> getPayments() {
            return null;
        }

        @Override
        public ObservableList<Expense> getExpenses() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setExpenses(Group group) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredExpenseList(Predicate<Expense> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<TransactionSummary> getTransactionSummary() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isCurrentExpenseList(Group group) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Expense getExpense(int index) {
            return null;
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Person person;

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.person = person;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.person.isSamePerson(person);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
