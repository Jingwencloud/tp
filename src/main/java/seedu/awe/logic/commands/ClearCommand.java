package seedu.awe.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.awe.commons.core.Messages.MESSAGE_CLEARCOMMAND_SUCCESS;

import seedu.awe.model.AddressBook;
import seedu.awe.model.Model;

/**
 * Clears the awe book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAddressBook(new AddressBook());
        return new CommandResult(MESSAGE_CLEARCOMMAND_SUCCESS);
    }
}
