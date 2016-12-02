package pg.helper;

import java.io.InputStream;
import javafx.scene.image.Image;
import pg.exception.ErrorCode;
import pg.exception.ProgramException;

/**
 * @author Gawa [Paweł Gawędzki]
 * 2016-12-02 22:47:08
 */
public final class ResourceHelper {

    public ResourceHelper() {}
    
    public Image readImage(String imgPath) throws ProgramException {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(imgPath)) {
            return new Image(is);
        } catch (Exception ex) {
            throw new ProgramException(ErrorCode.BUTTON_IMG, ex, imgPath);
        }
    }

}
