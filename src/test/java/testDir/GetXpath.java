package test.java.testDir;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GetXpath{
    private static Map<String, Map<String, String>> xpathAll = new HashMap<>();
    private static File pathInputID = getFileToResourceDirectoryInJar("/xpathFiles/InputID.txt");
    private static File pathButtonID = getFileToResourceDirectoryInJar("/xpathFiles/ButtonID.txt");
    private static File pathButtonNoNameID = getFileToResourceDirectoryInJar("/xpathFiles/ButtonNoNameID.txt");
    private static File pathUtilsID = getFileToResourceDirectoryInJar("/xpathFiles/UtilsID.txt");


    static {
        xpathAll.put(pathInputID.toString(), new HashMap<>());
        xpathAll.put(pathButtonID.toString(), new HashMap<>());
        xpathAll.put(pathButtonNoNameID.toString(), new HashMap<>());
        xpathAll.put(pathUtilsID.toString(), new HashMap<>());
        setXpathToMap();
    }

    public static String getInputID(String name) {
        return xpathAll.get(pathInputID.toString()).get(name);
    }

    public static String getButtonID(String name) {
        return xpathAll.get(pathButtonID.toString()).get(name);
    }

    public static String getButtonNoNameID(String name) {
        return xpathAll.get(pathButtonNoNameID.toString()).get(name);
    }

    public static String getUtilsID(String name) {
        return xpathAll.get(pathUtilsID.toString()).get(name);
    }


    /**
     * Сканирует файлы и добавляет в Map'у найденные теги
     */
    private static void setXpathToMap() {
        for (String path : xpathAll.keySet()) {
            HashMap<String, String> tags = new HashMap<>();
            String myFile = null;
            try {
                myFile = Files.readString(Paths.get(path));
            } catch (IOException e) {
                throw new RuntimeException("Файл по адресу: " + path + " отсутствует");
            }
            try (Scanner sc = new Scanner(myFile)) {
                while (sc.hasNextLine()) {
                    String tag = sc.nextLine();
                    tags.put(tag.replaceAll(" ;.*", ""), tag.replaceAll(".*;", ""));
                }
                xpathAll.put(path, tags);
            }
        }
    }


    @SneakyThrows
    public static File getFileToResourceDirectoryInJar(String fileName) {
        var file = new ClassPathResource(fileName).getInputStream();
        File somethingFile = File.createTempFile(
                fileName.replaceAll("(.*)(\\..*)", "$1"),
                fileName.replaceAll("(.*)(\\..*)", "$2"));
        FileUtils.copyInputStreamToFile(file, somethingFile);
        return somethingFile;
    }


}
