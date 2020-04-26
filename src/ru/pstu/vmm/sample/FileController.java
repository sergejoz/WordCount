package ru.pstu.vmm.sample;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class FileController {
    // считает частоту встречи слова
    public static Map WordFrequency(List<String> texts) {
        List<String> words = new ArrayList<>();
        Map<String, Integer> wordFrequency = new HashMap<>();


        for (String text : texts) {

            // удаляем символы, можно дополнить
            String nosymbolstext = text.replaceAll("[\\-\\+\\.\\^:\\?\\=,\\\\]","");
            // делим по словам
            String[] wordArray = nosymbolstext.split(" ");

            // переносим массива слов в лист
            for (int i = 0; i < wordArray.length; i++) {
                String word = wordArray[i];
                words.add(word);
            }

            // подсчет количества встречи слова
            for (String word : words) {
                if (wordFrequency.containsKey(word)) {
                    wordFrequency.put(word, wordFrequency.get(word) + 1);
                } else {
                    wordFrequency.put(word, 1);
                }
            }
        }

        return wordFrequency;
    }

    // вывод массива
    public static void print(Map<String, Integer> map) {
        map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(System.out::println);
    }

    // красивый вывод табличкой
    public static void printB(Map<String, Integer> map) {
        LinkedHashMap<String, Integer> reverseSortedMap = new LinkedHashMap<>();
        map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));

        String leftAlignFormat = "| %-15s | %-4d |%n";
        System.out.format("+-----------------+------+%n");
        System.out.format("| Word            | Count|%n");
        System.out.format("+-----------------+------+%n");
        //reverseSortedMap.entrySet()
        //        .forEach(System.out::println);
        reverseSortedMap.forEach((k,v)->System.out.format(leftAlignFormat, k, v));
        System.out.format("+-----------------+------+%n");
    }

    // возвращает текст
    public static List<String> ReadFile(String FileName) throws IOException {
        return Files.readAllLines(Paths.get(FileName));
    }
}

