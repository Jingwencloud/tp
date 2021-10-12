package seedu.awe.logic.commands;
import static java.util.Objects.requireNonNull;
import static seedu.awe.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;

import seedu.awe.logic.commands.exceptions.CommandException;
import seedu.awe.model.Model;
import seedu.awe.model.group.Group;
import seedu.awe.model.group.GroupName;
import seedu.awe.model.person.Person;

public class GroupAddPersonCommand extends Command {
    public static final String COMMAND_WORD = "groupaddperson";
    public static final String MESSAGE_SUCCESS = "New member(s) added to group";
    public static final String MESSAGE_ERROR = "Person(s) not added. Be sure to use the exact names of group members";
    public static final String MESSAGE_DUPLICATE_PERSON = "%1$s is already in the group";
    public static final String MESSAGE_USAGE = "groupaddperson gn/[GROUPNAME] n/[NAME1] n/[OPTIONAL NAME2]";

    private final GroupName groupName;
    private final ArrayList<Person> newMembers;
    private final boolean isValidCommand;

    /**
     * Creates a GroupAddPersonCommand to add the specified {@code Person}
     */
    public GroupAddPersonCommand(GroupName groupName, ArrayList<Person> newMembers, boolean isValidCommand) {
        requireAllNonNull(groupName, newMembers, isValidCommand);
        this.groupName = groupName;
        this.newMembers = newMembers;
        this.isValidCommand = isValidCommand;
    }

    public GroupName getGroupName() {
        return groupName;
    }

    public ArrayList<Person> getNewMembers() {
        return newMembers;
    }

    public boolean getValidCommand() {
        return isValidCommand;
    }

    /**
     * Returns int object tracking the number of non-matching members between this.newMembers and otherMembers.
     *
     * @param numberOfNonMatchingMembers int value to track the number of members in otherMembers
     *                                   that are not present in this.members.
     * @param member Person object that is being searched for in otherMembers.
     * @param otherMembers List of Person objects from another instance of CreateGroupCommand.
     * @return int object to track the number of members in otherMembers that are not present in this.newMembers.
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
     * Returns a boolean object representing if this.newMembers contains the same Person objects as otherMembers.
     *
     * @param otherMembers List of Person objects from another instance of CreateGroupCommand.
     * @return boolean object representing if this.newMembers contains the same Person objects as otherMembers.
     */
    public boolean checkSameMembers(ArrayList<Person> otherMembers) {
        int numberOfNonMatchingMembers = otherMembers.size();
        for (Person member : this.newMembers) {
            numberOfNonMatchingMembers = checkForMember(numberOfNonMatchingMembers, member, otherMembers);
        }
        return numberOfNonMatchingMembers == 0;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!isValidCommand) {
            throw new CommandException(MESSAGE_ERROR);
        }

        Group oldGroup = model.getGroupByName(groupName);
        ArrayList<Person> membersFromOldGroup = oldGroup.getMembers();
        for (Person member : newMembers) {
            if (membersFromOldGroup.contains(member)) {
                throw new CommandException(String.format(MESSAGE_DUPLICATE_PERSON, member.getName()));
            }
        }
        newMembers.addAll(membersFromOldGroup);
        Group newGroup = new Group(groupName, newMembers, oldGroup.getTags());
        model.deleteGroup(oldGroup);
        model.addGroup(newGroup);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof GroupAddPersonCommand)) {
            return false;
        }
        GroupAddPersonCommand otherCommand = (GroupAddPersonCommand) other;
        if (this.isValidCommand == (otherCommand.getValidCommand())
                && checkSameMembers(otherCommand.getNewMembers())
                && this.groupName.equals(otherCommand.getGroupName())) {
            return true;
        }
        return false;
    }
}
