package seedu.awe.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.awe.commons.core.Messages.MESSAGE_HELPCOMMAND_USAGE;
import static seedu.awe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.awe.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.awe.testutil.Assert.assertThrows;
import static seedu.awe.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.awe.logic.commands.AddContactCommand;
import seedu.awe.logic.commands.ClearCommand;
import seedu.awe.logic.commands.DeleteContactCommand;
import seedu.awe.logic.commands.EditContactCommand;
import seedu.awe.logic.commands.EditContactCommand.EditPersonDescriptor;
import seedu.awe.logic.commands.FindContactsCommand;
import seedu.awe.logic.commands.FindGroupsCommand;
import seedu.awe.logic.commands.HelpCommand;
import seedu.awe.logic.commands.ListContactsCommand;
import seedu.awe.logic.commands.ListGroupsCommand;
import seedu.awe.logic.parser.exceptions.ParseException;
import seedu.awe.model.group.GroupContainsKeywordsPredicate;
import seedu.awe.model.person.NameContainsKeywordsPredicate;
import seedu.awe.model.person.Person;
import seedu.awe.testutil.EditPersonDescriptorBuilder;
import seedu.awe.testutil.PersonBuilder;
import seedu.awe.testutil.PersonUtil;

public class AweParserTest {

    private final AweParser parser = new AweParser();

    @Test
    public void parseCommand_addContact() throws Exception {
        Person person = new PersonBuilder().build();
        AddContactCommand command = (AddContactCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddContactCommand(person), command);
    }

    @Test
    public void parseCommand_deleteContact() throws Exception {
        DeleteContactCommand command = (DeleteContactCommand) parser.parseCommand(
                DeleteContactCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteContactCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_editContact() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditContactCommand command = (EditContactCommand) parser.parseCommand(EditContactCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditContactCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_findContact() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindContactsCommand command = (FindContactsCommand) parser.parseCommand(
                FindContactsCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindContactsCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_contacts() throws Exception {
        assertTrue(parser.parseCommand(ListContactsCommand.COMMAND_WORD) instanceof ListContactsCommand);
        assertTrue(parser.parseCommand(ListContactsCommand.COMMAND_WORD + " 3") instanceof ListContactsCommand);
    }

    @Test
    public void parseCommand_findGroups() throws Exception {
        List<String> keywords = Arrays.asList("London", "Japan", "Taiwan");
        FindGroupsCommand command = (FindGroupsCommand) parser.parseCommand(
                FindGroupsCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindGroupsCommand(new GroupContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_groups() throws Exception {
        assertTrue(parser.parseCommand(ListGroupsCommand.COMMAND_WORD) instanceof ListGroupsCommand);
        assertTrue(parser.parseCommand(ListGroupsCommand.COMMAND_WORD + " 3") instanceof ListGroupsCommand);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_HELPCOMMAND_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
