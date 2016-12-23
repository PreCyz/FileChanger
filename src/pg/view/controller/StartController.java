package pg.view.controller;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;

import static pg.constant.ProgramConstants.IMG_RESOURCE_PATH;

import pg.constant.ProgramConstants;
import pg.exception.ProgramException;
import pg.helper.MessageHelper;
import pg.helper.PropertiesHelper;
import pg.logger.impl.FileLogger;
import pg.picturefilechanger.ChangeDetails;
import pg.picturefilechanger.impl.FileChangerImpl;
import pg.view.ViewHandler;

/**
 * @author Gawa [Paweł Gawędzki]
 * 2016-11-21 20:34:16
 */
public class StartController extends AbstractController {
    
    @FXML private TextField coreNameTextField;
    @FXML private ComboBox<String> fileConnectorComboBox;
    @FXML private TitledPane mandatoryTitledPane;
    @FXML private TitledPane optionalTitledPane;
    @FXML private Button runButton;
    @FXML private Button sourceButton;
    @FXML private Button destinationButton;
    @FXML private Button exitButton;
    @FXML private Label maxIndexesLabel;
    @FXML private Label sourceLabel;
    @FXML private Label destinationLabel;
    @FXML private ListView<String> logListView;
    @FXML private TextField fileExtensionsTextField;
    @FXML private CheckBox editExtensionsCheckBox;

    private ChangeDetails changeDetails;
    private MessageHelper messageHelper;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        changeDetails = new ChangeDetails();
        messageHelper = MessageHelper.getInstance(bundle);
        logger = new FileLogger(messageHelper, this.getClass(), logListView);
        setUpFromAppProperties();
        setUpButtons();
    }

    private void setUpFromAppProperties() {
        try {
            URL url = this.getClass().getClassLoader().getResource(ProgramConstants.APP_CONFIG_PATH);
            PropertiesHelper propertiesHelper = new PropertiesHelper();
            Properties properties = propertiesHelper.loadProgramProperties(url.getPath());

            coreNameTextField.setText(properties.getProperty("core.name.lastUsed"));

            String fileConnectors = properties.getProperty("file.connectors");
            List<String> list = Arrays.asList(fileConnectors.split(","));
            fileConnectorComboBox.setItems(FXCollections.observableList(list));
            fileConnectorComboBox.setValue(list.get(0));
            int idx = list.indexOf(properties.getProperty("file.connector.lastUsed"));
            if (idx > -1) {
                fileConnectorComboBox.setValue(list.get(idx));
            }

            fileExtensionsTextField.setText(properties.getProperty("file.extensions"));
        } catch (ProgramException ex) {
            logger.log(ex);
        } finally {
            logger.log("!!!Setup from app config finished.");
        }
    }

    private void setUpButtons() {
        setImgForButton(sourceButton);
        setImgForButton(destinationButton);
        setImgForButton(runButton);
        setImgForButton(exitButton);
    }

    private void setImgForButton(Button button) {
        final String imgExtension = ".png";
        try {
            Image buttonImage = resourceHelper.readImage(IMG_RESOURCE_PATH + button.getId() + imgExtension);
            button.setGraphic(new ImageView(buttonImage));
        } catch (ProgramException ex) {
            logger.log(ex);
        }
    }

    public void exit() {
        logger.log("!!!Naciśnięto przycisk 'Exit'. Program zakończy działanie.");
        System.exit(0);
    }

    public void run() {
        logger.log("!!!Naciśnięto przycisk 'Run'.");
        maxIndexesLabel.setText("YOU PRESSED RUN MAN !!");
        changeDetails.setCoreName(coreNameTextField.getText());
        changeDetails.setFileNameIndexConnector(fileConnectorComboBox.getValue());
        if (!changeDetails.isReady()) {
            String errMsg = String.format("!!!Not all required parameters are set.%nRequired parameters are:%n- source%n" +
                    "- destination%n- core name.!");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("!!!Can't perform change.");
            alert.setContentText(errMsg);

            alert.showAndWait();
        } else {
            try {
                new FileChangerImpl(changeDetails.toStringArray(), bundle).run();
                updateAppConfiguration();
            } catch (ProgramException ex) {
                buildErrorAlertWithSolution(ex);
            }
        }
    }

    private void updateAppConfiguration() {
        try {
            URL url = this.getClass().getClassLoader().getResource(ProgramConstants.APP_CONFIG_PATH);
            PropertiesHelper propertiesHelper = new PropertiesHelper();
            Properties appProperties = propertiesHelper.loadProgramProperties(url.getPath());

            propertiesHelper.overwriteProperty(appProperties, "core.name.lastUsed", changeDetails.getCoreName());
            propertiesHelper.overwriteProperty(appProperties, "file.connector.lastUsed",
                    changeDetails.getFileNameIndexConnector());
            propertiesHelper.overwriteProperty(appProperties, "file.extensions", changeDetails.getFileExtension());

            propertiesHelper.saveProgramProperties(appProperties, ProgramConstants.APP_CONFIG_PATH);
        } catch (ProgramException ex) {
            logger.log(ex);
        } finally {
            logger.log("!!!Zaktualizowano konfigurację programu.");
        }
    }

    private void buildErrorAlertWithSolution(ProgramException ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("!!!Error Dialog");
        alert.setHeaderText("!!!Can't perform change.");
        alert.setContentText(messageHelper.getErrorMsg(ex.getErrorCode(), ex.getArgument()));
        alert.showAndWait();
    }

    public void source() {
        logger.log("!!!Naciśnięto przycisk 'Src'.");
        DirectoryChooser directoryChooser = new DirectoryChooser();
        configureDirectoryChooser(directoryChooser);
        File sourceDir = directoryChooser.showDialog(ViewHandler.window());
        changeDetails.setSourceDir(sourceDir.getAbsolutePath());
        sourceLabel.setText(sourceDir.getAbsolutePath());
    }

    private void configureDirectoryChooser(DirectoryChooser directoryChooser) {
        directoryChooser.setTitle("!!!Wybierz katalog");
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    }

    public void destination() {
        logger.log("!!!Naciśnięto przycisk 'Dst'.");
        DirectoryChooser directoryChooser = new DirectoryChooser();
        configureDirectoryChooser(directoryChooser);
        File destinationDir = directoryChooser.showDialog(ViewHandler.window());
        changeDetails.setDestinationDir(destinationDir.getAbsolutePath());
        destinationLabel.setText(destinationDir.getAbsolutePath());
    }

    public void extensions() {
        fileExtensionsTextField.setEditable(editExtensionsCheckBox.isSelected());
    }

}
