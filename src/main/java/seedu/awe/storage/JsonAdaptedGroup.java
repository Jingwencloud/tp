package seedu.awe.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.awe.commons.exceptions.IllegalValueException;
import seedu.awe.model.expense.Expense;
import seedu.awe.model.group.Group;
import seedu.awe.model.group.GroupName;
import seedu.awe.model.person.Person;
import seedu.awe.model.tag.Tag;

public class JsonAdaptedGroup {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Group's %s field is missing!";

    private final String groupName;
    private final List<JsonAdaptedPerson> members = new ArrayList<>();
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final List<JsonAdaptedExpense> expenses = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedGroup} with the given group details.
     */
    @JsonCreator
    public JsonAdaptedGroup(@JsonProperty("name") String groupName,
                            @JsonProperty("members") List<JsonAdaptedPerson> members,
                            @JsonProperty("tags") List<JsonAdaptedTag> tags,
                            @JsonProperty("expenses") List<JsonAdaptedExpense> expenses) {
        this.groupName = groupName;
        if (members != null) {
            this.members.addAll(members);
        }
        if (tags != null) {
            this.tags.addAll(tags);
        }
        if (expenses != null) {
            this.expenses.addAll(expenses);
        }
    }

    /**
     * Converts a given {@code Group} into this class for Jackson use.
     */
    public JsonAdaptedGroup(Group source) {
        groupName = source.getGroupName().getName();
        members.addAll(source.getMembers().stream()
                .map(JsonAdaptedPerson::new)
                .collect(Collectors.toList()));
        tags.addAll(source.getTags()
                .stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        expenses.addAll(source.getExpenses()
                .stream()
                .map(JsonAdaptedExpense::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Group} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Group toModelType() throws IllegalValueException {
        final ArrayList<Person> modelMembers = new ArrayList<>();
        for (JsonAdaptedPerson member : members) {
            modelMembers.add(member.toModelType());
        }
        final List<Tag> groupTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            groupTags.add(tag.toModelType());
        }

        final ArrayList<Expense> modelExpenses = new ArrayList<>();

        modelExpenses.addAll(StorageUtil.convertAdaptedExpensesToExpenses(expenses));

        if (groupName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    GroupName.class.getSimpleName()));
        }
        if (!GroupName.isValidGroupName(groupName)) {
            throw new IllegalValueException(GroupName.MESSAGE_CONSTRAINTS);
        }
        final GroupName modelName = new GroupName(groupName);

        final Set<Tag> modelTags = new HashSet<>(groupTags);

        Group group = new Group(modelName, modelMembers, modelTags, modelExpenses);

        return group;
    }

}
