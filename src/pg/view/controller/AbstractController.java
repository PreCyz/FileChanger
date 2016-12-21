package pg.view.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import pg.helper.ResourceHelper;
import pg.logger.impl.ConsoleLogger;
import pg.helper.MessageHelper;
import pg.logger.AppLogger;

/**
 * @author Gawa [Paweł Gawędzki]
 * 2016-12-02 22:05:20
 */
public abstract class AbstractController implements Initializable {
    protected ResourceBundle bundle;
    protected URL location;
    protected ResourceHelper resourceHelper;
    protected MessageHelper messageHelper;
    protected AppLogger logger;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        bundle = resources;
        resourceHelper = new ResourceHelper();
        messageHelper = MessageHelper.getInstance(bundle);
        logger = new ConsoleLogger(messageHelper);
    }

}
