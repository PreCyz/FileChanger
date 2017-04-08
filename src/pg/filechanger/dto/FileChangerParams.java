package pg.filechanger.dto;

import pg.helper.MessageHelper;

import java.util.Locale;
import java.util.ResourceBundle;

import static pg.constant.AppConstants.RESOURCE_BUNDLE;

/** Created by gawa */
public enum FileChangerParams {

    source("param.source"),
    destination("param.destination"),
    extensions("param.extensions"),
    coreName("param.coreName"),
    nameConnector("param.nameConnector");

    private String msg;
    private MessageHelper messageHelper;

    FileChangerParams(String msg) {
        this.msg = msg;
        messageHelper = MessageHelper.getInstance(
                ResourceBundle.getBundle(RESOURCE_BUNDLE, Locale.getDefault()));
    }

    public String message() {
        return messageHelper.getFullMessage(msg);
    }

}
