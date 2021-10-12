package seedu.awe.logic.parser;
import static seedu.awe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.awe.logic.commands.GroupAddPersonCommand.MESSAGE_ERROR;
import static seedu.awe.logic.parser.CliSyntax.PREFIX_GROUP_NAME;
import static seedu.awe.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.awe.logic.commands.GroupAddPersonCommand;
import seedu.awe.logic.parser.exceptions.ParseException;
import seedu.awe.model.Model;
import seedu.awe.model.ReadOnlyAddressBook;
import seedu.awe.model.group.GroupName;
import seedu.awe.model.person.Name;
import seedu.awe.model.person.Person;

public class GroupAddPersonCommandParser implements Parser<GroupAddPersonCommand> {
    private static final String BAD_FORMATTING = "\"groupaddperson command\" is not properly formatted";
    private ObservableList<Person> allMembers;
    private final ArrayList<Person> newMembersToAdd;

    /**
     * Creates new GroupAddPersonCommandParser object.
     *
     * @param model Model object passed into constructor to provide list of contacts.
     */
    public GroupAddPersonCommandParser(Model model) {
        ReadOnlyAddressBook addressBook = model.getAddressBook();
        this.allMembers = addressBook.getPersonList();
        this.newMembersToAdd = new ArrayList<>();
    }

    /**
     * Returns GroupAddPersonCommand based on user input.
     *
     * @param args User input into addressbook.
     * @return GroupAddPersonCommand object to represent command to be executed.
     * @throws ParseException If user input is incorrectly formatted.
     */
    public GroupAddPersonCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_GROUP_NAME, PREFIX_NAME);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_GROUP_NAME, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    GroupAddPersonCommand.MESSAGE_USAGE));
        }

        GroupName groupName = ParserUtil.parseGroupName(argMultimap.getValue(PREFIX_GROUP_NAME).get());
        List<Name> newMemberNames = ParserUtil.parseMemberNames(argMultimap.getAllValues(PREFIX_NAME));
        ArrayList<Person> newMembers = findNewMembers(newMemberNames);

        boolean isValidCommand = true;
        if (groupName.getName().equals(BAD_FORMATTING) || Objects.isNull(newMembers)) {
            isValidCommand = false;
        }

        return new GroupAddPersonCommand(groupName, newMembers, isValidCommand);
    }

    /**
     * Returns list of members in the group.
     *
     * @param newMemberNames List of names representing new members to be added into group.
     * @return ArrayList of Person objects representing members to be added to the group
     */
    public ArrayList<Person> findNewMembers(List<Name> newMemberNames) throws ParseException {
        try {
            for (Name name : newMemberNames) {
                addMemberIfExist(name);
            }
            if (!newMemberNames.isEmpty() && newMembersToAdd.isEmpty()) {
                throw new ParseException(MESSAGE_ERROR);
            }
            return newMembersToAdd;
        } catch (IndexOutOfBoundsException e) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
        }
    }

    /**
     * Returns boolean object representing if member has been added into newMembersToAdd.
     *
     * @param memberName Name object representing member whose presence is being checked for.
     * @return boolean object representing if member has been added into newMembersToAdd.
     * @throws ParseException if the person does not exist in the contact list.
     */
    public boolean addMemberIfExist(Name memberName) throws ParseException {
        boolean added = false;
        Person memberFound = findMember(memberName, allMembers);
        if (!Objects.isNull(memberFound)) {
            newMembersToAdd.add(memberFound);
            added = true;
        }
        if (!added) {
            throw new ParseException(GroupAddPersonCommand.MESSAGE_ERROR);
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
