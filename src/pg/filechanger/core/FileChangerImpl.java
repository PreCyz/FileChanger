package pg.filechanger.core;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import pg.constant.AppConstants;
import pg.exception.ErrorCode;
import pg.exception.ProgramException;
import pg.filechanger.dto.ChangeDetails;
import pg.filechanger.dto.FileChangerParams;
import pg.logger.AppLogger;

/**
 * @author Paweł Gawędzki
 */
public class FileChangerImpl extends AbstractFileChanger {

	public FileChangerImpl(AppLogger logger, ResourceBundle bundle) {
		super(logger, bundle);
	}

	public FileChangerImpl(String[] params, ResourceBundle bundle, AppLogger logger) {
        super(params, bundle, logger);
    }

    public FileChangerImpl(ChangeDetails changeDetails, ResourceBundle bundle, AppLogger logger) {
        super(changeDetails, bundle, logger);
    }

    @Override
    protected ChangeDetails createChangeDetails(String[] parameters) {
        Properties properties = AbstractFileChanger.transformArgumentsToProperties(parameters);
        changeDetails = new ChangeDetails(
                properties.getProperty(FileChangerParams.source.name()),
                properties.getProperty(FileChangerParams.destination.name()),
                properties.getProperty(FileChangerParams.coreName.name()),
                properties.getProperty(FileChangerParams.nameConnector.name()));
        return changeDetails;
    }

    @Override
    protected void createDestinationIfNotExists() throws ProgramException {
        File destFile = new File(changeDetails.getDestinationDir());
        if (!destFile.exists()) {
            boolean destCreated = destFile.mkdir();
            if (destCreated) {
                logger.log(messageHelper.getFullMessage("file.dir.created",
		                changeDetails.getDestinationDir()));
            } else {
                throw new ProgramException(ErrorCode.DESTINATION_NOT_CREATED, changeDetails.getDestinationDir());
            }
        }
    }

    @Override
    public Map<String, Integer> createMaxIndexMap(ChangeDetails changeDetails) {
	    logger.log(messageHelper.getFullMessage("log.fileName.pattern", " ",
			    changeDetails.getFileNameIndexConnector()));
        Map<String, Integer> maxExtIdxMap = new HashMap<>();
        for (String extension : changeDetails.getFileExtension().split(",")) {
            ChangeDetails tempChangeDetails = new ChangeDetails(
                    changeDetails.getDestinationDir(),
                    changeDetails.getFileNameIndexConnector()
            );
            tempChangeDetails.setFileExtension(extension);
            maxExtIdxMap.put(extension, findNextAfterMaxIndex(tempChangeDetails));
        }
        return maxExtIdxMap;
    }

    private int findNextAfterMaxIndex(ChangeDetails changeDetails) {
        File destination = new File(changeDetails.getDestinationDir());
        int max = 0;
        for (File file : destination.listFiles()) {
            if (file.getName().contains(changeDetails.getFileExtension())) {
                int num = extractFileIndex(file, changeDetails);
                if (num > max) {
                    max = num;
                }
            }
        }
        logger.log(messageHelper.getFullMessage("file.maximum.idx", changeDetails.getFileExtension(), max));
        return max + 1;
    }

    private int extractFileIndex(File file, ChangeDetails changeDetails) throws NumberFormatException {
        int beginIdx = file.getName().indexOf(changeDetails.getFileNameIndexConnector()) + 1;
        if (beginIdx <= 0) {
        	return 0;
        }
        int endIdx = file.getName().indexOf(".");
        return Integer.parseInt(file.getName().substring(beginIdx, endIdx));
    }

    @Override
    protected void processChange(Map<String, Integer> idxMap, ChangeDetails changeDetails) {
        File[] dirContent = new File(changeDetails.getSourceDir()).listFiles();
        for (File file : dirContent) {
            if (file.isFile()) {
                processChangeFile(file, idxMap);
            } else if (file.isDirectory()) {
                String dirPath = String.format("%s%s", file.getPath(), AppConstants.FILE_SEPARATOR);
                ChangeDetails dirChangeDetails = new ChangeDetails(
                        dirPath,
                        changeDetails.getDestinationDir(),
                        changeDetails.getFileCoreName(),
                        changeDetails.getFileNameIndexConnector()
                );
                processChange(idxMap, dirChangeDetails);
            }
        }
    }

    private void processChangeFile(File fileToProcess, Map<String, Integer> maxIdxMap) {
        String fileExt = fileToProcess.getName().substring(fileToProcess.getName().indexOf(".") + 1).toLowerCase();
        if (isFileExtensionProcessable(fileExt)) {
            int maxIdx = maxIdxMap.get(fileExt);
            changeDetails.setFileIndex(maxIdx);
            changeDetails.setFileExtension(fileExt);

            changeFileName(fileToProcess);

            maxIdxMap.put(fileExt, ++maxIdx);
        }
    }

    private boolean isFileExtensionProcessable(String fileExt) {
        for (String extension : changeDetails.getFileExtension().split(",")) {
            if (extension.equalsIgnoreCase(fileExt)) {
                return true;
            }
        }
        return false;
    }

    private void changeFileName(File file) {
        String newName = createFileName(changeDetails);
        if (file.renameTo(new File(newName))) {
            logger.log(messageHelper.getFullMessage("file.name.changed", file.getName(), newName));
        }
    }

    private String createFileName(ChangeDetails changeFileDetails) {
        StringBuilder builder = new StringBuilder();
        builder.append(changeFileDetails.getDestinationDir())
                .append(changeFileDetails.getFileCoreName())
                .append(changeFileDetails.getFileNameIndexConnector())
                .append(changeFileDetails.getFileIndex())
                .append(".")
                .append(changeFileDetails.getFileExtension());
        return builder.toString();
    }

}
