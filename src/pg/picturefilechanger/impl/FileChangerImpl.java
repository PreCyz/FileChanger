package pg.picturefilechanger.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import pg.picturefilechanger.ChangeDetails;
import pg.picturefilechanger.AbstractFileChanger;

/**
 *
 * @author Paweł Gawędzki
 */
public class FileChangerImpl extends AbstractFileChanger {

    public FileChangerImpl(String[] params) {
        super(params);
    }

    @Override
    protected Map<String, Integer> createMaxIndexMap(Properties properties) {
        Map<String, Integer> maxExtIdxMap = new HashMap<>();
        for (Extentions ext : Extentions.values()) {
            ChangeDetails changeDetails = new ChangeDetails(
                    null,
                    properties.getProperty(Params.destination.name()),
                    null,
                    properties.getProperty(Params.nameConnector.name())
            );
            changeDetails.setFileExtension(ext.name());
            maxExtIdxMap.put(ext.name(), findFileExtensionMaxIndex(changeDetails) + 1);
        }
        return maxExtIdxMap;
    }

    @Override
    protected void processChange(Map<String, Integer> idxMap, ChangeDetails changeDetails) {
        File[] dirContent = new File(changeDetails.getSourceDir()).listFiles();
        for (File file : dirContent) {
            if (file.isFile()) {
                processChangeFile(file, idxMap, changeDetails);
            } else if (file.isDirectory()) {
                String dirPath = String.format("%s%s", file.getPath(), FILE_SEPARATOR);
                ChangeDetails dirChangeDetails = new ChangeDetails(
                        dirPath,
                        changeDetails.getDestinationDir(),
                        changeDetails.getFileNamePrefix(),
                        changeDetails.getFileNameIndexConnector()
                );
                processChange(idxMap, dirChangeDetails);
            }
        }
    }

    private void processChangeFile(File fileToProces, Map<String, Integer> maxIdxMap, ChangeDetails changeDetails) {
        String fileExt = fileToProces.getName().substring(fileToProces.getName().indexOf(".") + 1).toLowerCase();
        if (Extentions.isFileExtensionProcessable(fileExt)) {
            int maxIdx = maxIdxMap.get(fileExt);
            changeDetails.setFileIndex(maxIdx);
            changeDetails.setFileExtension(fileExt);

            changeFileName(fileToProces, changeDetails);

            maxIdxMap.put(fileExt, ++maxIdx);
        }
    }

    private void changeFileName(File file, ChangeDetails changeDetails) {
        String newName = createFileName(changeDetails);
        if (file.renameTo(new File(newName))) {
            System.out.printf("Zmieniono nazwe pliku [%s] na [%s].\n", file.getName(), newName);
        }
    }

    private String createFileName(ChangeDetails changeFileDetails) {
        return changeFileDetails.getDestinationDir() + changeFileDetails.getFileNamePrefix()
                + changeFileDetails.getFileNameIndexConnector() + changeFileDetails.getFileIndex()
                + "." + changeFileDetails.getFileExtension();
    }

    private int findFileExtensionMaxIndex(ChangeDetails changeDetails) {
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
        System.out.printf("Maksymalny indeks dla plików %s=%d.\n", changeDetails.getFileExtension(), max);
        return max;
    }

    private int extractFileIndex(File file, ChangeDetails changeDetails) throws NumberFormatException {
        int beginIdx = file.getName().indexOf(changeDetails.getFileNameIndexConnector()) + 1;
        int endIdx = file.getName().indexOf(".");
        return Integer.parseInt(file.getName().substring(beginIdx, endIdx));
    }

    //example source=sciezka destination=sciezka extentions=ext1,ext2,ext3,ext4...
    @Override
    protected Properties transformArgumentsToProperties(String[] args) {
        Properties properties = new Properties();
        for (String s : args) {
            properties.put(s.substring(0, s.indexOf("=")), s.substring(s.indexOf("=") + 1));
        }
        return properties;
    }

    @Override
    protected void exitOnPropertiesValidationError(Properties properties) {
        for (Params param : Params.values()) {
            if (properties.getProperty(param.name()) == null || "".equals(properties.getProperty(param.name()))) {
                System.err.printf("Zła wartość wymaganego argumentu %s[%s]=[%s].\n", param.name(), param.getMsg(), properties.getProperty(param.name()));
                System.exit(1);
            }
        }
    }

    @Override
    protected void displaySourceInfo(Properties properties) {
        System.out.printf("Zdjęcia wzięte z [%s].\n", properties.getProperty(Params.source.name()));
    }

    @Override
    protected void exitOnEmptyProperties(Properties properties) {
        if (properties.isEmpty()) {
            System.err.println("Nie podałeś wymaganych argumentów.");
            System.exit(0);
        }
    }

    @Override
    protected void displayPropertiesDetails(Properties properties) {
        StringBuilder sb = new StringBuilder(LINE_SEPARATOR);
        properties.entrySet().stream().forEach((entry) -> {
            sb.append(String.format("%s[%s]=%s %s", entry.getKey(), Params.valueOf(entry.getKey() + "").getMsg(), entry.getValue(), LINE_SEPARATOR));
        });
        System.out.printf("Program zostanie wykonany z argumentami%s\n", sb.toString());
    }

    @Override
    protected ChangeDetails createChangeDetails(Properties properties) {
        ChangeDetails changeDetails = new ChangeDetails(
                properties.getProperty(Params.source.name()),
                properties.getProperty(Params.destination.name()),
                properties.getProperty(Params.filePrefix.name()),
                properties.getProperty(Params.nameConnector.name()));
        return changeDetails;
    }

    @Override
    protected void createDestinationIfNotExists(ChangeDetails changeDetails) {
        File destFile = new File(changeDetails.getDestinationDir());
        if (!destFile.exists()) {
            boolean destCreated = destFile.mkdir();
            if (destCreated) {
                System.out.printf("Został utworzony katalog [%s].\n", changeDetails.getDestinationDir());
            } else {
                System.out.printf("Katalog [%s] nie został utworzony !!!\n", changeDetails.getDestinationDir());
            }
        }
    }
}
