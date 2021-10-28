package seedu.awe.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.awe.model.AddressBook;
import seedu.awe.model.ReadOnlyAddressBook;
import seedu.awe.model.expense.Cost;
import seedu.awe.model.expense.Description;
import seedu.awe.model.expense.Expense;
import seedu.awe.model.group.Group;
import seedu.awe.model.group.GroupName;
import seedu.awe.model.person.Address;
import seedu.awe.model.person.Email;
import seedu.awe.model.person.Name;
import seedu.awe.model.person.Person;
import seedu.awe.model.person.Phone;
import seedu.awe.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"),
                    getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"),
                    getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"),
                    getTagSet("colleagues"))
        };
    }

    public static Expense[] getSampleExpenses() {
        Person[] members = getSamplePersons();

        return new Expense[] {
            new Expense(members[0], new Cost("10"), new Description("Transportation"), addSampleMembers(0, 3)),
            new Expense(members[1], new Cost("300"), new Description("Buffet"), addSampleMembers(0, 3)),
            new Expense(members[2], new Cost("60"), new Description("Souvenirs"), addSampleMembers(0, 3)),
            new Expense(members[3], new Cost("200"), new Description("Entry Tickets"), addSampleMembers(3, 6)),
            new Expense(members[3], new Cost("150"), new Description("Lunch"), addSampleMembers(3, 6)),
            new Expense(members[4], new Cost("180"), new Description("Supper"), addSampleMembers(3, 6))
        };
    }

    public static Group[] getSampleGroups() {
        ArrayList<Person> londonMembers = addSampleMembers(0, 3);
        ArrayList<Person> baliMembers = addSampleMembers(3, 6);
        ArrayList<Expense> londonExpenses = addSampleExpenses(0, 3);
        ArrayList<Expense> baliExpenses = addSampleExpenses(3, 6);

        return new Group[] {
            new Group(new GroupName("London"), londonMembers, getTagSet("SchoolTrip"),
                    londonExpenses),
            new Group(new GroupName("Bali"), baliMembers, getTagSet("3DayTrip"),
                    baliExpenses)
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        for (Group sampleGroup : getSampleGroups()) {
            sampleAb.addGroup(sampleGroup);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a list of members containing the sample persons
     * within the given range.
     *
     * @param start The starting index to add member.
     * @param end   The ending index to add member.
     * @return List of members.
     */
    public static ArrayList<Person> addSampleMembers(int start, int end) {
        ArrayList<Person> members = new ArrayList<>();
        Person[] persons = getSamplePersons();
        for (int i = start; i < end; i++) {
            members.add(persons[i]);
        }
        return members;
    }

    /**
     * Returns a list of expenses containing the sample expenses
     * within the given range.
     *
     * @param start The starting index to add expense.
     * @param end   The ending index to add expense.
     * @return List of expenses.
     */
    public static ArrayList<Expense> addSampleExpenses(int start, int end) {
        ArrayList<Expense> expenses = new ArrayList<>();
        Expense[] sampleExpenses = getSampleExpenses();
        for (int i = start; i < end; i++) {
            expenses.add(sampleExpenses[i]);
        }
        return expenses;
    }

}
