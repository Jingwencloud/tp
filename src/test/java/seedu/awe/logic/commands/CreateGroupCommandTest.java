package seedu.awe.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.awe.commons.core.Messages.MESSAGE_CREATEGROUPCOMMAND_DUPLICATE_GROUP;
import static seedu.awe.commons.core.Messages.MESSAGE_CREATEGROUPCOMMAND_SUCCESS;
import static seedu.awe.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
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
import seedu.awe.testutil.GroupBuilder;

public class CreateGroupCommandTest {
    private Model model;

    @Test
    public void constructor_nullGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new CreateGroupCommand(null, null, true, new HashSet<>()));
    }

    @Test
    public void execute_groupAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingGroupAdded modelStub = new ModelStubAcceptingGroupAdded();
        GroupBuilder builder = new GroupBuilder();
        GroupName bali = builder.getValidGroupName();
        ArrayList<Person> members = builder.getValidMembers();
        Group groupAdded = new Group(bali, members);

        CommandResult commandResult = new CreateGroupCommand(bali, members, true, new HashSet<>())
                .execute(modelStub);

        assertEquals(MESSAGE_CREATEGROUPCOMMAND_SUCCESS, commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(groupAdded), modelStub.groupsAdded);
    }

    @Test
    public void execute_duplicateGroup_throwsCommandException() {
        Group validGroup = new GroupBuilder().buildValidGroup();
        GroupBuilder builder = new GroupBuilder();
        GroupName bali = builder.getValidGroupName();
        ArrayList<Person> members = builder.getValidMembers();
        CreateGroupCommand createGroupCommand = new CreateGroupCommand(bali, members, true, new HashSet<>());
        ModelStub modelStub = new ModelStubWithGroup(validGroup);

        assertThrows(CommandException.class, MESSAGE_CREATEGROUPCOMMAND_DUPLICATE_GROUP, () ->
                        createGroupCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        GroupName bali = new GroupName("Bali");
        GroupName oslo = new GroupName("Oslo");
        ArrayList<Person> members = new GroupBuilder().getValidMembers();
        CreateGroupCommand createBaliCommand = new CreateGroupCommand(bali, members, true,
                new HashSet<>());
        CreateGroupCommand createOsloCommand = new CreateGroupCommand(oslo, members, true,
                new HashSet<>());


        // same object -> returns true
        assertTrue(createBaliCommand.equals(createBaliCommand));
        assertTrue(createOsloCommand.equals(createOsloCommand));

        // different types -> returns false
        assertFalse(createOsloCommand.equals(1));

        // null -> returns false
        assertFalse(createOsloCommand.equals(null));

        // different person -> returns false
        assertFalse(createOsloCommand.equals(createBaliCommand));
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
     * A Model stub that contains a single group.
     */
    private class ModelStubWithGroup extends ModelStub {
        private final Group group;

        ModelStubWithGroup(Group group) {
            requireNonNull(group);
            this.group = group;
        }

        @Override
        public boolean hasGroup(Group group) {
            requireNonNull(group);
            return this.group.isSameGroup(group);
        }
    }

    /**
     * A Model stub that always accept the group being added.
     */
    private class ModelStubAcceptingGroupAdded extends ModelStub {
        final ArrayList<Group> groupsAdded = new ArrayList<>();

        @Override
        public boolean hasGroup(Group group) {
            requireNonNull(group);
            return groupsAdded.stream().anyMatch(group::isSameGroup);
        }

        @Override
        public void addGroup(Group group) {
            requireNonNull(group);
            groupsAdded.add(group);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }

        @Override
        public void setAllMembersOfGroup(Group group) {
            requireNonNull(group);
        }
    }

}

