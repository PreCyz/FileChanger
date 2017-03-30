package pg.view.window.impl;

import pg.view.controller.AbstractController;
import pg.view.window.AbstractWindow;

import java.util.ResourceBundle;

/**
 * Created by gawa on 30.03.17.
 */
public class LoggerWindow extends AbstractWindow {

	public LoggerWindow(AbstractController controller, ResourceBundle bundle) {
		super(controller, bundle);
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
