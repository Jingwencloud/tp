package seedu.awe.model.group;


import java.util.List;
import java.util.function.Predicate;

import seedu.awe.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class GroupContainsKeywordsPredicate implements Predicate<Group> {
    private final List<String> keywords;

    public GroupContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Group group) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(group.getGroupName().getName(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof GroupContainsKeywordsPredicate)) { // instanceof handles nulls
            return false;
        }

        return keywords.equals(((GroupContainsKeywordsPredicate) other).keywords); // state check
    }

}
