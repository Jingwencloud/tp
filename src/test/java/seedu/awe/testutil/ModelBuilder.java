package seedu.awe.testutil;

import static seedu.awe.testutil.TypicalGroups.AMSTERDAM;
import static seedu.awe.testutil.TypicalGroups.BALI;
import static seedu.awe.testutil.TypicalGroups.CHINA;
import static seedu.awe.testutil.TypicalGroups.DUBAI;
import static seedu.awe.testutil.TypicalGroups.HELSINKI;
import static seedu.awe.testutil.TypicalGroups.INDIA;
import static seedu.awe.testutil.TypicalGroups.LONDON;
import static seedu.awe.testutil.TypicalGroups.MALIBU;
import static seedu.awe.testutil.TypicalGroups.OSLO;
import static seedu.awe.testutil.TypicalGroups.PARIS;
import static seedu.awe.testutil.TypicalPersons.ALICE;
import static seedu.awe.testutil.TypicalPersons.AMY;
import static seedu.awe.testutil.TypicalPersons.BENSON;
import static seedu.awe.testutil.TypicalPersons.BOB;
import static seedu.awe.testutil.TypicalPersons.CARL;
import static seedu.awe.testutil.TypicalPersons.DANIEL;
import static seedu.awe.testutil.TypicalPersons.ELLE;
import static seedu.awe.testutil.TypicalPersons.FIONA;
import static seedu.awe.testutil.TypicalPersons.GEORGE;
import static seedu.awe.testutil.TypicalPersons.HOON;
import static seedu.awe.testutil.TypicalPersons.IDA;

import javafx.collections.transformation.FilteredList;
import seedu.awe.model.AddressBook;
import seedu.awe.model.Model;
import seedu.awe.model.ModelManager;
import seedu.awe.model.UserPrefs;
import seedu.awe.model.group.Group;
import seedu.awe.model.group.exceptions.DuplicateGroupException;
import seedu.awe.model.person.Person;

/**
 * A utility class to help with building Person objects.
 */
public class ModelBuilder {

    public static final AddressBook DEFAULT_ADDRESSBOOK = new AddressBook();
    public static final UserPrefs DEFAULT_USERPREFS = new UserPrefs();
    public static final FilteredList<Person> DEFAULT_FILTEREDPERSONS =
            new FilteredList<Person>(DEFAULT_ADDRESSBOOK.getPersonList());
    public static final FilteredList<Group> DEFAULT_FILTEREDGROUPS =
            new FilteredList<Group>(DEFAULT_ADDRESSBOOK.getGroupList());

    private AddressBook addressBook;
    private UserPrefs userPrefs;
    private FilteredList<Person> filteredPersons;
    private FilteredList<Group> filteredGroups;

    /**
     * Creates a {@code ModelBuilder} with the default details.
     */
    public ModelBuilder() {
        addressBook = new AddressBook();
        addTypicalPersons(addressBook);
        addTypicalGroups(addressBook);
        userPrefs = DEFAULT_USERPREFS;
        filteredPersons = DEFAULT_FILTEREDPERSONS;
        filteredGroups = DEFAULT_FILTEREDGROUPS;
    }

    /**
     * Initializes the ModelBuilder with the data of {@code ModelToCopy}.
     */
    public ModelBuilder(Model modelToCopy) {
        addressBook = (AddressBook) modelToCopy.getAddressBook();
        userPrefs = (UserPrefs) modelToCopy.getUserPrefs();
        filteredPersons = (FilteredList<Person>) modelToCopy.getFilteredPersonList();
        filteredGroups = (FilteredList<Group>) modelToCopy.getFilteredGroupList();
    }

    /**
     * Sets the {@code AddressBook} of the {@code Model} that we are building.
     */
    public ModelBuilder withAddressBook(AddressBook addressBook) {
        this.addressBook = addressBook;
        return this;
    }

    /**
     * Sets the {@code UserPrefs} of the {@code Model} that we are building.
     */
    public ModelBuilder withUserPrefs(UserPrefs userPrefs) {
        this.userPrefs = userPrefs;
        return this;
    }

    /**
     * Adds all Person attributes from TypicalPersons into the sample addressbook.
     *
     * @param addressBook AddressBook object to be used for testing purposes.
     */
    public void addTypicalPersons(AddressBook addressBook) {
        addressBook.addPerson(ALICE);
        addressBook.addPerson(BENSON);
        addressBook.addPerson(CARL);
        addressBook.addPerson(DANIEL);
        addressBook.addPerson(ELLE);
        addressBook.addPerson(FIONA);
        addressBook.addPerson(GEORGE);
        addressBook.addPerson(HOON);
        addressBook.addPerson(IDA);
        addressBook.addPerson(AMY);
        addressBook.addPerson(BOB);
    }

    /**
     * Adds all Person attributes from TypicalPersons into the sample addressbook.
     *
     * @param addressBook AddressBook object to be used for testing purposes.
     */
    public void addTypicalGroups(AddressBook addressBook) throws DuplicateGroupException {
        addressBook.addGroup(AMSTERDAM);
        addressBook.addGroup(BALI);
        addressBook.addGroup(INDIA);
        addressBook.addGroup(LONDON);
        addressBook.addGroup(DUBAI);
        addressBook.addGroup(PARIS);
        addressBook.addGroup(CHINA);
        addressBook.addGroup(MALIBU);
        addressBook.addGroup(HELSINKI);
        addressBook.addGroup(OSLO);
    }

    public Model build() {
        return new ModelManager(addressBook, userPrefs);
    }

    public Model buildEmptyModel() {
        return new ModelManager(new AddressBook(), new UserPrefs());
    }
}

