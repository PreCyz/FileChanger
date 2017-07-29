package pg.filechanger.dto;

import pg.helper.MessageHelper;

import java.util.ArrayList;
import java.util.List;

import static pg.filechanger.dto.FileChangerParams.*;

/**
 *
 * @author premik
 */
public class ChangeDetails {

    private String sourceDir;
    private String destinationDir;
    private String fileCoreName;
    private String fileNameIndexConnector;
    private String fileExtension;
    private int fileIndex;

    public ChangeDetails() {}

    public ChangeDetails(String destinationDir, String fileNameIndexConnector) {
    	this.destinationDir = destinationDir;
    	this.fileNameIndexConnector = fileNameIndexConnector;
    }

    public ChangeDetails(String sourceDir, String destinationDir, String fileCoreName,
                         String fileNameIndexConnector) {
    	this(destinationDir, fileNameIndexConnector);
        this.sourceDir = sourceDir;
        this.fileCoreName = fileCoreName;
    }

    public String getSourceDir() {
        return sourceDir;
    }

    public void setSourceDir(String sourceDir) {
        this.sourceDir = sourceDir;
    }

    public String getDestinationDir() {
        return destinationDir;
    }

    public void setDestinationDir(String destinationDir) {
        this.destinationDir = destinationDir;
    }

    public String getFileCoreName() {
        return fileCoreName;
    }

    public void setFileCoreName(String fileCoreName) {
        this.fileCoreName = fileCoreName;
    }

    public String getFileNameIndexConnector() {
        return fileNameIndexConnector;
    }

    public void setFileNameIndexConnector(String fileNameIndexConnector) {
        this.fileNameIndexConnector = fileNameIndexConnector;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public int getFileIndex() {
        return fileIndex;
    }

    public void setFileIndex(int fileIndex) {
        this.fileIndex = fileIndex;
    }

    public boolean isReady() {
        boolean ready = !MessageHelper.empty(sourceDir);
        ready &= !MessageHelper.empty(destinationDir);
        ready &= !MessageHelper.empty(fileCoreName);
        return ready;
    }

    public String[] toStringArray() {
        int actualSize = 0;
        List<String> params = new ArrayList<>(6);
        if (!MessageHelper.empty(sourceDir)) {
            params.add(source+"="+sourceDir);
            ++actualSize;
        }
        if (!MessageHelper.empty(destinationDir)) {
            params.add(destination+"="+destinationDir);
            ++actualSize;
        }
        if (!MessageHelper.empty(fileExtension)) {
            params.add(extensions+"="+fileExtension);
            ++actualSize;
        }
        if (!MessageHelper.empty(fileCoreName)) {
            params.add(coreName +"="+ fileCoreName);
            ++actualSize;
        }
        if (!MessageHelper.empty(fileNameIndexConnector)) {
            params.add(nameConnector+"="+fileNameIndexConnector);
            ++actualSize;
        }
        return params.toArray(new String[actualSize]);
    }
}
