package pg.view.window;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import pg.constant.AppConstants;
import pg.view.ViewHandler;
import pg.view.controller.AbstractController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by gawa on 30.03.17.
 */
public abstract class AbstractWindow {

	private final ResourceBundle bundle;
	private final AbstractController controller;

	public AbstractWindow(AbstractController controller, ResourceBundle bundle) {
		this.controller = controller;
		this.bundle = bundle;
	}

	public Parent root() throws IOException {
		FXMLLoader loader = new FXMLLoader(url(), bundle);
		loader.setController(controller);
		return loader.load();
	}

	private URL url() {
		return getClass().getClassLoader().getResource(AppConstants.FXML_RESOURCE_PATH + fxmlFileName());
	}

	public String windowImgFilePath() {
		return AppConstants.IMG_RESOURCE_PATH + windowImgFileName();
	}

	public void refreshWindowSize() {
		controller.calculateWindowWidth();
	}

	public boolean resizable() {
		return false;
	}

	public String css() {
		return getClass()
				.getClassLoader()
				.getResource(AppConstants.CSS_RESOURCE_PATH + cssFileName())
				.toExternalForm();
	}

	protected abstract String fxmlFileName();
	protected abstract String windowImgFileName();
	protected abstract String cssFileName();
	public abstract String windowTitleBundle();
}
