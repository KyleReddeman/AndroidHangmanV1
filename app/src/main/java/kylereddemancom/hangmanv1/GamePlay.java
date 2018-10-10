package kylereddemancom.hangmanv1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class GamePlay extends AppCompatActivity {
    private Board board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
        board = new Board(getWordFromWordBank());
        updateBoardText();
    }

    public void submitLetter(View view) {
        EditText guessTextField = (EditText) findViewById(R.id.editText);
        String guess = guessTextField.getText().toString();
        if(guess.length() > 0) {
            if(!board.letterGuessed(guess)) {
                board.addLetter(guess);
                updateStatusText(board.contains(guess));
            }
        }
        updateBoardText();
        guessTextField.setText("");
        guessTextField.setHint(R.string.text_EnterGuess);
    }

    private void updateBoardText() {
        TextView boardText = findViewById(R.id.textView);
        boardText.setText(board.toString());
    }

    private void updateStatusText(boolean isCorrect) {
        TextView statusText = findViewById(R.id.textView2);
        statusText.setVisibility(View.VISIBLE);
        if(isCorrect) {
            statusText.setBackgroundResource(R.color.colorCorrect);
            if(board.won()) {
                statusText.setText(R.string.status_won);
            }
            else {
                statusText.setText(R.string.status_correct);
            }
        }
        else {
            statusText.setBackgroundResource(R.color.colorIncorrect);
            if(board.lose()) {
                statusText.setText(R.string.status_lose);
            }
            else {
                statusText.setText(R.string.status_incorrect);
            }
        }

    }

    private String getWordFromWordBank() {
        List<String> wordBank = createWordBank();
        Random random	= new Random();
        return wordBank.get(random.nextInt(wordBank.size())).trim();
    }

    private List<String>	createWordBank() {
        List<String> wordBank = null;
        try {
            InputStream fileInput = getAssets().open("wordBank.txt");
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
