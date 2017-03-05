package pg.picturefilechanger.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import pg.constant.ProgramConstants;
import pg.exception.ErrorCode;
import pg.exception.ProgramException;
import pg.picturefilechanger.ChangeDetails;
import pg.picturefilechanger.AbstractFileChanger;

/**
 * @author Paweł Gawędzki
 */
public class FileChangerImpl extends AbstractFileChanger {

    public FileChangerImpl(String[] params, ResourceBundle bundle) {
        super(params, bundle);
    }

    public FileChangerImpl(ChangeDetails changeDetails, ResourceBundle bundle) {
        super(changeDetails, bundle);
    }

    @Override
    protected ChangeDetails createChangeDetails(String[] parameters) {
        Properties properties = AbstractFileChanger.transformArgumentsToProperties(parameters);
        changeDetails = new ChangeDetails(
                properties.getProperty(Params.source.name()),
                properties.getProperty(Params.destination.name()),
                properties.getProperty(Params.filePrefix.name()),
                properties.getProperty(Params.nameConnector.name()));
        return changeDetails;
    }

    @Override
    protected void createDestinationIfNotExists() throws ProgramException {
        File destFile = new File(changeDetails.getDestinationDir());
        if (!destFile.exists()) {
            boolean destCreated = destFile.mkdir();
            if (destCreated) {
                System.out.println(messageHelper.getFullMessage("file.dir.created",
                        changeDetails.getDestinationDir()));
            } else {
                throw new ProgramException(ErrorCode.DESTINATION_NOT_CREATED, changeDetails.getDestinationDir());
            }
        }
    }

    @Override
    protected Map<String, Integer> createMaxIndexMap() {
        Map<String, Integer> maxExtIdxMap = new HashMap<>();
        for (Extensions ext : Extensions.values()) {
            ChangeDetails tempChangeDetails = new ChangeDetails(
                    null,
                    changeDetails.getDestinationDir(),
                    null,
                    changeDetails.getFileNameIndexConnector()
            );
            tempChangeDetails.setFileExtension(ext.name());
            maxExtIdxMap.put(ext.name(), findNextAfterMaxIndex(tempChangeDetails));
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
        System.out.println(messageHelper.getFullMessage("file.maximum.idx",
                changeDetails.getFileExtension(), max));
        return max + 1;
    }

    private int extractFileIndex(File file, ChangeDetails changeDetails) throws NumberFormatException {
        int beginIdx = file.getName().indexOf(changeDetails.getFileNameIndexConnector()) + 1;
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
                String dirPath = String.format("%s%s", file.getPath(), ProgramConstants.FILE_SEPARATOR);
                ChangeDetails dirChangeDetails = new ChangeDetails(
                        dirPath,
                        changeDetails.getDestinationDir(),
                        changeDetails.getCoreName(),
                        changeDetails.getFileNameIndexConnector()
                );
                processChange(idxMap, dirChangeDetails);
            }
        }
    }

    private void processChangeFile(File fileToProces, Map<String, Integer> maxIdxMap) {
        String fileExt = fileToProces.getName().substring(fileToProces.getName().indexOf(".") + 1).toLowerCase();
        if (Extensions.isFileExtensionProcessable(fileExt)) {
            int maxIdx = maxIdxMap.get(fileExt);
            changeDetails.setFileIndex(maxIdx);
            changeDetails.setFileExtension(fileExt);

            changeFileName(fileToProces);

            maxIdxMap.put(fileExt, ++maxIdx);
        }
    }

    private void changeFileName(File file) {
        String newName = createFileName(changeDetails);
        if (file.renameTo(new File(newName))) {
            System.out.println(messageHelper.getFullMessage("file.name.changed", file.getName(), newName));
        }
    }

    private String createFileName(ChangeDetails changeFileDetails) {
        return changeFileDetails.getDestinationDir() + changeFileDetails.getCoreName()
                + changeFileDetails.getFileNameIndexConnector() + changeFileDetails.getFileIndex()
                + "." + changeFileDetails.getFileExtension();
    }

}
