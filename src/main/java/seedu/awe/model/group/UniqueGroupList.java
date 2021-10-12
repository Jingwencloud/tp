package seedu.awe.model.group;

import static java.util.Objects.requireNonNull;
import static seedu.awe.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.awe.model.group.exceptions.DuplicateGroupException;
import seedu.awe.model.group.exceptions.GroupNotFoundException;
import seedu.awe.model.person.Person;
import seedu.awe.model.person.exceptions.DuplicatePersonException;

/**
 * A list of groups that enforces uniqueness between its elements and does not allow nulls.
 * A group is considered unique by comparing using {@code Group#isSameGroup(Group)}. As such, adding and updating of
 * groups uses Group#isSameGroup(Group) for equality so as to ensure that the group being added or updated is
 * unique in terms of identity in the UniqueGroupList. However, the removal of a group uses Group#equals(Object) so
 * as to ensure that the group with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Group#isSameGroup(Group)
 */
public class UniqueGroupList implements Iterable<Group> {

    private final ObservableList<Group> internalList = FXCollections.observableArrayList();
    private final ObservableList<Group> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent group as the given argument.
     */
    public boolean contains(Group toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameGroup);
    }

    /**
     * Adds a group to the list.
     * The group must not already exist in the list.
     */
    public void add(Group toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateGroupException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the group {@code target} in the list with {@code editedGroup}.
     * {@code target} must exist in the list.
     * The group identity of {@code editedGroup} must not be the same as another existing group in the list.
     */
    public void setGroup(Group target, Group editedGroup) {
        requireAllNonNull(target, editedGroup);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new GroupNotFoundException();
        }

        if (!target.isSameGroup(editedGroup) && contains(editedGroup)) {
            throw new DuplicateGroupException();
        }

        internalList.set(index, editedGroup);
    }

    /**
     * Removes the equivalent group from the list.
     * The group must exist in the list.
     */
    public void remove(Group toRemove) {
        requireNonNull(toRemove);
        boolean isGroupFound = false;
        Group groupToDelete = null;
        for (int i = 0; i < internalList.size(); i++) {
            Group group = internalList.get(i);
            if (group.isSameGroup(toRemove)) {
                isGroupFound = true;
                groupToDelete = group;
                break;
            }
        }
        if (!isGroupFound) {
            throw new GroupNotFoundException();
        }
        internalList.remove(groupToDelete);
    }

    /**
     * Replaces the person {@code target} in groups with {@code editedPerson}.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void updatePerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        for (int i = 0; i < internalList.size(); i++) {
            Group group = internalList.get(i);
            Optional<Group> updatedPersonGroup = group.updatePerson(target, editedPerson);
            if (updatedPersonGroup.isPresent()) {
                internalList.set(i, updatedPersonGroup.get());
            }
        }
    }

    /**
     * Replace the contents of this list with a new list.
     *
     * @param replacement New contents to place in the original list.
     */
    public void setGroups(UniqueGroupList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code groups}.
     * {@code groups} must not contain duplicate groups.
     */
    public void setGroups(List<Group> groups) {
        requireAllNonNull(groups);
        if (!groupsAreUnique(groups)) {
            throw new DuplicatePersonException();
        }

        internalList.setAll(groups);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Group> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Group> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UniqueGroupList)) { // instanceof handles nulls
            return false;
        }

        return internalList.equals(((UniqueGroupList) other).internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code groups} contains only unique groups.
     */
    private boolean groupsAreUnique(List<Group> groups) {
        for (int i = 0; i < groups.size() - 1; i++) {
            for (int j = i + 1; j < groups.size(); j++) {
                if (groups.get(i).isSameGroup(groups.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Get group object from internal list by group name.
     * @param groupName name of group to find from list.
     * @return group object from internal list.
     */
    public Group getGroupByName(GroupName groupName) {
        requireNonNull(groupName);
        for (int i = 0; i < internalList.size(); i++) {
            Group group = internalList.get(i);
            if (group.getGroupName().equals(groupName)) {
                return group;
            }
        }
        throw new GroupNotFoundException();
    }
}
