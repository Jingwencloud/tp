package seedu.awe.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.awe.commons.core.GuiSettings;
import seedu.awe.commons.core.LogsCenter;
import seedu.awe.logic.commands.Command;
import seedu.awe.logic.commands.CommandResult;
import seedu.awe.logic.commands.exceptions.CommandException;
import seedu.awe.logic.parser.AweParser;
import seedu.awe.logic.parser.exceptions.ParseException;
import seedu.awe.model.Model;
import seedu.awe.model.ReadOnlyAddressBook;
import seedu.awe.model.expense.Expense;
import seedu.awe.model.group.Group;
import seedu.awe.model.payment.Payment;
import seedu.awe.model.person.Person;
import seedu.awe.model.transactionsummary.TransactionSummary;
import seedu.awe.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final AweParser aweParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        aweParser = new AweParser(model);
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = aweParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveAddressBook(model.getAddressBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ObservableList<Group> getFilteredGroupList() {
        return model.getFilteredGroupList();
    }

    @Override
    public ObservableList<Expense> getExpenses() {
        return model.getExpenses();
    }

    @Override
    public ObservableList<TransactionSummary> getTransactionSummary() {
        return model.getTransactionSummary();
    }

    @Override
    public ObservableList<Payment> getPayments() {
        return model.getPayments();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
