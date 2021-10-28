package seedu.awe.logic.commands;
import static java.util.Objects.requireNonNull;
import static seedu.awe.commons.core.Messages.MESSAGE_GROUPREMOVETAG_ERROR;
import static seedu.awe.commons.core.Messages.MESSAGE_GROUPREMOVETAG_NONEXISTENT_TAG;
import static seedu.awe.commons.core.Messages.MESSAGE_GROUPREMOVETAG_SUCCESS;
import static seedu.awe.commons.core.Messages.MESSAGE_NONEXISTENT_GROUP;
import static seedu.awe.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.awe.logic.commands.exceptions.CommandException;
import seedu.awe.model.Model;
import seedu.awe.model.group.Group;
import seedu.awe.model.group.GroupName;
import seedu.awe.model.tag.Tag;

public class GroupRemoveTagCommand extends Command {
    public static final String COMMAND_WORD = "groupremovetag";

    private final GroupName groupName;
    private final Set<Tag> tagsToBeRemoved;
    private final boolean isValidCommand;

    /**
     * Creates a GroupRemoveTagCommand to remove the specified {@code Tag}
     */
    public GroupRemoveTagCommand(GroupName groupName, Set<Tag> tagsToBeRemoved, boolean isValidCommand) {
        requireAllNonNull(groupName, tagsToBeRemoved, isValidCommand);
        this.groupName = groupName;
        this.tagsToBeRemoved = tagsToBeRemoved;
        this.isValidCommand = isValidCommand;
    }

    public GroupName getGroupName() {
        return groupName;
    }

    public Set<Tag> getTagsToBeRemoved() {
        return tagsToBeRemoved;
    }

    public boolean getValidCommand() {
        return isValidCommand;
    }

    /**
     * Returns int object tracking the number of non-matching tags between this.tagsToBeRemoved and otherTags.
     *
     * @param numberOfNonMatchingTags int value to track the number of tags in otherTags
     *                                   that are not present in this.tagsToBeRemoved.
     * @param tag Tag object that is being searched for in otherTags.
     * @param otherTags List of Tag objects from another instance of GroupRemoveTagCommand.
     * @return int object to track the number of tags in otherTags that are not present in this.tagsToBeRemoved.
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
     * Returns a boolean object representing if this.tagsToBeRemoved contains the same Tag objects as otherTags.
     *
     * @param otherTags List of Tag objects from another instance of GroupRemoveTagCommand.
     * @return boolean object representing if this.tagsToBeRemoved contains the same Tag objects as otherTags.
     */
    public boolean checkSameTags(Set<Tag> otherTags) {
        int numberOfNonMatchingTags = otherTags.size();
        for (Tag tag : this.tagsToBeRemoved) {
            checkForTag(numberOfNonMatchingTags, tag, otherTags);
        }
        return numberOfNonMatchingTags == 0;
    }

    /**
     * Checks if the set of tags stored in the group has the tags to be removed
     *
     * @param tagsFromOldGroup Set of Tag objects representing original tags in a group.
     * @param tagsToBeRemoved Set of Tag objects representing tags to be removed from a group.
     * @return whether the tagsToBeRemoved is contains in the original group
     */
    public Optional<Tag> checkRemoveTags(Set<Tag> tagsFromOldGroup, Set<Tag> tagsToBeRemoved) {
        Set<Tag> remainingTags = new HashSet<>(tagsFromOldGroup);
        for (Tag tagToBeRemoved : tagsToBeRemoved) {
            boolean tagToBeRemovedIsPresent = remainingTags.remove(tagToBeRemoved);
            if (!tagToBeRemovedIsPresent) {
                return Optional.of(tagToBeRemoved);
            }
        }
        return Optional.empty();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!isValidCommand) {
            throw new CommandException(MESSAGE_GROUPREMOVETAG_ERROR);
        }

        Group oldGroup = model.getGroupByName(groupName);
        if (Objects.isNull(oldGroup)) {
            throw new CommandException(String.format(MESSAGE_NONEXISTENT_GROUP, groupName));
        }
        Set<Tag> tagsFromOldGroup = oldGroup.getTags();
        Optional<Tag> tagNotInTheGroup = checkRemoveTags(tagsFromOldGroup, tagsToBeRemoved);
        if (tagNotInTheGroup.isPresent()) {
            throw new CommandException(
                    String.format(MESSAGE_GROUPREMOVETAG_NONEXISTENT_TAG, tagNotInTheGroup.get().getTagName())
            );
        }

        Group newGroup = oldGroup;
        for (Tag tag: tagsToBeRemoved) {
            newGroup = newGroup.removeTag(tag);
        }

        model.setGroup(oldGroup, newGroup);
        return new CommandResult(MESSAGE_GROUPREMOVETAG_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof GroupRemoveTagCommand)) {
            return false;
        }
        GroupRemoveTagCommand otherCommand = (GroupRemoveTagCommand) other;
        if (this.isValidCommand == otherCommand.getValidCommand()
                && checkSameTags(otherCommand.getTagsToBeRemoved())
                && this.groupName.equals(otherCommand.getGroupName())) {
            return true;
        }
        return false;
    }
}

