package pg.picturefilechanger;

/**
 *
 * @author premik
 */
public class ChangeDetails {

    private String sourceDir;
    private String destinationDir;
    private String fileNamePrefix;
    private String fileNameIndexConnector;
    private String fileExtension;
    private int fileIndex;

    public ChangeDetails() {}

    public ChangeDetails(String sourceDir, String destinationDir, String fileNamePrefix, String fileNameIndexConnector) {
        this.sourceDir = sourceDir;
        this.destinationDir = destinationDir;
        this.fileNamePrefix = fileNamePrefix;
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

    public String getFileNamePrefix() {
        return fileNamePrefix;
    }

    public void setFileNamePrefix(String fileNamePrefix) {
        this.fileNamePrefix = fileNamePrefix;
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
}
