package seedu.awe.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.awe.commons.core.Messages.MESSAGE_LISTGROUPSCOMMAND_SUCCESS;
import static seedu.awe.model.Model.PREDICATE_SHOW_ALL_GROUPS;

import seedu.awe.model.Model;

/**
 * Lists all Groups in the awe book to the user.
 */
public class ListGroupsCommand extends Command {

    public static final String COMMAND_WORD = "groups";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
        return new CommandResult(MESSAGE_LISTGROUPSCOMMAND_SUCCESS, false, false,
                true, false, false,
                false, false);
    }
}
