package ru.pstu.vmm.sample;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class FileController {
    // считывает название файлов в лист из директории проекта
    public static List<String> getFiles() {
        // подключение к БД для чтения имен
        DatabaseHandler dbHandler = new DatabaseHandler();
        String pathRoot = System.getProperty("user.dir");
        File dirRoot = new File(pathRoot);
        List<File> fileList = Arrays.asList(dirRoot.listFiles());

        List<String> filesText = new ArrayList<String>();
        Integer index = 0;
        for (File file : fileList) {
            String path = file.getPath();
            // берем только файлы в формете .txt
            if (path.contains(".txt")) {
                filesText.add(path);
                // сохранение в БД имен файлов
                dbHandler.addFileName(file.getName());
                index++;
            }
        }

        return filesText;
    }
}

