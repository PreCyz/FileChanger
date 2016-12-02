package pg.constant;

/**
 * @author Gawa [Paweł Gawędzki]
 * 2016-11-27 15:51:34
 */
public final class ProgramConstants {

    private ProgramConstants() {}
    
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    public static final String RESOURCE_BUNDLE = "pg.resource.bundle";
    public static final String DEFAULT_BUNDLE_NAME = "bundle";
    public static final String APP_CONFIG = "appConfiguration.properties";
    public static final String RESOURCE_PATH = "pg/resource/";
    public static final String BUNDLE_PATH = String.format("%s%s", RESOURCE_PATH.replace("/", "."), DEFAULT_BUNDLE_NAME);
    public static final String APP_CONFIG_PATH = String.format("%s%s", RESOURCE_PATH, APP_CONFIG);
    public static final String IMG_PATH = RESOURCE_PATH + "img/";

}
