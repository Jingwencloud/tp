package seedu.awe.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.awe.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.awe.testutil.Assert.assertThrows;
import static seedu.awe.testutil.TypicalGroups.DUBAI;
import static seedu.awe.testutil.TypicalPersons.ALICE;
import static seedu.awe.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.awe.logic.commands.exceptions.CommandException;
import seedu.awe.model.expense.Expense;
import seedu.awe.model.group.Group;
import seedu.awe.model.group.GroupName;
import seedu.awe.model.group.UniqueGroupList;
import seedu.awe.model.payment.Payment;
import seedu.awe.model.person.Person;
import seedu.awe.model.person.UniquePersonList;
import seedu.awe.model.person.exceptions.DuplicatePersonException;
import seedu.awe.testutil.PersonBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        assertTrue(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getPersonList().remove(0));
    }

    @Test
    public void getPersons_returnsPersonList() {
        assertEquals(addressBook.getPersons(), new UniquePersonList());
        addressBook.addPerson(ALICE);
        UniquePersonList personList = new UniquePersonList();
        personList.add(ALICE);
        assertEquals(addressBook.getPersons(), personList);
    }

    @Test
    public void getGroups_returnsGroupList() {
        assertEquals(addressBook.getGroups(), new UniqueGroupList());
        addressBook.addGroup(DUBAI);
        UniqueGroupList groupList = new UniqueGroupList();
        groupList.add(DUBAI);
        assertEquals(addressBook.getGroups(), groupList);
    }

    @Test
    public void equals() {
        assertEquals(addressBook, addressBook);
        assertNotEquals(addressBook, new ModelManager());
    }

    @Test
    public void hashcode() {
        assertEquals(addressBook.hashCode(), addressBook.hashCode());
        int oldCode = addressBook.hashCode();
        addressBook.addPerson(ALICE);
        int newCode = addressBook.hashCode();
        assertEquals(addressBook.hashCode(), addressBook.hashCode());
        assertNotEquals(oldCode, newCode);
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Expense> expenses = FXCollections.observableArrayList();
        private final ObservableList<Payment> payments = FXCollections.observableArrayList();

        AddressBookStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Expense> getExpenseList() {
            return expenses;
        }

        @Override
        public Optional<Group> getGroupFromExpenseList() throws CommandException {
            return Optional.empty();
        }

        @Override
        public ObservableList<Payment> getPaymentList() {
            return payments;
        }

        @Override
        public ObservableList<Group> getGroupList() {
            return null;
        }

        @Override
        public Group getGroupByName(GroupName groupName) {
            return null;
        }

        @Override
        public void setPayments(List<Payment> payments) {

        }
    }

}
