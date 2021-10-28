package seedu.awe.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import seedu.awe.ui.listener.ContactButtonListener;

/**
 * A button to toggle the view to PersonPanel.
 */
public class ContactViewButton extends UiPart<Region> {

    private static final String FXML = "PersonViewButton.fxml";

    @FXML
    private Button personViewButton;

    /**
     * Constructor for PersonViewButton.
     *
     * @param viewPanel viewPanel to set onclick action
     */
    public ContactViewButton(ViewPanel viewPanel) {
        super(FXML);
        personViewButton.setOnAction(new ContactButtonListener(viewPanel));
    }


}
