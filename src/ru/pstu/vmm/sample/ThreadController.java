package ru.pstu.vmm.sample;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

// новый класс для чтения файла и подсчета частоты
public class ThreadController implements Runnable  {
    private String AllText = "";
    private String pathFile;
    private String[] words;
    private String[] ignoresymols = {",", "\\*", "!", "\\?", "–", "-", "%", "\"", "«", "»", ":"};
    private GlobalMap globalMap;
    private ArrayList<String> uniqueWords = new ArrayList<String>();

    public class FileWords{
        public String fileName;
        public String[] words;

        public FileWords(String fileName, String[] words) {
            this.fileName = fileName;
            this.words = words;
        }
    }

    ArrayList<FileWords> FileWordsList = new ArrayList<>();

    public ThreadController(String pathFile, GlobalMap globalMap) {
        this.pathFile = pathFile;
        this.globalMap = globalMap;
    }

    public ThreadController() {
    }

    @Override
    public void run(){
        for (int i = 0; i < words.length; i++) {
            int count = 0;
            //наличие слова в словаре
            if (uniqueWords.contains(words[i]))
                continue;

            //кол-во повторений
            for (int j = 0; j < words.length; j++) {
                if (words[i].equals(words[j]))
                    count++;
            }

            uniqueWords.add(words[i]);
            globalMap.WordFreq(words[i], count);
        }
    }



    public void ReadFile(){
        try{
            String filetext =  Files.readAllLines(Paths.get(pathFile)).toString();
            AllText += filetext;
            textFormating();
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    // возвращает список всех файлов с их словами внутри
    public ArrayList<FileWords> GetFileWordsList()
    {
        return FileWordsList;
    }

    public void textFormating(){
        try{
            for (int i = 0; i < ignoresymols.length; i++){
                AllText = AllText.replaceAll(ignoresymols[i], "");
            }
            AllText = AllText.toLowerCase();
            AllText = AllText.replaceAll("\\.", " ");
            AllText = AllText.replaceAll("[\\s]{2,}", " ");
            AllText = AllText.replaceAll(" +", " ");
            AllText = AllText.trim();
            words = AllText.split(" ");
            FileWordsList.add(new FileWords(pathFile, words));
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    public int getLength(){
        return words.length;
    }
}
