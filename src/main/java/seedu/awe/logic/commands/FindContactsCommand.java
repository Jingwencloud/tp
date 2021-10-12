package seedu.awe.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.awe.commons.core.Messages;
import seedu.awe.model.Model;
import seedu.awe.model.person.NameContainsKeywordsPredicate;

/**
 * Finds and lists all persons in awe book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindContactsCommand extends Command {

    public static final String COMMAND_WORD = "findcontacts";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final NameContainsKeywordsPredicate predicate;

    public FindContactsCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()),
                false, false, false, true, false);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FindContactsCommand)) { // instanceof handles nulls
            return false;
        }

        return predicate.equals(((FindContactsCommand) other).predicate); // state check
    }
}
