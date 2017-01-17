package pg.view.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import pg.exception.ProgramException;
import pg.helper.AppConfigHelper;
import pg.logger.impl.FileLogger;
import pg.picturefilechanger.ChangeDetails;
import pg.picturefilechanger.impl.FileChangerImpl;
import pg.view.AppViewHandler;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import static pg.constant.ProgramConstants.IMG_RESOURCE_PATH;

/**
 * @author Gawa [Paweł Gawędzki]
 * 2016-11-21 20:34:16
 */
public class MainController extends AbstractController {
    
    @FXML private TextField coreNameTextField;
    @FXML private ComboBox<String> fileConnectorComboBox;
    @FXML private TitledPane mandatoryTitledPane;
    @FXML private TitledPane optionalTitledPane;
    @FXML private Button runButton;
    @FXML private Button sourceButton;
    @FXML private Button destinationButton;
    @FXML private Button exitButton;
    @FXML private Button showLogsButton;
    @FXML private Label maxIndexesLabel;
    @FXML private Label sourceLabel;
    @FXML private Label destinationLabel;
    @FXML private ListView<String> logListView;
    @FXML private TextField fileExtensionsTextField;
    @FXML private CheckBox editExtensionsCheckBox;

    private ChangeDetails changeDetails;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        changeDetails = new ChangeDetails();
        logger = new FileLogger(messageHelper, this.getClass(), logListView);
        editExtensionsCheckBox.setOnAction(extensionsAction());
        setUpFromAppProperties();
        setUpButtons();
    }

    private void setUpFromAppProperties() {
        try {
            AppConfigHelper appConfigHelper = AppConfigHelper.getInstance();
            coreNameTextField.setText(appConfigHelper.getCoreNameLastUsed());
            fileConnectorComboBox.setItems(FXCollections.observableList(appConfigHelper.getFileConnectors()));
            fileConnectorComboBox.setValue(appConfigHelper.getFileConnectorLastUsed());
            fileExtensionsTextField.setText(appConfigHelper.getExtensions());
        } catch (ProgramException ex) {
            logger.log(ex);
        } finally {
            logger.log(messageHelper.getFullMessage("log.app.setup.finished"));
        }
    }

    private void setUpButtons() {
        setupButton(sourceButton, sourceAction());
        setupButton(destinationButton, destinationAction());
        setupButton(runButton, runAction());
        setupButton(exitButton, exitAction());
        setupButton(showLogsButton, showLogsAction());
    }

    private void setupButton(Button button, EventHandler<ActionEvent> actionEventHandler) {
        final String imgExtension = ".png";
        try {
            Image buttonImage = resourceHelper.readImage(IMG_RESOURCE_PATH + button.getId() + imgExtension);
            button.setGraphic(new ImageView(buttonImage));
        } catch (ProgramException ex) {
            logger.log(ex);
        }
        button.setOnAction(actionEventHandler);
    }

    private EventHandler<ActionEvent> exitAction() {
        return e -> {
            logger.log(messageHelper.getFullMessage("log.button.pressed", exitButton.getText()));
            logger.log(messageHelper.getFullMessage("log.exit.program"));
            System.exit(0);
        };
    }

    private EventHandler<ActionEvent> runAction() {
        return e -> {
            logger.log(messageHelper.getFullMessage("log.button.pressed", runButton.getText()));
            maxIndexesLabel.setText("YOU PRESSED RUN MAN !!");
            changeDetails.setCoreName(coreNameTextField.getText());
            changeDetails.setFileNameIndexConnector(fileConnectorComboBox.getValue());
            if (!changeDetails.isReady()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(messageHelper.getFullMessage("alert.title"));
                alert.setHeaderText(messageHelper.getFullMessage("alert.error.header.text"));
                alert.setContentText(messageHelper.getFullMessage("alert.required.parameters.not.set"));

                alert.showAndWait();
            } else {
                try {
                    new FileChangerImpl(changeDetails.toStringArray(), bundle).run();
                    AppConfigHelper.getInstance().updateAppConfiguration(changeDetails);
                } catch (ProgramException ex) {
                    buildErrorAlertWithSolution(ex);
                } finally {
                    logger.log(messageHelper.getFullMessage("log.change.finished"));
                }
            }
        };
    }

    private void buildErrorAlertWithSolution(ProgramException ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(messageHelper.getFullMessage("alert.title"));
        alert.setHeaderText(messageHelper.getFullMessage("alert.error.header.text"));
        alert.setContentText(messageHelper.getErrorMsg(ex.getErrorCode(), ex.getArgument()));
        alert.showAndWait();
    }

    private EventHandler<ActionEvent> sourceAction() {
        return e -> {
            logger.log(messageHelper.getFullMessage("log.button.pressed", sourceButton.getText()));
            DirectoryChooser directoryChooser = new DirectoryChooser();
            configureDirectoryChooser(directoryChooser);
            File sourceDir = directoryChooser.showDialog(AppViewHandler.window());
            changeDetails.setSourceDir(sourceDir.getAbsolutePath());
            sourceLabel.setText(sourceDir.getAbsolutePath());
        };
    }

    private void configureDirectoryChooser(DirectoryChooser directoryChooser) {
        directoryChooser.setTitle(messageHelper.getFullMessage("fileChooser.choose.folder"));
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    }

    private EventHandler<ActionEvent> destinationAction() {
        return e -> {
            logger.log(messageHelper.getFullMessage("log.button.pressed", destinationButton.getText()));
            DirectoryChooser directoryChooser = new DirectoryChooser();
            configureDirectoryChooser(directoryChooser);
            File destinationDir = directoryChooser.showDialog(AppViewHandler.window());
            changeDetails.setDestinationDir(destinationDir.getAbsolutePath());
            destinationLabel.setText(destinationDir.getAbsolutePath());
        };
    }

    private EventHandler<ActionEvent> showLogsAction() {
        return e -> {
            logger.log(messageHelper.getFullMessage("log.button.pressed", showLogsButton.getText()));
            viewHandler.launchLoggerView();
        };
    }

    private EventHandler<ActionEvent> extensionsAction() {
        return e -> fileExtensionsTextField.setEditable(editExtensionsCheckBox.isSelected());
    }

}
