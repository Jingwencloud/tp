package seedu.awe.logic.commands;

import static seedu.awe.commons.core.Messages.MESSAGE_ADDCONTACTCOMMAND_DUPLICATE;
import static seedu.awe.commons.core.Messages.MESSAGE_ADDCONTACTCOMMAND_SUCCESS;
import static seedu.awe.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.awe.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.awe.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.awe.model.Model;
import seedu.awe.model.ModelManager;
import seedu.awe.model.UserPrefs;
import seedu.awe.model.person.Person;
import seedu.awe.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddContactCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Person validPerson = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPerson(validPerson);

        assertCommandSuccess(new AddContactCommand(validPerson), model,
                String.format(MESSAGE_ADDCONTACTCOMMAND_SUCCESS, validPerson), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = model.getAddressBook().getPersonList().get(0);
        assertCommandFailure(new AddContactCommand(personInList), model, MESSAGE_ADDCONTACTCOMMAND_DUPLICATE);
    }

}
