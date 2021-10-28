package seedu.awe.logic.commands;
import static java.util.Objects.requireNonNull;
import static seedu.awe.commons.core.Messages.MESSAGE_GROUPADDTAGCOMMAND_DUPLICATE_TAG;
import static seedu.awe.commons.core.Messages.MESSAGE_GROUPADDTAGCOMMAND_SUCCESS;
import static seedu.awe.commons.core.Messages.MESSAGE_NONEXISTENT_GROUP;
import static seedu.awe.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.Set;

import seedu.awe.logic.commands.exceptions.CommandException;
import seedu.awe.model.Model;
import seedu.awe.model.group.Group;
import seedu.awe.model.group.GroupName;
import seedu.awe.model.tag.Tag;

public class GroupAddTagCommand extends Command {
    public static final String COMMAND_WORD = "groupaddtag";

    private final GroupName groupName;
    private final Set<Tag> newTags;
    private final boolean isValidCommand;

    /**
     * Creates a GroupAddTagCommand to add the specified {@code Tag}
     */
    public GroupAddTagCommand(GroupName groupName, Set<Tag> newTags, boolean isValidCommand) {
        requireAllNonNull(groupName, newTags, isValidCommand);
        this.groupName = groupName;
        this.newTags = newTags;
        this.isValidCommand = isValidCommand;
    }

    public GroupName getGroupName() {
        return groupName;
    }

    public Set<Tag> getNewTags() {
        return newTags;
    }

    public boolean getValidCommand() {
        return isValidCommand;
    }

    /**
     * Returns int object tracking the number of non-matching tags between this.newTags and otherTags.
     *
     * @param numberOfNonMatchingTags int value to track the number of tags in otherTags
     *                                   that are not present in this.newTags.
     * @param tag Tag object that is being searched for in otherTags.
     * @param otherTags List of Tag objects from another instance of GroupAddTagCommand.
     * @return int object to track the number of tags in otherTags that are not present in this.newTags.
     */
    public int checkForTag(int numberOfNonMatchingTags, Tag tag, Set<Tag> otherTags) {
        for (Tag otherTag : otherTags) {
            if (tag.equals(otherTag)) {
                numberOfNonMatchingTags--;
                break;
            }
        }
        return numberOfNonMatchingTags;
    }

    /**
     * Returns a boolean object representing if this.newTags contains the same Tag objects as otherTags.
     *
     * @param otherTags List of Tag objects from another instance of GroupAddTagCommand.
     * @return boolean object representing if this.newTags contains the same Tag objects as otherTags.
     */
    public boolean checkSameTags(Set<Tag> otherTags) {
        int numberOfNonMatchingTags = otherTags.size();
        for (Tag tag : this.newTags) {
            checkForTag(numberOfNonMatchingTags, tag, otherTags);
        }
        return numberOfNonMatchingTags == 0;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!isValidCommand) {
            throw new CommandException(Tag.MESSAGE_CONSTRAINTS);
        }

        Group oldGroup = model.getGroupByName(groupName);
        if (Objects.isNull(oldGroup)) {
            throw new CommandException(String.format(MESSAGE_NONEXISTENT_GROUP, groupName));
        }

        Set<Tag> tagsFromOldGroup = oldGroup.getTags();
        for (Tag tag : newTags) {
            if (tagsFromOldGroup.contains(tag)) {
                throw new CommandException(String.format(MESSAGE_GROUPADDTAGCOMMAND_DUPLICATE_TAG, tag.getTagName()));
            }
        }
        Group newGroup = oldGroup.addTag(newTags);
        model.setGroup(oldGroup, newGroup);
        return new CommandResult(MESSAGE_GROUPADDTAGCOMMAND_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof GroupAddTagCommand)) {
            return false;
        }
        GroupAddTagCommand otherCommand = (GroupAddTagCommand) other;
        if (this.isValidCommand == otherCommand.getValidCommand()
                && checkSameTags(otherCommand.getNewTags())
                && this.groupName.equals(otherCommand.getGroupName())) {
            return true;
        }
        return false;
    }
}

