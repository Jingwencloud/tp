package seedu.awe.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.awe.commons.core.Messages.MESSAGE_LISTCONTACTSCOMMAND_SUCCESS;
import static seedu.awe.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.awe.model.Model;

/**
 * Lists all persons in the awe book to the user.
 */
public class ListContactsCommand extends Command {

    public static final String COMMAND_WORD = "contacts";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_LISTCONTACTSCOMMAND_SUCCESS, false, false,
                false, true, false,
                false, false);
    }
}
