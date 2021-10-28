package seedu.awe.logic.commands;

import static seedu.awe.commons.core.Messages.MESSAGE_HELPCOMMAND_SHOWING_HELP;

import seedu.awe.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_HELPCOMMAND_SHOWING_HELP, true, false, false,
                false, false, false,
                false);
    }
}
