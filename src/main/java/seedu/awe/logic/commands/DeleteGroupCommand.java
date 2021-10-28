package seedu.awe.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.awe.commons.core.Messages.MESSAGE_DELETEGROUPCOMMAND_GROUP_DOES_NOT_EXIST;
import static seedu.awe.commons.core.Messages.MESSAGE_DELETEGROUPCOMMAND_SUCCESS;
import static seedu.awe.model.Model.PREDICATE_SHOW_ALL_GROUPS;

import seedu.awe.logic.commands.exceptions.CommandException;
import seedu.awe.model.Model;
import seedu.awe.model.group.Group;

public class DeleteGroupCommand extends Command {
    public static final String COMMAND_WORD = "deletegroup";


    private Group group;

    /**
     * Delete group command, delete based on name
     *
     * @param group group object to be deleted
     */
    public DeleteGroupCommand(Group group) {
        this.group = group;
    }

    /**
     * Get group field
     *
     * @return Group object
     */
    public Group getGroup() {
        return group;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasGroup(group)) {
            throw new CommandException(MESSAGE_DELETEGROUPCOMMAND_GROUP_DOES_NOT_EXIST);
        }

        Group groupFromInternalList = model.getGroupByName(group.getGroupName());
        model.deleteGroup(groupFromInternalList);
        model.setAllMembersOfGroup(groupFromInternalList);
        int numberOfMembers = groupFromInternalList.getMembers().size();
        String groupName = groupFromInternalList.getGroupName().getName();
        model.updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
        return new CommandResult(String.format(MESSAGE_DELETEGROUPCOMMAND_SUCCESS, groupName, numberOfMembers),
                false, false, true, false, false, false, false);
    }

    @Override
    public boolean equals (Object otherCommand) {
        if (this == otherCommand) {
            return true;
        }
        if (!(otherCommand instanceof DeleteGroupCommand)) {
            return false;
        }
        DeleteGroupCommand otherDeleteGroupCommand = (DeleteGroupCommand) otherCommand;
        return this.getGroup().equals(otherDeleteGroupCommand.getGroup());
    }
}
