package pg.picturefilechanger;

import pg.helper.MessageHelper;

/**
 *
 * @author premik
 */
public class ChangeDetails {

    private String sourceDir;
    private String destinationDir;
    private String coreName;
    private String fileNameIndexConnector;
    private String fileExtension;
    private int fileIndex;

    public ChangeDetails() {}

    public ChangeDetails(String sourceDir, String destinationDir, String coreName, String fileNameIndexConnector) {
        this.sourceDir = sourceDir;
        this.destinationDir = destinationDir;
        this.coreName = coreName;
        this.fileNameIndexConnector = fileNameIndexConnector;
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

    public String getCoreName() {
        return coreName;
    }

    public void setCoreName(String coreName) {
        this.coreName = coreName;
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
        boolean ready = !MessageHelper.empty(destinationDir);
        ready &= !MessageHelper.empty(sourceDir);
        ready &= !MessageHelper.empty(coreName);
        return ready;
    }
}
