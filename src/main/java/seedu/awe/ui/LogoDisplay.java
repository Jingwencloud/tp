package seedu.awe.ui;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

/**
 * The logo of AWE to be displayed in the MainWindow.
 */
public class LogoDisplay extends UiPart<Region> {

    private static final String FXML = "LogoDisplay.fxml";

    @FXML
    private ImageView logoDisplay;

    /**
     * Constructor for LogoDisplay.
     * @param url The location of the logo to be displayed.
     */
    public LogoDisplay(String url) {
        super(FXML);
        Image image = new Image(url);
        logoDisplay.setImage(image);
    }

}
