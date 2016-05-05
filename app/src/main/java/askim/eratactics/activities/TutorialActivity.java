package askim.eratactics.activities;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

import askim.eratactics.R;

public class TutorialActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String toDisplay = intent.getStringExtra("tutorial");
        toDisplay.toLowerCase();
        if (toDisplay.equals("game"))
            setContentView(R.layout.tutorial_game);
        else if (toDisplay.equals("teams"))
            setContentView(R.layout.tutorial_team);
        else
            throw new NullPointerException();

        Button back = (Button) findViewById(R.id.tutorialBackButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
