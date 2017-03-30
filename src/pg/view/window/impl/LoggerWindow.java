package pg.view.window.impl;

import pg.view.ViewHandler;
import pg.view.controller.LoggerController;
import pg.view.window.AbstractWindow;

import java.util.ResourceBundle;

/**
 * Created by gawa on 30.03.17.
 */
public class LoggerWindow extends AbstractWindow {

	public LoggerWindow(ViewHandler viewHandler, ResourceBundle bundle) {
		super(viewHandler, bundle);
		this.controller = new LoggerController(viewHandler);
	}

	@Override
	protected String fxmlFileName() {
		return "logger.fxml";
	}

	@Override
	protected String windowImgFileName() {
		return "loggerWindowIcon.png";
	}

	@Override
	protected String cssFileName() {
		return null;
	}

	@Override
	public String windowTitleBundle() {
		return "window.logger.title";
	}
}
