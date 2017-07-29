package pg.view;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Window;
import pg.exception.ProgramException;
import pg.helper.MessageHelper;

import java.util.Locale;
import java.util.ResourceBundle;

import static pg.constant.AppConstants.RESOURCE_BUNDLE;

/**
 * Created by gawa on 18.01.17.
 */
public abstract class AbstractWindowHandler implements WindowHandler {

    protected Window window;

    public Window window() {
        return window;
    }

    public static void handleException(ProgramException exception) {
        MessageHelper messageHelper = MessageHelper.getInstance(ResourceBundle.getBundle(
                RESOURCE_BUNDLE, Locale.getDefault()));
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(messageHelper.getFullMessage("alert.title", Alert.AlertType.ERROR));
        alert.setHeaderText(messageHelper.getFullMessage("alert.header.text"));
        alert.setContentText(messageHelper.getErrorMsg(exception.getErrorCode()));
        alert.setWidth(1000);
        alert.setWidth(750);

        Label label = new Label(messageHelper.getFullMessage("alert.exceptionDetails.label"));

        TextArea textArea = new TextArea(exception.getMessage());
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }

    public abstract void launchStartView();
    public abstract void launchLoggerView();
    public abstract void changeWindowWidth(double width);

}
