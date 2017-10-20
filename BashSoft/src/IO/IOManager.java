package IO;

import java.io.File;
import java.util.LinkedList;
import StaticData.SessionData;
import StaticData.ExceptionMessages;

/**
 * Created by teodor donchev on 10/16/2017.
 */
public class IOManager {

    public static void changeCurrentDirRelativePath(String relativePath){
        if(relativePath.equals("..")){
            //go one directory up;

            String currentPath = SessionData.currentPath;
            int indexOfLastSlash = currentPath.lastIndexOf("\\");

            SessionData.currentPath = currentPath.substring(0, indexOfLastSlash);
        }else{
            //go to a given directory

            String currentPath = SessionData.currentPath;
            currentPath += "\\" + relativePath;
            changeCurrentDirAbsolute(currentPath);
        }
    }

    public static void changeCurrentDirAbsolute(String absolutePath) {
        File file = new File(absolutePath);

        if (!file.exists()) {
            OutputWriter.displayException(ExceptionMessages.INVALID_PATH);
            return;
        }

        SessionData.currentPath = absolutePath;
    }

    public static void traverseDirectory(int depth){
        LinkedList<File> subFolders = new LinkedList<>();

        String path = SessionData.currentPath;

        File root = new File(path);
        subFolders.add(root);

        while(subFolders.size() != 0){
            File currentFolder = subFolders.removeFirst();

            int initialIndentation = path.split("\\\\").length;

            if(depth - initialIndentation < 0){
                break;
            }

            OutputWriter.writeMessageOnNewLine(currentFolder.toString());

            if(currentFolder.listFiles() != null){
                try {
                    for (File file : currentFolder.listFiles()) {
                        if (file.isDirectory()) {
                            subFolders.add(file);
                        }else {
                            int indexOfLastSlash = file.toString().lastIndexOf("\\");
                            for (int i = 0; i < indexOfLastSlash; i++) {
                                OutputWriter.writeMessage("-");
                            }
                            OutputWriter.writeMessageOnNewLine(file.getName());
                        }
                    }
                }
                catch (Exception e){
                    System.out.println("Access denied.");
                }
            }
        }
    }

    public static void createDirectoryInCurrentFolder(String name){
        String path = getCurrentDirectoryPath() + "\\" + name;
        File file = new File(path);
        file.mkdir();
    }

    private static String getCurrentDirectoryPath() {
        return SessionData.currentPath;
    }
}
