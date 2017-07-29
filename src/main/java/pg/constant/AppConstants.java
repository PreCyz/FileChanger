package pg.constant;

/**
 * @author Gawa [Paweł Gawędzki]
 * 2016-11-27 15:51:34
 */
public final class AppConstants {

    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    public static final String RESOURCE_BUNDLE = "bundle.translation";
    public static final String APP_CONFIG = "application.properties";
    public static final String BUNDLE_PATH = "bundle.translation";
    public static final String IMG_RESOURCE_PATH = String.format("img%s", FILE_SEPARATOR);
    public static final String FXML_RESOURCE_PATH = String.format("fxml%s", FILE_SEPARATOR);
    public static final String CSS_RESOURCE_PATH = String.format("css%s", FILE_SEPARATOR);

    private AppConstants() {}

}
