package seedu.awe.logic.parser;
import static seedu.awe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.awe.logic.commands.GroupRemovePersonCommand.MESSAGE_ERROR;
import static seedu.awe.logic.parser.CliSyntax.PREFIX_GROUP_NAME;
import static seedu.awe.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.awe.logic.commands.GroupRemovePersonCommand;
import seedu.awe.logic.parser.exceptions.ParseException;
import seedu.awe.model.Model;
import seedu.awe.model.ReadOnlyAddressBook;
import seedu.awe.model.group.GroupName;
import seedu.awe.model.person.Name;
import seedu.awe.model.person.Person;

public class GroupRemovePersonCommandParser implements Parser<GroupRemovePersonCommand> {
    private static final String BAD_FORMATTING = "\"groupaddperson command\" is not properly formatted";
    private ObservableList<Person> allMembers;
    private final ArrayList<Person> membersToBeRemoved;

    /**
     * Creates new GroupRemovePersonCommandParser object.
     *
     * @param model Model object passed into constructor to provide list of contacts.
     */
    public GroupRemovePersonCommandParser(Model model) {
        ReadOnlyAddressBook addressBook = model.getAddressBook();
        this.allMembers = addressBook.getPersonList();
        this.membersToBeRemoved = new ArrayList<>();
    }

    /**
     * Returns GroupRemovePersonCommand based on user input.
     *
     * @param args User input into addressbook.
     * @return GroupRemovePersonCommand object to represent command to be executed.
     * @throws ParseException If user input is incorrectly formatted.
     */
    public GroupRemovePersonCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_GROUP_NAME, PREFIX_NAME);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_GROUP_NAME, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    GroupRemovePersonCommand.MESSAGE_USAGE));
        }

        GroupName groupName = ParserUtil.parseGroupName(argMultimap.getValue(PREFIX_GROUP_NAME).get());
        List<Name> membersToBeRemovedNames = ParserUtil.parseMemberNames(argMultimap.getAllValues(PREFIX_NAME));

        ArrayList<Person> membersToBeRemoved = findMembersToBeRemoved(membersToBeRemovedNames);
        boolean isValidCommand = true;
        if (groupName.getName().equals(BAD_FORMATTING) || Objects.isNull(membersToBeRemoved)) {
            isValidCommand = false;
        }

        return new GroupRemovePersonCommand(groupName, membersToBeRemoved, isValidCommand);
    }

    /**
     * Returns list of members in the group.
     *
     * @param membersToBeRemovedNames List of names representing members to be removed from group.
     * @return ArrayList of Person objects representing members to be added to the group
     */
    public ArrayList<Person> findMembersToBeRemoved(List<Name> membersToBeRemovedNames) throws ParseException {
        try {
            for (Name name : membersToBeRemovedNames) {
                addMemberToRemoveList(name);
            }
            if (!membersToBeRemovedNames.isEmpty() && membersToBeRemovedNames.isEmpty()) {
                throw new ParseException(MESSAGE_ERROR);
            }
            return membersToBeRemoved;
        } catch (IndexOutOfBoundsException e) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
        }
    }

    /**
     * Returns boolean object representing if member has been added into this.membersToBeRemoved.
     *
     * @param memberName Name object representing member whose presence is being checked for.
     * @return boolean object representing if member has been added into this.membersToBeRemoved.
     * @throws ParseException if the person does not exist in the contact list.
     */
    public boolean addMemberToRemoveList(Name memberName) throws ParseException {
        boolean added = false;
        Person memberFound = findMember(memberName, allMembers);
        if (!Objects.isNull(memberFound)) {
            membersToBeRemoved.add(memberFound);
            added = true;
        }
        if (!added) {
            throw new ParseException(GroupRemovePersonCommand.MESSAGE_ERROR);
        }
        return added;
    }

    /**
     * Returns person that matches a given Name object if in contact list.
     * If person not in contact list, null is returned.
     *
     * @param memberName Name object representing person that is being searched for in the contact list.
     * @param members ObservableList of Person objects representing all contacts in the contact list.
     * @return Person object if name matches that of a person from the contact list.
     */
    public static Person findMember(Name memberName, ObservableList<Person> members) {
        for (Person member : members) {
            if (member.getName().equals(memberName)) {
                return member;
            }
        }
        return null;
    }
}
