package ru.pstu.vmm.sample;

import java.io.*;
import java.util.*;

public class Main {
    // главный класс
    public static void main(String[] args) {
        // поиск файлов и добавление их в список
        List<String> filesTexts = FileController.getFiles();
        // запуск
        ThreadsStart(filesTexts);
    }

    // подготовка и запуск тредов
    private static void ThreadsStart(List<String> filesTexts) {
        try {
            int lengthText = 0;
            GlobalMap globalMap = new GlobalMap();

            int threadCounts = filesTexts.size();
            ThreadController[] threadController = new ThreadController[threadCounts];
            Thread[] threads = new Thread[threadCounts];

            for (int i = 0; i < threadController.length; i++) {
                threadController[i] = new ThreadController(filesTexts.get(i), globalMap);
                threadController[i].ReadFile();
                lengthText += threadController[i].getLength();
                threads[i] = new Thread(threadController[i]);
            }

            globalMap.LengthAllText(lengthText);

            //старт тредов
            for (Thread thread : threads) {
                thread.start();
            }

            //ждем, пока треды завершатся
            for (Thread thread : threads) {
                thread.join();
            }

            //выводим таблицу
            globalMap.printMap();
            globalMap.SaveResultsToFileWords(filesTexts);

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}
