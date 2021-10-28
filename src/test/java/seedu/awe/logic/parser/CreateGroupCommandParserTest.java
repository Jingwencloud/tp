package seedu.awe.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.awe.commons.core.Messages.MESSAGE_CREATEGROUPCOMMAND_EMPTY_GROUP;
import static seedu.awe.commons.core.Messages.MESSAGE_CREATEGROUPCOMMAND_ERROR;
import static seedu.awe.commons.core.Messages.MESSAGE_CREATEGROUPCOMMAND_INVALID_NAMES;
import static seedu.awe.commons.core.Messages.MESSAGE_CREATEGROUPCOMMAND_USAGE;
import static seedu.awe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.awe.logic.commands.CommandTestUtil.GROUPNAME_DESC_BALI;
import static seedu.awe.logic.commands.CommandTestUtil.INVALID_GROUP_NAME_DESC;
import static seedu.awe.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.awe.logic.commands.CommandTestUtil.INVALID_NAME_DESC_ONE;
import static seedu.awe.logic.commands.CommandTestUtil.INVALID_NAME_DESC_TWO;
import static seedu.awe.logic.commands.CommandTestUtil.NAME_DESC_ALICE;
import static seedu.awe.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.awe.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.awe.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.awe.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.awe.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.awe.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.awe.testutil.Assert.assertThrows;
import static seedu.awe.testutil.TypicalGroups.BALI;
import static seedu.awe.testutil.TypicalPersons.ALICE;
import static seedu.awe.testutil.TypicalPersons.AMY;
import static seedu.awe.testutil.TypicalPersons.BOB;
import static seedu.awe.testutil.TypicalPersons.NONEXISTENTPERSON;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.awe.logic.commands.CreateGroupCommand;
import seedu.awe.logic.parser.exceptions.EmptyGroupException;
import seedu.awe.logic.parser.exceptions.ParseException;
import seedu.awe.model.group.GroupName;
import seedu.awe.model.group.exceptions.DuplicateGroupException;
import seedu.awe.model.person.Name;
import seedu.awe.model.person.Person;
import seedu.awe.testutil.ModelBuilder;

public class CreateGroupCommandParserTest {
    private CreateGroupCommandParser parser;
    private CreateGroupCommandParser emptyParser;

    public CreateGroupCommandParserTest() {
        parser = new CreateGroupCommandParser(new ModelBuilder().build());
        emptyParser = new CreateGroupCommandParser(new ModelBuilder().buildEmptyModel());
    }

    /**
     * Resets parser. Necessary as CreateGroupCommand parser needs to be initialised with a model for each call.
     * Failure to reset parser will result in Duplicate exceptions being raised.
     */
    public void resetParser() throws DuplicateGroupException {
        parser = new CreateGroupCommandParser(new ModelBuilder().build());
    }

    @Test
    public void parse_allFieldsPresent_success() {
        GroupName expectedGroupName = BALI.getGroupName();
        ArrayList<Person> expectedGroupMembers = new ArrayList<>(BALI.getMembers());

        // regular input for CreateGroupCommand
        assertParseSuccess(parser, GROUPNAME_DESC_BALI + NAME_DESC_ALICE + NAME_DESC_BOB + NAME_DESC_AMY,
                new CreateGroupCommand(expectedGroupName, expectedGroupMembers, true, new HashSet<>()));

        resetParser();
        // names in different order
        assertParseSuccess(parser, GROUPNAME_DESC_BALI + NAME_DESC_BOB + NAME_DESC_AMY + NAME_DESC_ALICE,
                new CreateGroupCommand(expectedGroupName, expectedGroupMembers, true, new HashSet<>()));

        resetParser();
        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + GROUPNAME_DESC_BALI
                        + NAME_DESC_ALICE + NAME_DESC_BOB + NAME_DESC_AMY,
                new CreateGroupCommand(expectedGroupName, expectedGroupMembers, true, new HashSet<>()));

        resetParser();
        // name repeats
        assertParseSuccess(parser, GROUPNAME_DESC_BALI
                        + NAME_DESC_BOB + NAME_DESC_AMY + NAME_DESC_ALICE + NAME_DESC_ALICE,
                new CreateGroupCommand(expectedGroupName, expectedGroupMembers, true, new HashSet<>()));

        resetParser();
        // multiple name repeats
        assertParseSuccess(parser, GROUPNAME_DESC_BALI
                        + NAME_DESC_BOB + NAME_DESC_BOB + NAME_DESC_AMY + NAME_DESC_ALICE + NAME_DESC_ALICE,
                new CreateGroupCommand(expectedGroupName, expectedGroupMembers, true, new HashSet<>()));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() throws DuplicateGroupException {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_CREATEGROUPCOMMAND_USAGE);

        resetParser();
        // missing group prefix
        assertParseFailure(parser, NAME_DESC_BOB + NAME_DESC_AMY + NAME_DESC_ALICE, expectedMessage);

        resetParser();
        // missing name prefix
        assertParseFailure(parser, GROUPNAME_DESC_BALI, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_CREATEGROUPCOMMAND_USAGE);
        resetParser();
        // invalid group name
        assertParseFailure(parser, INVALID_GROUP_NAME_DESC + NAME_DESC_BOB + NAME_DESC_AMY + NAME_DESC_ALICE,
                GroupName.MESSAGE_CONSTRAINTS);

