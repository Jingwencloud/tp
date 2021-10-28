package seedu.awe.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.awe.commons.core.Messages.MESSAGE_DELETEGROUPCOMMAND_GROUP_DOES_NOT_EXIST;
import static seedu.awe.commons.core.Messages.MESSAGE_DELETEGROUPCOMMAND_SUCCESS;
import static seedu.awe.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.awe.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.awe.testutil.TypicalGroups.BALI;
import static seedu.awe.testutil.TypicalGroups.OSLO;
import static seedu.awe.testutil.TypicalGroups.VIENNA_NOT_IN_GROUPS;

import org.junit.jupiter.api.Test;

import seedu.awe.model.Model;
import seedu.awe.model.group.Group;
import seedu.awe.testutil.ModelBuilder;

public class DeleteGroupCommandTest {

    private Model model = new ModelBuilder().build();

    public void resetModel() {
        model = new ModelBuilder().build();
    }

    @Test
    public void execute_validGroupCommand_success() {
        // Group is within model
        Group groupToDelete = BALI;
        DeleteGroupCommand deleteGroupCommand = new DeleteGroupCommand(groupToDelete);

        CommandResult commandResult = new CommandResult(String.format(MESSAGE_DELETEGROUPCOMMAND_SUCCESS,
                groupToDelete.getGroupName(), groupToDelete.getMembers().size()), false, false,
                true, false, false, false, false);

        assertCommandSuccess(deleteGroupCommand, model, commandResult, model);
    }

    @Test
    public void execute_invalidGroup_throwsCommandException() {
        // Group not in model
        Group groupNotInModel = VIENNA_NOT_IN_GROUPS;
        DeleteGroupCommand deleteGroupCommand = new DeleteGroupCommand(groupNotInModel);

        assertCommandFailure(deleteGroupCommand, model, MESSAGE_DELETEGROUPCOMMAND_GROUP_DOES_NOT_EXIST);
    }

    @Test
    public void equals() {
        DeleteGroupCommand deleteFirstGroupCommand = new DeleteGroupCommand(BALI);
        DeleteGroupCommand deleteSecondGroupCommand = new DeleteGroupCommand(OSLO);

        // same object -> returns true
        assertTrue(deleteFirstGroupCommand.equals(deleteFirstGroupCommand));

        // same values -> returns true
        DeleteGroupCommand deleteFirstGroupCommandCopy = new DeleteGroupCommand(BALI);
        assertTrue(deleteFirstGroupCommand.equals(deleteFirstGroupCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstGroupCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstGroupCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstGroupCommand.equals(deleteSecondGroupCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no group.
     */
    private void showNoGroup(Model model) {
        model.updateFilteredGroupList(g -> false);

        assertTrue(model.getFilteredGroupList().isEmpty());
    }
}
