package pg.remover;

import pg.exception.*;
import pg.helper.TimeHelper;
import pg.filechanger.validator.impl.DuplicateRemoverValidator;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.*;

/**
 *
 * @author Gawa
 */
public class DuplicateRemoverMain {

    private DuplicateRemover duplicateRemover;

    public DuplicateRemoverMain(String srcDirPath) {
        this.duplicateRemover = new DuplicateRemover(srcDirPath);
    }

    public DuplicateRemover getDuplicateRemover() {
        return duplicateRemover;
    }

    public static void main(String[] args) {
        try {
        	new DuplicateRemoverValidator(args).validate();
            String sourcePath = args[0];
            LocalTime start = LocalTime.now();
            DuplicateRemoverMain dfr = new DuplicateRemoverMain(sourcePath);
            dfr.getDuplicateRemover().processDuplicates();
            LocalTime stop = LocalTime.now();
            System.out.println(TimeHelper.durationInfo(start, stop));
        } catch (NoSuchAlgorithmException | IOException | ProgramException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
