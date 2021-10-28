package seedu.awe.logic.parser;

import static seedu.awe.commons.core.Messages.MESSAGE_HELPCOMMAND_USAGE;
import static seedu.awe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.awe.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.awe.logic.commands.AddContactCommand;
import seedu.awe.logic.commands.AddExpenseCommand;
import seedu.awe.logic.commands.CalculatePaymentsCommand;
import seedu.awe.logic.commands.ClearCommand;
import seedu.awe.logic.commands.Command;
import seedu.awe.logic.commands.CreateGroupCommand;
import seedu.awe.logic.commands.DeleteContactCommand;
import seedu.awe.logic.commands.DeleteExpenseCommand;
import seedu.awe.logic.commands.DeleteGroupCommand;
import seedu.awe.logic.commands.EditContactCommand;
import seedu.awe.logic.commands.ExitCommand;
import seedu.awe.logic.commands.FindContactsCommand;
import seedu.awe.logic.commands.FindExpensesCommand;
import seedu.awe.logic.commands.FindGroupsCommand;
import seedu.awe.logic.commands.GroupAddContactCommand;
import seedu.awe.logic.commands.GroupAddTagCommand;
import seedu.awe.logic.commands.GroupEditNameCommand;
import seedu.awe.logic.commands.GroupRemoveContactCommand;
import seedu.awe.logic.commands.GroupRemoveTagCommand;
import seedu.awe.logic.commands.HelpCommand;
import seedu.awe.logic.commands.ListContactsCommand;
import seedu.awe.logic.commands.ListExpensesCommand;
import seedu.awe.logic.commands.ListGroupsCommand;
import seedu.awe.logic.commands.ListTransactionSummaryCommand;
import seedu.awe.logic.parser.exceptions.ParseException;
import seedu.awe.model.Model;

/**
 * Parses user input.
 */
public class AweParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private Model model;

    public AweParser() {}
    public AweParser(Model model) {
        this.model = model;
    }
    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_HELPCOMMAND_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddContactCommand.COMMAND_WORD:
            return new AddContactCommandParser().parse(arguments);

        case EditContactCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteContactCommand.COMMAND_WORD:
            return new DeleteContactCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindContactsCommand.COMMAND_WORD:
            return new FindContactsCommandParser().parse(arguments);

        case FindGroupsCommand.COMMAND_WORD:
            return new FindGroupsCommandParser().parse(arguments);

        case FindExpensesCommand.COMMAND_WORD:
            return new FindExpensesCommandParser().parse(arguments);

        case ListContactsCommand.COMMAND_WORD:
            return new ListContactsCommand();

        case ListGroupsCommand.COMMAND_WORD:
            return new ListGroupsCommand();

        case ListExpensesCommand.COMMAND_WORD:
            return new ListExpensesCommandParser().parse(arguments);

        case DeleteExpenseCommand.COMMAND_WORD:
            return new DeleteExpenseCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case CreateGroupCommand.COMMAND_WORD:
            return new CreateGroupCommandParser(model).parse(arguments);

        case DeleteGroupCommand.COMMAND_WORD:
            return new DeleteGroupCommandParser(model).parse(arguments);

        case AddExpenseCommand.COMMAND_WORD:
            return new AddExpenseCommandParser(model).parse(arguments);

        case GroupAddContactCommand.COMMAND_WORD:
            return new GroupAddContactCommandParser(model).parse(arguments);

        case GroupRemoveContactCommand.COMMAND_WORD:
            return new GroupRemoveContactCommandParser(model).parse(arguments);

        case GroupEditNameCommand.COMMAND_WORD:
            return new GroupEditNameCommandParser().parse(arguments);

        case GroupAddTagCommand.COMMAND_WORD:
            return new GroupAddTagCommandParser().parse(arguments);

        case GroupRemoveTagCommand.COMMAND_WORD:
            return new GroupRemoveTagCommandParser().parse(arguments);

        case ListTransactionSummaryCommand.COMMAND_WORD:
            return new ListTransactionSummaryCommandParser().parse(arguments);

        case CalculatePaymentsCommand.COMMAND_WORD:
            return new CalculatePaymentsCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
