package classes.lab3.Model;

import classes.lab3.Exceptions.FileReadException;
import classes.lab3.Exceptions.InvalidFileFormatException;

import java.io.*;
import java.util.*;

public class Dictionary {
    private Map<String, String> entries;
    
    public Dictionary() {
        this.entries = new HashMap<>();
    }
    
    public void loadFromFile(String filename) throws FileReadException, InvalidFileFormatException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] parts = line.split("\\|", 2);
                if (parts.length != 2) {
                    throw new InvalidFileFormatException(
                        String.format("Неверный формат в строке %d: %s", lineNumber, line)
                    );
                }
                String key = parts[0].trim().toLowerCase();
                String value = parts[1].trim();
                if (key.isEmpty() || value.isEmpty()) {
                    throw new InvalidFileFormatException(
                        String.format("Пустое слово или перевод в строке %d: %s", lineNumber, line)
                    );
                }
                entries.put(key, value);
            }
        } catch (FileNotFoundException e) {
            throw new FileReadException("Файл не найден: " + filename);
        } catch (IOException e) {
            throw new FileReadException("Ошибка чтения файла: " + e.getMessage());
        }
    }
    
    public String findBestTranslation(String[] words, int startIndex) {
        String longestMatch = null;
        String translation = null;
        int matchLength = 0;
        String punctuation = "";
        for (int j = startIndex; j < words.length; j++) {
            List<String> currentWords = new ArrayList<>();
            for (int k = startIndex; k <= j; k++) {
                currentWords.add(words[k]);
            }
            String phrase = String.join(" ", currentWords).toLowerCase();
            if (entries.containsKey(phrase)) {
                String currentTranslation = entries.get(phrase);
                if (phrase.length() > matchLength) {
                    longestMatch = phrase;
                    translation = currentTranslation;
                    matchLength = phrase.length();
                    punctuation = "";
                }
            }
            String lastWord = currentWords.get(currentWords.size() - 1);
            String cleanLastWord = removePunctuation(lastWord);
            if (!lastWord.equals(cleanLastWord)) {
                currentWords.set(currentWords.size() - 1, cleanLastWord);
                String cleanPhrase = String.join(" ", currentWords).toLowerCase();
                if (entries.containsKey(cleanPhrase)) {
                    String currentTranslation = entries.get(cleanPhrase);
                    String punct = lastWord.substring(cleanLastWord.length());
                    if (cleanPhrase.length() > matchLength) {
                        longestMatch = cleanPhrase;
                        translation = currentTranslation;
                        matchLength = cleanPhrase.length();
                        punctuation = punct;
                    }
                }
            }
        }
        if (longestMatch != null) {
            return translation + punctuation + "|" + longestMatch.split("\\s+").length;
        }
        return null;
    }
    
    private String removePunctuation(String word) {
        if (word == null || word.isEmpty()) {
            return word;
        }
        int i = word.length() - 1;
        while (i >= 0 && isPunctuation(word.charAt(i))) {
            i--;
        }
        return word.substring(0, i + 1);
    }
    
    private boolean isPunctuation(char c) {
        return ",.!?;:()[]{}'\"".indexOf(c) != -1;
    }
    
    public String processSingleWord(String word) {
        String cleanWord = removePunctuation(word.toLowerCase());
        String punctuation = word.substring(cleanWord.length());
        if (entries.containsKey(cleanWord)) {
            return entries.get(cleanWord) + punctuation;
        }
        
        return word;
    }
    
    public int size() {
        return entries.size();
    }
    
    public Map<String, String> getEntries() {
        return Collections.unmodifiableMap(entries);
    }
}