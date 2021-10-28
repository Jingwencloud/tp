package seedu.awe.logic.commands;

import static seedu.awe.commons.core.Messages.MESSAGE_EXITCOMMAND_ACKNOWLEDGEMENT;
import static seedu.awe.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.awe.model.Model;
import seedu.awe.model.ModelManager;

public class ExitCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_exit_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_EXITCOMMAND_ACKNOWLEDGEMENT,
                false, true, false, false,
                false, false, false);
        assertCommandSuccess(new ExitCommand(), model, expectedCommandResult, expectedModel);
    }
}
