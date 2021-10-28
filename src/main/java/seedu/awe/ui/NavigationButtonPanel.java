package seedu.awe.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;


/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class NavigationButtonPanel extends UiPart<Region> {

    private static final String FXML = "NavigationButtonPanel.fxml";


    // Buttons for toggling
    private GroupViewButton groupViewButton;
    private ContactViewButton contactViewButton;

    @FXML
    private StackPane personButtonPlaceholder;

    @FXML
    private StackPane groupButtonPlaceholder;

    /**
     * Constructor for NavigationButtonPanel.
     *
     * @param viewPanel To pass to buttons to allow them to toggle panel on click.
     */
    public NavigationButtonPanel(ViewPanel viewPanel) {
        super(FXML);

        fillInnerParts(viewPanel);
    }

    void fillInnerParts(ViewPanel viewPanel) {
        groupViewButton = new GroupViewButton(viewPanel);
        groupButtonPlaceholder.getChildren().add(groupViewButton.getRoot());

        contactViewButton = new ContactViewButton(viewPanel);
        personButtonPlaceholder.getChildren().add(contactViewButton.getRoot());

    }
}
