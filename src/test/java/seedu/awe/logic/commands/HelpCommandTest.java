package seedu.awe.logic.commands;

import static seedu.awe.commons.core.Messages.MESSAGE_HELPCOMMAND_SHOWING_HELP;
import static seedu.awe.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.awe.model.Model;
import seedu.awe.model.ModelManager;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_HELPCOMMAND_SHOWING_HELP, true,
            false, false, false, false, false, false);
        assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
    }
}
