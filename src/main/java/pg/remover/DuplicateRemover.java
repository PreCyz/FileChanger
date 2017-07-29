package pg.remover;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author Gawa
 */
public class DuplicateRemover {

    private static final String DUPLICATES = "duplicates" + File.separator;
    
    private final String dirPath;
    private final String destDir;
    private List<File> possibleDuplicates;
    private Map<String, File> noDuplicatesMap;
    private List<File> duplicatesList;

    public DuplicateRemover(String dirPath) {
        this.dirPath = dirPath;
        this.destDir = String.format("%s%s", dirPath, DUPLICATES);
    }
    
    protected List<File> getPossibleDuplicates(){
        return possibleDuplicates;
    }
    protected Map<String, File> getNoDuplicatesMap(){
        return noDuplicatesMap;
    }
    protected List<File> getDuplicatesList() {
        return duplicatesList;
    }
    
    public void processDuplicates() throws NoSuchAlgorithmException, IOException {
        createPossibleDuplicateFileList();
        possibleDuplicates.forEach(file -> {
            System.out.println(String.format("Potencjalny duplikat: %s", file.getName()));
        });
        createDuplicatesList();
        duplicatesList.forEach(file -> {
            System.out.println(String.format("Duplikat: %s", file.getName()));
        });
        if (!duplicatesList.isEmpty()) {
            createDuplicateDirIfNotExists();
            moveDuplicates();
        }
    }
    
    protected void createPossibleDuplicateFileList() {
        List<File> fileList = getFileOnlyList();
        possibleDuplicates = new ArrayList<>();
        noDuplicatesMap = new HashMap<>();
        fileList.stream().forEach((file) -> {
            String fileSize = String.valueOf(file.length()); 
            if (noDuplicatesMap.containsKey(fileSize)) {
                possibleDuplicates.add(file);
            } else {
                noDuplicatesMap.put(fileSize, file);
            }
        });
    }
    
    protected List<File> getFileOnlyList() {
        File dir = new File(dirPath);
        List<File> onlyFileList = Arrays.stream(dir.listFiles())
                .filter(File::isFile)
                .collect(Collectors.toList());
        return onlyFileList;
    }
    
    protected void createDuplicatesList() throws NoSuchAlgorithmException, IOException {
        duplicatesList = new ArrayList<>();
        for (File possibleDuplicate : possibleDuplicates) {
            File notDuplicate = noDuplicatesMap.get(
                    String.valueOf(possibleDuplicate.length())
            );
            if (notDuplicate != null) {
                String posDupHash = getSHAHashForFile(possibleDuplicate);
                String notDupHash = getSHAHashForFile(notDuplicate);
                if (posDupHash.equals(notDupHash)) {
                    duplicatesList.add(possibleDuplicate);
                }
            }
        }
    }
    
    protected String getSHAHashForFile(File file) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        byte[] dataBytes = Files.readAllBytes(file.toPath());
        byte[] mdbytes = md.digest(dataBytes);

        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < mdbytes.length; i++) {
            hexString.append(Integer.toHexString(0xFF & mdbytes[i]));
        }
        return hexString.toString();
    }
    
    public void createDuplicateDirIfNotExists() throws IOException {
        Path dstDir = Paths.get(destDir);
        if (!Files.exists(dstDir)) {
            Files.createDirectory(dstDir);
        }
    }
    
    protected void moveDuplicates() throws IOException {
        List<File> pomList = new ArrayList<>();
        pomList.addAll(duplicatesList);
        pomList.forEach(file -> {
            try {
                Path source = Paths.get(file.getAbsolutePath());
                Path destination = Paths.get(String.format("%s%s", destDir, file.getName()));
                Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);
                System.out.println(
                        String.format("Duplikat %s przeniesiony do %s"
                                , file.getName()
                                , destination.toString()
                        )
                );
                duplicatesList.remove(file);
            } catch (IOException ex) {
                System.out.println("Nie można przenieść pliku." + ex.getMessage());
            }
        });
    }
}