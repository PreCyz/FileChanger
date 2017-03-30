package pg.view.controller;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import pg.exception.ProgramException;
import pg.helper.AppConfigHelper;
import pg.logger.impl.FileLogger;
import pg.picturefilechanger.ChangeDetails;
import pg.picturefilechanger.impl.FileChangerImpl;
import pg.picturefilechanger.validator.impl.ArgsValidator;
import pg.view.WindowHandler;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import static pg.constant.AppConstants.IMG_RESOURCE_PATH;

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
    @FXML private CheckBox hideLogCheckBox;

    private ChangeDetails changeDetails;

    private final short numberOfCharacters = 29;

    public MainController(WindowHandler windowHandler) {
        super(windowHandler);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        changeDetails = new ChangeDetails();
        logger = new FileLogger(messageHelper, logListView);
        editExtensionsCheckBox.setOnAction(extensionsAction());
        hideLogCheckBox.setOnAction(hideLogAction());
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
            hideLogCheckBox.setSelected(appConfigHelper.getHideLogs());
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
            String buttonId = button.getId();
            button.setText(messageHelper.getFullMessage(
                    "changerTab.button." + buttonId.substring(0, buttonId.indexOf("Button"))));
        }
        button.setOnAction(actionEventHandler);
    }

    private EventHandler<ActionEvent> exitAction() {
        return e -> {
            logger.log(messageHelper.getFullMessage("log.button.pressed", exitButton.getId()));
            logger.log(messageHelper.getFullMessage("log.exit.program"));
            System.exit(0);
        };
    }

    private EventHandler<ActionEvent> runAction() {
        return e -> {
            logger.log(messageHelper.getFullMessage("log.button.pressed", runButton.getId()));
            maxIndexesLabel.setText("YOU PRESSED RUN MAN !!");
            changeDetails.setFileCoreName(coreNameTextField.getText());
            changeDetails.setFileNameIndexConnector(fileConnectorComboBox.getValue());
            changeDetails.setFileExtension(fileExtensionsTextField.getText());
            if (!changeDetails.isReady()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(messageHelper.getFullMessage("alert.title", Alert.AlertType.ERROR.name()));
                alert.setHeaderText(messageHelper.getFullMessage("alert.header.text"));
                alert.setContentText(messageHelper.getFullMessage("alert.required.parameters.not.set"));

                alert.showAndWait();
            } else {
                try {
                    new ArgsValidator(changeDetails, bundle);
                    new FileChangerImpl(changeDetails, bundle).run();
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
        buildAlert(messageHelper.getErrorMsg(ex.getErrorCode(), ex.getArgument()), Alert.AlertType.ERROR);
    }

    private void buildAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(messageHelper.getFullMessage("alert.title", alertType));
        alert.setHeaderText(messageHelper.getFullMessage("alert.header.text"));
        alert.setContentText(message);
        alert.showAndWait();
    }

    private EventHandler<ActionEvent> sourceAction() {
        return e -> {
            logger.log(messageHelper.getFullMessage("log.button.pressed", sourceButton.getId()));
            DirectoryChooser directoryChooser = new DirectoryChooser();
            configureDirectoryChooser(directoryChooser);
            File sourceDir = directoryChooser.showDialog(windowHandler.window());
            if (sourceDir != null) {
                changeDetails.setSourceDir(sourceDir.getAbsolutePath());
                sourceLabel.setText(fitValueToLabel(sourceDir.getAbsolutePath()));
            } else {
                logger.log(messageHelper.getFullMessage("alert.source.noDir"));
                buildAlert(messageHelper.getFullMessage("alert.source.noDir"), Alert.AlertType.WARNING);
            }
        };
    }

    private void configureDirectoryChooser(DirectoryChooser directoryChooser) {
        directoryChooser.setTitle(messageHelper.getFullMessage("fileChooser.choose.folder"));
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    }

    private String fitValueToLabel(String value) {
        if (value.length() > numberOfCharacters) {
            return String.format("...%s", value.substring(value.length() - numberOfCharacters, value.length()));
        }
        return value;
    }

    private EventHandler<ActionEvent> destinationAction() {
        return e -> {
            logger.log(messageHelper.getFullMessage("log.button.pressed", destinationButton.getId()));
            DirectoryChooser directoryChooser = new DirectoryChooser();
            configureDirectoryChooser(directoryChooser);
            File destinationDir = directoryChooser.showDialog(windowHandler.window());
            if (destinationDir != null) {
                changeDetails.setDestinationDir(destinationDir.getAbsolutePath());
                destinationLabel.setText(fitValueToLabel(destinationDir.getAbsolutePath()));
            } else {
                logger.log(messageHelper.getFullMessage("alert.destination.noDir"));
                buildAlert(messageHelper.getFullMessage("alert.destination.noDir"), Alert.AlertType.WARNING);
            }
        };
    }

    private EventHandler<ActionEvent> showLogsAction() {
        return e -> {
            logger.log(messageHelper.getFullMessage("log.button.pressed", showLogsButton.getId()));
            windowHandler.launchLoggerView();
        };
    }

    private EventHandler<ActionEvent> extensionsAction() {
        return e -> {
            fileExtensionsTextField.setEditable(editExtensionsCheckBox.isSelected());
            fileExtensionsTextField.styleProperty().bind(Bindings.when(editExtensionsCheckBox.selectedProperty())
                .then("-fx-background-color: white;")
                .otherwise("-fx-background-color: #ECECEC;"));
        };
    }

    private EventHandler<ActionEvent> hideLogAction() {
        return event -> {
            logListView.setVisible(!hideLogCheckBox.isSelected());
            Pane pane = (Pane) logListView.getParent();
            if (hideLogCheckBox.isSelected()) {
                windowHandler.changeWindowWidth(pane.getWidth());
            } else {
                windowHandler.changeWindowWidth(-pane.getWidth());
            }
        };
    }

    public void calculateWindowWidth() {
        if (hideLogCheckBox.isSelected()) {
            logListView.setVisible(!hideLogCheckBox.isSelected());
            Pane pane = (Pane) logListView.getParent();
            windowHandler.changeWindowWidth(pane.getWidth());
        }
    }

}
