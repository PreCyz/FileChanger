package pg.filechanger.service;

import java.io.File;
import java.util.*;

import pg.constant.AppConstants;
import pg.exception.*;
import pg.filechanger.dto.ChangeDetails;
import pg.helper.MessageHelper;
import pg.logger.AppLogger;

/**
 * @author Gawa
 */
public abstract class AbstractFileService {

    protected final AppLogger logger;
    protected final ResourceBundle bundle;
    protected final MessageHelper messageHelper;
    protected ChangeDetails changeDetails;

    public AbstractFileService(AppLogger logger, ResourceBundle bundle) {
	    this.logger = logger;
	    this.bundle = bundle;
	    messageHelper = MessageHelper.getInstance(bundle);
    }

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

    protected final Map<String, Integer> createMaxIndexMap(ChangeDetails changeDetails) {
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

	public abstract void run() throws ProgramException;

}
