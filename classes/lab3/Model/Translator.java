package classes.lab3.Model;

import classes.lab3.Exceptions.FileReadException;
import classes.lab3.Exceptions.InvalidFileFormatException;

public class Translator {
    private Dictionary dictionary;
    
    public Translator() {
        this.dictionary = new Dictionary();
    }
    
    public void loadDictionary(String filename) throws FileReadException, InvalidFileFormatException {
        dictionary.loadFromFile(filename);
    }
    
    public String translate(String text) {
        if (text == null || text.trim().isEmpty()) {
            return text;
        }
        String[] words = text.split("\\s+");
        StringBuilder result = new StringBuilder();
        int i = 0;
        while (i < words.length) {
            String translationResult = dictionary.findBestTranslation(words, i);
            if (translationResult != null) {
                String[] parts = translationResult.split("\\|", 2);
                String translation = parts[0];
                int wordCount = Integer.parseInt(parts[1]);
                if (result.length() > 0) {
                    result.append(" ");
                }
                result.append(translation);
                i += wordCount;
            } else {
                if (result.length() > 0) {
                    result.append(" ");
                }
                result.append(dictionary.processSingleWord(words[i]));
                i++;
            }
        }
        return result.toString();
    }
    
    public Dictionary getDictionary() {
        return dictionary;
    }
}