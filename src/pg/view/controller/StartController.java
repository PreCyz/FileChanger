package pg.view.controller;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * @author Gawa [Paweł Gawędzki]
 * 2016-11-21 20:34:16
 */
public class StartController implements Initializable {
    
    @FXML private TextField coreNameTextField;
    @FXML private ComboBox<String> fileConnectorComboBox;
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<String> list = Arrays.asList(new String[]{"", "-", "_", "+"});
        coreNameTextField.setText("Test");
        FXCollections.observableList(list);
        fileConnectorComboBox.setItems(FXCollections.observableList(list));
    }

}
