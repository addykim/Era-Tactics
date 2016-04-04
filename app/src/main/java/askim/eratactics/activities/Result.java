package askim.eratactics.activities;

import android.os.Bundle;
import android.app.Activity;
import android.widget.Button;
import android.widget.TextView;

import askim.eratactics.R;

public class Result extends Activity {

    private TextView text;
    private TextView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        text = (TextView) findViewById(R.id.result_text);
        backButton = (TextView) findViewById(R.id.back_to_splash);
        //TODO grab win status from last activity

        // if win
        text.setText(R.string.postGameCleared);
        // else
        text.setText(R.string.postGameLose);


    }

}
