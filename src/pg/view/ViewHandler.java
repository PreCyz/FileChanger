package pg.view;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import pg.exception.ProgramException;
import pg.helper.MessageHelper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;
import java.util.ResourceBundle;

import static pg.constant.ProgramConstants.RESOURCE_BUNDLE;

/**
 * Created by gawa on 14.01.17.
 */
public interface ViewHandler {

    void launchStartView();
    void launchLoggerView();

    static void handleException(ProgramException exception) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("!!!Start Exception Dialog");
        alert.setHeaderText("!!!Look, an Exception Dialog");
        alert.setContentText(MessageHelper.getInstance(
                ResourceBundle.getBundle(RESOURCE_BUNDLE, Locale.getDefault())).getErrorMsg(exception.getErrorCode()));

        // Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        Label label = new Label("!!!The exception message was:");

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
}
