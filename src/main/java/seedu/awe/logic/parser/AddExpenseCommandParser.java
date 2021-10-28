package seedu.awe.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.awe.commons.core.Messages.MESSAGE_ADDEXPENSECOMMAND_NOT_PART_OF_GROUP;
import static seedu.awe.commons.core.Messages.MESSAGE_ADDEXPENSECOMMAND_USAGE;
import static seedu.awe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.awe.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.awe.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.awe.logic.parser.CliSyntax.PREFIX_EXCLUDE;
import static seedu.awe.logic.parser.CliSyntax.PREFIX_GROUP_NAME;
import static seedu.awe.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javafx.collections.ObservableList;
import seedu.awe.logic.commands.AddExpenseCommand;
import seedu.awe.logic.parser.exceptions.ParseException;
import seedu.awe.model.Model;
import seedu.awe.model.ReadOnlyAddressBook;
import seedu.awe.model.expense.Cost;
import seedu.awe.model.expense.Description;
import seedu.awe.model.group.GroupName;
import seedu.awe.model.person.Name;
import seedu.awe.model.person.Person;

/**
 * Parses input arguments and creates a new AddExpenseCommand object
 */
public class AddExpenseCommandParser implements Parser<AddExpenseCommand> {

    private ObservableList<Person> allMembers;

    /**
     * Creates new CreateGroupParser object.
     *
     * @param model Model object passed into constructor to provide list of contacts.
     */
    public AddExpenseCommandParser(Model model) {
        ReadOnlyAddressBook addressBook = model.getAddressBook();
        allMembers = addressBook.getPersonList();
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @param args user input
     * @return AddExpenseCommand object to represent command to be executed.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddExpenseCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_NAME, PREFIX_GROUP_NAME, PREFIX_COST, PREFIX_DESCRIPTION, PREFIX_EXCLUDE);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_GROUP_NAME, PREFIX_COST, PREFIX_DESCRIPTION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_ADDEXPENSECOMMAND_USAGE));
        }

        List<Name> names = ParserUtil.parseNames(argMultimap.getAllValues(PREFIX_NAME));
        GroupName groupName = ParserUtil.parseGroupName(argMultimap.getValue(PREFIX_GROUP_NAME).get());
        List<Cost> costs = ParserUtil.parseCosts(argMultimap.getAllValues(PREFIX_COST));
        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        List<Name> excludedNames = ParserUtil.parseExcluded(argMultimap.getAllValues(PREFIX_EXCLUDE));

        if (names.size() != costs.size()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_ADDEXPENSECOMMAND_USAGE));
        }

        Person payer = getPayer(names);

        if (payer == null) {
            throw new ParseException(MESSAGE_ADDEXPENSECOMMAND_NOT_PART_OF_GROUP);
        }

        Cost totalCost = getTotalCost(costs);
        ArrayList<Person> namesAsPersons = namesToPerson(names);
        List<Person> toExclude = findExcluded(excludedNames);

        return new AddExpenseCommand(payer, totalCost, description, groupName, namesAsPersons, costs, toExclude);
    }

    private List<Person> findExcluded(List<Name> toExclude) {
        final ArrayList<Person> excludedPersons = new ArrayList<>();
        for (Name name : toExclude) {
            Person person = findPerson(name);
            if (person != null) {
                excludedPersons.add(person);
            }
        }
        return excludedPersons;
    }

    private ArrayList<Person> namesToPerson(List<Name> names) {
        final ArrayList<Person> persons = new ArrayList<>();
        for (Name name : names) {
            Person person = findPerson(name);
            persons.add(person);
        }
        return persons;
    }

    /**
     * Returns person that matches a given Name object if in addressbook.
     * If person not in addressbook, null is returned.
     *
     * @param memberName Name object representing person that is being searched for in the addressbook.
     * @return Person object if name matches that of a person from the addressbook.
     */
    private Person findPerson(Name memberName) {
        for (Person payee : allMembers) {
            if (payee.getName().equals(memberName)) {
                return payee;
            }
        }
        return null;
    }

    private Person getPayer(List<Name> names) {
        Name payerName = names.remove(0);
        for (Person payer : allMembers) {
            if (payer.getName().equals(payerName)) {
                return payer;
            }
        }
        return null;
    }

    private Cost getTotalCost(List<Cost> costs) {
        Cost totalCost = costs.remove(0);
        return totalCost;
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
