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
    private GameManager gameManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
        gameManager = new GameManager(this);
        updateBoardText();
    }

    public void submitLetter(View view) {
        EditText guessTextField = (EditText) findViewById(R.id.editText);
        String guess = guessTextField.getText().toString();
        gameManager.guess(guess);
        updateBoardText();
        updateStatusText();
        updateInorrectText();
        updateUsedLettersText();
        guessTextField.setText("");
        guessTextField.setHint(R.string.text_EnterGuess);
    }

    private void updateBoardText() {
        TextView boardText = findViewById(R.id.textView);
        boardText.setText(gameManager.getBoard().toString());
    }

    private void updateUsedLettersText() {
        TextView usedLettersText = findViewById(R.id.textView5);
        usedLettersText.setText(gameManager.getBoard().usedLetters());
    }

    private void updateInorrectText() {
        TextView incorrectText = findViewById(R.id.textView4);
        String text = getString(R.string.text_incorrect_guesses);
        incorrectText.setText(text + gameManager.getIncorrectCount() + "/" + GameManager.MAX_INCORRECT);

    }

    private void updateStatusText() {
        TextView statusText = findViewById(R.id.textView2);
        statusText.setVisibility(View.VISIBLE);
        //should replace with switch. eh, maybe later
        if(gameManager.getStatus() == GameManager.STATUS_CORRECT) {
            statusText.setBackgroundResource(R.color.colorCorrect);
            statusText.setText(R.string.status_correct);
        }
        else if(gameManager.getStatus() == GameManager.STATUS_INCORRECT) {
            statusText.setBackgroundResource(R.color.colorIncorrect);
            statusText.setText(R.string.status_incorrect);
        }
        else if(gameManager.getStatus() == GameManager.STATUS_WON) {
            statusText.setBackgroundResource(R.color.colorCorrect);
            statusText.setText(R.string.status_won);
        }
        else if(gameManager.getStatus() == GameManager.STATUS_LOST) {
            statusText.setBackgroundResource(R.color.colorIncorrect);
            statusText.setText(R.string.status_lost);
        }
        else if(gameManager.getStatus() == GameManager.STATUS_REPEAT) {
            statusText.setBackgroundResource(R.color.colorRepeat);
            statusText.setText(R.string.status_repeat);
        }


    }



}
