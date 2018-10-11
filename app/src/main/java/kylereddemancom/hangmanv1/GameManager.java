package kylereddemancom.hangmanv1;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameManager {

    private int incorrectCount;
    private Board board;
    private int status;
    public static final int MAX_INCORRECT = 8;
    public static final int STATUS_CORRECT = 0;
    public static final int STATUS_INCORRECT = 1;
    public static final int STATUS_WON = 2;
    public static final int STATUS_LOST = 3;
    public static final int STATUS_REPEAT = 4;

    public GameManager(Context context) {
        board = new Board(getWordFromWordBank(context));
    }

    public void guess(String userGuess) {
        if(!board.letterGuessed(userGuess)) {
            String oldBoard = board.toString();
            board.addLetter(userGuess);
            if(board.toString().equalsIgnoreCase(oldBoard)) {
                incorrectCount++;
                if(incorrectCount >= MAX_INCORRECT) {
                    status = STATUS_LOST;
                }
                else {
                    status = STATUS_INCORRECT;
                }
            }
            else {
                if(board.getWord().equalsIgnoreCase(board.getWorkingWord())) {
                    status = STATUS_WON;
                }
                else {
                    status = STATUS_CORRECT;
                }
            }
        }
        else {
            status = STATUS_REPEAT;
        }
    }

    public int getStatus() {
        return status;
    }

    public int getIncorrectCount() {
        return incorrectCount;
    }

    public Board getBoard() {
        return board;
    }

    private String getWordFromWordBank(Context context) {
        List<String> wordBank = createWordBank(context);
        Random random	= new Random();
        return wordBank.get(random.nextInt(wordBank.size())).trim();
    }

    private List<String> createWordBank(Context context) {
        List<String> wordBank = null;
        try {
            InputStream fileInput = context.getAssets().open("wordBank.txt");
            wordBank =	new ArrayList<String>();
            int size = fileInput.available();
            byte[] buffer = new byte[size];
            fileInput.read(buffer);
            fileInput.close();
            String words = new String(buffer);
            String[] wordArray = words.split("\n");
            wordBank = Arrays.asList(wordArray);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return wordBank;
    }
}
