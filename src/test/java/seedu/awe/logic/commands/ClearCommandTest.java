package seedu.awe.logic.commands;

import static seedu.awe.commons.core.Messages.MESSAGE_CLEARCOMMAND_SUCCESS;
import static seedu.awe.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.awe.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.awe.model.AddressBook;
import seedu.awe.model.Model;
import seedu.awe.model.ModelManager;
import seedu.awe.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, MESSAGE_CLEARCOMMAND_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());

        assertCommandSuccess(new ClearCommand(), model, MESSAGE_CLEARCOMMAND_SUCCESS, expectedModel);
    }

}
