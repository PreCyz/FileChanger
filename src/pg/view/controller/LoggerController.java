package pg.view.controller;

import pg.helper.MessageHelper;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Gawa [Paweł Gawędzki]
 * 2016-11-21 20:34:16
 */
public class LoggerController extends AbstractController {

    private MessageHelper messageHelper;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        messageHelper = MessageHelper.getInstance(bundle);
    }

}
