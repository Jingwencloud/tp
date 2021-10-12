package seedu.awe.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.awe.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;

import seedu.awe.logic.commands.exceptions.CommandException;
import seedu.awe.model.Model;
import seedu.awe.model.group.Group;
import seedu.awe.model.group.GroupName;
import seedu.awe.model.person.Person;

public class GroupRemovePersonCommand extends Command {
    public static final String COMMAND_WORD = "groupremoveperson";
    public static final String MESSAGE_SUCCESS = "Member(s) removed from group";
    public static final String MESSAGE_ERROR = "Person(s) not removed from group."
                                                + "Be sure to use the exact names of group members";
    public static final String MESSAGE_USAGE = "groupremoveperson gn/[GROUPNAME] n/[NAME1] n/[OPTIONAL NAME2]";

    private final GroupName groupName;
    private final ArrayList<Person> membersToBeRemoved;
    private final boolean isValidCommand;

    /**
     * Creates a GroupRemovePersonCommand to add the specified {@code Person}
     */
    public GroupRemovePersonCommand(GroupName groupName, ArrayList<Person> membersToBeRemoved, boolean isValidCommand) {
        requireAllNonNull(groupName, membersToBeRemoved, isValidCommand);
        this.groupName = groupName;
        this.membersToBeRemoved = membersToBeRemoved;
        this.isValidCommand = isValidCommand;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!isValidCommand) {
            throw new CommandException(MESSAGE_ERROR);
        }

        Group oldGroup = model.getGroupByName(groupName);
        ArrayList<Person> membersFromOldGroup = oldGroup.getMembers();
        ArrayList<Person> remainingMembers = removeMembers(membersFromOldGroup, membersToBeRemoved);
        Group newGroup = new Group(groupName, remainingMembers, oldGroup.getTags());
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
        for (Person member : this.membersToBeRemoved) {
            numberOfNonMatchingMembers = checkForMember(numberOfNonMatchingMembers, member, otherMembers);
        }
        return numberOfNonMatchingMembers == 0;
    }

    /**
     * Returns List of Person objects representing the remaining members after removing a given list of members.
     *
     * @param membersFromOldGroup List of Person objects representing original members in a group.
     * @param membersToBeRemoved List of Person objects representing members to be removed from a group.
     * @return List of Person objects representing the remaining members after removing a given list of members
     * @throws CommandException If member to be removed is not present in the List of original members.
     */
    public ArrayList<Person> removeMembers(ArrayList<Person> membersFromOldGroup,
                                           ArrayList<Person> membersToBeRemoved) throws CommandException {
        ArrayList<Person> remainingMembers = new ArrayList<>(membersFromOldGroup);
        for (Person memberToBeRemoved : membersToBeRemoved) {
            boolean memberToBeRemovedIsPresent = remainingMembers.remove(memberToBeRemoved);
            if (!memberToBeRemovedIsPresent) {
                throw new CommandException(MESSAGE_ERROR);
            }
        }
        return remainingMembers;
    }
}
