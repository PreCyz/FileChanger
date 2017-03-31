package pg.duplicat;

import pg.helper.DuplicateRemover;
import pg.helper.TimeHelper;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.*;

/**
 *
 * @author Gawa
 */
public class DuplicateFileRemover {

    private DuplicateRemover duplicateRemover;

    public DuplicateFileRemover(String srcDirPath) {
        this.duplicateRemover = new DuplicateRemover(srcDirPath);
    }

    public DuplicateRemover getDuplicateRemover() {
        return duplicateRemover;
    }

    public static void main(String[] args) {
        try {
            validateArgs(args);
            String sourcePath = args[0];
            LocalTime start = LocalTime.now();
            DuplicateFileRemover dfr = new DuplicateFileRemover(sourcePath);
            dfr.getDuplicateRemover().processDuplicates();
            LocalTime stop = LocalTime.now();
            System.out.println(TimeHelper.durationInfo(start, stop));
        } catch (NoSuchAlgorithmException | IOException | UnsupportedOperationException ex) {
            System.out.println(ex.getMessage());
        }
    }

    protected static void validateArgs(String[] args) {
        if(args == null || args.length == 0){
            throw new UnsupportedOperationException("Nie podano scieżki do katalogu.");
        }
        if(args[0] == null || "".equals(args[0].trim())){
            throw new UnsupportedOperationException("Niewłaściwy parametr.");
        }
    }
}
