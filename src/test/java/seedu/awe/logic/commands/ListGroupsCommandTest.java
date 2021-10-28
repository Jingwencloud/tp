package seedu.awe.logic.commands;

import static seedu.awe.commons.core.Messages.MESSAGE_LISTGROUPSCOMMAND_SUCCESS;
import static seedu.awe.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.awe.model.Model;
import seedu.awe.model.ModelManager;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListGroupsCommand.
 */
public class ListGroupsCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_showGroup_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_LISTGROUPSCOMMAND_SUCCESS,
                false, false, true, false,
                false, false, false);
        assertCommandSuccess(new ListGroupsCommand(), model, expectedCommandResult, expectedModel);
    }
}
