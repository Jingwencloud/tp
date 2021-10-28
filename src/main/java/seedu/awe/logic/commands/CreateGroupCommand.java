package seedu.awe.logic.commands;
import static java.util.Objects.requireNonNull;
import static seedu.awe.commons.core.Messages.MESSAGE_CREATEGROUPCOMMAND_DUPLICATE_GROUP;
import static seedu.awe.commons.core.Messages.MESSAGE_CREATEGROUPCOMMAND_ERROR;
import static seedu.awe.commons.core.Messages.MESSAGE_CREATEGROUPCOMMAND_SUCCESS;
import static seedu.awe.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.awe.model.Model.PREDICATE_SHOW_ALL_GROUPS;

import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Logger;

import seedu.awe.commons.core.LogsCenter;
import seedu.awe.logic.commands.exceptions.CommandException;
import seedu.awe.model.Model;
import seedu.awe.model.ModelManager;
import seedu.awe.model.group.Group;
import seedu.awe.model.group.GroupName;
import seedu.awe.model.person.Person;
import seedu.awe.model.tag.Tag;

public class CreateGroupCommand extends Command {
    public static final String COMMAND_WORD = "creategroup";

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private final ArrayList<Person> members;
    private final GroupName groupName;
    private final boolean isValidCommand;
    private final Set<Tag> tags;

    /**
     * Creates a CreateGroupCommand to create the specified {@code Group}
     */
    public CreateGroupCommand(GroupName groupName, ArrayList<Person> members, boolean isValidCommand, Set<Tag> tags) {
        requireAllNonNull(groupName, members, isValidCommand, tags);
        this.groupName = groupName;
        this.members = members;
        this.isValidCommand = isValidCommand;
        this.tags = tags;
    }

    public ArrayList<Person> getMembers() {
        return members;
    }

    public GroupName getGroupName() {
        return groupName;
    }

    public boolean getValidCommand() {
        return isValidCommand;
    }

    /**
     * Returns int object tracking the number of non matching members between this.members and otherMembers.
     *
     * @param numberOfNonMatchingMembers int value to track the number of members in otherMembers
     *                                   that are not present in this.members.
     * @param member Person object that is being searched for in otherMembers.
     * @param otherMembers List of Person objects from another instance of CreateGroupCommand.
     * @return int object to track the number of members in otherMembers that are not present in this.members.
     */
    public int checkForMember(int numberOfNonMatchingMembers, Person member, ArrayList<Person> otherMembers) {
        for (Person otherMember : otherMembers) {
            if (member.equals(otherMember)) {
                numberOfNonMatchingMembers--;
                break;
            }
        }
        return numberOfNonMatchingMembers;
    }

    /**
     * Returns a boolean object representing if this.members contains the same Person objects as otherMembers.
     *
     * @param otherMembers List of Person objects from another instance of CreateGroupCommand.
     * @return boolean object representing if this.members contains the same Person objects as otherMembers.
     */
    public boolean checkSameMembers(ArrayList<Person> otherMembers) {
        int numberOfNonMatchingMembers = otherMembers.size();
        for (Person member : this.members) {
            numberOfNonMatchingMembers = checkForMember(numberOfNonMatchingMembers, member, otherMembers);
        }
        return numberOfNonMatchingMembers == 0;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!isValidCommand) {
            return new CommandResult(MESSAGE_CREATEGROUPCOMMAND_ERROR);
        }

        Group group = new Group(groupName, members, tags);
        if (model.hasGroup(group)) {
            throw new CommandException(MESSAGE_CREATEGROUPCOMMAND_DUPLICATE_GROUP);
        }
        model.addGroup(group);
        model.setAllMembersOfGroup(group);
        logger.fine("Created group \"" + group.getGroupName() + "\"");
        model.updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
        return new CommandResult(MESSAGE_CREATEGROUPCOMMAND_SUCCESS, false, false, true, false, false, false, false);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof CreateGroupCommand)) {
            return false;
        }
        CreateGroupCommand otherCommand = (CreateGroupCommand) other;
        if (this.isValidCommand == (otherCommand.getValidCommand())
                && checkSameMembers(otherCommand.getMembers())
                && this.groupName.equals(otherCommand.getGroupName())) {
            return true;
        }
        return false;
    }
}
