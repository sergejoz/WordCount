package ru.pstu.vmm.sample;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
    // главный класс
    public static void main(String[] args) {
        // чтение файла
        try {
            List<String> text = FileController.ReadFile("testfile.txt");
            Map wordFrequency = FileController.WordFrequency(text);
            FileController.printB(wordFrequency);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}
