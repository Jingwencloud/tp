package seedu.awe.logic.commands;

import static seedu.awe.commons.core.Messages.MESSAGE_EXITCOMMAND_ACKNOWLEDGEMENT;

import seedu.awe.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_EXITCOMMAND_ACKNOWLEDGEMENT, false, true,
                false, false, false,
                false, false);
    }

}
