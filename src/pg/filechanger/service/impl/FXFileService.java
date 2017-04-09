package pg.filechanger.service.impl;

import pg.exception.ProgramException;
import pg.filechanger.service.AbstractFileService;
import pg.filechanger.dto.ChangeDetails;
import pg.filechanger.validator.impl.FileChangerValidator;
import pg.helper.*;
import pg.logger.AppLogger;

import java.util.*;

/**
 * Created by gawa on 09.04.17.
 */
public class FXFileService extends AbstractFileService {

	private Map<String, Integer> maxExtIdxMap;

	public FXFileService(ResourceBundle bundle, AppLogger logger) {
		super(logger, bundle);
		this.changeDetails = new ChangeDetails();
	}

	public Map<String, Integer> getMaxExtIdxMap() {
		return maxExtIdxMap;
	}

	public void addSourceDir(String sourceDir) {
		changeDetails.setSourceDir(sourceDir);
	}

	public void addDestinationDir(String destinationDir) {
		changeDetails.setDestinationDir(destinationDir);
	}

	public void addFileCoreName(String fileCoreName) {
		changeDetails.setFileCoreName(fileCoreName);
	}

	public void addFileNameIndexConnector(String fileNameIndexConnector) {
		changeDetails.setFileNameIndexConnector(fileNameIndexConnector);
	}

	public void addFileExtension(String fileExtension) {
		changeDetails.setFileExtension(fileExtension);
	}

	public void createMaxIndexMap() {
		maxExtIdxMap = super.createMaxIndexMap(changeDetails);
	}

	@Override
	public void run() throws ProgramException {
		new FileChangerValidator(changeDetails, bundle).validate();
		createDestinationIfNotExists();
		if (maxExtIdxMap == null) {
			createMaxIndexMap(changeDetails);
		}
		processChange(maxExtIdxMap, changeDetails);
	}

	public boolean isUpdateIndexesPossible() {
		return !MessageHelper.empty(changeDetails.getDestinationDir())
				&& !MessageHelper.empty(changeDetails.getFileExtension());
	}

	public String buildMaxIndexesLabelText() {
		StringBuilder builder = new StringBuilder();
		final String eof = "\n";
		builder.append(messageHelper.getFullMessage("log.next.indexes", eof));
		maxExtIdxMap.forEach((key, value) -> builder.append(key).append(" = ").append(value).append(eof));
		return builder.toString();

	}

	public boolean isChangePossible() {
		return changeDetails.isReady();
	}

	public void updateAppConfiguration() throws ProgramException {
		AppConfigHelper.getInstance().updateAppConfiguration(changeDetails);
	}

}
