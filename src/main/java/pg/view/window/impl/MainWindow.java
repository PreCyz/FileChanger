package pg.view.window.impl;

import pg.view.controller.AbstractController;
import pg.view.window.AbstractWindow;

import java.util.ResourceBundle;

/**
 * Created by gawa on 30.03.17.
 */
public class MainWindow extends AbstractWindow {

	public MainWindow(AbstractController controller, ResourceBundle bundle) {
		super(controller, bundle);
	}

	@Override
	protected String fxmlFileName() {
		return "main.fxml";
	}

	@Override
	public String windowImgFileName() {
		return "mainWindowIcon.png";
	}

	@Override
	protected String cssFileName() {
		return "main.css";
	}

	@Override
	public String windowTitleBundle() {
		return "window.main.title";
	}
}
