package kylereddemancom.hangmanv1;

import java.util.*;

public class Board {
    private char[] boardLetters;
    private int length;
    private String word;
    private List<Character> usedLetters;
    private int incorrectCount;
    private static final int MAX_INCORRECT = 8;

    public Board(String word) {
        this.word = word;
        usedLetters = new ArrayList<Character>();
        length = word.length();
        boardLetters = new char[length];
        for (int i = 0; i < length; i++) {
            char temp = word.charAt(i);
            if (temp != ' ') {
                boardLetters[i] = '_';
            } else {
                boardLetters[i] = ' ';
            }
        }
    }

    public Board() {
        this("");
    }

    public String usedLetters() {
        String letters = "";
        if (usedLetters.size() > 0) {
            letters += usedLetters.get(0);
        }
        for (int i = 1; i < usedLetters.size(); i++) {
            letters += "," + usedLetters.get(i);
        }
        return letters;
    }

    public void addLetter(String s) {
        set(s);
        usedLetters.add(0, Character.toLowerCase(s.trim().charAt(0)));
    }

    public boolean letterGuessed(String s) {
        return usedLetters.contains(s.toLowerCase().trim().charAt(0));
    }

    public void set(String s) {
        s = s.trim();
        char c = s.charAt(0);
        c = Character.toLowerCase(c);
        for (int i = 0; i < length; i++) {
            if (c == Character.toLowerCase(word.charAt(i))) {
                boardLetters[i] = word.charAt(i);
            }
        }
    }

    public boolean won() {
        String workingWord = "";
        for (int i = 0; i < length; i++) {
            workingWord += boardLetters[i];
        }
        return word.equalsIgnoreCase(workingWord);
    }

    public boolean lose() {
        return incorrectCount >= MAX_INCORRECT;
    }

    public boolean contains(String s) {
        s = s.trim();
        char c = s.charAt(0);
        c = Character.toLowerCase(c);
        for (int i = 0; i < length; i++) {
            if (c == Character.toLowerCase(word.charAt(i))) {
                return true;
            }
        }
        incorrectCount++;
        return false;
    }

    public String toString() {
        String s = "";
        for (int i = 0; i < length; i++) {
            s += boardLetters[i] + " ";
        }
        return s;
    }


}
