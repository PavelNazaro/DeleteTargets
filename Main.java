import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main {
    public static final String PATH = "C:\\Users\\User\\GoogleDrive\\STprojectsAll";
    public static final String PATH2 = "C:\\Users\\pavel.nazarov\\My Drive\\STprojectsAll";

    public static void main(String[] args) {
        File rootFolder = new File(PATH);
        if (!rootFolder.exists()){
            rootFolder = new File(PATH2);
        }

        if (rootFolder.exists()) {
            walkThroughFiles(rootFolder);
        }
    }

    private static void walkThroughFiles(File rootFolder) {
        boolean flag = false;
        File[] files = rootFolder.listFiles(File::isDirectory);
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            String fileName = file.getName();
            if (fileName.equals("target") ||
                    fileName.startsWith("target(") ||
                    fileName.startsWith("target (")){
                flag = deleteFolderAndSubFolders(file, true);
            }
            if (!flag){
                walkThroughFiles(file);
            }
        }
    }

    private static boolean deleteFolderAndSubFolders(File rootFolder, boolean b) {
        if (b) System.out.println("Delete: " + rootFolder);

        File[] files = rootFolder.listFiles();
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (file.isDirectory())  {
                deleteFolderAndSubFolders(file, false);
            }

            try {
                Files.delete(file.toPath());
            } catch (IOException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }
        }

        try {
            return Files.deleteIfExists(rootFolder.toPath());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
