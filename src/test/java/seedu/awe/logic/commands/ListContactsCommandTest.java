package seedu.awe.logic.commands;

import static seedu.awe.commons.core.Messages.MESSAGE_LISTCONTACTSCOMMAND_SUCCESS;
import static seedu.awe.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.awe.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.awe.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.awe.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.awe.model.Model;
import seedu.awe.model.ModelManager;
import seedu.awe.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListContactCommand.
 */
public class ListContactsCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListContactsCommand(), model, MESSAGE_LISTCONTACTSCOMMAND_SUCCESS,
                expectedModel, false, true, false, false, false);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListContactsCommand(), model, MESSAGE_LISTCONTACTSCOMMAND_SUCCESS,
                expectedModel, false, true, false, false, false);
    }

    @Test
    public void execute_showContacts_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_LISTCONTACTSCOMMAND_SUCCESS,
                false, false, false, true, false, false, false);
        assertCommandSuccess(new ListContactsCommand(), new ModelManager(), expectedCommandResult, new ModelManager());
    }
}