        resetParser();
        // invalid names returns EmptyGroupException
        String userInput = GROUPNAME_DESC_BALI + INVALID_NAME_DESC + INVALID_NAME_DESC_ONE + INVALID_NAME_DESC_TWO;
        assertThrows(EmptyGroupException.class,
                String.format(MESSAGE_CREATEGROUPCOMMAND_EMPTY_GROUP, MESSAGE_CREATEGROUPCOMMAND_INVALID_NAMES), () ->
                        parser.parse(userInput));

        resetParser();
        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + GROUPNAME_DESC_BALI
                        + NAME_DESC_ALICE + NAME_DESC_BOB + NAME_DESC_AMY, expectedMessage);
    }

    @Test
    public void findGroupMembers_validValues_success() throws ParseException {
        resetParser();
        //valid names for Bob, Amy, and Alice
        ArrayList<Name> membersToFind = new ArrayList<>();
        membersToFind.add(BOB.getName());
        membersToFind.add(AMY.getName());
        membersToFind.add(ALICE.getName());

        ArrayList<Person> members = new ArrayList<>();
        members.add(BOB);
        members.add(AMY);
        members.add(ALICE);
        assertEquals(parser.findGroupMembers(membersToFind), members);
    }

    @Test
    public void findGroupMembers_invalidValues_failure() throws ParseException {
        resetParser();
        //valid names and additional person not found in the model addressbook.
        ArrayList<Name> membersToFindExtra = new ArrayList<>();
        membersToFindExtra.add(BOB.getName());
        membersToFindExtra.add(AMY.getName());
        membersToFindExtra.add(ALICE.getName());
        membersToFindExtra.add(NONEXISTENTPERSON.getName());
        assertThrows(ParseException.class, MESSAGE_CREATEGROUPCOMMAND_ERROR, () ->
                emptyParser.findGroupMembers(membersToFindExtra));

        //Argument membersToFind contains members but CreateGroupCommandParser has empty toBeAddedToGroup List.
        //Throws ParseException.
        ArrayList<Name> membersToFind = new ArrayList<>();
        membersToFind.add(BOB.getName());
        membersToFind.add(AMY.getName());
        membersToFind.add(ALICE.getName());
        assertThrows(ParseException.class, MESSAGE_CREATEGROUPCOMMAND_ERROR, () ->
                emptyParser.findGroupMembers(membersToFind));

        //Argument membersToFind is empty list and CreateGroupCommandParser has empty toBeAddedToGroup List.
        //Throws EmptyGroupException.
        resetParser();
        ArrayList<Name> emptyMembersToFind = new ArrayList<>();
        String exceptionMessage = String.format(MESSAGE_CREATEGROUPCOMMAND_EMPTY_GROUP,
                MESSAGE_CREATEGROUPCOMMAND_INVALID_NAMES,
                MESSAGE_CREATEGROUPCOMMAND_USAGE);
        assertThrows(EmptyGroupException.class, exceptionMessage, () ->
                parser.findGroupMembers(emptyMembersToFind));
    }

    @Test
    public void addMemberIfExist_validValues_trueReturned() throws ParseException {
        resetParser();
        //Add valid Person name (Bob)
        assertTrue(parser.addMemberIfExist(BOB.getName()));

        //Add valid Person name (Alice)
        assertTrue(parser.addMemberIfExist(ALICE.getName()));

        //Add valid Person name (Amy)
        assertTrue(parser.addMemberIfExist(AMY.getName()));
    }

    @Test
    public void addMemberIfExist_invalidValues_parseExceptionThrown() throws ParseException {
        resetParser();
        //Add valid Person name (Bob)
        assertTrue(parser.addMemberIfExist(BOB.getName()));

        //Attempts to add a repeat Bob name will throw parseException
        assertThrows(ParseException.class, MESSAGE_CREATEGROUPCOMMAND_ERROR, () ->
                parser.addMemberIfExist(BOB.getName()));

        resetParser();
        //Add Person who does not exist
        assertThrows(ParseException.class, MESSAGE_CREATEGROUPCOMMAND_ERROR, () ->
                parser.addMemberIfExist(NONEXISTENTPERSON.getName()));
    }

    @Test
    public void findMember_validValues_personReturned() {
        ObservableList<Person> membersToSearch = new ModelBuilder().build().getAddressBook().getPersonList();

        //search for existing member
        Person bob = CreateGroupCommandParser.findMember(BOB.getName(), membersToSearch);
        assertEquals(bob, BOB);

        //search for existing member
        Person alice = CreateGroupCommandParser.findMember(ALICE.getName(), membersToSearch);
        assertEquals(alice, ALICE);

    }

    @Test
    public void findMember_invalidValues_nullReturned() {
        ObservableList<Person> membersToSearch = new ModelBuilder().build().getAddressBook().getPersonList();

        //search for non-existent member
        Person nonExistentPerson = CreateGroupCommandParser
                .findMember(NONEXISTENTPERSON.getName(), membersToSearch);
        assertEquals(nonExistentPerson, null);

    }

}
