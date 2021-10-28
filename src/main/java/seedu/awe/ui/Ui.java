package seedu.awe.ui;

import javafx.stage.Stage;

/**
 * API of UI component
 */
public interface Ui {

    /** Starts the UI (and the App).  */
    void start(Stage primaryStage);

    /** Sets the boolean indicating if there was an error reading from storage. */
    void setIsDataError(boolean isDataError);

}
