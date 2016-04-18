package askim.eratactics.activities;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import askim.eratactics.R;

public class ResultActivity extends Activity {

    private TextView text;
    private ImageView image;
    private TextView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent results = getIntent(); // gets the previously created intent

        text = (TextView) findViewById(R.id.result_header);
        image = (ImageView) findViewById(R.id.result_image);
        backButton = (TextView) findViewById(R.id.back_to_splash);
        boolean win = results.getBooleanExtra("win", false);
        if (win) {
            text.setText(R.string.postGameCleared);
        } else {
            text.setText(R.string.postGameLose);
            image.setImageResource(R.drawable.lose_game);
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //TODO back button go back to splash screen
            finish();

            }
        });
    }
}
