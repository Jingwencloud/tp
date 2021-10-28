package seedu.awe.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.awe.commons.core.GuiSettings;
import seedu.awe.commons.core.LogsCenter;
import seedu.awe.logic.Logic;
import seedu.awe.logic.commands.CommandResult;
import seedu.awe.logic.commands.exceptions.CommandException;
import seedu.awe.logic.parser.exceptions.EmptyGroupException;
import seedu.awe.logic.parser.exceptions.ParseException;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {
    private static UiView viewEnum = UiView.CONTACT_PAGE;

    private static final String FXML = "MainWindow.fxml";

    private static final String url = "images/awelogo.png";

    private final Logger logger = LogsCenter.getLogger(getClass());


    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private LogoDisplay logoDisplay;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

    // Panels for toggling
    private ViewPanel viewPanel;
    private NavigationButtonPanel navigationButtonPanel;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane listPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private StackPane viewPanelPlaceholder;

    @FXML
    private StackPane navigationButtonPlaceholder;

    @FXML
    private StackPane logoDisplayPlaceholder;


    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    public static UiView getViewEnum() {
        return viewEnum;
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        viewPanel = new ViewPanel(logic);
        viewPanelPlaceholder.getChildren().add(viewPanel.getRoot());

        logoDisplay = new LogoDisplay(url);
        logoDisplayPlaceholder.getChildren().add(logoDisplay.getRoot());

        navigationButtonPanel = new NavigationButtonPanel(viewPanel);
        navigationButtonPlaceholder.getChildren().add(navigationButtonPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show(boolean isDataError) {
        primaryStage.show();
        if (isDataError) {
            helpWindow.show();
        }
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.awe.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());


            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            if (commandResult.isShowContacts()) {
                viewEnum = UiView.CONTACT_PAGE;
                viewPanel.toggleView(viewEnum);
            }

            if (commandResult.isShowGroups()) {
                viewEnum = UiView.GROUP_PAGE;
                viewPanel.toggleView(viewEnum);
            }

            if (commandResult.isShowExpenses()) {
                viewEnum = UiView.EXPENSE_PAGE;
                viewPanel.toggleView(viewEnum);
            }

            if (commandResult.isShowTransactionSummary()) {
                viewEnum = UiView.TRANSACTION_SUMMARY;
                viewPanel.toggleView(viewEnum);
            }

            if (commandResult.isShowPaymentsCommand()) {
                viewEnum = UiView.PAYMENT_PAGE;
                viewPanel.toggleView(viewEnum);
            }

            return commandResult;
        } catch (CommandException | ParseException | EmptyGroupException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
