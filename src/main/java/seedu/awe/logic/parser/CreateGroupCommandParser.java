package seedu.awe.logic.parser;

import static seedu.awe.commons.core.Messages.MESSAGE_CREATEGROUPCOMMAND_EMPTY_GROUP;
import static seedu.awe.commons.core.Messages.MESSAGE_CREATEGROUPCOMMAND_ERROR;
import static seedu.awe.commons.core.Messages.MESSAGE_CREATEGROUPCOMMAND_INVALID_NAMES;
import static seedu.awe.commons.core.Messages.MESSAGE_CREATEGROUPCOMMAND_USAGE;
import static seedu.awe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.awe.logic.parser.CliSyntax.PREFIX_GROUP_NAME;
import static seedu.awe.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.awe.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import javafx.collections.ObservableList;
import seedu.awe.logic.commands.CreateGroupCommand;
import seedu.awe.logic.parser.exceptions.EmptyGroupException;
import seedu.awe.logic.parser.exceptions.ParseException;
import seedu.awe.model.Model;
import seedu.awe.model.ReadOnlyAddressBook;
import seedu.awe.model.group.GroupName;
import seedu.awe.model.person.Name;
import seedu.awe.model.person.Person;
import seedu.awe.model.tag.Tag;

public class CreateGroupCommandParser implements Parser<CreateGroupCommand> {
    private static final String BAD_FORMATTING = "\"creategroup command\" is not properly formatted";
    private ObservableList<Person> allMembers;
    private final ArrayList<Person> toBeAddedToGroup;

    /**
     * Creates new CreateGroupParser object.
     *
     * @param model Model object passed into constructor to provide list of contacts.
     */
    public CreateGroupCommandParser(Model model) {
        ReadOnlyAddressBook addressBook = model.getAddressBook();
        this.allMembers = addressBook.getPersonList();
        this.toBeAddedToGroup = new ArrayList<>();
    }

    /**
     * Returns CreateGroupCommand based on user input.
     *
     * @param args User input into addressbook.
     * @return CreateGroupCommand object to represent command to be executed.
     * @throws ParseException If user input is incorrectly formatted.
     */
    public CreateGroupCommand parse(String args) throws ParseException, EmptyGroupException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_GROUP_NAME, PREFIX_NAME, PREFIX_TAG);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_GROUP_NAME, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_CREATEGROUPCOMMAND_USAGE));
        }

        GroupName groupName = ParserUtil.parseGroupName(argMultimap.getValue(PREFIX_GROUP_NAME).get());
        List<Name> memberNames = ParserUtil.parseMemberNames(argMultimap.getAllValues(PREFIX_NAME));
        Set<Tag> tagSet = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        ArrayList<Person> members = findGroupMembers(memberNames);

        boolean isValidCommand = true;
        if (groupName.getName().equals(BAD_FORMATTING) || Objects.isNull(members)) {
            isValidCommand = false;
        }

        return new CreateGroupCommand(groupName, members, isValidCommand, tagSet);
    }

    /**
     * Returns list of members in the group.
     * If user input not valid, null is returned.
     *
     * @param memberNames List of names representing names entered in command.
     * @return ArrayList of Person objects representing members to be added to the group
     */
    public ArrayList<Person> findGroupMembers(List<Name> memberNames) throws ParseException, EmptyGroupException {
        try {
            for (Name name : memberNames) {
                addMemberIfExist(name);
            }
            if (!memberNames.isEmpty() && toBeAddedToGroup.isEmpty()) {
                throw new ParseException(MESSAGE_CREATEGROUPCOMMAND_ERROR);
            } else if (toBeAddedToGroup.isEmpty()) {
                throw new EmptyGroupException(MESSAGE_INVALID_COMMAND_FORMAT);
            }
            return toBeAddedToGroup;
        } catch (IndexOutOfBoundsException e) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
        } catch (EmptyGroupException err) {
            throw new EmptyGroupException(
                    String.format(MESSAGE_CREATEGROUPCOMMAND_EMPTY_GROUP,
                            MESSAGE_CREATEGROUPCOMMAND_INVALID_NAMES,
                            MESSAGE_CREATEGROUPCOMMAND_USAGE));
        }
    }

    /**
     * Returns list of members in the group.
     * If user input not valid, null is returned.
     *
     * @param memberName Name object representing name of member to be added.
     */
    public boolean addMemberIfExist(Name memberName) throws ParseException {
        boolean added = false;
        Person memberFound = findMember(memberName, this.allMembers);
        Stream<Name> namesOfMembers = this.toBeAddedToGroup.stream().map(member -> member.getName());
        long numOfMembersWithSameName = namesOfMembers.filter(existingName -> existingName.equals(memberName)).count();
        if (!Objects.isNull(memberFound) && numOfMembersWithSameName == 0) {
            toBeAddedToGroup.add(memberFound);
            added = true;
        }
        if (!added) {
            throw new ParseException(MESSAGE_CREATEGROUPCOMMAND_ERROR);
        }
        return added;
    }

    /**
     * Returns person that matches a given Name object if in addressbook.
     * If person not in addressbook, null is returned.
     *
     * @param memberName Name object representing person that is being searched for in the addressbook.
     * @param members ObservableList of Person objects representing all contacts in the addressbook.
     * @return Person object if name matches that of a person from the addressbook.
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
