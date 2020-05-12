package ru.pstu.vmm.sample;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

// глобальный список
public class GlobalMap {
    private int lengthText;
    private HashMap<String, Double> AllWordsFrequency;

    public GlobalMap(){
        AllWordsFrequency = new HashMap<String, Double>();
    }

    public void LengthAllText(int lengthText) {
        this.lengthText = lengthText;
    }

    //частота слова
    public void WordFreq(String k, int count) {
        ReentrantLock mutex = new ReentrantLock();

        try{
            mutex.lock();

            String key = k;
            Double wordFreq = ((double) count/(double)lengthText) * 100;

            if (!AllWordsFrequency.containsKey(key)){
                AllWordsFrequency.put(key, wordFreq);
            }
            else{
                double freq = AllWordsFrequency.get(key) + wordFreq;
                AllWordsFrequency.replace(key, freq);
            }
        }
        finally {
            mutex.unlock();
        }
    }

    public void printMap(){
        DatabaseHandler dbHandler = new DatabaseHandler();
        DecimalFormat formating = new DecimalFormat("##.###");
        LinkedHashMap<String, Double> reverseSortedMap = new LinkedHashMap<>();
        AllWordsFrequency.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));

        String leftAlignFormat = "| %-15s | %-5s |%n";
        System.out.format("+-----------------+-------+%n");
        System.out.format("| Word            | Count |%n");
        System.out.format("+-----------------+------+%n");
        reverseSortedMap.forEach((k,v)->System.out.format(leftAlignFormat, k, formating.format(v)));
        // добавляем результаты в БД
        reverseSortedMap.forEach((k,v)->dbHandler.addWordFreq(k, formating.format(v)));
        System.out.format("+-----------------+-------+%n");
    }
}
