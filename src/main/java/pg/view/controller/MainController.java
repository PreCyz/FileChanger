package pg.view.controller;

import javafx.beans.binding.Bindings;
import javafx.beans.value.*;
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
import pg.filechanger.service.impl.FXFileService;
import pg.helper.*;
import pg.logger.impl.FileLogger;
import pg.view.WindowHandler;

import java.io.File;
import java.net.URL;
import java.util.*;

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
    @FXML private TextArea maxIndexesAreaText;
    @FXML private Label sourceLabel;
    @FXML private Label destinationLabel;
    @FXML private Label fileNamePatternLabel;
    @FXML private ListView<String> logListView;
    @FXML private TextField fileExtensionsTextField;
    @FXML private CheckBox editExtensionsCheckBox;
    @FXML private CheckBox hideLogCheckBox;

    private FXFileService fxFileService;

	public MainController(WindowHandler windowHandler) {
        super(windowHandler);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        logger = new FileLogger(logListView);
        logger.log(messageHelper.getFullMessage("log.fileLog.initialized", FileLogger.class.getSimpleName()));
        fxFileService = new FXFileService(resources, logger);
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
            fileConnectorComboBox.valueProperty().addListener(fileConnectorChangeListener());
	        fxFileService.addFileNameIndexConnector(appConfigHelper.getFileConnectorLastUsed());
            fileExtensionsTextField.setText(appConfigHelper.getExtensions());
	        fileExtensionsTextField.setOnAction(fileExtensionsAction());
	        fxFileService.addFileExtension(appConfigHelper.getExtensions());
            hideLogCheckBox.setSelected(appConfigHelper.getHideLogs());
            fileNamePatternLabel.setText(messageHelper.getFullMessage("log.fileName.pattern", "\n"
		            , fileConnectorComboBox.getValue()));
        } catch (ProgramException ex) {
            logger.log(messageHelper.getErrorMsg(ex.getErrorCode()));
        } finally {
            logger.log(messageHelper.getFullMessage("log.app.setup.finished"));
        }
    }

	private ChangeListener<? super String> fileConnectorChangeListener() {
		return (ChangeListener<String>) (observableValue, oldValue, currentValue) -> {
			logger.log(messageHelper.getFullMessage("log.fileConnector.valueChanged", currentValue));
			fxFileService.addFileNameIndexConnector(currentValue);
			fileNamePatternLabel.setText(messageHelper.getFullMessage("log.fileName.pattern", "\n"
					, fileConnectorComboBox.getValue()));
			setMaxIndexesLabelText();
		};
	}

    private void setMaxIndexesLabelText() {
        if (fxFileService.isUpdateIndexesPossible()) {
	        fxFileService.createMaxIndexMap();
            maxIndexesAreaText.setText(fxFileService.buildMaxIndexesLabelText());
        }
    }

	private EventHandler<ActionEvent> fileExtensionsAction() {
		return e -> {
			fxFileService.addFileExtension(fileExtensionsTextField.getText());
			logger.log(messageHelper.getFullMessage("log.fileExtensions.valueChanged",
					fileExtensionsTextField.getText()));
			setMaxIndexesLabelText();
		};
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
            logger.log(messageHelper.getErrorMsg(ex.getErrorCode(), button.getId()));
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
            fxFileService.addFileCoreName(coreNameTextField.getText());
	        fxFileService.addFileNameIndexConnector(fileConnectorComboBox.getValue());
	        fxFileService.addFileExtension(fileExtensionsTextField.getText());
            if (!fxFileService.isChangePossible()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(messageHelper.getFullMessage("alert.title", Alert.AlertType.ERROR.name()));
                alert.setHeaderText(messageHelper.getFullMessage("alert.header.text"));
                alert.setContentText(messageHelper.getFullMessage("alert.required.parameters.not.set"));
                alert.showAndWait();
            } else {
                try {
	                fxFileService.run();
                    fxFileService.updateAppConfiguration();
                } catch (ProgramException ex) {
                    buildErrorAlertWithSolution(ex);
                } finally {
                    logger.log(messageHelper.getFullMessage("log.change.finished"));
                }
            }
        };
    }

    private void buildErrorAlertWithSolution(ProgramException ex) {
	    StringBuilder message = new StringBuilder();
	    if (ex.getErrorCode() != null) {
	        message.append(messageHelper.getErrorMsg(ex.getErrorCode(), ex.getArgument())).append("\n");
        }
        if (!MessageHelper.empty(ex.getLocalizedMessage())) {
	        message.append(ex.getLocalizedMessage()).append("\n");
        }
        buildAlert(message.toString(), Alert.AlertType.ERROR);
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
                fxFileService.addSourceDir(sourceDir.getAbsolutePath());
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
	    final short maxNumberOfCharactersOnLabel = 29;
	    if (value.length() > maxNumberOfCharactersOnLabel) {
            return String.format("...%s", value.substring(value.length() - maxNumberOfCharactersOnLabel, value.length()));
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
                fxFileService.addDestinationDir(destinationDir.getAbsolutePath());
                destinationLabel.setText(fitValueToLabel(destinationDir.getAbsolutePath()));
	            setMaxIndexesLabelText();
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
