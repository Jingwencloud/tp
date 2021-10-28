package seedu.awe.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.awe.commons.core.Messages.MESSAGE_ADDCONTACTCOMMAND_DUPLICATE;
import static seedu.awe.commons.core.Messages.MESSAGE_ADDCONTACTCOMMAND_SUCCESS;

import seedu.awe.logic.commands.exceptions.CommandException;
import seedu.awe.model.Model;
import seedu.awe.model.person.Person;

/**
 * Adds a person to the awe book.
 */
public class AddContactCommand extends Command {

    public static final String COMMAND_WORD = "addcontact";

    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddContactCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_ADDCONTACTCOMMAND_DUPLICATE);
        }

        model.addPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_ADDCONTACTCOMMAND_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddContactCommand)) { // instanceof handles nulls
            return false;
        }

        return toAdd.equals(((AddContactCommand) other).toAdd);
    }
}
